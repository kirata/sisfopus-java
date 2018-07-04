/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisfopus;

import javax.swing.JOptionPane;

/**
 *
 * @author cosnors
 */
public class FormLogin extends javax.swing.JFrame {
    KoneksiDB kon = new KoneksiDB();
    public String denda;
    public FormMenuUtamaAdmin menu = null;

    /**
     * Creates new form Aplikasi_Login
     */
    public FormLogin() {
        initComponents();
        kon.setKoneksi();
        tLevel.setVisible(false);
        
        
    }
    
//    public String getDendaPerhari(){
//        
//        
//   }
    
    void bersih(){
        tKodePegawai.setText("");
        tPassword.setText("");
        tLevel.setText("");
    }
   
    private void prosesLogin(){
        String kodePegawai = "";
        String password = "";
        
        kodePegawai=tKodePegawai.getText();
        password = tPassword.getText();
        try {
            String sql = "SELECT * FROM pegawai "
                    + "WHERE kode_pegawai = '"+kodePegawai+"' OR nip = '"+kodePegawai+"' AND password = '"+password+"'";
            kon.rs=kon.st.executeQuery(sql);
            
            if (kon.rs.next()) {
               if (tLevel.getText().equals("1")) {
                    FormMenuUtamaAdmin admin = new FormMenuUtamaAdmin();
                    FormPengaturanPassword gantipassword = new FormPengaturanPassword();

                    admin.setLocationRelativeTo(null);
                    admin.setVisible(true);

                    admin.NamaPegawai = kon.rs.getString("nama_pegawai");
                    admin.KodePegawai = kon.rs.getString("kode_pegawai");
                    admin.Level  = kon.rs.getString("level_pegawai");

                    gantipassword.KodePegawai = kon.rs.getString("kode_pegawai");

                    this.dispose();
                }
                else if(tLevel.getText().equals("2")){
                    FormMenuUtamaPustakawan menu = new FormMenuUtamaPustakawan();
                    FormPengaturanPassword gantipassword = new FormPengaturanPassword();

                    menu.setLocationRelativeTo(null);
                    menu.setVisible(true);
                    menu.NamaPegawai = kon.rs.getString("nama_pegawai");
                    menu.KodePegawai = kon.rs.getString("kode_pegawai");
                    menu.Level  = kon.rs.getString("level_pegawai");
                    
                    gantipassword.KodePegawai = kon.rs.getString("kode_pegawai");
                    this.dispose();
                }  
            }
              
            else {
                JOptionPane.showMessageDialog(null, "Kode pegawai/nip dan Password tidak sesuai");
                bersih();
                tKodePegawai.setEnabled(true);
                tKodePegawai.requestFocus();
            }
            
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tKodePegawai = new javax.swing.JTextField();
        btLogin = new javax.swing.JButton();
        btKeluar = new javax.swing.JButton();
        tLevel = new javax.swing.JTextField();
        tPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel1.setText("LOGIN");

        jLabel2.setText("Kode Pegawai");

        jLabel3.setText("Password");

        tKodePegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tKodePegawaiActionPerformed(evt);
            }
        });

        btLogin.setText("LOGIN");
        btLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLoginActionPerformed(evt);
            }
        });

        btKeluar.setText("KELUAR");
        btKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(157, 157, 157))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tKodePegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(btLogin)
                                .addGap(116, 116, 116)
                                .addComponent(btKeluar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tPassword)
                                        .addGap(12, 12, 12)))))))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tKodePegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btLogin)
                    .addComponent(btKeluar))
                .addGap(49, 49, 49))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tKodePegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tKodePegawaiActionPerformed
        // TODO add your handling code here:
        String kodePegawai = "";
        kodePegawai = tKodePegawai.getText();
        try {
            String sql = " SELECT * FROM pegawai WHERE kode_pegawai = '"+ kodePegawai +"' OR nip = '"+ kodePegawai +"' ";
            kon.rs = kon.st.executeQuery(sql);
            if (kon.rs.next()) {
                tKodePegawai.setEnabled(false);
                tLevel.setText(kon.rs.getString("level_pegawai"));
                tPassword.setEnabled(true);
                tPassword.requestFocus();
                btLogin.setEnabled(true);
                tKodePegawai.setEnabled(true);
            }
            else {
                JOptionPane.showMessageDialog(null, "Kode pegawai/nip Salah");
                bersih();
                tKodePegawai.setEnabled(true);
                tKodePegawai.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }
        
        
    }//GEN-LAST:event_tKodePegawaiActionPerformed

    private void btLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLoginActionPerformed
        // TODO add your handling code here:
        prosesLogin();
    }//GEN-LAST:event_btLoginActionPerformed

    private void btKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKeluarActionPerformed
        // TODO add your handling code here:
//        System.exit(0);
        dispose();
    }//GEN-LAST:event_btKeluarActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        tPassword.setEnabled(false);
        btLogin.setEnabled(false);
        tKodePegawai.requestFocus();
    }//GEN-LAST:event_formWindowActivated

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btKeluar;
    private javax.swing.JButton btLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField tKodePegawai;
    private javax.swing.JTextField tLevel;
    private javax.swing.JPasswordField tPassword;
    // End of variables declaration//GEN-END:variables
}
