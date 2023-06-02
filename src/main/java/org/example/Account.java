package org.example;

public class Account {

    private User user;

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

    public void setUser(User user) {
        this.user = user;
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


    public boolean transferAmount (User toUser, User fromUser, double amount) {


        try {
            if(getBalance() > amount){
                double bal = getBalance();
                double newBal = bal - amount;
                setBalance(newBal);
                return true;
            }else {
                throw new Exception("Not enough money on the account to make the transfer!");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean recieveAmount (User fromUser, int fromId, double amount, User toUser, int toId) {

        try {


        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
