package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimelineEntry {

    private String entryId;
    private String caseId;
    private String action;
    private String performedBy;
    private String timestamp;

    public TimelineEntry(String entryId, String caseId,
                         String action, String performedBy) {

        this.entryId = entryId;
        this.caseId = caseId;
        this.action = action;
        this.performedBy = performedBy;

        // Time is recorded automatically when the entry is created
        this.timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public String getEntryId() {
        return entryId;
    }

    public String getCaseId() {
        return caseId;
    }

    public String getAction() {
        return action;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void displayEntry() {

        System.out.println("[" + timestamp + "] " + action
                + " (by " + performedBy + ")");
    }
}
