package model;

public class Evidence {

    private String evidenceId;
    private String caseId;
    private String evidenceType;
    private String description;
    private String addedBy;

    public Evidence(String evidenceId, String caseId, String evidenceType,
                    String description, String addedBy) {

        this.evidenceId = evidenceId;
        this.caseId = caseId;
        this.evidenceType = evidenceType;
        this.description = description;
        this.addedBy = addedBy;
    }

    public String getEvidenceId() {
        return evidenceId;
    }

    public String getCaseId() {
        return caseId;
    }

    public String getEvidenceType() {
        return evidenceType;
    }

    public String getDescription() {
        return description;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void displayEvidence() {

        System.out.println("----------------------------");
        System.out.println("Evidence ID: " + evidenceId);
        System.out.println("Case ID: " + caseId);
        System.out.println("Type: " + evidenceType);
        System.out.println("Description: " + description);
        System.out.println("Added By: " + addedBy);
    }
}
