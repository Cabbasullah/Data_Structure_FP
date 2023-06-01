import java.util.*;
import FinancialInfo.FinancialInfo;
import WELCOME.WELCOME;
import ProcessAccount.ProcessAccount;

class Main {
    public static final String ANSI_RESET = "\u001B[0m";

    // Declaring the color
    // Custom declaration
    public static final String ANSI_BLUE = "\u001B[34m";
    // Custom declaration
    public static final String WHITE_BG = "\u001B[47m";

    public static void main(String[] args) {
        ProcessAccount newpc = new ProcessAccount();
        /* UpdateRecords upr=new UpdateRecords(); */

        Scanner scn = new Scanner(System.in);
        char option;
        /* do { */

        System.out.println(
                WHITE_BG + "************************************************************************" + ANSI_RESET);

        System.out.print(ANSI_BLUE + "\nCreate An Account OR login into Your Existing Account\n\n |A:add L:login|: "
                + ANSI_RESET);
        option = scn.next().charAt(0);
        switch (option) {
            case 'A' -> newpc.createAccount();
            case 'L' -> {
                WELCOME obWelcome = new WELCOME();
                obWelcome.loginPage("", " ");
                FinancialInfo newinfo = new FinancialInfo();
                newinfo.addFinancialInfo();
            }
            case 'P' -> {
                newpc.LoadContent();
                newpc.PrintDetails();
            }

            case 'T' -> System.exit(0);
        }

        /* } while (option != 'T'); */
        scn.close();

    }

}
