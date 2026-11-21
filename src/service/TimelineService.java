package service;

import model.*;
import java.util.ArrayList;

public class TimelineService {

    private ArrayList<TimelineEntry> entries;
    private int entryCount;

    private AccessControlService accessService;

    // Constructor
    public TimelineService() {

        entries = new ArrayList<>();
        entryCount = 100;

        accessService = new AccessControlService();
    }

    // Called by the other services whenever something happens in a case.
    // No permission check here because it is not triggered by a menu option.
    public void addEntry(String caseId, String action, String performedBy) {

        entryCount++;

        String entryId = "T" + entryCount;

        TimelineEntry entry = new TimelineEntry(
                entryId, caseId, action, performedBy
        );

        entries.add(entry);
    }

    // View the investigation timeline of a case
    public void viewTimeline(User user, String caseId) {

        if (!accessService.hasPermission(user, "VIEW_TIMELINE")) {
            System.out.println("Access Denied!");
            return;
        }

        boolean found = false;

        System.out.println("\n===== TIMELINE FOR CASE " + caseId + " =====");

        for (TimelineEntry entry : entries) {

            if (entry.getCaseId().equalsIgnoreCase(caseId)) {
                entry.displayEntry();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No timeline entries found for this case.");
        }
    }

    // Used by ReportService
    public int getTotalEntries() {
        return entries.size();
    }
}
