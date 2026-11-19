package service;

import model.*;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private HashMap<String, User> users;

    private int reporterCount;
    private int investigatorCount;

    public AuthService() {

        users = new HashMap<>();

        reporterCount = 0;
        investigatorCount = 0;

        User admin = new Admin(
                "A101",
                "admin",
                "Admin@123",
                "System Admin"
        );

        users.put("admin", admin);
    }

    public boolean registerReporter(String username,
                                    String password,
                                    String fullName) {

        if (username == null || username.trim().isEmpty()
                || fullName == null || fullName.trim().isEmpty()
                || password == null || password.length() < 8) {
            return false;
        }

        if (users.containsKey(username)) {
            return false;
        }

        reporterCount++;

        String userId = "R" + (100 + reporterCount);

        User reporter = new Reporter(
                userId,
                username,
                password,
                fullName
        );

        users.put(username, reporter);

        return true;
    }

    public boolean registerInvestigator(String username,
                                        String password,
                                        String fullName,
                                        String specialization) {

        if (username == null || username.trim().isEmpty()
                || fullName == null || fullName.trim().isEmpty()
                || specialization == null
                || specialization.trim().isEmpty()
                || password == null || password.length() < 8) {
            return false;
        }

        if (users.containsKey(username)) {
            return false;
        }

        investigatorCount++;

        String userId = "I" + (100 + investigatorCount);

        User investigator = new Investigator(
                userId,
                username,
                password,
                fullName,
                specialization
        );

        users.put(username, investigator);

        return true;
    }

    public User login(String username, String password) {

        if (username == null || password == null) {
            return null;
        }

        User user = users.get(username);

        if (user == null) {
            return null;
        }

        if (user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    public void displayUsers() {

        System.out.println("\n===== REGISTERED USERS =====");

        for (Map.Entry<String, User> entry : users.entrySet()) {

            User user = entry.getValue();

            System.out.println(
                    user.getUserId() + " | "
                    + user.getUsername() + " | "
                    + user.getRole() + " | "
                    + user.getFullName()
            );
        }
    }

public User getUserByUsername(String username) {
    return users.get(username);
}

public void displayInvestigators() {

    boolean found = false;

    System.out.println("\n===== REGISTERED INVESTIGATORS =====");

    for (User user : users.values()) {

        if (user.getRole().equals("INVESTIGATOR")) {

            System.out.println("--------------------------");
            System.out.println("Name: " + user.getFullName());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Specialization: "
                    + ((Investigator) user).getSpecialization());

            found = true;
        }
    }

    if (!found) {
        System.out.println("No investigators registered yet.");
    }
}
}
