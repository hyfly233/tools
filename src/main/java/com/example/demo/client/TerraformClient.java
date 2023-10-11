package com.example.demo.client;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

//@Component
public class TerraformClient implements AutoCloseable {
    private static final String TERRAFORM_EXE_NAME = "terraform";
    //show 显示state文件
    //plan 显示远端与模版定义的变化
    private static final String VERSION_COMMAND = "version",
            INIT_COMMAND = "init",
            PLAN_COMMAND = "plan",
            PLAN_JSON_2_FILE_COMMAND = "plan_json",
            APPLY_COMMAND = "apply",
            DESTROY_COMMAND = "destroy",
            SHOW_COMMAND = "show",
            SHOW_FILE_PLAN_COMMAND = "show_plan";

    private static final String TOKEN = "token";

    private static final Map<String, String> TERRAFORM_COMMAND = new HashMap<>();

    private static final Map<String, String[]> NON_INTERACTIVE_COMMAND_MAP = new HashMap<>();

    static {
        TERRAFORM_COMMAND.put(VERSION_COMMAND, "version");
        TERRAFORM_COMMAND.put(INIT_COMMAND, "init");
        TERRAFORM_COMMAND.put(PLAN_COMMAND, "plan");
        TERRAFORM_COMMAND.put(PLAN_JSON_2_FILE_COMMAND, "plan");
        TERRAFORM_COMMAND.put(APPLY_COMMAND, "apply");
        TERRAFORM_COMMAND.put(DESTROY_COMMAND, "destroy");
        TERRAFORM_COMMAND.put(SHOW_COMMAND, "show");
        TERRAFORM_COMMAND.put(SHOW_FILE_PLAN_COMMAND, "show");
    }

    static {
        NON_INTERACTIVE_COMMAND_MAP.put(APPLY_COMMAND, new String[]{"-auto-approve", "-no-color", "-json"});
        NON_INTERACTIVE_COMMAND_MAP.put(INIT_COMMAND, new String[]{"-no-color"});
        NON_INTERACTIVE_COMMAND_MAP.put(PLAN_COMMAND, new String[]{"-no-color", "-json"});
//        NON_INTERACTIVE_COMMAND_MAP.put(PLAN_JSON_2_FILE_COMMAND, new String[]{"-no-color", "-out=./" + TfFileName.PLAN_FILE_NAME, "-json", "-var-file=./" + TfFileName.VALUES_FILE_NAME});
        NON_INTERACTIVE_COMMAND_MAP.put(PLAN_JSON_2_FILE_COMMAND, new String[]{"-no-color", "-out=./tfplan", "-json", "-var-file=./values.tfvars.json"});
        NON_INTERACTIVE_COMMAND_MAP.put(DESTROY_COMMAND, new String[]{"-auto-approve", "-no-color", "-json"});
        NON_INTERACTIVE_COMMAND_MAP.put(SHOW_COMMAND, new String[]{"-json", "-no-color"});
        NON_INTERACTIVE_COMMAND_MAP.put(SHOW_FILE_PLAN_COMMAND, new String[]{"-json", "-no-color", "./tfplan"});
    }

    private final ExecutorService executor = Executors.newWorkStealingPool();
    private final TerraformOptions options;
    private File workingDirectory;
    private boolean inheritIO;
    private Consumer<String> outputListener, errorListener;
    private Runnable startTask, endTask;

    public TerraformClient() {
        this(new TerraformOptions());
    }

    public TerraformClient(TerraformOptions options) {
        assert options != null;
        this.options = options;
    }

    public Runnable getStartTask() {
        return this.startTask;
    }

    public void setStartTask(Runnable startTask) {
        this.startTask = startTask;
    }

    public Runnable getEndTask() {
        return this.endTask;
    }

    public void setEndTask(Runnable endTask) {
        this.endTask = endTask;
    }

    public Consumer<String> getOutputListener() {
        return this.outputListener;
    }

    public void setOutputListener(Consumer<String> listener) {
        this.outputListener = listener;
    }

    public Consumer<String> getErrorListener() {
        return this.errorListener;
    }

    public void setErrorListener(Consumer<String> listener) {
        this.errorListener = listener;
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public void setWorkingDirectory(Path folderPath) {
        this.setWorkingDirectory(folderPath.toFile());
    }

    public boolean isInheritIO() {
        return this.inheritIO;
    }

    public void setInheritIO(boolean inheritIO) {
        this.inheritIO = inheritIO;
    }

    public CompletableFuture<String> version() throws IOException {
        ProcessLauncher launcher = this.getTerraformLauncher(VERSION_COMMAND);
        StringBuilder version = new StringBuilder();
        Consumer<String> outputListener = this.getOutputListener();
        launcher.setOutputListener(m -> {
            version.append(version.length() == 0 ? m : "");
            if (outputListener != null) {
                outputListener.accept(m);
            }
        });
        return launcher.launch().thenApply((c) -> c == 0 ? version.toString() : null);
    }

    public CompletableFuture<Boolean> init() throws IOException {
        this.checkRunningParameters();
        return this.run(INIT_COMMAND);
    }

    public CompletableFuture<Boolean> plan() throws IOException {
        this.checkRunningParameters();
//        return this.run(INIT_COMMAND, PLAN_COMMAND);
        return this.run(PLAN_COMMAND);
    }

    public CompletableFuture<Boolean> apply() throws IOException {
        this.checkRunningParameters();
//        return this.run(INIT_COMMAND, APPLY_COMMAND);
        return this.run(APPLY_COMMAND);
    }

    public CompletableFuture<Boolean> destroy() throws IOException {
        this.checkRunningParameters();
//        return this.run(INIT_COMMAND, DESTROY_COMMAND);
        return this.run(DESTROY_COMMAND);
    }

    public CompletableFuture<Boolean> planJson() throws IOException {
        this.checkRunningParameters();
        return this.run(INIT_COMMAND, PLAN_JSON_2_FILE_COMMAND, SHOW_FILE_PLAN_COMMAND);
//        return this.run(PLAN_JSON_2_FILE_COMMAND);
    }

    private CompletableFuture<Boolean> run(String... commands) throws IOException {
        assert commands.length > 0;
        ProcessLauncher[] launchers = new ProcessLauncher[commands.length];
        for (int i = 0; i < commands.length; i++) {
            launchers[i] = this.getTerraformLauncher(commands[i]);
        }

        CompletableFuture<Integer> result = launchers[0].launch().thenApply(c -> c == 0 ? 1 : -1);
        for (int i = 1; i < commands.length; i++) {
            result = result.thenCompose(index -> {
                if (index > 0) {
                    System.out.println("index: " + index + " commands[index]: " + TERRAFORM_COMMAND.get(commands[index]));
                    return launchers[index].launch().thenApply(c -> c == 0 ? index + 1 : -1);
                }
                return CompletableFuture.completedFuture(-1);
            });
        }
        return result.thenApply(i -> i > 0);
    }

    private void checkRunningParameters() {
        if (this.getWorkingDirectory() == null) {
            throw new IllegalArgumentException("working directory should not be null");
        }
    }

    private ProcessLauncher getTerraformLauncher(String command) throws IOException {
        ProcessLauncher launcher = new ProcessLauncher(this.executor, TERRAFORM_EXE_NAME, TERRAFORM_COMMAND.get(command));
        launcher.setDirectory(this.getWorkingDirectory());
        launcher.setInheritIO(this.isInheritIO());
        launcher.setEnvironmentVariable(TOKEN, this.options.getToken());
        launcher.appendCommands(NON_INTERACTIVE_COMMAND_MAP.get(command));
        launcher.setOutputListener(this.getOutputListener());
        launcher.setErrorListener(this.getErrorListener());
        launcher.setStartTask(this.getStartTask());
        launcher.setEndTask(this.getEndTask());
        return launcher;
    }

    @Override
    public void close() throws Exception {
        this.executor.shutdownNow();
        if (!this.executor.awaitTermination(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("executor did not terminate");
        }
    }
}
