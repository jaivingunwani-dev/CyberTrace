
import model.*;
import service.AuthService;
import service.CaseService;
import service.SuspectService;
import service.AccessControlService;
import service.EvidenceService;
import service.TimelineService;
import service.ReportService;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        AuthService authService = new AuthService();
        AccessControlService accessService = new AccessControlService();
        TimelineService timelineService = new TimelineService();
        CaseService caseService = new CaseService(timelineService);
        SuspectService suspectService = new SuspectService(
                caseService, timelineService);
        EvidenceService evidenceService = new EvidenceService(
                caseService, timelineService);
        ReportService reportService = new ReportService(
                caseService, suspectService,
                evidenceService, timelineService);

        while (true) {

            System.out.println("\n======================================");
            System.out.println("             CYBERTRACE");
            System.out.println("   Digital Crime Investigation System");
            System.out.println("======================================");

            System.out.println("1. Login");
            System.out.println("2. Register as Reporter");
            System.out.println("3. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:

                    System.out.print("Enter Username: ");
                    String username = sc.nextLine();

                    System.out.print("Enter Password: ");
                    String password = sc.nextLine();

                    User loggedInUser = authService.login(
                            username, password);

                    if (loggedInUser != null) {

                        System.out.println("\nLogin Successful!");

                        System.out.println("Welcome, "
                                + loggedInUser.getFullName());

                        System.out.println("Role: "
                                + loggedInUser.getRole());

                        // ================= ADMIN DASHBOARD =================

                        if (loggedInUser.getRole().equals("ADMIN")) {

                            boolean adminRunning = true;

                            while (adminRunning) {

                                System.out.println("\n===== ADMIN DASHBOARD =====");
                                System.out.println("1. Manage Users");
                                System.out.println("2. Manage Cases");
                                System.out.println("3. Manage Investigators");
                                System.out.println("4. Manage Suspects");
                                System.out.println("5. Manage Evidence");
                                System.out.println("6. Reports and Analytics");
                                System.out.println("7. Logout");

                                System.out.print("Enter your choice: ");
                                int adminChoice = Integer.parseInt(sc.nextLine());

                                switch (adminChoice) {

                                    // Manage Users
                                    case 1:

                                        authService.displayUsers();
                                        break;

                                    // Manage Cases
                                    case 2: {

                                        boolean caseMenuRunning = true;

                                        while (caseMenuRunning) {

                                            System.out.println("\n===== CASE MANAGEMENT =====");
                                            System.out.println("1. View All Cases");
                                            System.out.println("2. Assign Investigator");
                                            System.out.println("3. Back to Admin Dashboard");

                                            System.out.print("Enter your choice: ");
                                            int caseChoice = Integer.parseInt(sc.nextLine());

                                            switch (caseChoice) {

                                                case 1:

                                                    caseService.viewAllCases(loggedInUser);
                                                    break;

                                                case 2:

                                                    authService.displayInvestigators();

                                                    System.out.print("\nEnter Case ID: ");
                                                    String caseId = sc.nextLine();

                                                    System.out.print("Enter Investigator Username: ");
                                                    String investigatorUsername = sc.nextLine();

                                                    User investigator = authService.getUserByUsername(
                                                            investigatorUsername);

                                                    caseService.assignInvestigator(
                                                            loggedInUser,
                                                            caseId,
                                                            investigator);

                                                    break;

                                                case 3:

                                                    caseMenuRunning = false;
                                                    break;

                                                default:

                                                    System.out.println("Invalid choice!");
                                            }
                                        }

                                        break;
                                    }

                                    // Manage Investigators
                                    case 3: {

                                        boolean investigatorMenuRunning = true;

                                        while (investigatorMenuRunning) {

                                            System.out.println("\n===== INVESTIGATOR MANAGEMENT =====");
                                            System.out.println("1. Register Investigator");
                                            System.out.println("2. View Investigators");
                                            System.out.println("3. Back to Admin Dashboard");

                                            System.out.print("Enter your choice: ");
                                            int investigatorMenuChoice = Integer.parseInt(sc.nextLine());

                                            switch (investigatorMenuChoice) {

                                                case 1:

                                                    System.out.println("\n===== INVESTIGATOR REGISTRATION =====");

                                                    System.out.print("Enter Full Name: ");
                                                    String investigatorName = sc.nextLine();

                                                    System.out.print("Create Username: ");
                                                    String newInvestigatorUsername = sc.nextLine();

                                                    System.out.print("Create Password: ");
                                                    String investigatorPassword = sc.nextLine();

                                                    System.out.print("Enter Specialization: ");
                                                    String specialization = sc.nextLine();

                                                    boolean investigatorRegistered = authService.registerInvestigator(
                                                            newInvestigatorUsername,
                                                            investigatorPassword,
                                                            investigatorName,
                                                            specialization);

                                                    if (investigatorRegistered) {
                                                        System.out.println("\nInvestigator Registered Successfully!");
                                                    } else {
                                                        System.out.println("\nInvestigator Registration Failed!");
                                                        System.out.println("Check username or password requirements.");
                                                    }

                                                    break;

                                                case 2:

                                                    authService.displayInvestigators();
                                                    break;

                                                case 3:

                                                    investigatorMenuRunning = false;
                                                    break;

                                                default:

                                                    System.out.println("Invalid choice!");
                                            }
                                        }

                                        break;
                                    }
                                    case 4: {

                                        boolean suspectMenuRunning = true;

                                        while (suspectMenuRunning) {

                                            System.out.println("\n===== SUSPECT MANAGEMENT =====");
                                            System.out.println("1. Add Suspect");
                                            System.out.println("2. View All Suspects");
                                            System.out.println("3. View Suspects by Case");
                                            System.out.println("4. Update Suspect Status");
                                            System.out.println("5. Back to Admin Dashboard");

                                            System.out.print("Enter your choice: ");
                                            int suspectChoice = Integer.parseInt(sc.nextLine());

                                            switch (suspectChoice) {

                                                case 1:

                                                    System.out.print("Enter Suspect Name: ");
                                                    String suspectName = sc.nextLine();

                                                    System.out.print("Enter Suspect Description: ");
                                                    String suspectDescription = sc.nextLine();

                                                    System.out.print("Enter Related Case ID: ");
                                                    String suspectCaseId = sc.nextLine();

                                                    suspectService.addSuspect(
                                                            loggedInUser,
                                                            suspectName,
                                                            suspectDescription,
                                                            suspectCaseId);

                                                    break;

                                                case 2:

                                                    suspectService.viewAllSuspects(loggedInUser);
                                                    break;

                                                case 3:

                                                    System.out.print("Enter Case ID: ");
                                                    String searchCaseId = sc.nextLine();

                                                    suspectService.viewSuspectsByCase(
                                                            loggedInUser,
                                                            searchCaseId);

                                                    break;

                                                case 4:

                                                    System.out.print("Enter Suspect ID: ");
                                                    String updateSuspectId = sc.nextLine();

                                                    System.out.println("Available Statuses:");
                                                    System.out.println("1. UNDER INVESTIGATION");
                                                    System.out.println("2. CLEARED");
                                                    System.out.println("3. ARRESTED");

                                                    System.out.print("Enter New Status: ");
                                                    String newSuspectStatus = sc.nextLine();

                                                    suspectService.updateSuspectStatus(
                                                            loggedInUser,
                                                            updateSuspectId,
                                                            newSuspectStatus);

                                                    break;

                                                case 5:

                                                    suspectMenuRunning = false;
                                                    break;

                                                default:

                                                    System.out.println("Invalid choice!");
                                            }
                                        }

                                        break;
                                    }

                                    // Manage Evidence
                                    case 5: {

                                        boolean evidenceMenuRunning = true;

                                        while (evidenceMenuRunning) {

                                            System.out.println("\n===== EVIDENCE MANAGEMENT =====");
                                            System.out.println("1. Add Evidence");
                                            System.out.println("2. View All Evidence");
                                            System.out.println("3. View Evidence by Case");
                                            System.out.println("4. Back to Admin Dashboard");

                                            System.out.print("Enter your choice: ");
                                            int evidenceChoice = Integer.parseInt(sc.nextLine());

                                            switch (evidenceChoice) {

                                                case 1:

                                                    System.out.print("Enter Case ID: ");
                                                    String evidenceCaseId = sc.nextLine();

                                                    System.out.print("Enter Evidence Type (Screenshot, Email, Log File...): ");
                                                    String evidenceType = sc.nextLine();

                                                    System.out.print("Enter Evidence Description: ");
                                                    String evidenceDescription = sc.nextLine();

                                                    evidenceService.addEvidence(
                                                            loggedInUser,
                                                            evidenceCaseId,
                                                            evidenceType,
                                                            evidenceDescription);

                                                    break;

                                                case 2:

                                                    evidenceService.viewAllEvidence(loggedInUser);
                                                    break;

                                                case 3:

                                                    System.out.print("Enter Case ID: ");
                                                    String searchEvidenceCaseId = sc.nextLine();

                                                    evidenceService.viewEvidenceByCase(
                                                            loggedInUser,
                                                            searchEvidenceCaseId);

                                                    break;

                                                case 4:

                                                    evidenceMenuRunning = false;
                                                    break;

                                                default:

                                                    System.out.println("Invalid choice!");
                                            }
                                        }

                                        break;
                                    }

                                    // Reports and Analytics
                                    case 6: {

                                        boolean reportMenuRunning = true;

                                        while (reportMenuRunning) {

                                            System.out.println("\n===== REPORTS AND ANALYTICS =====");
                                            System.out.println("1. System Summary Report");
                                            System.out.println("2. Full Case Report");
                                            System.out.println("3. View Case Timeline");
                                            System.out.println("4. Back to Admin Dashboard");

                                            System.out.print("Enter your choice: ");
                                            int reportChoice = Integer.parseInt(sc.nextLine());

                                            switch (reportChoice) {

                                                case 1:

                                                    reportService.generateSystemReport(loggedInUser);
                                                    break;

                                                case 2:

                                                    System.out.print("Enter Case ID: ");
                                                    String reportCaseId = sc.nextLine();

                                                    reportService.generateCaseReport(
                                                            loggedInUser,
                                                            reportCaseId);

                                                    break;

                                                case 3:

                                                    System.out.print("Enter Case ID: ");
                                                    String timelineCaseId = sc.nextLine();

                                                    timelineService.viewTimeline(
                                                            loggedInUser,
                                                            timelineCaseId);

                                                    break;

                                                case 4:

                                                    reportMenuRunning = false;
                                                    break;

                                                default:

                                                    System.out.println("Invalid choice!");
                                            }
                                        }

                                        break;
                                    }

                                    case 7:

                                        System.out.println("Admin logged out successfully.");
                                        adminRunning = false;
                                        break;

                                    default:

                                        System.out.println("Invalid choice!");
                                }
                            }
                        }

                        // ================= REPORTER DASHBOARD =================

                        else if (loggedInUser.getRole().equals("REPORTER")) {

                            boolean reporterRunning = true;

                            while (reporterRunning) {

                                System.out.println("\n===== REPORTER DASHBOARD =====");
                                System.out.println("1. Register New Complaint");
                                System.out.println("2. View My Complaints");
                                System.out.println("3. Track Complaint Status");
                                System.out.println("4. Logout");

                                System.out.print("Enter your choice: ");
                                int reporterChoice = Integer.parseInt(sc.nextLine());

                                switch (reporterChoice) {

                                    case 1: {

                                        System.out.print("Enter complaint title: ");
                                        String title = sc.nextLine();

                                        System.out.print("Enter complaint description: ");
                                        String description = sc.nextLine();

                                        System.out.print("Enter crime category: ");
                                        String category = sc.nextLine();

                                        caseService.registerCase(
                                                loggedInUser,
                                                title,
                                                description,
                                                category);

                                        break;
                                    }

                                    case 2:

                                        caseService.viewMyCases(loggedInUser);
                                        break;

                                    case 3: {

                                        System.out.print("Enter Case ID: ");
                                        String trackCaseId = sc.nextLine();

                                        caseService.trackComplaint(
                                                loggedInUser,
                                                trackCaseId);

                                        break;
                                    }

                                    case 4:

                                        System.out.println("Reporter logged out successfully.");
                                        reporterRunning = false;
                                        break;

                                    default:

                                        System.out.println("Invalid choice!");
                                }
                            }
                        }

                        // ================= INVESTIGATOR DASHBOARD =================

                        else if (loggedInUser.getRole().equals("INVESTIGATOR")) {

                            boolean investigatorRunning = true;

                            while (investigatorRunning) {

                                System.out.println("\n===== INVESTIGATOR DASHBOARD =====");
                                System.out.println("1. View Assigned Cases");
                                System.out.println("2. Update Case Status");
                                System.out.println("3. Manage Suspects");
                                System.out.println("4. Add Digital Evidence");
                                System.out.println("5. View Investigation Timeline");
                                System.out.println("6. Logout");

                                System.out.print("Enter your choice: ");
                                int investigatorChoice = Integer.parseInt(sc.nextLine());

                                switch (investigatorChoice) {

                                    case 1:

                                        caseService.viewAssignedCases(loggedInUser);
                                        break;

                                    case 2: {

                                        System.out.print("Enter Case ID: ");
                                        String caseId = sc.nextLine();

                                        System.out.print("Enter New Status: ");
                                        String newStatus = sc.nextLine();

                                        caseService.updateCaseStatus(
                                                loggedInUser,
                                                caseId,
                                                newStatus);

                                        break;
                                    }

                                    case 3: {

                                        boolean suspectMenuRunning = true;

                                        while (suspectMenuRunning) {

                                            System.out.println("\n===== SUSPECT MANAGEMENT =====");
                                            System.out.println("1. Add Suspect");
                                            System.out.println("2. View All Suspects");
                                            System.out.println("3. View Suspects by Case");
                                            System.out.println("4. Update Suspect Status");
                                            System.out.println("5. Back");

                                            System.out.print("Enter your choice: ");
                                            int suspectChoice = Integer.parseInt(sc.nextLine());

                                            switch (suspectChoice) {

                                                case 1:

                                                    System.out.print("Enter Suspect Name: ");
                                                    String suspectName = sc.nextLine();

                                                    System.out.print("Enter Description: ");
                                                    String suspectDescription = sc.nextLine();

                                                    System.out.print("Enter Case ID: ");
                                                    String suspectCaseId = sc.nextLine();

                                                    suspectService.addSuspect(
                                                            loggedInUser,
                                                            suspectName,
                                                            suspectDescription,
                                                            suspectCaseId);

                                                    break;

                                                case 2:

                                                    suspectService.viewAllSuspects(loggedInUser);
                                                    break;

                                                case 3:

                                                    System.out.print("Enter Case ID: ");
                                                    String searchCaseId = sc.nextLine();

                                                    suspectService.viewSuspectsByCase(
                                                            loggedInUser,
                                                            searchCaseId);

                                                    break;

                                                case 4:

                                                    System.out.print("Enter Suspect ID: ");
                                                    String updateSuspectId = sc.nextLine();

                                                    System.out.print("Enter New Status: ");
                                                    String newSuspectStatus = sc.nextLine();

                                                    suspectService.updateSuspectStatus(
                                                            loggedInUser,
                                                            updateSuspectId,
                                                            newSuspectStatus);

                                                    break;

                                                case 5:

                                                    suspectMenuRunning = false;
                                                    break;

                                                default:

                                                    System.out.println("Invalid choice!");
                                            }
                                        }

                                        break;
                                    }
                                    case 4: {

                                        System.out.print("Enter Case ID: ");
                                        String evidenceCaseId = sc.nextLine();

                                        System.out.print("Enter Evidence Type (Screenshot, Email, Log File...): ");
                                        String evidenceType = sc.nextLine();

                                        System.out.print("Enter Evidence Description: ");
                                        String evidenceDescription = sc.nextLine();

                                        evidenceService.addEvidence(
                                                loggedInUser,
                                                evidenceCaseId,
                                                evidenceType,
                                                evidenceDescription);

                                        break;
                                    }

                                    case 5: {

                                        System.out.print("Enter Case ID: ");
                                        String timelineCaseId = sc.nextLine();

                                        timelineService.viewTimeline(
                                                loggedInUser,
                                                timelineCaseId);

                                        break;
                                    }

                                    case 6:

                                        System.out.println("Investigator logged out successfully.");
                                        investigatorRunning = false;
                                        break;

                                    default:

                                        System.out.println("Invalid choice!");
                                }
                            }
                        }

                    } else {

                        System.out.println("\nInvalid Username or Password!");
                    }

                    break;

                // ================= REPORTER REGISTRATION =================

                case 2:

                    System.out.println("\n===== REPORTER REGISTRATION =====");

                    System.out.print("Enter Full Name: ");
                    String fullName = sc.nextLine();

                    System.out.print("Create Username: ");
                    String newUsername = sc.nextLine();

                    System.out.print("Create Password: ");
                    String newPassword = sc.nextLine();

                    boolean registered = authService.registerReporter(
                            newUsername,
                            newPassword,
                            fullName);

                    if (registered) {
                        System.out.println("\nRegistration Successful!");
                        System.out.println("You can now login.");
                    } else {
                        System.out.println("\nRegistration Failed!");
                        System.out.println(
                                "Username may already exist or input is invalid.");
                    }

                    break;

                // ================= EXIT =================

                case 3:

                    System.out.println("\nThank you for using CYBERTRACE!");
                    sc.close();
                    return;

                default:

                    System.out.println("\nInvalid choice! Try again.");
            }
        }
    }
}
