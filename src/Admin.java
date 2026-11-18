package model;

public class Admin extends User {

    public Admin(String userId, String username,String password, String fullName) {
        super(userId, username, password, fullName);
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }

    @Override
    public void showDashboard() {

        System.out.println("\n===== ADMIN DASHBOARD =====");

        System.out.println("1. Manage Users");
        System.out.println("2. Manage Cases");
        System.out.println("3. Manage Investigators");
        System.out.println("4. Manage Suspects");
        System.out.println("5. Manage Evidence");
        System.out.println("6. Reports and Analytics");
        System.out.println("7. Logout");
    }
}
