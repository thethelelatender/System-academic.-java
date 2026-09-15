package loginapp;

import java.util.Scanner;


public class Main {
    

    public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);


        Login login = new Login();

        System.out.println("===== USER REGISTRATION =====");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cell phone number: ");
        String cellPhoneNumber = scanner.nextLine();

        String registrationMessage =
                login.registerUser(username, password, cellPhoneNumber);

        System.out.println(registrationMessage);

        if (registrationMessage.equals("Registration successful. Welcome!")) {

            System.out.println("\n===== USER LOGIN =====");

            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            boolean loginSuccess =
                    login.loginUser(loginUsername, loginPassword);

            System.out.println(login.returnLoginStatus(loginSuccess));
        }

        scanner.close();
    }
}
