package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Swosh {

    private ArrayList<User> userList = new ArrayList<>();

    public Swosh(){
        super();
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public boolean addUser(String name, String email, int age, int ID, String password, int personalNr){
        try{
            User user = new User(name, email, ID, password, personalNr);
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

    private void updateFile(String info) throws IOException {
        try {
            File userInfo = new File("C:\\userInfo.txt");
            FileWriter fw = new FileWriter(userInfo);
            fw.write(info);
            fw.flush();
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void userInfoToFile(String infoToWrite) throws IOException {
        try {
            File userInfo = new File("C:\\userInfo.txt");
            if(userInfo.createNewFile()) {
                FileWriter fw = new FileWriter(userInfo);
                fw.write(infoToWrite);
                fw.flush();
                fw.close();
            }else {
                updateFile(infoToWrite);
                System.out.println("File has been updated!");
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
