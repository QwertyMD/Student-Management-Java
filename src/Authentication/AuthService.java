package Authentication;

import Utils.DBConnection;

import java.sql.Connection;

public class AuthService implements AuthInterface {
    private UserInterface userInterface;

    public AuthService(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public boolean authenticate(String username, String password) {
        try (Connection conn = DBConnection.getDbConnection();) {
            User user = userInterface.findByUsername(username);
            if (user == null) {
                return false; // User not found
            }
            return user.password.equals(password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        try (Connection conn = DBConnection.getDbConnection();) {
            if (oldPassword.equals(newPassword)) {
                return false;
            }

            return userInterface.updatePassword(userId, newPassword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
