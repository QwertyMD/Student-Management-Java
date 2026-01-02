package Authentication;

public interface UserInterface {
    User findByUsername(String username);

    boolean saveUser(User user);

    boolean updatePassword(int userId, String newPassword);

    boolean deleteUser(int userId);
}
