package com.mycompany.onlinerpg;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vadimkaz58
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DatabaseProcedures {
    public Connection connection;
    DatabaseProcedures(String ipBDServer) {
        String url = "jdbc:mysql://" + ipBDServer +":3306/onlinerpg?serverTimezone=Europe/Moscow";
        String login = "onlinerpgPlayer";
        String password = "user";
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProcedures.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    DatabaseProcedures(String ipBDServer, String login, String pass) {
        String url = "jdbc:mysql://" + ipBDServer +":3306/onlinerpg?serverTimezone=Europe/Moscow";
        try {
            connection = DriverManager.getConnection(url, login, pass);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProcedures.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int[] loginDB(String login, String password) throws SQLException {
        CallableStatement proc = connection.prepareCall("{ call login(?, ?, ?, ?, ?) }");
        proc.setString(1, login);
        proc.setString(2, password);
        proc.execute();
        int[] exitsAndRole = {proc.getInt(3), proc.getInt(4), proc.getInt(5)};
        return exitsAndRole;
    }
    
    public boolean registDB(String login, String password) throws SQLException {
        CallableStatement proc = connection.prepareCall("{ call add_account(?, ?, ?) }");
        proc.setString(1, login);
        proc.setString(2, password);
        proc.execute();
        return proc.getBoolean(3);
    }
    
    public Object[][] getPlayersDB() throws SQLException {
            PreparedStatement statement = connection.prepareStatement("select * from players");
            ResultSet rs = statement.executeQuery();
            rs.next();
            Object[][] ob = new Object[rs.getInt(1)][5];
            int i = 0;
            while (rs.next()) {
                 ob[i][0] = (Object) rs.getInt(1);
                 ob[i][1] = (Object) rs.getString(2);
                 ob[i][2] = (Object) rs.getString(3);
                 ob[i][3] = (Object) rs.getInt(4);
                 ob[i][4] = (Object) rs.getBoolean(5);
                 i++;
            }
            return ob;
    }
    
    public boolean ban(int id) throws SQLException {
        CallableStatement proc = connection.prepareCall("{ call Ban(?, ?) }");
        proc.setInt(1, id);
        proc.execute();
        return proc.getBoolean(2);
    }
    
    public boolean addAdminDB(int id, int role) throws SQLException {
        CallableStatement proc = connection.prepareCall("{ call add_admins(?, ?, ?) }");
        proc.setInt(1, id);
        proc.setInt(2, role);
        proc.execute();
        return proc.getBoolean(3);
    }
    
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseProcedures.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
