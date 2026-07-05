package com.supportfaq.customersupportfaqaiagent.dto;

public class AiModeResponse {

    private String mode;
    private String name;
    private String description;

    public AiModeResponse(String mode, String name, String description) {
        this.mode = mode;
        this.name = name;
        this.description = description;
    }

    public String getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
