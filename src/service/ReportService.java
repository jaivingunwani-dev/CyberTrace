package service;

import model.*;

public class ReportService {

    private AccessControlService accessService;

    private CaseService caseService;
    private SuspectService suspectService;
    private EvidenceService evidenceService;
    private TimelineService timelineService;

    // Constructor
    public ReportService(CaseService caseService,
                         SuspectService suspectService,
                         EvidenceService evidenceService,
                         TimelineService timelineService) {

        accessService = new AccessControlService();

        this.caseService = caseService;
        this.suspectService = suspectService;
        this.evidenceService = evidenceService;
        this.timelineService = timelineService;
    }

    // Overall summary of the system (Admin)
    public void generateSystemReport(User user) {

        if (!accessService.hasPermission(user, "VIEW_REPORTS")) {
            System.out.println("Access Denied!");
            return;
        }

        System.out.println("\n===== SYSTEM REPORT =====");

        System.out.println("\n--- Cases ---");
        System.out.println("Total Cases: " + caseService.getTotalCases());
        System.out.println("Pending: "
                + caseService.countByStatus("PENDING"));
        System.out.println("Under Investigation: "
                + caseService.countByStatus("UNDER INVESTIGATION"));
        System.out.println("Resolved: "
                + caseService.countByStatus("RESOLVED"));
        System.out.println("Closed: "
                + caseService.countByStatus("CLOSED"));
        System.out.println("Not Assigned: "
                + caseService.countUnassigned());

        System.out.println("\n--- Suspects ---");
        System.out.println("Total Suspects: "
                + suspectService.getTotalSuspects());
        System.out.println("Under Investigation: "
                + suspectService.countByStatus("UNDER INVESTIGATION"));
        System.out.println("Cleared: "
                + suspectService.countByStatus("CLEARED"));
        System.out.println("Arrested: "
                + suspectService.countByStatus("ARRESTED"));

        System.out.println("\n--- Evidence and Activity ---");
        System.out.println("Total Evidence: "
                + evidenceService.getTotalEvidence());
        System.out.println("Total Timeline Entries: "
                + timelineService.getTotalEntries());
    }

    // Full report of a single case (Admin)
    public void generateCaseReport(User user, String caseId) {

        if (!accessService.hasPermission(user, "VIEW_REPORTS")) {
            System.out.println("Access Denied!");
            return;
        }

        Case c = caseService.getCaseById(caseId);

        if (c == null) {
            System.out.println("Case not found!");
            return;
        }

        System.out.println("\n===== CASE REPORT: " + caseId + " =====");

        caseService.displayCase(c);

        suspectService.viewSuspectsByCase(user, caseId);
        evidenceService.viewEvidenceByCase(user, caseId);
        timelineService.viewTimeline(user, caseId);
    }
}
