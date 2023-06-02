package org.example;

import java.util.ArrayList;

public class User {

    private ArrayList<Account> accounts = new ArrayList<>();
    private String name;
    private String email;
    private int age;
    private final int userID;

    public User ( String name, String email, int age, int userID) {

        this.name = name;
        this.email = email;
        this.age = age;
        this.userID = userID;
    }

    public ArrayList<Account> getAccount() {
        return accounts;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUserID(){
        return userID;
    }

    public void addAccount (User user, int accID, double amount) {
        try{
        Account newAcc = new Account(user,amount,accID);
        ArrayList<Account> accList = getAccount();
        accList.add(newAcc);
        throw new Exception("Couldn't add account to user!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String removeAccount (User user, Account acc) {
        try {
            user.getAccount().remove(acc);
            return "Account: "+acc.getID()+" from User: "+acc.getUser()+" has been deleted!";
        }catch (Exception e) {
            e.printStackTrace();
        }

        return "No such account found on user!";
    }
    public boolean transferAmount (Swosh swosh, User toUser, Account toAcc, User fromUser, Account fromAcc, double amount) {

        if(swosh.getUserList().contains(fromUser) && swosh.getUserList().contains(toUser)) {

        }


        return true;
    }

}
