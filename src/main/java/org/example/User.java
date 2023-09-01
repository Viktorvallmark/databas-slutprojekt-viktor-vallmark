package org.example;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;

public class User {

    private final ArrayList<Account> accounts = new ArrayList<>();

    private String name = "";
    private String email = "";

    private String password;

    private final long personalNr;
    private final long userID;

    private int phoneNr = 0;
    private String address = "";


    public User ( String name, String email, long userID, String password, int personalNr) {

        this.name = name;
        this.email = email;
        this.userID = userID;
        this.password = password;
        this.personalNr = personalNr;
    }

    public User (String password, long personalNr, long userID) {
        this.password = password;
        this.personalNr = personalNr;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public long getPersonalNr() {
        return personalNr;
    }

    public long getUserID(){
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

    public boolean addAccount (User user, long accID, double amount) {
        try{
        Account newAcc = new Account(user,amount,accID);
        accounts.add(newAcc);
        return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeAccount (User user, Account acc) {
        try {
            user.getAccount().remove(acc);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public Transactions transferAmount (@NotNull Swosh swosh, User toUser, Account toAcc, User fromUser, Account fromAcc, double amount) {
        //TODO: Change transferAmount to incorporate Account class and its method changeAmount better
        Instant time = Instant.now();
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        try {
            if (swosh.getUserList().contains(fromUser) && swosh.getUserList().contains(toUser)) {
                boolean transferFrom = fromAcc.changeAmount(amount, "remove");
                boolean transferTo = toAcc.changeAmount(amount, "add");
                return new Transactions(date, fromUser, toUser, fromAcc, toAcc, amount);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String printAccounts(@NotNull ArrayList<Account> accList) {
        StringBuilder temp = new StringBuilder();
        for(Account acc : accList) {
            temp.append(acc.toString());
        }
    return temp.toString();
    }

    @Override
    public String toString() {
        return "User{" +
                " name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", personalNr=" + personalNr +
                ", userID=" + userID +
                ", phoneNr=" + phoneNr +
                ", address='" + address + '\'' +
                '}';
    }
}
