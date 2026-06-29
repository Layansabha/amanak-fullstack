// Report.java
package com.example.amanakk;

import java.util.List;

public class Report {
    private String type;
    private String details;
    private List<String> attachments;

    public Report(String type, String details, List<String> attachments) {
        this.type = type;
        this.details = details;
        this.attachments = attachments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
}
