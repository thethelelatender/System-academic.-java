package loginapp;

public class LoginTest {

    // =========================
    // Username Tests
    // =========================

    
    public void testCheckUserNameValid() {
        Login login = new Login();

        assertTrue(login.checkUserName("ab_1"));
    }

    
    public void testCheckUserNameInvalidTooLong() {
        Login login = new Login();

        assertFalse(login.checkUserName("abcdef_"));
    }

    
    public void testCheckUserNameInvalidNoUnderscore() {
        Login login = new Login();

        assertFalse(login.checkUserName("abc12"));
    }


    // =========================
    // Password Tests
    // =========================

    
    public void testCheckPasswordComplexityValid() {
        Login login = new Login();

        assertTrue(login.checkPasswordComplexity("Password1!"));
    }

    
    public void testCheckPasswordComplexityTooShort() {
        Login login = new Login();

        assertFalse(login.checkPasswordComplexity("Pass1!"));
    }

    
    public void testCheckPasswordComplexityNoCapitalLetter() {
        Login login = new Login();

        assertFalse(login.checkPasswordComplexity("password1!"));
    }

   
    public void testCheckPasswordComplexityNoNumber() {
        Login login = new Login();

        assertFalse(login.checkPasswordComplexity("Password!"));
    }

    
    public void testCheckPasswordComplexityNoSpecialCharacter() {
        Login login = new Login();

        assertFalse(login.checkPasswordComplexity("Password1"));
    }


    // =========================
    // Cell Phone Tests
    // =========================

    
    public void testCheckCellPhoneNumberValid() {
        Login login = new Login();

        assertTrue(login.checkCellPhoneNumber("+27123456789"));
    }

    
    public void testCheckCellPhoneNumberInvalid() {
        Login login = new Login();

        assertFalse(login.checkCellPhoneNumber("0123456789"));
    }

    
    public void testCheckCellPhoneNumberInvalidInternationalCode() {
        Login login = new Login();

        assertFalse(login.checkCellPhoneNumber("27123456789"));
    }


    // =========================
    // Registration Tests
    // =========================

    
    public void testSuccessfulRegistration() {
        Login login = new Login();

        String result = login.registerUser(
                "ab_1",
                "Password1!",
                "+27123456789"
        );

        assertEquals(
                "Registration successful. Welcome!",
                result
        );
    }

    
    public void testRegistrationInvalidUsername() {
        Login login = new Login();

        String result = login.registerUser(
                "abcdef",
                "Password1!",
                "+27123456789"
        );

        assertEquals(
                "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.",
                result
        );
    }

    
    public void testRegistrationInvalidPassword() {
        Login login = new Login();

        String result = login.registerUser(
                "ab_1",
                "password",
                "+27123456789"
        );

        assertEquals(
                "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.",
                result
        );
    }

    
    public void testRegistrationInvalidCellPhoneNumber() {
        Login login = new Login();

        String result = login.registerUser(
                "ab_1",
                "Password1!",
                "0123456789"
        );

        assertEquals(
                "Cell phone number incorrectly formatted or does not contain international code.",
                result
        );
    }


    // =========================
    // Login Tests
    // =========================

    
    public void testSuccessfulLogin() {
        Login login = new Login();

        login.registerUser(
                "ab_1",
                "Password1!",
                "+27123456789"
        );

        assertTrue(
                login.loginUser("ab_1", "Password1!")
        );
    }

    
    public void testLoginIncorrectUsername() {
        Login login = new Login();

        login.registerUser(
                "ab_1",
                "Password1!",
                "+27123456789"
        );

        assertFalse(
                login.loginUser("wrong", "Password1!")
        );
    }

    
    public void testLoginIncorrectPassword() {
        Login login = new Login();

        login.registerUser(
                "ab_1",
                "Password1!",
                "+27123456789"
        );

        assertFalse(
                login.loginUser("ab_1", "WrongPassword1!")
        );
    }


    // =========================
    // Login Status Tests
    // =========================

    
    public void testSuccessfulLoginStatus() {
        Login login = new Login();

        login.registerUser(
                "ab_1",
                "Password1!",
                "+27123456789"
        );

        String result = login.returnLoginStatus(true);

        assertEquals(
                "Welcome ab_1, it is great to see you again.",
                result
        );
    }

    
    public void testFailedLoginStatus() {
        Login login = new Login();

        login.registerUser(
                "ab_1",
                "Password1!",
                "+27123456789"
        );

        String result = login.returnLoginStatus(false);

        assertEquals(
                "Username or password incorrect, please try again.",
                result
        );
    }

    private void assertTrue(boolean checkUserName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertFalse(boolean checkUserName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertEquals(String registration_successful_Welcome, String result) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}