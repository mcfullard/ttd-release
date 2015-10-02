package za.ttd.game;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

/**
 * @author minnaar
 * @since 2015/10/02.
 *
 * Code contributions from http://stackoverflow.com/questions/20807647/secretkeyfactory-getinstancepbkdf2withhmacsha512-throws-nosuchalgorithmexcep
 *
 */
public class Security {
    public static final String algorithm = "PBKDF2WithHmacSHA1";
    public static final int saltbytesize = 24;
    public static final int hashbytesize = 24;
    public static final int iterations = 1000;

    public static void generateHash(Player player, String password) throws
            NoSuchAlgorithmException,
            InvalidKeySpecException
    {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[saltbytesize];
        secureRandom.nextBytes(salt);
        byte[] hash = pbkdf2(password.toCharArray(),
                salt,
                iterations,
                hashbytesize);
        player.setSalt(salt);
        player.setHash(hash);
    }

    public static boolean hashMatch(Player player, String password) throws
            NoSuchAlgorithmException,
            InvalidKeySpecException
    {
        byte[] inputHash = pbkdf2(password.toCharArray(),
                player.getSalt(),
                iterations,
                hashbytesize);
        return Arrays.equals(inputHash, player.getHash());
    }

    private static byte[] pbkdf2(char[] password, byte[] salt,
                                 int iterations, int bytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
        return skf.generateSecret(spec).getEncoded();
    }
}
