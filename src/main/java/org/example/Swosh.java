package org.example;

import java.util.ArrayList;

public class Swosh {

    private ArrayList<User> userList = new ArrayList<>();

    public Swosh(){
        super();
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public boolean addUser(String name, String email, int age, int ID){
        try{
            User user = new User(name, email, age, ID);
            getUserList().add(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeUser(User user){
        try{
            getUserList().remove(user);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
