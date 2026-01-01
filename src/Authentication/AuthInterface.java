package Authentication;

public interface AuthInterface {
    boolean authenticate(String username, String password);
    boolean changePassword(int userId, String oldPassword, String newPassword);
}
