/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisfopus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author cosnors
 */
public class KoneksiDB {
    Connection conn;
    Statement st;
    ResultSet rs;
    
    public Connection setKoneksi(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost:33062/sisfopus_db","root","root");
            st=conn.createStatement();
            
//            JOptionPane.showMessageDialog(null,"Sip, Koneksi udah jalan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Koneksi Gagal Bro : " +e);
        }
       
        return conn;
    }
    
}
