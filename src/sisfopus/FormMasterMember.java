/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisfopus;

import java.awt.event.KeyEvent;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cosnors
 */
public class FormMasterMember extends javax.swing.JFrame {
    KoneksiDB kon = new KoneksiDB();
    private Object [] [] dataMember=null;
    private String [] label = {"Kode Member","Nama","NIM","Telepon","Waktu Bergabung","Status"};
    private String nim;
    /**
     * Creates new form FormBuku
     */
    public FormMasterMember() {
        initComponents();
        kon.setKoneksi();
        BacaTabelMember();
        nonaktif();
    }
    public String generateKodeMember(String nim) { // Ditambahkan parameter nim karena kode yang di generate mengandung NIM
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String uniq = sdf.format(new Date());
        return "MEM"+nim;
    }
    public String currentWaktuSekarang() { // Ditambahkan parameter nim karena kode yang di generate mengandung NIM
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ii:ss");
        String uniq = sdf.format(new Date());
        return uniq;
    }
    private void BacaTabelMember()
    {
        try{
            String sql="SELECT * FROM member ORDER BY waktu_bergabung_member DESC";
            kon.rs=kon.st.executeQuery(sql);
            ResultSetMetaData m=kon.rs.getMetaData();
            int kolom=m.getColumnCount();
            int baris=0;
            while(kon.rs.next()){
                baris=kon.rs.getRow();
            }
            dataMember=new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
                dataMember[x][0]=kon.rs.getString("kode_member");
                dataMember[x][1]=kon.rs.getString("nama_member");
                dataMember[x][2]=kon.rs.getString("nim_member");
                dataMember[x][3]=kon.rs.getString("telepon_member");
                dataMember[x][4]=kon.rs.getString("waktu_bergabung_member");
                dataMember[x][5]=kon.rs.getString("status_member");
                x++;
            }
            tblMember.setModel(new DefaultTableModel(dataMember,label));


        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
        private void BacaTabelMemberCari()
        {
            try{
                String sql="SELECT * FROM member WHERE nama_member LIKE '%"+tCari.getText()+"%'";
                kon.rs=kon.st.executeQuery(sql);
                ResultSetMetaData m=kon.rs.getMetaData();
                int kolom=m.getColumnCount();
                int baris=0;
                while(kon.rs.next()){
                    baris=kon.rs.getRow();
            }
                dataMember=new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
                dataMember[x][0]=kon.rs.getString("kode_member");
                dataMember[x][1]=kon.rs.getString("nama_member");
                dataMember[x][2]=kon.rs.getString("nim_member");
                dataMember[x][3]=kon.rs.getString("telepon_member");
                dataMember[x][4]=kon.rs.getString("waktu_bergabung_member");
                dataMember[x][5]=kon.rs.getString("status_member");
                x++;
        }
            tblMember.setModel(new DefaultTableModel(dataMember,label));
        }
            catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
        private void SetTabel()
        {
            int row=tblMember.getSelectedRow();
            tKdMember.setText((String)tblMember.getValueAt(row, 0));
            tNamaMember.setText((String)tblMember.getValueAt(row, 1));
            tNim.setText((String)tblMember.getValueAt(row, 2));
            tTeleponMember.setText((String)tblMember.getValueAt(row, 3));
            tStatusMember.setText((String)tblMember.getValueAt(row, 5));
        }
        private void Bersih()
        {
            tKdMember.setText("");
            tNamaMember.setText("");
            tNim.setText("");
            tTeleponMember.setText("");
            tStatusMember.setText("");
        }
        private void aktif()
        {
            tKdMember.setEnabled(true);
            tNamaMember.setEnabled(true);
            tNim.setEnabled(true);
            tTeleponMember.setEnabled(true);
            tStatusMember.setEnabled(true);
        }
        private void nonaktif()
        {
            tKdMember.setEnabled(false);
            tNamaMember.setEnabled(false);
            tNim.setEnabled(false);
            tTeleponMember.setEnabled(false);
            tStatusMember.setEnabled(false);
        }
        private void SimpanData()
        {
            try{
                String sql=" INSERT INTO member(kode_member,nama_member,nim_member,telepon_member,waktu_bergabung_member,status_member) "
                        + "VALUES('"+tKdMember.getText()+"',"
                        +"'"+tNamaMember.getText()+"','"
                        + ""+tNim.getText()+"',"
                        + "'"+tTeleponMember.getText()+"',"
                        + "CURRENT_TIMESTAMP()," // otomatis insert waktu saat eksekusi
                        +"'"+tStatusMember.getText()+"')";
                System.out.println(sql);
                kon.st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data Member Berhasil Disimpan");
                Bersih();
                BacaTabelMember();
                System.out.println(sql);
            }
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        private void UpdateData()
        {
            try{
                String sql = "UPDATE member SET nama_member='"+tNamaMember.getText()+"',"
                        +"nim_member='"+tNim.getText()+"',"
                        +"telepon_member='"+tTeleponMember.getText()+"',"
                        +"status_member='"+tStatusMember.getText()+"' WHERE kode_member='"+tKdMember.getText()+"'";
                kon.st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null,"Data Member Berhasil Di Update");
                Bersih();
                BacaTabelMember();
            }
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        private void HapusData()
        {
            try{
                String sql="DELETE FROM member WHERE kode_member = '"+tKdMember.getText()+"'";
                kon.st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data Member Berhasil Dihapus");
                Bersih();
                BacaTabelMember();
            }
            catch (SQLException e)
            {
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

        jLabel3 = new javax.swing.JLabel();
        btKeluar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tKdMember = new javax.swing.JTextField();
        tNamaMember = new javax.swing.JTextField();
        tNim = new javax.swing.JTextField();
        tTeleponMember = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tStatusMember = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMember = new javax.swing.JTable();
        btTambah = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("INPUT DATA MEMBER");

        btKeluar.setText("KELUAR");
        btKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKeluarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATA MEMBER", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 11))); // NOI18N

        jLabel4.setText("Kode Member");

        jLabel5.setText("Nama ");

        jLabel6.setText("NIM");

        jLabel7.setText("Nomor Telepon");

        tKdMember.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tKdMemberKeyPressed(evt);
            }
        });

        tNamaMember.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tNamaMemberKeyPressed(evt);
            }
        });

        tNim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tNimKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tNimKeyPressed(evt);
            }
        });

        jLabel8.setText("Status");

        tStatusMember.setEditable(false);

        jLabel9.setText("1.Aktif 2.Nonaktif");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tKdMember, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(tTeleponMember, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tStatusMember))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(tNamaMember, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                        .addComponent(tNim, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tKdMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tNamaMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tNim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tTeleponMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tStatusMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Data Member");

        jLabel2.setText("Cari Member");

        tCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tCariKeyTyped(evt);
            }
        });

        tblMember.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblMember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMemberMouseClicked(evt);
            }
        });
        tblMember.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblMemberKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblMember);

        btTambah.setText("TAMBAH");
        btTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btTambah)
                                        .addGap(432, 432, 432)
                                        .addComponent(btKeluar)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(231, 231, 231))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(212, 212, 212))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btTambah)
                    .addComponent(btKeluar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKeluarActionPerformed
        if(btKeluar.getText().equals("KELUAR"))
        {
            dispose();
        }
        else if(btKeluar.getText().equals("BATAL"))
        {
            Bersih();
            nonaktif();
            btKeluar.setText("KELUAR");
            btTambah.setText("TAMBAH");
        }

    }//GEN-LAST:event_btKeluarActionPerformed

    private void tKdMemberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tKdMemberKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            try{
                String sql ="SELECT * FROM buku WHERE kode_buku='"+tKdMember.getText()+"'";
                kon.rs=kon.st.executeQuery(sql);
                if (kon.rs.next()){
                    tKdMember.setEnabled(false);
                    tKdMember.setText(kon.rs.getString("kode_buku"));
                    tNamaMember.setText(kon.rs.getString("judul_buku"));
                    tNim.setText(kon.rs.getString("penerbit_buku"));
                    tTeleponMember.setText(kon.rs.getString("penulis_buku"));
                    tStatusMember.setText(kon.rs.getString("stok_buku"));
                    aktif();
                    btTambah.setText("UPDATE");
                }else{
                    tNamaMember.requestFocus();
                }
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_tKdMemberKeyPressed

    private void tNamaMemberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tNamaMemberKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tNim.requestFocus();
        }
    }//GEN-LAST:event_tNamaMemberKeyPressed

    private void tNimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tNimKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tTeleponMember.requestFocus();
            tKdMember.setText(generateKodeMember(tNim.getText()));
        }
//        else if(evt.getKeyCode()==KeyEvent.KEY_LOCATION_NUMPAD){
//            tKdMember.setText(generateKodeMember(tNim.getText()));
//        }
    }//GEN-LAST:event_tNimKeyPressed

    private void tCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tCariKeyTyped
        // TODO add your handling code here:
        kon.setKoneksi();
        BacaTabelMemberCari();
    }//GEN-LAST:event_tCariKeyTyped

    private void tblMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMemberMouseClicked
        SetTabel();
    }//GEN-LAST:event_tblMemberMouseClicked

    private void tblMemberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMemberKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            HapusData();
        }
    }//GEN-LAST:event_tblMemberKeyPressed

    private void btTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTambahActionPerformed
        if(btTambah.getText().equals("TAMBAH"))
        {
            btTambah.setText("SIMPAN");
            btKeluar.setText("BATAL");
            Bersih();
            aktif();
            tStatusMember.setText("1");
        }
        else if(btTambah.getText().equals("SIMPAN"))
        {
            SimpanData();
            BacaTabelMember();
            btTambah.setText("TAMBAH");
            btKeluar.setText("KELUAR");
            Bersih();
            nonaktif();
        }
        else if(btTambah.getText().equals("UPDATE"))
        {
            UpdateData();
            BacaTabelMember();
            btTambah.setText("TAMBAH");
            btKeluar.setText("KELUAR");
            Bersih();
            nonaktif();
        }
    }//GEN-LAST:event_btTambahActionPerformed

    private void tNimKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tNimKeyTyped
        // TODO add your handling code here:
//        tKdMember.setText(generateKodeMember(tNim.getText()));
    }//GEN-LAST:event_tNimKeyTyped

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
            java.util.logging.Logger.getLogger(FormMasterMember.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMasterMember.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMasterMember.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMasterMember.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormMasterMember().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btKeluar;
    private javax.swing.JButton btTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tCari;
    private javax.swing.JTextField tKdMember;
    private javax.swing.JTextField tNamaMember;
    private javax.swing.JTextField tNim;
    private javax.swing.JTextField tStatusMember;
    private javax.swing.JTextField tTeleponMember;
    private javax.swing.JTable tblMember;
    // End of variables declaration//GEN-END:variables
}
