package com.company;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionSingleton {
    private static DBConnectionSingleton INSTANCE = new DBConnectionSingleton();
    private static Connection connection;

    private DBConnectionSingleton() {
        try {

            InputStream inputStream = getClass().getResourceAsStream("/resources/db.properties");
            //System.out.println("InputStream " + inputStream);
            InputStreamReader fileReader = new InputStreamReader(inputStream);
            System.out.println("InputStreamReader " + fileReader);
//            FileReader fileReader = new FileReader("com.company.necessaryfiles/query.properties");
            Properties properties = new Properties();
            properties.load(fileReader);


            String username = System.getProperty("username");
            String password = System.getProperty("password");
            System.out.printf("Username:password as environment variable: [%s]:[%s]\n", username, password);

            final String url = properties.getProperty("url");
            final String user_name = properties.getProperty("username");
            final String pass = properties.getProperty("password");

            properties.list(System.out);
            connection = DriverManager.getConnection(url, user_name, pass);

            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/home_task?useSSL=false&allowPublicKeyRetrieval=true", "root", "");
            System.out.println("Connection created");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
