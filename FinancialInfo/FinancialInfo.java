package FinancialInfo;

import java.util.*;
import java.util.HashMap;

import java.util.Date;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import FinancialRecord.FinancialRecord;

import SearchContent.SearchContent;

public class FinancialInfo {

    public static final String ANSI_RESET = "\u001B[0m";

    // Declaring the color
    // Custom declaration
    public static final String BG = "\u001B[47m";
    public static final String BCyan = "\u001B[46m";
    public static final String Cyan = "\u001B[36m";
    public static final String Black = "\u001B[30m";
    public static final String Green = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";

    public static final String Blue = "\u001B[32m";

    /* LinkedList<FinancialRecord> recodlist = new LinkedList<>(); */
    public double actualBudget() {
        double[] expenseResult = totalExpenses();
        double totalExpense = expenseResult[0];
        double targetBudget = 0;
        try (BufferedReader bReader = new BufferedReader(new FileReader("budget.txt"))) {
            String line = bReader.readLine();
            while (line != null) {
                if (line.startsWith("Target Budget:")) {
                    String budgetValueStr = line.substring(line.indexOf(":") + 1).trim();
                    targetBudget = Double.parseDouble(budgetValueStr);
                    break;

                }
                line = bReader.readLine();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        double budgetStatus = targetBudget - totalExpense;

        return budgetStatus;

    }

    public double[] totalExpenses() {
        double total = 0.0;
        int count = 0;

        try {
            BufferedReader bReader = new BufferedReader(new FileReader("financial_records.txt"));
            String line = bReader.readLine();

            while (line != null) {
                String[] tokens = line.split(" : ");

                /*
                 * for(String token: tokens){
                 * System.out.println(token);
                 * }
                 */

                if (tokens.length >= 2) {
                    try {
                        String value = tokens[0].trim();
                        double amount = Double.parseDouble(value);
                        total = total + amount;
                        count++;

                    } catch (NumberFormatException e) {
                        // ignore the lines where amount is not valid double
                    }

                }

                line = bReader.readLine();
            }

            bReader.close();

        } catch (IOException e) {
            e.getMessage();
        }
        System.out.println(String.format("\nThe total expense: " + total));

        double average = total / count;
        String formatedavg = String.format("%.2f", average);
        System.out.println("Overall Average Spent so Far: " + formatedavg);

        return new double[] { total, average };
    }

    public double updateTargetBudget() {
        Scanner scn = new Scanner(System.in);
        double updatedBudget = 0.0;

        try (BufferedReader bReader = new BufferedReader(new FileReader("budget.txt"))) {
            String line = bReader.readLine();
            boolean fileIsEmpty = true;

            while (line != null) {
                if (!line.isEmpty()) {
                    fileIsEmpty = false;
                    System.out.print("Current target budget: " + line);
                    System.out.print("\nEnter new target budget: ");
                    double newBudget = scn.nextDouble();
                    scn.nextLine();
                    updatedBudget = newBudget;
                }
                line = bReader.readLine();
            }

            if (fileIsEmpty) {
                System.out.print("Enter target budget: ");
                double newBudget = scn.nextDouble();
                scn.nextLine();
                updatedBudget = newBudget;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scn.close();
        }

        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter("budget.txt", false))) {
            bWriter.write(String.valueOf(updatedBudget));
            bWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return updatedBudget;
    }

    public void avetlExpense() {
        File filename = new File("financial_records.txt");

        HashMap<String, Double> totalexpense = new HashMap<>();
        double total = 0.0;
        HashMap<String, Integer> numberexpense = new HashMap<>();
        int count = 0;

        try (BufferedReader expensreader = new BufferedReader(new FileReader(filename))) {

            String line = expensreader.readLine();
            String current = null;
            while (line != null) {
                if (line.trim().isEmpty()) {
                    line = expensreader.readLine();
                    continue;
                }
                if (line.trim().endsWith(":")) {
                    current = line.trim().replaceAll(":", "");
                    total = 0.0;
                    count = 0;
                    totalexpense.put(current, 0.0);
                    numberexpense.put(current, 0);

                } else {
                    try {
                        String[] elements = line.split(":");
                        String value = elements[0];
                        double amount = Double.parseDouble(value);
                        total += amount;
                        count++;
                        totalexpense.put(current, totalexpense.get(current) + amount);
                        numberexpense.put(current, numberexpense.get(current) + 1);
                    } catch (NumberFormatException e) {
                        System.out.print("Parsing Error" + e.getMessage());
                    }
                }
                line = expensreader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (HashMap.Entry<String, Double> mapexpense : totalexpense.entrySet()) {
            total = mapexpense.getValue();
            String categoryname = mapexpense.getKey();
            count = numberexpense.get(categoryname);

            double average = total / count;
            System.out.println();
            System.out.println(BLUE + "=================================================" + ANSI_RESET);
            System.out.println(categoryname);
            System.out.println("Total expense: " + total);
            System.out.println("Average expenses: " + average);
            System.out.println(BLUE + "=================================================" + ANSI_RESET);
            System.out.println();

        }
    }

    /*
     * public void addfinacialInfo() {
     * String Filename = "financial_records.txt";
     * 
     * LinkedList<FinancialRecord> newlist = new LinkedList<>();
     * HashMap<String, LinkedList<FinancialRecord>> expensesByCategory = new
     * HashMap<>();
     * Scanner scn = new Scanner(System.in);
     * 
     * }
     */

    public LinkedList<FinancialRecord> newlist;
    public HashMap<String, LinkedList<FinancialRecord>> expensesByCategory;

    public FinancialInfo() {
        newlist = new LinkedList<>();
        expensesByCategory = new HashMap<>();
    }

    public void addFinancialInfo() {
        Scanner scn = new Scanner(System.in);
        char Options;
        do {

            System.out.println(Blue
                    + "=================\nOPTIONS\na. Exit \nb. Add more transactions \nc. Update More Transactions \nd. Delete Records \ne. Search Content\nf. Update Target Budget \n=================");
            Options = scn.next().charAt(0);
            if (Options == 'a') {
                System.exit(0);
                return;
            }

            if (Options == 'b') {

                System.out.println("\n" + Cyan + "=====================" + ANSI_RESET + "" + Cyan
                        + "\n\nTRANSACTIONS ENTRY\n\n" + ANSI_RESET + Cyan + "=====================" + ANSI_RESET
                        + "\n");

                System.out.println(Black + "Number of Transactions: " + ANSI_RESET);
                int n = scn.nextInt();
                scn.nextLine();

                long startTimeFileWrite = System.currentTimeMillis();
                HashSet<String> checkCateg = new HashSet<>();
                for (int i = 0; i < n; i++) {
                    System.out.println(Black + "Enter the amount " + (i + 1) + ": " + ANSI_RESET);
                    double amount = scn.nextDouble();
                    System.out.println(Black + "Enter the date (MM/dd/yyyy): " + ANSI_RESET);
                    String dateString = scn.next();
                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                    Date date;
                    try {
                        date = format.parse(dateString);
                    } catch (Exception e) {
                        System.out.println("Invalid date format");
                        return;
                    }
                    scn.nextLine(); // consume newline character
                    System.out.println(Black + "Enter the description: " + ANSI_RESET);
                    String desc = scn.nextLine();
                    System.out.println(Black + "Enter the category: " + ANSI_RESET);
                    String catg = scn.nextLine();
                    FinancialRecord expense = new FinancialRecord(amount, date, desc, catg);
                    newlist.add(expense);
                }

                for (FinancialRecord expense : newlist) {
                    LinkedList<FinancialRecord> categoryExpensesList = expensesByCategory.get(expense.getCategory());
                    if (categoryExpensesList == null) {
                        categoryExpensesList = new LinkedList<>();
                        expensesByCategory.put(expense.getCategory(), categoryExpensesList);
                    }
                    categoryExpensesList.add(expense);
                }

                // Print the list after adding all expenses

                System.out.println("\nList of contents \n");
                for (Map.Entry<String, LinkedList<FinancialRecord>> entry : expensesByCategory.entrySet()) {
                    LinkedList<FinancialRecord> categoryExpensesList = entry.getValue();
                    for (FinancialRecord expense : categoryExpensesList) {
                        System.out.println(expense);
                        System.out.println();
                    }
                    System.out.println(); // Add an empty line between categories
                }

                // Measure the time taken to add records to the file

                try {
                    File newfile = new File("financial_records.txt");
                    BufferedWriter bwriter = new BufferedWriter(new FileWriter(newfile, true));

                    for (String category : expensesByCategory.keySet()) {
                        LinkedList<FinancialRecord> categoryExpenses = expensesByCategory.get(category);

                        boolean categ = false;
                        try (BufferedReader bReader = new BufferedReader(new FileReader(newfile))) {
                            String line = bReader.readLine();
                            while (line != null) {
                                if (line.contains(category)) {
                                    categ = true;
                                    break;
                                }
                                line = bReader.readLine();
                            }
                        }

                        if (!checkCateg.contains(category) && !categ) {
                            bwriter.write(category + ":");
                            bwriter.newLine();
                            checkCateg.add(category);
                            for (FinancialRecord expense : categoryExpenses) {
                                bwriter.write(
                                        "  " + expense.getAmount() + " : " + expense.getDate() + " : "
                                                + expense.getDescrip()
                                                + "\n");
                                bwriter.newLine();
                            }
                        }
                    }

                    bwriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                long endTimeFileWrite = System.currentTimeMillis();
                long fileWriteTime = endTimeFileWrite - startTimeFileWrite;
                System.out.println("Time taken to add records to the file: " + fileWriteTime + " milliseconds");
            }

            // Measure the time taken to add records to the file

            if (Options == 'c') {
                System.out.println("\n" + Cyan + "=====================" + ANSI_RESET + "" + Cyan
                        + "\n\nTRANSACTIONS UPDATE\n\n" + ANSI_RESET + Cyan + "=====================" + ANSI_RESET
                        + "\n");
                System.out.println("Number of Transactions to Update: ");
                int n = scn.nextInt();
                scn.nextLine();

                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

                for (int i = 0; i < n; i++) {
                    System.out.println("Update by category: ");
                    String category = scn.nextLine();
                    LinkedList<FinancialRecord> expenses = expensesByCategory.get(category);
                    if (expenses != null) {
                        System.out.println("Expenses found for the category '" + category + "':");
                        for (FinancialRecord expense : expenses) {
                            System.out.println(expense);
                        }

                        LinkedList<FinancialRecord> updatedExpenses = new LinkedList<>();

                        for (FinancialRecord expense : expenses) {
                            System.out.print(Black + "Enter new amount: " + ANSI_RESET);
                            double amount = scn.nextDouble();
                            System.out.print(Black + "Enter new date (MM/dd/yyyy): " + ANSI_RESET);
                            String dateString = scn.next();
                            Date date;
                            try {
                                date = format.parse(dateString);
                            } catch (ParseException e) {
                                System.out.println("Invalid date format");
                                return;
                            }
                            scn.nextLine(); // consume newline character
                            System.out.print(Black + "Enter new description: " + ANSI_RESET);
                            String desc = scn.nextLine();
                            System.out.print(Black + "Enter new category: " + ANSI_RESET);
                            String catg = scn.nextLine();
                            FinancialRecord updatedExpense = new FinancialRecord(amount, date, desc, catg);
                            updatedExpenses.add(updatedExpense);
                        }

                        expensesByCategory.put(category, updatedExpenses);

                        System.out.println("\nUpdated contents\n");
                        for (Map.Entry<String, LinkedList<FinancialRecord>> entry : expensesByCategory.entrySet()) {
                            /* String catg = entry.getKey(); */
                            LinkedList<FinancialRecord> categoryExpensesList = entry.getValue();

                            for (FinancialRecord expen : categoryExpensesList) {
                                System.out.println(expen.getCategory() + ":");
                                System.out.println(
                                        "  " + expen.getAmount() + " : " + format.format(expen.getDate()) + " : "
                                                + expen.getDescrip());
                            }
                            System.out.println();
                        }
                        boolean categoryFound = false;
                        try {
                            File inputFile = new File("financial_records.txt");
                            File tempFile = new File("temp.txt");
                            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                            String line;

                            while ((line = reader.readLine()) != null) {
                                if (line.startsWith(category.trim() + ":")) {
                                    categoryFound = true;
                                    for (FinancialRecord exp : updatedExpenses) {
                                        writer.write(exp.getCategory() + ":");
                                        writer.newLine();
                                        writer.write("  " + exp.getAmount() + " : " + format.format(exp.getDate())
                                                + " : " + exp.getDescrip());
                                        writer.newLine();
                                    }
                                    while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                                        // Skip the lines
                                    }
                                } else {
                                    writer.write(line);
                                    writer.newLine();
                                }
                            }

                            reader.close();
                            writer.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        File inputFile = new File("financial_records.txt");
                        File tempFile = new File("temp.txt");
                        if (!categoryFound) {
                            System.out.println("Category not found in the file.");
                            tempFile.delete();
                            return;
                        }
                        if (categoryFound) {
                            if (inputFile.delete()) {
                                tempFile.renameTo(inputFile);
                            } else {
                                System.out.println("Error deleting the original file");
                            }
                            System.out.println("\nFile updated successfully.");

                        }

                    } else {
                        System.out.println("No expenses found for the category '" + category + "'.");
                    }
                }
            }

            if (Options == 'd') {

                System.out.println("\n" + Cyan + "========================" + ANSI_RESET + "" + Cyan
                        + "\n\nDELETE CATEGORIES\n\n" + ANSI_RESET + Cyan + "========================" + ANSI_RESET
                        + "\n");

                System.out.println(Black + "Enter the number of categories to delete: " + ANSI_RESET);
                int numCategories = scn.nextInt();
                scn.nextLine(); // consume newline character

                boolean fileModified = false; // Flag to track if any changes were made to the file

                for (int i = 0; i < numCategories; i++) {
                    System.out.println(Black + "Enter the category name to delete: " + ANSI_RESET);
                    String categoryName = scn.nextLine();

                    LinkedList<FinancialRecord> categoryExpensesList = expensesByCategory.get(categoryName);
                    if (categoryExpensesList != null) {
                        expensesByCategory.remove(categoryName);
                        fileModified = true;

                        try {
                            File inputFile = new File("financial_records.txt");
                            File tempFile = new File("temp_records.txt");
                            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                            String currentLine;
                            boolean found = false;
                            while ((currentLine = reader.readLine()) != null) {
                                if (currentLine.startsWith(categoryName + ":")) {
                                    found = true;
                                    continue;
                                }

                                if (!found) {
                                    writer.write(currentLine);
                                    writer.newLine();
                                }

                                if (currentLine.startsWith("  ")) {
                                    // Skip content associated with the deleted category
                                    continue;
                                }

                                found = false;
                            }

                            writer.close();
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

                if (fileModified) {
                    // Rename the temporary file to the original file
                    File originalFile = new File("financial_records.txt");
                    File tempFile = new File("temp_records.txt");
                    if (originalFile.delete()) {
                        tempFile.renameTo(originalFile);
                    } else {
                        System.out.println("Error deleting the original file");

                    }
                }
            }

            if (Options == 'e') {
                SearchContent scontent = new SearchContent();
                scontent.searchedRecord("");
            }
            if (Options == 'f') {
                updateTargetBudget();

            }
            if (Options == 'a') {
                System.exit(0);
            }

        } while (Options != 'a');

    }
}
