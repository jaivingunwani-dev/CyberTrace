package model;

public class Case {

    private String caseId;
    private String title;
    private String description;
    private String category;
    private String status;

    private String reporterUsername;
    private String investigatorUsername;

    public Case(String caseId, String title, String description,
                String category, String reporterUsername) {

        this.caseId = caseId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.reporterUsername = reporterUsername;

        this.status = "PENDING";
        this.investigatorUsername = "NOT ASSIGNED";
    }

    public String getCaseId() {
        return caseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public String getReporterUsername() {
        return reporterUsername;
    }

    public String getInvestigatorUsername() {
        return investigatorUsername;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInvestigatorUsername(String investigatorUsername) {
        this.investigatorUsername = investigatorUsername;
    }
}