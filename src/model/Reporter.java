package model;

public class Reporter extends User {

    public Reporter(String userId, String username, String password, String fullName) {

        super(userId, username, password, fullName);
    }

    @Override
    public String getRole() {
        return "REPORTER";
    }

    @Override
    public void showDashboard() {

        System.out.println("\n===== REPORTER DASHBOARD =====");

        System.out.println("1. Register New Complaint");
        System.out.println("2. View My Complaints");
        System.out.println("3. Track Complaint Status");
        System.out.println("4. Logout");
    }
}
