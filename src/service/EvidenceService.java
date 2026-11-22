package service;

import model.*;
import java.util.HashMap;

public class EvidenceService {

    private HashMap<String, Evidence> evidenceList;
    private int evidenceCount;

    private AccessControlService accessService;
    private CaseService caseService;
    private TimelineService timelineService;

    // Constructor
    public EvidenceService(CaseService caseService,
                           TimelineService timelineService) {

        evidenceList = new HashMap<>();
        evidenceCount = 100;

        accessService = new AccessControlService();
        this.caseService = caseService;
        this.timelineService = timelineService;
    }

    // Add digital evidence to a case
    public void addEvidence(User user, String caseId,
                            String evidenceType, String description) {

        if (!accessService.hasPermission(user, "MANAGE_EVIDENCE")) {
            System.out.println("Access Denied!");
            return;
        }

        if (evidenceType == null || evidenceType.trim().isEmpty()
                || description == null || description.trim().isEmpty()) {
            System.out.println("Please enter valid evidence details.");
            return;
        }

        Case c = caseService.getCaseById(caseId);

        if (c == null) {
            System.out.println("Case not found!");
            return;
        }

        // An investigator can only add evidence to his own cases
        if (user.getRole().equals("INVESTIGATOR")
                && !c.getInvestigatorUsername().equals(user.getUsername())) {
            System.out.println("This case is not assigned to you!");
            return;
        }

        evidenceCount++;

        String evidenceId = "E" + evidenceCount;

        Evidence evidence = new Evidence(
                evidenceId, caseId, evidenceType,
                description, user.getUsername()
        );

        evidenceList.put(evidenceId, evidence);

        timelineService.addEntry(
                caseId,
                "Evidence " + evidenceId + " (" + evidenceType + ") added",
                user.getUsername()
        );

        System.out.println("Evidence added successfully!");
        System.out.println("Evidence ID: " + evidenceId);
    }

    // View all evidence
    public void viewAllEvidence(User user) {

        if (!accessService.hasPermission(user, "MANAGE_EVIDENCE")) {
            System.out.println("Access Denied!");
            return;
        }

        if (evidenceList.isEmpty()) {
            System.out.println("No evidence added yet.");
            return;
        }

        System.out.println("\n===== ALL EVIDENCE =====");

        for (Evidence evidence : evidenceList.values()) {
            evidence.displayEvidence();
        }
    }

    // View evidence of a particular case
    public void viewEvidenceByCase(User user, String caseId) {

        if (!accessService.hasPermission(user, "MANAGE_EVIDENCE")) {
            System.out.println("Access Denied!");
            return;
        }

        boolean found = false;

        System.out.println("\n===== EVIDENCE FOR CASE " + caseId + " =====");

        for (Evidence evidence : evidenceList.values()) {

            if (evidence.getCaseId().equalsIgnoreCase(caseId)) {
                evidence.displayEvidence();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No evidence found for this case.");
        }
    }

    // Used by ReportService
    public int getTotalEvidence() {
        return evidenceList.size();
    }
}
