package main.java.travelbook.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility program for SHA256 hash.
 * @author luchua-bc
 * @version 1.0
 */
public class HashUtil {
	private static final String SSHA_PREFIX = "{SSHA}";
	private static final int SSHA_256_LENGTH = 32; // SHA-256 is 32 bytes long
	private static final int SALT_LENGTH = 16; // Use a 16 byte salt

	/**
	 * Returns the password hash with a random salt added. 
	 * @param password The clear-text password to be hashed
	 */
	public static String getPasswordHash(String password) {
		try {
			byte[] salt = getSalt();
			String cipher = getSaltedPasswordHash(password, salt);
			
			return cipher;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Checks whether a password matches a previously generated password hash.
	 * @param password The clear-text password to be checked
	 * @param passwordHash The password hash that was previously generated from a password
	 */
	public static boolean validatePassword(String password, String passwordHash) {
		boolean isValid = false;
		try {
			String cipher = passwordHash.substring(SSHA_PREFIX.length());
		
			byte[] cipherBytes = Base64.getDecoder().decode(cipher.getBytes());
			byte[] salt = new byte[SALT_LENGTH];
			System.arraycopy(cipherBytes, SSHA_256_LENGTH, salt, 0, SALT_LENGTH);

			String result = getSaltedPasswordHash(password, salt);
			// Compare the newly hashed password taking the same salt with the input hash.
			isValid = result.equals(passwordHash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return isValid;
	}
	
	/**
	 * Returns a randomly generated salt with the specified length.
	 * @throws NoSuchAlgorithmException
	 */
	private static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_LENGTH];
		random.nextBytes(salt);
		return salt;
	}

	/**
	 * Generates a salted password with the given clear-text password and the salt. 
	 * @param password The clear-text password to be hashed
	 * @param salt The salt to be added to the password hash
	 * @throws NoSuchAlgorithmException
	 */
	private static String getSaltedPasswordHash(String password, byte[] salt) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		// Add the salt to the message digest
		md.update(salt);
	
		byte[] passBytes = password.getBytes();
		byte[] allBytes = new byte[passBytes.length + SALT_LENGTH];
		System.arraycopy(passBytes, 0, allBytes, 0, passBytes.length);
		System.arraycopy(salt, 0, allBytes, passBytes.length, SALT_LENGTH);
	
		// Hash both the password and the salt
		byte[] messageDigest = md.digest(allBytes);
	
		// Combine the hash and the hashed password
		byte[] cipherBytes = new byte[SSHA_256_LENGTH + SALT_LENGTH];		
		System.arraycopy(messageDigest, 0, cipherBytes, 0, SSHA_256_LENGTH);
		System.arraycopy(salt, 0, cipherBytes, SSHA_256_LENGTH, SALT_LENGTH);
		
		String result = SSHA_PREFIX + Base64.getEncoder().encodeToString(cipherBytes);
		return result;
	}
}
