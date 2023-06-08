package org.example;

import java.sql.*;

public class CreateQuery {

   private InitializeDatabase database;
   private Connection connection;


    public CreateQuery()
    {
        super();
    }
    private void setConnection(Connection connection)
    {
        this.connection = connection;
    }
    private void setDatabase(InitializeDatabase data)
    {
        this.database = data;
    }

    private String handleQuery(ResultSet resultSet) throws SQLException {

        int id = 0;
        String name = "";
        String email="";
        String phone="";
        String address="";
        String password="";
        int personalNr=0;

        while (resultSet.next()) {
            id = resultSet.getInt("id");
            name = resultSet.getString("name");
            email = resultSet.getString("email");
            phone = resultSet.getString("phone");
            address = resultSet.getString("address");
            password = resultSet.getString("password");
            personalNr = resultSet.getInt("personalnumber");
        }
        return "User:{ " + id +", "+name+", "+email+", "+phone+", "+address+", "+password+", "+personalNr+" }";
    }

    public String createQuery(String query, InitializeDatabase data, String type, String table) throws SQLException
    {
        if(type.equals("create") || type.equals("insert")){
        try {
            setDatabase(data);
            Connection connection = database.getConnection();
            setConnection(connection);
            Statement statement = connection.createStatement();
            int results = statement.executeUpdate(query);
            System.out.println("Results of the query: "+ results);
            return String.valueOf(results);


        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        }else if(type.equals("select") && table.equals("user"))
        {
            setDatabase(data);
            Connection connection = database.getConnection();
            setConnection(connection);
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            return handleQuery(results);
        }else if(type.equals("select") && table.equals("account"))
        {
            setDatabase(data);
            Connection connection = database.getConnection();
            setConnection(connection);
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            int id = 0;
            String username = "";
            double balance = 0.0d;

            while(results.next()) {
                id = results.getInt("userid");
                username = results.getString("username");
                balance = results.getDouble("balance");
            }

            return "Account:{userid:"+id+",username: "+ username+ ",balance "+balance+"}";
        }else if(type.equals("select") && table.equals("transactions"))
        {
            setDatabase(data);
            Connection connection = database.getConnection();
            setConnection(connection);
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            int id = 0;
            String date = "";
            String accnamefrom = "";
            double amount = 0.0d;

            while(results.next()) {
                id = results.getInt("userid");
                date = results.getString("date");
                accnamefrom = results.getString("accnamefrom");
                amount = results.getDouble("balance");
            }

            return "Account:{userid:"+id+", date:"+date+"from_account: "+ accnamefrom+ ",amount "+amount+"}";
        }
        connection.close();
        return "Please use a proper SQL query!";
    }

}
