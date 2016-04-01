import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Class used to store username, passwords and timing values.
 * In a file represented as Serializable objects.
 */

public class Storage {
    private User user;

    public Storage(User user) {
        this.user = user;
    }

    public Storage() {
    }

    public void seralizeUser() {
        String passwordToHash = user.getPassword();
        String securePassword = get_SHA_1_SecurePassword(passwordToHash);
        user.setPassword(securePassword);

        try {
            FileOutputStream fileOut =
                    new FileOutputStream("./Storage/users.ser", true);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(user);
            out.close();
            fileOut.flush();
            fileOut.close();
            System.out.printf("Serialized data is saved in /Storage/users.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public ArrayList<User> deseralizeUser() {
        ArrayList<User> savedUsers = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("./Storage/users.ser");
            while (true) {
                ObjectInputStream in = new ObjectInputStream(fileIn);
                try {
                    User u = (User) in.readObject();
                    savedUsers.add(u);
                } catch (EOFException e) {
                    // Close readers
                    in.close();
                    fileIn.close();
                    return savedUsers;
                }
            }
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("User class not found");
            c.printStackTrace();
        }
        return savedUsers;
    }

    /**
     * ===============================================
     *
     *         SHA Encryption
     *
     * ===============================================
     */

    public String get_SHA_1_SecurePassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
