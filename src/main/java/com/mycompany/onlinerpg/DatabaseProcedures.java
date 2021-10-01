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
    
    public boolean loginDB(String login, String password) throws SQLException {
        CallableStatement proc = connection.prepareCall("{ call login(?, ?, ?) }");
        proc.setString(1, login);
        proc.setString(2, password);
        proc.execute();
        return proc.getBoolean(3);
    }
    
    public boolean registDB(String login, String password) throws SQLException {
        CallableStatement proc = connection.prepareCall("{ call add_account(?, ?, ?) }");
        proc.setString(1, login);
        proc.setString(2, password);
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
