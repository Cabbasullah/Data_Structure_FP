package WELCOME;

import java.util.*;

import FinancialInfo.FinancialInfo;

import java.io.*;

/* import UserDetails.UserDetails; */
public class WELCOME {
    FinancialInfo fInfo = new FinancialInfo();
    public static final String ANSI_RESET = "\u001B[0m";

    // Declaring the color
    // Custom declaration
    public static final String BG = "\u001B[40m";
    public static final String Blue = "\u001B[32m";
    Scanner scn = new Scanner(System.in);

    /* UserDetails user = new UserDetails("", ""); */

    public void loginPage(String loginName, Object password) {
        // Check for null values
        if (loginName == null || password == null) {
            System.out.println("Invalid login name or password");
            return;
        }

        String username = "Login Name: ";
        int screenWidth = 80;
        int leftPadding = (screenWidth - username.length()) / 5;

        System.out.print(String.format("%" + leftPadding + "s%s", "", username));
        String login = scn.nextLine();

        String passwordText = "Password: ";
        System.out.print(String.format("%" + leftPadding + "s%s", "", passwordText));
        scn.nextLine();

        String passwordString = ((String) password).trim();
        boolean found = false;

        try (BufferedReader bReader = new BufferedReader(new FileReader("newfile.txt"))) {
            String line = bReader.readLine();

            while (line != null) {
                if (line.contains(login) && line.contains(passwordString)) {
                    String name = Blue + "\nHi, " + login + ANSI_RESET;
                    System.out.println(
                            "\n" + BG + "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
                                    + ANSI_RESET);
                    int ndashboardWidth = 80;
                    int ndashboardLeftPadding = (ndashboardWidth - name.length());
                    System.out.printf("%n%" + ndashboardLeftPadding + "s%s%n%n", "", name);
                    String welcomeText = Blue + "Welcome to your dashboard. Your current financial records are below:"
                            + ANSI_RESET;

                    BufferedReader readbudget = new BufferedReader(new FileReader("budget.txt"));
                    String budget = readbudget.readLine();
                    System.out.println(Blue + "Target Budget: " + ANSI_RESET);
                    boolean budgetfound = false;
                    while (budget != null) {
                        System.out.println(budget);

                        budgetfound = true;

                        Double actualbudgetvalue = fInfo.actualBudget();
                        System.out.println("Actual Budget: " + actualbudgetvalue);

                        budget = readbudget.readLine(); // Read the next line
                    }
                    readbudget.close();

                    if (!budgetfound) {
                        System.out.println("No budget found.");
                    }

                    int dashboardWidth = 80;
                    int dashboardLeftPadding = (dashboardWidth - welcomeText.length()) / 2;

                    System.out.printf("%n%" + dashboardLeftPadding + "s%s%n%n", "", welcomeText);

                    try (BufferedReader records = new BufferedReader(new FileReader("financial_records.txt"))) {
                        String newline = records.readLine();
                        int count = 1;

                        while (newline != null) {
                            System.out.println(count + ". " + newline);
                            count++;
                            newline = records.readLine();
                        }

                        fInfo.avetlExpense();

                        fInfo.totalExpenses();
                        System.out.println(
                                "\n" + BG + "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"
                                        + ANSI_RESET);
                    }

                    found = true;

                }
                line = bReader.readLine();

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        if (!found) {
            System.out.println("Invalid login name or password");
        }
    }

}
