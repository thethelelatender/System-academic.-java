
package loginapp;



public class Login {

    private String username;
    private String password;
    private String cellPhoneNumber;

    // Method to check the username format
    public boolean checkUserName(String username) {
        return username.length() <= 5 && username.contains("_");
    }

    // Method to check password complexity
    public boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&  // At least one uppercase letter
               password.matches(".*\\d.*") &&    // At least one number
               password.matches(".*[!@#$%^&*(),.?\":{}|<>].*"); // At least one special character
    }

    // Method to check cell phone number format
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber.matches("\\+27\\d{9}"); // Example format: +27123456789
    }

    // Method to register user
    public String registerUser(String username, String password, String cellPhoneNumber) {
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;

        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "Registration successful. Welcome!";
    }

    // Method to login user
    public boolean loginUser(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Method to return login status
    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + username + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

}