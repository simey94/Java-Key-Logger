import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Class used to store username, passwords and timing values.
 * In a file represented as Serializable objects.
 */

public class Storage {
    // TODO: Store in secure file then populate a hash table on program start
    private User user;

    public Storage(User user) {
        this.user = user;
    }

    public void seralizeUser() {
        String passwordToHash = user.getPassword();
        System.out.println("USER PASS ========================== : " + user.getPassword());
        String salt = null;
        try {
            salt = getSalt();
            System.out.println("USER SALT ========================== : " + salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String securePassword = get_SHA_1_SecurePassword(passwordToHash, salt);
        user.setPassword(securePassword);
        System.out.println(securePassword);

        try {
            FileOutputStream fileOut =
                    new FileOutputStream("/Users/michaelsime/CS4203_CompSec_KeyLogger/Storage/users.ser", true);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(user);
            out.close();
            fileOut.close();
            deseralizeUser();
            System.out.printf("Serialized data is saved in /Storage/users.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void deseralizeUser() {
        try {
            FileInputStream fileIn = new FileInputStream("/Users/michaelsime/CS4203_CompSec_KeyLogger/Storage/users.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            User u = (User) in.readObject();
            System.out.println("DESER name ======= " + u.getUsername());
            System.out.println("DESER pass ======= " + u.getPassword());
            System.out.println("DESER timings ======= " + u.getTimings());
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("User class not found");
            c.printStackTrace();
            return;
        }
    }

    /**
     * ===============================================
     * <p>
     * SHA Encryption with Salting
     * <p>
     * ===============================================
     */


    private String get_SHA_1_SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt.getBytes(("UTF-16")));
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }
}
