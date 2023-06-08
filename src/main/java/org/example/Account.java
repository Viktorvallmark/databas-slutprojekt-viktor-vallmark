package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Account {

    private final User user;

    private final int accID;

    private double balance;


    public Account(User user, double balance, int id) {
        this.user = user;
        this.balance = balance;
        this.accID = id;
    }

    public User getUser() {
        return user;
    }


    public int getID() {
        return accID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public boolean changeAmount (double amount, @NotNull String addOrRemove) {

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
        return "Account{" +
                "accID=" + accID +
                ", balance=" + balance +
                '}';
    }
}
