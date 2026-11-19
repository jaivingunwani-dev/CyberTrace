package model;

public class Investigator extends User {

    private String specialization;

    public Investigator(String userId, String username, String password, String fullName, String specialization) {

        super(userId, username, password, fullName);

        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public String getRole() {
        return "INVESTIGATOR";
    }

    @Override
    public void showDashboard() {

        System.out.println("\n===== INVESTIGATOR DASHBOARD =====");

        System.out.println("1. View Assigned Cases");
        System.out.println("2. Update Case Status");
        System.out.println("3. Manage Suspects");
        System.out.println("4. Add Digital Evidence");
        System.out.println("5. View Investigation Timeline");
        System.out.println("6. Logout");
    }
}
