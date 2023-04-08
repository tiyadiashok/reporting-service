package com.jpm.cfs.reportingserver.dto.error;

public enum StatusCode {

    EC001("Invalid request"),
    EC002("No response found");

    private final String description;

    StatusCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
