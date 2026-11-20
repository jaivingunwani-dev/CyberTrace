package model;

public class Suspect {

    private String suspectId;
    private String name;
    private String description;
    private String caseId;
    private String status;

    public Suspect(String suspectId, String name,
                   String description, String caseId) {

        this.suspectId = suspectId;
        this.name = name;
        this.description = description;
        this.caseId = caseId;
        this.status = "UNDER INVESTIGATION";
    }

    public String getSuspectId() {
        return suspectId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCaseId() {
        return caseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displaySuspect() {

        System.out.println("----------------------------");
        System.out.println("Suspect ID: " + suspectId);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Related Case ID: " + caseId);
        System.out.println("Status: " + status);
    }
}