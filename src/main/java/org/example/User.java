package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class User {

    private ArrayList<Account> accounts = new ArrayList<>();
    private String name;
    private String email;

    private String password;

    private final int personalNr;
    private final int userID;

    private int phoneNr;
    private String address;


    public User ( String name, String email,  int userID, String password, int personalNr) {

        this.name = name;
        this.email = email;
        this.userID = userID;
        this.password = password;
        this.personalNr = personalNr;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPersonalNr() {
        return personalNr;
    }

    public int getUserID(){
        return userID;
    }
    public int getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(int phoneNr) {
        this.phoneNr = phoneNr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    public boolean transferAmount (@NotNull Swosh swosh, User toUser, Account toAcc, User fromUser, Account fromAcc, double amount) {

        if(swosh.getUserList().contains(fromUser) && swosh.getUserList().contains(toUser)) {
            boolean transferFrom = fromAcc.changeAmount(amount, "remove");
            boolean transferTo = toAcc.changeAmount(amount, "add");
            return transferFrom && transferTo;
        }

        return false;
    }

    private String printAccounts(@NotNull ArrayList<Account> accList) {
        String temp = "";
        for(Account acc : accList) {
            temp += acc.toString();
        }
    return temp;
    }

    @Override
    public String toString() {
        return "User{" +
                "accounts=" + printAccounts(accounts) +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", personalNr=" + personalNr +
                ", userID=" + userID +
                ", phoneNr=" + phoneNr +
                ", address='" + address + '\'' +
                '}';
    }
}
