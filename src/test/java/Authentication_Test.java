import com.example.quizCoach.authentication.AuthenticationManager_Test;
import com.example.quizCoach.authentication.AuthenticationConstant;
import com.example.quizCoach.database.MockUserDAO;
import com.example.quizCoach.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

public class Authentication_Test {

    private AuthenticationManager_Test manager;

    @BeforeEach
    public void setup() {
        MockUserDAO.USERS.clear(); // Reset DB
        manager = new AuthenticationManager_Test();
    }

    // --------- validateString Tests ---------
    @Test
    public void testValidateValidUsername() {
        assertTrue(manager.validateString("John_Doe1", AuthenticationConstant.usernameRegex));
    }

    @Test
    public void testValidateInvalidUsername() {
        assertFalse(manager.validateString("1InvalidName", AuthenticationConstant.usernameRegex));
    }

    @Test
    public void testValidateValidEmail() {
        assertTrue(manager.validateString("john.doe@example.com", AuthenticationConstant.emailRegex));
    }

    @Test
    public void testValidateInvalidEmail() {
        assertFalse(manager.validateString("john.doe@com", AuthenticationConstant.emailRegex));
    }

    @Test
    public void testValidateValidPassword() {
        assertTrue(manager.validateString("Abcd1234@", AuthenticationConstant.passwordRegex));
    }

    @Test
    public void testValidateInvalidPassword() {
        assertFalse(manager.validateString("abc123", AuthenticationConstant.passwordRegex));
    }

    // --------- Signup Tests ---------
    @Test
    public void testSignupCreatesUserSuccessfully() throws Exception {
        manager.Signup("NewUser1", "newuser@example.com", "Password1@");
        assertTrue(manager.checkifUserExists("NewUser1"));
    }

    @Test
    public void testSignupFailsWithInvalidUsername() {
        Exception ex = assertThrows(Exception.class, () ->
                manager.Signup("1bad", "bad@example.com", "Password1@")
        );
        assertEquals("Invalid Username", ex.getMessage());
    }

    @Test
    public void testSignupFailsWithDuplicateUsername() throws Exception {
        manager.Signup("UserX", "userx@example.com", "Password1@");
        Exception ex = assertThrows(Exception.class, () ->
                manager.Signup("UserX", "newx@example.com", "Password1@")
        );
        assertEquals("This Username already existed", ex.getMessage());
    }

    @Test
    public void testSignupFailsWithInvalidEmail() {
        Exception ex = assertThrows(Exception.class, () ->
                manager.Signup("UserEmail", "bad-email", "Password1@")
        );
        assertEquals("Invalid Email", ex.getMessage());
    }

    @Test
    public void testSignupFailsWithInvalidPassword() {
        Exception ex = assertThrows(Exception.class, () ->
                manager.Signup("UserPass", "user@example.com", "weakpass")
        );
        assertTrue(ex.getMessage().contains("Password must be at least"));
    }

    // --------- checkifUserExists Tests ---------
    @Test
    public void testUserExistsAfterSignup() throws Exception {
        manager.Signup("Checker", "check@example.com", "Password1@");
        assertTrue(manager.checkifUserExists("Checker"));
    }

    @Test
    public void testUserDoesNotExist() {
        assertFalse(manager.checkifUserExists("GhostUser"));
    }

    // --------- matchPasswordandUsername Tests ---------
    @Test
    public void testPasswordMatches() throws Exception {
        manager.Signup("PMatch", "pmatch@example.com", "Password1@");
        assertTrue(manager.matchPasswordandUsername("PMatch", "Password1@"));
    }

    @Test
    public void testPasswordDoesNotMatch() throws Exception {
        manager.Signup("PNoMatch", "pno@example.com", "Password1@");
        assertFalse(manager.matchPasswordandUsername("PNoMatch", "Wrong1@"));
    }

    @Test
    public void testMatchFailsForNonexistentUser() {
        Exception ex = assertThrows(Exception.class, () ->
                manager.matchPasswordandUsername("Nobody", "Whatever1@")
        );
        assertEquals("No User with this username", ex.getMessage());
    }

    // --------- LoginAsUser Tests ---------
    @Test
    public void testSuccessfulLoginSetsActiveUser() throws Exception {
        manager.Signup("LoginUser", "login@example.com", "Password1@");
        manager.LoginAsUser("LoginUser", "Password1@");
        assertNotNull(manager.getActiveUser());
    }

    @Test
    public void testFailedLoginDueToWrongPassword() throws Exception {
        manager.Signup("LoginFail", "loginfail@example.com", "Password1@");
        Exception ex = assertThrows(Exception.class, () ->
                manager.LoginAsUser("LoginFail", "WrongPass1@")
        );
        assertEquals("Incorrect Username or Password", ex.getMessage());
    }

    // --------- Logout Tests ---------
    @Test
    public void testLogoutClearsActiveUser() throws Exception {
        manager.Signup("LogoutUser", "logout@example.com", "Password1@");
        manager.LoginAsUser("LogoutUser", "Password1@");
        manager.Logout();
        assertNull(manager.getActiveUser());
    }

    // --------- getUser Test ---------
    @Test
    public void testGetUserReturnsMockedUser() {
        User user = manager.getUser("Any");
        assertEquals("Johnny", user.getUsername()); // as per stub
    }

    // --------- Password Hashing and Storage Behavior ---------
    @Test
    public void testPasswordIsHashedAfterSignup() throws Exception {
        manager.Signup("HashTest", "hash@example.com", "Password1@");
        User user = MockUserDAO.USERS.stream()
                .filter(u -> u.getUsername().equals("HashTest"))
                .findFirst()
                .orElseThrow();
        assertNotEquals("Password1@", user.getPassword()); // should not store raw password
    }

    @Test
    public void testPasswordHashContainsSaltAndHash() throws Exception {
        manager.Signup("SaltSplitTest", "split@example.com", "Password1@");
        User user = MockUserDAO.USERS.stream()
                .filter(u -> u.getUsername().equals("SaltSplitTest"))
                .findFirst()
                .orElseThrow();

        String[] parts = user.getPassword().split(":");
        assertEquals(2, parts.length);
    }

    @Test
    public void testPasswordCanBeVerified() throws Exception {
        manager.Signup("VerifyHashUser", "verify@example.com", "Password1@");
        assertTrue(manager.matchPasswordandUsername("VerifyHashUser", "Password1@"));
    }

    @Test
    public void testPasswordVerificationFailsForWrongPassword() throws Exception {
        manager.Signup("VerifyFailUser", "fail@example.com", "Password1@");
        assertFalse(manager.matchPasswordandUsername("VerifyFailUser", "WrongPassword@1"));
    }

    // --------- Direct Test of Private verifyPassword via Reflection ---------
    @Test
    public void testVerifyPasswordWithCorrectHash() throws Exception {
        String password = "TestPass1@";

        // Use reflection to access hashPassword
        Method hashPasswordMethod = AuthenticationManager_Test.class.getDeclaredMethod("hashPassword", String.class, byte[].class);
        hashPasswordMethod.setAccessible(true);

        Method verifyPasswordMethod = AuthenticationManager_Test.class.getDeclaredMethod("verifyPassword", String.class, String.class);
        verifyPasswordMethod.setAccessible(true);

        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        String hash = (String) hashPasswordMethod.invoke(manager, password, salt);

        boolean result = (boolean) verifyPasswordMethod.invoke(manager, password, hash);
        assertTrue(result);
    }

    @Test
    public void testVerifyPasswordFailsOnWrongPassword() throws Exception {
        String password = "CorrectPass1@";
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        Method hashPasswordMethod = AuthenticationManager_Test.class.getDeclaredMethod("hashPassword", String.class, byte[].class);
        hashPasswordMethod.setAccessible(true);
        String hash = (String) hashPasswordMethod.invoke(manager, password, salt);

        Method verifyPasswordMethod = AuthenticationManager_Test.class.getDeclaredMethod("verifyPassword", String.class, String.class);
        verifyPasswordMethod.setAccessible(true);
        boolean result = (boolean) verifyPasswordMethod.invoke(manager, "WrongPass1@", hash);
        assertFalse(result);
    }

    @Test
    public void testVerifyPasswordThrowsOnMalformedHash() throws Exception {
        Method verifyPasswordMethod = AuthenticationManager_Test.class.getDeclaredMethod("verifyPassword", String.class, String.class);
        verifyPasswordMethod.setAccessible(true);
        Exception ex = assertThrows(Exception.class, () ->
                verifyPasswordMethod.invoke(manager, "Password1@", "not_a_valid_hash")
        );
        assertTrue(ex.getCause() instanceof IllegalStateException);
    }

    @Test
    public void testGeneratedSaltIs16Bytes() throws Exception {
        Method generateSaltMethod = AuthenticationManager_Test.class.getDeclaredMethod("generateSalt");
        generateSaltMethod.setAccessible(true);
        byte[] salt = (byte[]) generateSaltMethod.invoke(manager);
        assertEquals(16, salt.length);
    }

    @Test
    public void testHashPasswordGeneratesConsistentHashForSameInputs() throws Exception {
        String password = "MyPassword1@";
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        Method hashPasswordMethod = AuthenticationManager_Test.class.getDeclaredMethod("hashPassword", String.class, byte[].class);
        hashPasswordMethod.setAccessible(true);

        String hash1 = (String) hashPasswordMethod.invoke(manager, password, salt);
        String hash2 = (String) hashPasswordMethod.invoke(manager, password, salt);

        assertEquals(hash1, hash2);
    }

    @Test
    public void testHashPasswordGeneratesDifferentHashesForDifferentSalts() throws Exception {
        String password = "MyPassword1@";

        Method hashPasswordMethod = AuthenticationManager_Test.class.getDeclaredMethod("hashPassword", String.class, byte[].class);
        hashPasswordMethod.setAccessible(true);

        byte[] salt1 = new byte[16];
        new SecureRandom().nextBytes(salt1);
        byte[] salt2 = new byte[16];
        new SecureRandom().nextBytes(salt2);

        String hash1 = (String) hashPasswordMethod.invoke(manager, password, salt1);
        String hash2 = (String) hashPasswordMethod.invoke(manager, password, salt2);

        assertNotEquals(hash1, hash2);
    }
}