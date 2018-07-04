/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisfopus;

/**
 *
 * @author cosnors
 */
public class SisfoPus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
//        Ngecek koneksi jalan atau enggak
//        KoneksiDB kon = new KoneksiDB();
//        kon.setKoneksi();

//        Load menu utama
        FormLogin formlogin = new FormLogin();
        formlogin.setVisible(true);
    }
    
}
