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

    private String handleQuery(ResultSet resultSet, InitializeDatabase data, String tableName) throws SQLException
    {
        while(resultSet.next())
        {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            Date created = resultSet.getDate("created");
            boolean online = resultSet.getBoolean("online");
            String phone = resultSet.getString("phone");
            String address = resultSet.getString("address");
        }
        return "";
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

        }
        connection.close();
    }

}
