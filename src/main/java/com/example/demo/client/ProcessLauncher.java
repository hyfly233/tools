package com.example.demo.client;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class ProcessLauncher {
    private Process process;
    private final ProcessBuilder builder;
    private Consumer<String> outputListener, errorListener;
    private boolean inheritIO;
    private final ExecutorService executor;
    private Runnable startTask, endTask;

    ProcessLauncher(ExecutorService executor, String... commands) {
        assert executor != null;
        this.executor = executor;
        this.process = null;
        this.builder = new ProcessBuilder(commands);
    }

    void setOutputListener(Consumer<String> listener) {
        assert this.process == null;
        this.outputListener = listener;
    }

    void setErrorListener(Consumer<String> listener) {
        assert this.process == null;
        this.errorListener = listener;
    }

    void setInheritIO(boolean inheritIO) {
        assert this.process == null;
        this.inheritIO = inheritIO;
    }

    void setDirectory(File directory) {
        assert this.process == null;
        this.builder.directory(directory);
    }

    void setStartTask(Runnable startTask) {
        this.startTask = startTask;
    }

    void setEndTask(Runnable endTask) {
        this.endTask = endTask;
    }

    void appendCommands(String... commands) {
        if (commands != null) {
            Stream<String> filteredCommands = Arrays.stream(commands).filter(c -> c != null && c.length() > 0);
            List<String> commandList = filteredCommands.collect(Collectors.toList());
            this.builder.command().addAll(commandList);
        }
    }

    void setEnvironmentVariable(String name, String value) {
        assert name != null && name.length() > 0;
        Map<String, String> env = this.builder.environment();
        value = (value != null ? env.put(name, value) : env.remove(name));
    }

    void setOrAppendEnvironmentVariable(String name, String value, String delimiter) {
        assert name != null && name.length() > 0;
        if (value != null && value.length() > 0) {
            String current = System.getenv(name);
            String target = (current == null || current.length() == 0 ? value : String.join(delimiter, current, value));
            this.setEnvironmentVariable(name, target);
        }
    }

    CompletableFuture<Integer> launch() {
        assert this.process == null;
        if (this.inheritIO) {
            this.builder.inheritIO();
        }
        try {
            this.process = this.builder.start();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        if (!this.inheritIO) {
            if (this.outputListener != null) {
                this.startTask.run();
                this.executor.submit(() -> this.readProcessStream(this.process.getInputStream(), this.outputListener));
                this.endTask.run();
            }
            if (this.errorListener != null) {
                this.startTask.run();
                this.executor.submit(() -> this.readProcessStream(this.process.getErrorStream(), this.errorListener));
                this.endTask.run();
            }
        }
        return CompletableFuture.supplyAsync(() -> {
            try {
                return this.process.waitFor();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }, this.executor);
    }

    int launchSync() throws InterruptedException {
        assert this.process == null;
        if (this.inheritIO) {
            this.builder.inheritIO();
        }
        try {
            this.process = this.builder.start();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        if (!this.inheritIO) {
            if (this.outputListener != null) {
                this.startTask.run();
                this.executor.submit(() -> this.readProcessStream(this.process.getInputStream(), this.outputListener));
                this.endTask.run();
            }
            if (this.errorListener != null) {
                this.startTask.run();
                this.executor.submit(() -> this.readProcessStream(this.process.getErrorStream(), this.errorListener));
                this.endTask.run();
            }
        }
        return this.process.waitFor();
    }

    private boolean readProcessStream(InputStream stream, Consumer<String> listener) {
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    listener.accept(line);
                }
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}
