package org.example;

import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Swosh {

    private ArrayList<User> userList = new ArrayList<>();

    private long userID = 1;


    public Swosh(){
        super();
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public long getUserID(){
        return userID;
    }
    public void incrementUserID() {
        this.userID++;
    }

    public User addUser(String name, String email, int age, int ID, String password, int personalNr){
        try{
            User user = new User(name, email, ID, password, personalNr);
            userList.add(user);
            return user;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void addUser(long id, String pw, long persNr) {
        try{
            User user = new User(pw, persNr, id);
            userList.add(user);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean removeUser(User user){
        try{
            getUserList().remove(user);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updateFile(String info, File file) throws IOException {
        try {

            FileWriter fw = new FileWriter(file);
            fw.write(info);
            fw.flush();
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
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
