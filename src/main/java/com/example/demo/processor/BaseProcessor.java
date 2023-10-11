package com.example.demo.processor;

import lombok.Data;

@Data
public class BaseProcessor implements IProcessor {

    protected final StringBuilder errorBuilder;
    protected boolean hasErr = false;
    protected boolean completed = false;

    BaseProcessor() {
        errorBuilder = new StringBuilder();
    }

    @Override
    public void parse(String line) {
        System.out.println(line);
    }

    @Override
    public void parseError(String line) {
        System.out.println(line);
    }

    @Override
    public String getErrMsg() {
        if (hasErr) {
            return errorBuilder.toString();
        }
        return null;
    }
}
