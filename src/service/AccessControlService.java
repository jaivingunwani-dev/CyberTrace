package service;

import model.User;

public class AccessControlService {

    public boolean hasPermission(User user, String operation) {

        if (user == null) {
            return false;
        }

        String role = user.getRole();

        if (role.equals("ADMIN")) {

            return true;
        }

        else if (role.equals("INVESTIGATOR")) {

            if (operation.equals("VIEW_ASSIGNED_CASES")
                    || operation.equals("UPDATE_CASE_STATUS")
                    || operation.equals("MANAGE_SUSPECTS")
                    || operation.equals("MANAGE_EVIDENCE")
                    || operation.equals("VIEW_TIMELINE")) {

                return true;
            }
        }

        else if (role.equals("REPORTER")) {

            if (operation.equals("REGISTER_COMPLAINT")
                    || operation.equals("VIEW_MY_COMPLAINTS")
                    || operation.equals("TRACK_COMPLAINT")) {

                return true;
            }
        }

        return false;
    }

    public void checkAccess(User user, String operation) {

        if (hasPermission(user, operation)) {

            System.out.println("Access Granted!");

        } else {

            System.out.println("Access Denied!");
        }
    }
}
