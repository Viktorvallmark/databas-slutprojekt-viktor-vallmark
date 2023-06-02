package org.example;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        InitializeDatabase database = new InitializeDatabase("root", "","localhost", 3306, "ViktorTesting", false);

        Connection k = database.getConnection();
        System.out.println(k);
    }
}