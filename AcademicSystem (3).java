import java.util.Scanner;
public class AcademicSystem {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Student Academic System");
        
        //Step 2: Cature Student Information
        
        System.out.println("Enter your student name:");
        String name = input.nextLine();
        
        System.out.println("Enter Age");
        int age = input.nextInt();
        int mark = 0;
        
        //Step 6 & Requirement 2: Validate the mark
        
        if (mark <0 || mark >100 ){
           System.out.println("Invalid mark! Must be between 0 and 100");
       return;
       }
        
       input.nextLine();
        
        //Capture Attendance
        System.out.println("Enter your percentage (0 - 100):");
        double attendancePercantage = input.nextDouble();
        
        input.nextLine();
        
        System.out.println("Enter Student Category:");
        String studentCategory = input.nextLine();
        
        System.out.print("Student information");
        System.out.println("Name:" + name);
        System.out.println("Age:" + age);
        System.out.println("Mark:" + mark);
        System.out.print("Attendance:" + attendance);
        System.out.println("Student Category:" + studentCategory);
        
        
        //Requirement 1: Age Status
        String ageStatus = null;
        if (age >= 18){
            System.out.println("Adult");
        }
        else {
            System.out.println("Minor");
        }
        System.out.println("Age Status:" + ageStatus);
        
        //Step 5 & Requirement 6: Special Category Status
        boolean isSpecialCategory = (age < 18 || age > 60 || studentCategory.equalsIgnoreCase("Special"));
        if (isSpecialCategory){
            System.out.println("Special Category: Granted");
        }
        else {
            System.out.println("Special Category: Standard");
        }
        
        // Exercise 1 & Requirement 4: Academic Grade Result
        if (mark >=90 && mark <=100) {
            System.out.println("Excellent");
            System.out.println("Distinction");
        }
        else if (mark >= 75 && mark <= 89) {
            System.out.println("Very good");
            System.out.println("Distinction");
        }
        else if (mark >= 60 && mark <= 74) {
            System.out.println("Good");
        }
        else if (mark >= 50 && mark<= 59) {
            System.out.println("Pass");
        }
        else {
            System.out.println("Fail");
        }
        
       //Exercise 2
       System.out.println("Enter your fee amount:");
       double fee = input.nextDouble();
    
       double discountPercentage;
       
       if (isSpecialCategory){
           discountPercentage = 20.0;
       }
       else {
            double dicountPercentage = 5.0;
       }
        String discountPercenatge = null;
       System.out.println("Fee discount:" +discountPercenatge + "%");
        
       
       //Exercise 3 & Requirements 3 and 5 : Amission / Examination Status
       boolean isEligibleExamination = (mark >= 50 && attendance >=75 );
       boolean isAdmitted = (age >= 18 && mark >= 50 && attendance >=75);
       
       if (isEligibleExamination ){
           System.out.println("Examination Status: Eligible");
       }
       else{
           System.out.println("Examination status: Not Eligible");
       }
       if (isAdmitted){
           System.out.println("Admission status: Admitted");
       }
       else{
           System.out.println("Admission status: Not Admitted");
       }
  
       //Exercise 4: Academic Support
       
       if (mark < 40 || attendance < 50){
           System.out.println("Academic Support Required");
       }
       else {
           System.out.println("No academic support required");
       }
       //Exercise 5: Operation Procedure
       boolean result1 = true || false && false;
       boolean result2 = (true || false) && false;
       System.out.println("Result of 'true || false && false':" + result1);
       System.out.println("Result of '(true || false) && false':" + result2);
       
       //Step 10 & 11 and Requirement 7: Service menu with switch case
       System.out.println("1. Academic Results");
        System.out.println("2. Attendance");
        System.out.println("3. Fees");
        System.out.println("4. Student Support");
        System.out.println("5. Exit");
        System.out.print("Select an option (1-5): ");
        
        int choice = input.nextInt();
        System.out.println();
     

        switch (choice) {
            case 1:
                System.out.println("--- [Academic Results] ---");
                System.out.println("Student: " + name);
            {
                String gradeResult = null;
                System.out.println("Mark: " + mark + "% (" + gradeResult + ")");
            }
                break;

            case 2:
                System.out.println("--- [Attendance Record] ---");
                System.out.println("Student: " + name);
                System.out.println("Attendance: " + attendance + "%");
                System.out.println("Exam Eligibility: " + (isEligibleExamination ? "Eligible" : "Not Eligible"));
                break;
            case 3:
                System.out.println("--- [Fee Structure & Discounts] ");
                System.out.println("Category: " + studentCategory);
            {
                String feeDiscount = null;
                System.out.println("Applied Discount: " + feeDiscount + "%");
            }
                break;

            case 4:
                System.out.println("--- [Student Support System] ---");
                if (mark < 40 || attendance < 50) {
                    System.out.println("Action Required: Student flag raised for mandatory academic support.");
                } else {
                    System.out.println("Status: Student is performing adequately.");
                }
                break;
            case 5:
                System.out.println("Exiting system. Have a great day!");
                break;
            default:
                System.out.println("Invalid menu selection! Please choose an option from 1 to 5.");
                break;
        }

        input.close();
    }
}

    
