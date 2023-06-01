package FinancialRecord;

import java.util.*;

public class FinancialRecord {

    private double amount;
    private Date date;
    private String description;
    private String category;

    public FinancialRecord(double amount, Date date, String description, String category) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double neamount) {
        this.amount = neamount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date newdate) {
        this.date = newdate;
    }

    public String getDescrip() {
        return description;
    }

    public void setDescript(String newdesc) {
        this.description = newdesc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategry(String catg) {
        this.category = catg;
    }

    @Override
    public String toString() {
        return "Amount: " + amount + "\nDate: " + date + "\nDescription: " + description + "\nCategory: " + category;
    }
}