package service;

import model.*;
import java.util.HashMap;

public class SuspectService {

    private HashMap<String, Suspect> suspects;
    private int suspectCount;

    private AccessControlService accessService;
    private CaseService caseService;
    private TimelineService timelineService;

    // Constructor
    public SuspectService(CaseService caseService,
                          TimelineService timelineService) {

        suspects = new HashMap<>();
        suspectCount = 100;

        accessService = new AccessControlService();
        this.caseService = caseService;
        this.timelineService = timelineService;
    }

    // Add a new suspect
    public void addSuspect(User user, String name,
                           String description, String caseId) {

        if (!accessService.hasPermission(user, "MANAGE_SUSPECTS")) {
            System.out.println("Access Denied!");
            return;
        }

        if (name == null || name.trim().isEmpty()
                || description == null || description.trim().isEmpty()) {
            System.out.println("Please enter valid suspect details.");
            return;
        }

        if (caseService.getCaseById(caseId) == null) {
            System.out.println("Case not found!");
            return;
        }

        suspectCount++;

        String suspectId = "S" + suspectCount;

        Suspect suspect = new Suspect(
                suspectId, name, description, caseId
        );

        suspects.put(suspectId, suspect);

        timelineService.addEntry(caseId,
                "Suspect " + suspectId + " (" + name + ") added",
                user.getUsername());

        System.out.println("Suspect added successfully!");
        System.out.println("Suspect ID: " + suspectId);
    }

    // View all suspects
    public void viewAllSuspects(User user) {

        if (!accessService.hasPermission(user, "MANAGE_SUSPECTS")) {
            System.out.println("Access Denied!");
            return;
        }

        if (suspects.isEmpty()) {
            System.out.println("No suspects registered yet.");
            return;
        }

        System.out.println("\n===== ALL SUSPECTS =====");

        for (Suspect suspect : suspects.values()) {
            suspect.displaySuspect();
        }
    }

    // View suspects related to a particular case
    public void viewSuspectsByCase(User user, String caseId) {

        if (!accessService.hasPermission(user, "MANAGE_SUSPECTS")) {
            System.out.println("Access Denied!");
            return;
        }

        boolean found = false;

        System.out.println("\n===== SUSPECTS FOR CASE " + caseId + " =====");

        for (Suspect suspect : suspects.values()) {

            if (suspect.getCaseId().equalsIgnoreCase(caseId)) {
                suspect.displaySuspect();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No suspects found for this case.");
        }
    }

    // Update suspect status
    public void updateSuspectStatus(User user,
                                    String suspectId,
                                    String newStatus) {

        if (!accessService.hasPermission(user, "MANAGE_SUSPECTS")) {
            System.out.println("Access Denied!");
            return;
        }

        Suspect suspect = suspects.get(suspectId);

        if (suspect == null) {
            System.out.println("Suspect not found.");
            return;
        }

        if (!newStatus.equalsIgnoreCase("UNDER INVESTIGATION")
                && !newStatus.equalsIgnoreCase("CLEARED")
                && !newStatus.equalsIgnoreCase("ARRESTED")) {

            System.out.println("Invalid status.");
            return;
        }

        suspect.setStatus(newStatus.toUpperCase());

        timelineService.addEntry(suspect.getCaseId(),
                "Suspect " + suspectId + " status changed to "
                        + newStatus.toUpperCase(),
                user.getUsername());

        System.out.println("Suspect status updated successfully!");
    }

    // Counting methods (used by ReportService)
    public int getTotalSuspects() {
        return suspects.size();
    }

    public int countByStatus(String status) {

        int count = 0;

        for (Suspect suspect : suspects.values()) {
            if (suspect.getStatus().equals(status)) {
                count++;
            }
        }

        return count;
    }
}