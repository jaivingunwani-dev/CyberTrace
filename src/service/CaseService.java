
package service;

import model.*;
import java.util.HashMap;

public class CaseService {

    private HashMap<String, Case> cases;
    private int caseCount;

    private AccessControlService accessService;
    private TimelineService timelineService;

    public CaseService(TimelineService timelineService) {
        cases = new HashMap<>();
        caseCount = 100;
        accessService = new AccessControlService();
        this.timelineService = timelineService;
    }

    // Register a new cybercrime complaint
    public Case registerCase(User reporter, String title,
                             String description, String category) {

        if (!accessService.hasPermission(reporter, "REGISTER_COMPLAINT")) {
            System.out.println("Access Denied!");
            return null;
        }

        String caseId = "C" + caseCount;
        caseCount++;

        Case newCase = new Case(
                caseId,
                title,
                description,
                category,
                reporter.getUsername()
        );

        cases.put(caseId, newCase);

        timelineService.addEntry(caseId, "Complaint registered",
                reporter.getUsername());

        System.out.println("Complaint registered successfully!");
        System.out.println("Your Case ID: " + caseId);

        return newCase;
    }

    // Display all cases (Admin)
    public void viewAllCases(User user) {

        if (!accessService.hasPermission(user, "VIEW_ALL_CASES")) {
            System.out.println("Access Denied!");
            return;
        }

        if (cases.isEmpty()) {
            System.out.println("No cases registered yet.");
            return;
        }

        for (Case c : cases.values()) {
            displayCase(c);
        }
    }

    // Display complaints registered by a Reporter
    public void viewMyCases(User reporter) {

        if (!accessService.hasPermission(reporter, "VIEW_MY_COMPLAINTS")) {
            System.out.println("Access Denied!");
            return;
        }

        boolean found = false;

        for (Case c : cases.values()) {

            if (c.getReporterUsername().equals(reporter.getUsername())) {
                displayCase(c);
                found = true;
            }
        }

        if (!found) {
            System.out.println("You have not registered any complaints.");
        }
    }

    // Assign a case to an Investigator (Admin)
    public boolean assignInvestigator(User admin, String caseId,
                                      User investigator) {

        if (!accessService.hasPermission(admin, "ASSIGN_INVESTIGATOR")) {
            System.out.println("Access Denied!");
            return false;
        }

        if (investigator == null ||
                !investigator.getRole().equals("INVESTIGATOR")) {
            System.out.println("Invalid Investigator!");
            return false;
        }

        Case c = cases.get(caseId);

        if (c == null) {
            System.out.println("Case not found!");
            return false;
        }

        c.setInvestigatorUsername(investigator.getUsername());

        timelineService.addEntry(caseId,
                "Assigned to investigator " + investigator.getUsername(),
                admin.getUsername());

        System.out.println("Investigator assigned successfully!");
        return true;
    }

    // Display cases assigned to an Investigator
    public void viewAssignedCases(User investigator) {

        if (!accessService.hasPermission(investigator, "VIEW_ASSIGNED_CASES")) {
            System.out.println("Access Denied!");
            return;
        }

        boolean found = false;

        for (Case c : cases.values()) {

            if (c.getInvestigatorUsername().equals(investigator.getUsername())) {
                displayCase(c);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No cases assigned to you.");
        }
    }

    // Update the status of an assigned case
    public boolean updateCaseStatus(User investigator, String caseId,
                                    String newStatus) {

        if (!accessService.hasPermission(investigator, "UPDATE_CASE_STATUS")) {
            System.out.println("Access Denied!");
            return false;
        }

        Case c = cases.get(caseId);

        if (c == null) {
            System.out.println("Case not found!");
            return false;
        }

        if (!c.getInvestigatorUsername().equals(investigator.getUsername())) {
            System.out.println("This case is not assigned to you!");
            return false;
        }

        if (!newStatus.equalsIgnoreCase("PENDING")
                && !newStatus.equalsIgnoreCase("UNDER INVESTIGATION")
                && !newStatus.equalsIgnoreCase("RESOLVED")
                && !newStatus.equalsIgnoreCase("CLOSED")) {

            System.out.println("Invalid case status!");
            return false;
        }

        String oldStatus = c.getStatus();

        c.setStatus(newStatus.toUpperCase());

        timelineService.addEntry(caseId,
                "Status changed from " + oldStatus + " to "
                        + newStatus.toUpperCase(),
                investigator.getUsername());

        System.out.println("Case status updated successfully!");
        return true;
    }

    // Let a Reporter track the status of his own complaint
    public void trackComplaint(User reporter, String caseId) {

        if (!accessService.hasPermission(reporter, "TRACK_COMPLAINT")) {
            System.out.println("Access Denied!");
            return;
        }

        Case c = cases.get(caseId);

        if (c == null
                || !c.getReporterUsername().equals(reporter.getUsername())) {
            System.out.println("No complaint found with this Case ID.");
            return;
        }

        System.out.println("\n===== COMPLAINT STATUS =====");
        System.out.println("Case ID: " + c.getCaseId());
        System.out.println("Title: " + c.getTitle());
        System.out.println("Status: " + c.getStatus());
        System.out.println("Investigator: " + c.getInvestigatorUsername());
    }

    // Find a case using its ID (used by the other services)
    public Case getCaseById(String caseId) {
        return cases.get(caseId);
    }

    // Counting methods (used by ReportService)
    public int getTotalCases() {
        return cases.size();
    }

    public int countByStatus(String status) {

        int count = 0;

        for (Case c : cases.values()) {
            if (c.getStatus().equals(status)) {
                count++;
            }
        }

        return count;
    }

    public int countUnassigned() {

        int count = 0;

        for (Case c : cases.values()) {
            if (c.getInvestigatorUsername().equals("NOT ASSIGNED")) {
                count++;
            }
        }

        return count;
    }

    // Common method to display case details
    public void displayCase(Case c) {

        System.out.println("\n------------------------------");
        System.out.println("Case ID: " + c.getCaseId());
        System.out.println("Title: " + c.getTitle());
        System.out.println("Description: " + c.getDescription());
        System.out.println("Category: " + c.getCategory());
        System.out.println("Status: " + c.getStatus());
        System.out.println("Reporter: " + c.getReporterUsername());
        System.out.println("Investigator: " + c.getInvestigatorUsername());
        System.out.println("------------------------------");
    }
}
