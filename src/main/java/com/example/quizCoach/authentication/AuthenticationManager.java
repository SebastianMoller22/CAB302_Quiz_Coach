package com.example.quizCoach.authentication;

import com.example.quizCoach.database.SqliteUserDAO;
import com.example.quizCoach.model.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * For Signup/Login/Logout and add/modify users' database
 */
public class AuthenticationManager {
    // Number of iterations for the PBKDF2 algorithm (more iterations = more secure but slower)
    // 65536 is a reasonable balance between security and performance
    private static final int ITERATIONS = 65536;

    // Desired key length (in bits) for the derived hash output
    // 256 bits = 32 bytes, providing strong cryptographic security
    private static final int KEY_LENGTH = 256;

    /**
     * the user using the session
     */
    private User activeUser;

    /**
     * the users' database
     */
    private SqliteUserDAO userDatabase;

    /**
     * the default constructor of the class
     */
    public AuthenticationManager() {
        userDatabase = new SqliteUserDAO();
        activeUser = null;
    }

    /**
     * the method for getting the active user
     * @return the current user of the session
     */
    public User getActiveUser() {return activeUser;}

    /**
     * set the active user to an user
     * @param user the user you want to set
     */
    private void setActiveUser(User user) {this.activeUser = user;}

    /**
     * for validating users' inputs
     * @param the_string the string you want to validate
     * @param regex the regex you want to compare with
     * @return whether the string is valid or not
     */
    public Boolean validateString(String the_string, String regex) {
        return the_string.matches(regex);
    }

    /**
     * for signing up as an user
     * @param username a distinct username
     * @param email the user's email
     * @param password the user's password
     * @throws Exception if any of the string is invalid
     */
    public void Signup(String username, String email, String password) throws Exception {
        if (!validateString(username, AuthenticationConstant.usernameRegex)){
            throw new Exception("Invalid Username");
        }
        if (checkifUserExists(username)) {
            throw new Exception("This Username already existed");
        }
        if (!validateString(email, AuthenticationConstant.emailRegex)) {
            throw new Exception("Invalid Email");
        }
        if (!validateString(password, AuthenticationConstant.passwordRegex)) {
            throw new Exception("Password must be at least 8 characters and include uppercase, lowercase, digit, and special character.");
        }
        byte[] salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        User newuser = new User(username, hashedPassword, email);
        userDatabase.addUser(newuser);
    }

    /**
     * Confirm whether the username has been used or not
     * @param username the username you want to check
     * @return whether the username has been used
     */
    public Boolean checkifUserExists(String username) {
        List<User> users = userDatabase.getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * to confirm whether the username and password match
     * @param username the user's username
     * @param password the user's password
     * @return if the username and password match
     * @throws Exception if they do not match
     */
    public Boolean matchPasswordandUsername(String username, String password) throws Exception {
        if (!checkifUserExists(username)) {
            throw new Exception("No User with this username");
        }
        User user = userDatabase.getUser(username);
        return verifyPassword(password, user.getPassword());
    }

    /**
     * Used by the users to login to their accounts
     * @param username the user's username
     * @param password the user's password
     * @throws Exception if username and password do not match
     */
    public void LoginAsUser(String username, String password) throws Exception {
        if (!matchPasswordandUsername(username, password)) {
            throw new Exception("Incorrect Username or Password");
        }
        setActiveUser(getUser(username));
    }

    /**
     * Log out of the account
     */
    public void Logout() {
        setActiveUser(null);
    }

    /**
     * to get the user from the username
     * @param username the username
     * @return the user with the matching username
     */
    public User getUser(String username) {
        // Return the user with that username
        return userDatabase.getUser(username);
    }

    /**
     * update the data of the active user to the database
     */
    public void updateActiveUser(Boolean passwordupdated) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (passwordupdated) {
            byte[] salt = generateSalt();
            String hashedPassword = hashPassword(activeUser.getPassword(), salt);
            activeUser.setPassword(hashedPassword);
        }
        userDatabase.updateUser(activeUser);
    }

    /**
     * Generates a cryptographically secure random salt.
     *
     * @return a 16-byte salt to be used for password hashing
     */
    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    /**
     * Hashes the given password using PBKDF2 with HMAC-SHA256 and the provided salt.
     *
     * @param password the raw password to hash
     * @param salt the salt to use in the hashing process
     * @return the hashed password in the format "salt:hash", both Base64 encoded
     * @throws NoSuchAlgorithmException if the PBKDF2 algorithm is not available
     * @throws InvalidKeySpecException if the key specification is invalid
     */
    private String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Verifies whether the given password matches the stored hash.
     *
     * @param inputPassword the password input to verify
     * @param storedHash the stored password hash in the format "salt:hash"
     * @return true if the input password matches the stored hash; false otherwise
     * @throws Exception if the hash format is invalid or hashing fails
     */
    private boolean verifyPassword(String inputPassword, String storedHash) throws Exception {
        String[] parts = storedHash.split(":");
        if (parts.length != 2) {
            throw new IllegalStateException("Invalid stored password hash.");
        }
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        String hashOfInput = hashPassword(inputPassword, salt);
        return hashOfInput.equals(storedHash);
    }
}
