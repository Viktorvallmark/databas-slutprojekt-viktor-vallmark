package org.example;

import org.apache.commons.text.StringEscapeUtils;

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

    public void createQuery(String query, InitializeDatabase data, String type) throws SQLException
    {
        if(type.equals("create") || type.equals("insert")){
        try {
            setDatabase(data);
            Connection connection = database.getConnection();
            setConnection(connection);
            Statement statement = connection.createStatement();
            int results = statement.executeUpdate(query);
            System.out.println("Results of the query: "+ results);


        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        }else if(type.equals("select"))
        {
            setDatabase(data);
            Connection connection = database.getConnection();
            setConnection(connection);
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            String temp = handleQuery(results);

        }
        connection.close();
    }

}
