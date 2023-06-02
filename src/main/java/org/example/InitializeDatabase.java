package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;

public class InitializeDatabase {

   private MysqlDataSource dataSource;


    public InitializeDatabase(String username, String password, String url, int port, String database, boolean useSSL)
    {
        initializeDatabase(username,password,url, port, database,useSSL);
    }

    public MysqlDataSource getDataSource()
    {
        return dataSource;
    }
    public MysqlDataSource initializeDatabase(String username, String password, String url, int port, String database, boolean useSSL)
    {
        try
        {
            System.out.println("Configuring data source...");
            this.dataSource = new MysqlDataSource();
            dataSource.setUser(username);
            dataSource.setPassword(password);
            dataSource.setURL("jdbc:mysql://"+ url +":"+ port +"/"+ database +"?serverTimezone=UTC");
            dataSource.setUseSSL(useSSL);
            System.out.println("Connection to the database is done!");
            System.out.println(database);
            return dataSource;
        }catch(Exception e)
        {
            System.out.println("fail");
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
    public Connection getConnection()
    {
        try
        {
            MysqlDataSource database = getDataSource();
            System.out.println("Fetching database...");
            System.out.println("done!");
            return database.getConnection();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("Connection to the database failed!");
            System.exit(0);
            return null;
        }
    }
}
