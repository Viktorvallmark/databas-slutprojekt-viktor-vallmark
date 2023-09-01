package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Account {

    private final User user;

    private final long accID;

    private ArrayList<Transactions> transactions = new ArrayList<>();

    private double balance;


    public Account(User user, double balance, long id) {
        this.user = user;
        this.balance = balance;
        this.accID = id;
    }

    public User getUser() {
        return user;
    }


    public long getID() {
        return accID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }

    public void addBalance(double balance) {
        this.balance += balance;
    }


    public boolean changeAmount (double amount, @NotNull String addOrRemove) {
    //TODO:Change to incorporate Transactions class and the arraylist of transactions
        double bal = getBalance();
        if(addOrRemove.equals("add")) {
            setBalance(bal+amount);
            return true;
        }else if (addOrRemove.equals("remove")) {
            if(bal-amount >0) {
                setBalance(bal - amount);
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {

        HashMap<Object, Object> transactionMap = new HashMap<>();

        for (Transactions transact : transactions) {
            transactionMap.putIfAbsent(transact.date(), transact.amount());
        }


        return "Account{" +
                "AccID=" + accID +
                ", Transactions=" + transactionMap +
                ", Balance=" + balance +'}';
    }
}
