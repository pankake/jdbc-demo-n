package com.kaleyra.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Arrays;

public class DatasourceTest {

    public static final String JDBC_URL = "jdbc:hsqldb:file:db/hsqldb-db;shutdown=true;hsqldb.write_delay=false";
    //    private static final String JDBC_URL = "jdbc:hsqldb:mem:inmemory;hsqldb.sqllog=3";
    private static final String USERNAME = "SA";
    private static final String PASSWORD = "";
    private Connection conn;
    PreparedStatement insertUser;
    @Before
    public void setupTest() throws SQLException {
        conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        conn.setAutoCommit(false);
        insertUser = conn.prepareStatement("INSERT INTO user (name, email) VALUES (?, ?)");
    }

    @After
    public void teardown() throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeQuery("");
        conn.commit();
    }

    @Test
    public void initConnection() throws SQLException {


        Statement stmt = conn.createStatement();

        stmt.executeQuery("CREATE TABLE user " +
                        "(id INTEGER IDENTITY PRIMARY KEY, " +
                        "name VARCHAR(30), " +
                        "email  VARCHAR(50))");


        fillUserValues(insertUser, "Luigi", "luigi.toziani@kaleyra.com");
        insertUser.execute();
//        stmt.executeQuery("INSERT INTO user (name, email) VALUES ('Luigi', 'luigi.toziani@kaleyra.com')");

        ResultSet rs = stmt.executeQuery("SELECT * FROM USER");
        while (rs.next()) {
            int x = rs.getInt("id");
            String s = rs.getString("name");
            String f = rs.getString("email");

            System.out.print(x);
            System.out.print(" ");
            System.out.print(s);
            System.out.print(" ");
            System.out.print(f);
            System.out.println();
        }

        conn.commit();
    }


    void fillUserValues(PreparedStatement statement, String... values) throws SQLException {
        for (int i = 1; i <= values.length; i++) {
            statement.setString(i, values[i-1]);
        }
    }

}