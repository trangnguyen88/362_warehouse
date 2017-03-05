package com.champion.dao;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class BaseDAO {

    public BaseDAO(){
        connect();
    }

    protected Connection connection = null;

    private boolean connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try {
            this.connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/warehouse","root", "root123$");

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    protected ResultSet getResultSet(String query){
        Statement stmt = null;
        try {
            stmt = this.connection.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected int executeScalar(String query){
        Statement stmt = null;
        try {
            stmt = this.connection.createStatement();
            ResultSet records = stmt.executeQuery(query);
            if(records.next()){
                return records.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    protected int executeUpdate(String query){
        Statement stmt = null;
        try {
            stmt = this.connection.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
