package Authentication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Utils.DBConnection;

public class UserDAO implements UserInterface {
    public User findByUsername(String username) {
        try (Connection conn = DBConnection.getDbConnection();) {
            String query = "SELECT * FROM users where username = ?";
            PreparedStatement prepStmt = conn.prepareStatement(query);
            prepStmt.setString(1, username);
            ResultSet result = prepStmt.executeQuery();
            if (result.next()) {
                User user = new User();
                user.id = result.getInt("id");
                user.username = result.getString("username");
                user.password = result.getString("password");
                return user;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveUser(User user) {
        try (Connection conn = DBConnection.getDbConnection();) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement prepStmt = conn.prepareStatement(query);
            prepStmt.setString(1, user.username);
            prepStmt.setString(2, user.password);

            int rowsAffected = prepStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updatePassword(int userId, String newPassword) {
        try (Connection conn = DBConnection.getDbConnection();) {
            String selectQuery = "SELECT * FROM users WHERE id = ?";
            PreparedStatement prepStmt = conn.prepareStatement(selectQuery);
            ResultSet rs = prepStmt.executeQuery();
            if (!rs.next()) {
                return false; // User not found
            }
            String updateQuery = "UPDATE users SET password = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(updateQuery);
            prepStmt.setString(1, newPassword);
            prepStmt.setInt(2, userId);
            int rowsAffected = prepStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteUser(int userId) {
        try (Connection conn = DBConnection.getDbConnection();) {
            String selectQuery = "SELECT * FROM users WHERE id = ?";
            PreparedStatement prepStmt = conn.prepareStatement(selectQuery);
            ResultSet rs = prepStmt.executeQuery();
            if (!rs.next()) {
                return false; // User not found
            }

            String deleteQuery = "DELETE FROM users WHERE id = ?";
            prepStmt = conn.prepareStatement(deleteQuery);
            prepStmt.setInt(1, userId);
            int rowsAffected = prepStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
