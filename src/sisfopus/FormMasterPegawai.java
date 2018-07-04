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
public class FormMasterPegawai extends javax.swing.JFrame {
    KoneksiDB kon = new KoneksiDB();
    private Object [] [] dataPegawai=null;
    private String [] label = {"Kode Pegawai","Nama","NIP","Telepon","Level","Status"};
    private String nim;
    /**
     * Creates new form FormBuku
     */
    public FormMasterPegawai() {
        initComponents();
        kon.setKoneksi();
        BacaTabelMember();
        nonaktif();
    }
    public String generateKodePegawai(String nip) { // Ditambahkan parameter nip karena kode yang di generate mengandung NIM
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        String uniq = sdf.format(new Date());
        return "PEG"+uniq+nip;
    }
    private void BacaTabelMember()
    {
        try{
            String sql="SELECT * FROM pegawai ORDER BY kode_pegawai DESC";
            kon.rs=kon.st.executeQuery(sql);
            ResultSetMetaData m=kon.rs.getMetaData();
            int kolom=m.getColumnCount();
            int baris=0;
            while(kon.rs.next()){
                baris=kon.rs.getRow();
            }
            dataPegawai=new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
                dataPegawai[x][0]=kon.rs.getString("kode_pegawai");
                dataPegawai[x][1]=kon.rs.getString("nama_pegawai");
                dataPegawai[x][2]=kon.rs.getString("nip");
                dataPegawai[x][3]=kon.rs.getString("telepon_pegawai");
                dataPegawai[x][4]=kon.rs.getString("level_pegawai");
                dataPegawai[x][5]=kon.rs.getString("status_pegawai");
                x++;
            }
            tblPegawai.setModel(new DefaultTableModel(dataPegawai,label));


        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
        private void BacaTabelMemberCari()
        {
            try{
                String sql="SELECT * FROM pegawai WHERE nama_pegawai LIKE '%"+tCari.getText()+"%'";
                kon.rs=kon.st.executeQuery(sql);
                ResultSetMetaData m=kon.rs.getMetaData();
                int kolom=m.getColumnCount();
                int baris=0;
                while(kon.rs.next()){
                    baris=kon.rs.getRow();
            }
                dataPegawai=new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
                dataPegawai[x][0]=kon.rs.getString("kode_pegawai");
                dataPegawai[x][1]=kon.rs.getString("nama_pegawai");
                dataPegawai[x][2]=kon.rs.getString("nip");
                dataPegawai[x][3]=kon.rs.getString("telepon_pegawai");
                dataPegawai[x][4]=kon.rs.getString("level_pegawai");
                dataPegawai[x][5]=kon.rs.getString("status_pegawai");
                x++;
        }
            tblPegawai.setModel(new DefaultTableModel(dataPegawai,label));
    }
            catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
        private void SetTabel()
        {
            int row=tblPegawai.getSelectedRow();
            tKdPegawai.setText((String)tblPegawai.getValueAt(row, 0));
            tNamaPegawai.setText((String)tblPegawai.getValueAt(row, 1));
            tNip.setText((String)tblPegawai.getValueAt(row, 2));
            tTeleponPegawai.setText((String)tblPegawai.getValueAt(row, 3));
            tLevelPegawai.setText((String)tblPegawai.getValueAt(row, 4));
            tStatusPegawai.setText((String)tblPegawai.getValueAt(row, 5));
        }
        private void Bersih()
        {
            tKdPegawai.setText("");
            tNamaPegawai.setText("");
            tNip.setText("");
            tPassword.setText("");
            tTeleponPegawai.setText("");
            tLevelPegawai.setText("");
            tStatusPegawai.setText("");
        }
        private void aktif()
        {
            tKdPegawai.setEnabled(true);
            tNamaPegawai.setEnabled(true);
            tNip.setEnabled(true);
            tPassword.setEnabled(true);
            tTeleponPegawai.setEnabled(true);
            tLevelPegawai.setEnabled(true);
            tStatusPegawai.setEnabled(true);
        }
        private void nonaktif()
        {
            tKdPegawai.setEnabled(false);
            tNamaPegawai.setEnabled(false);
            tNip.setEnabled(false);
            tPassword.setEnabled(false);
            tTeleponPegawai.setEnabled(false);
            tLevelPegawai.setEnabled(false);
            tStatusPegawai.setEnabled(false);
        }
        private void SimpanData()
        {
            try{
                String sql=" INSERT INTO pegawai(kode_pegawai,nip,password,nama_pegawai,telepon_pegawai,level_pegawai,status_pegawai) "
                        + "VALUES('"+tKdPegawai.getText()+"',"
                        +"'"+tNip.getText()+"',"
                        +"'"+tPassword.getText()+"',"
                        +"'"+tNamaPegawai.getText()+"',"
                        +"'"+tTeleponPegawai.getText()+"',"
                        +"'"+tStatusPegawai.getText()+"'," 
                        +"'"+tLevelPegawai.getText()+"')";
                kon.st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data Pegawai Berhasil Disimpan");
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
                String sql = "UPDATE pegawai SET "
                        +"nama_pegawai='"+tNamaPegawai.getText()+"',"
                        +"nip='"+tNip.getText()+"',"
                        +"password='"+tPassword.getText()+"',"
                        +"telepon_pegawai='"+tTeleponPegawai.getText()+"',"
                        +"status_pegawai='"+tStatusPegawai.getText()+"',"
                        +"level_pegawai='"+tLevelPegawai.getText()+"' "
                        + "WHERE kode_pegawai='"+tKdPegawai.getText()+"'";
                kon.st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null,"Data Pegawai Berhasil Di Update");
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
                String sql="DELETE FROM pegawai WHERE kode_pegawai = '"+tKdPegawai.getText()+"'";
                kon.st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data Pegawai Berhasil Dihapus");
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
        tKdPegawai = new javax.swing.JTextField();
        tNamaPegawai = new javax.swing.JTextField();
        tNip = new javax.swing.JTextField();
        tPassword = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tLevelPegawai = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tStatusPegawai = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tTeleponPegawai = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPegawai = new javax.swing.JTable();
        btTambah = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("INPUT DATA PEGAWAI");

        btKeluar.setText("KELUAR");
        btKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKeluarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATA PEGAWAI", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 11))); // NOI18N

        jLabel4.setText("Kode Pegawai");

        jLabel5.setText("Nama ");

        jLabel6.setText("NIP");

        jLabel7.setText("Password");

        tKdPegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tKdPegawaiKeyPressed(evt);
            }
        });

        tNamaPegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tNamaPegawaiKeyPressed(evt);
            }
        });

        tNip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tNipKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tNipKeyPressed(evt);
            }
        });

        jLabel8.setText("Level");

        tLevelPegawai.setEditable(false);

        jLabel9.setText("1.Petugas 2.Kepala");

        jLabel10.setText("Status");

        tStatusPegawai.setEditable(false);

        jLabel11.setText("1.Aktif 2.Nonaktif");

        jLabel12.setText("Telepon");

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
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tKdPegawai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(tPassword, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tLevelPegawai)
                            .addComponent(tTeleponPegawai, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(tNamaPegawai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                        .addComponent(tNip, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tStatusPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tKdPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tNamaPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tNip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(tTeleponPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tLevelPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tStatusPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Data Pegawai");

        jLabel2.setText("Cari Pegawai");

        tCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tCariKeyTyped(evt);
            }
        });

        tblPegawai.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPegawaiMouseClicked(evt);
            }
        });
        tblPegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblPegawaiKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblPegawai);

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
                .addComponent(jLabel3)
                .addGap(219, 219, 219))
            .addGroup(layout.createSequentialGroup()
                .addGap(271, 271, 271)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void tKdPegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tKdPegawaiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            try{
                String sql ="SELECT * FROM pegawai WHERE kode_pegawai='"+tKdPegawai.getText()+"'";
                kon.rs=kon.st.executeQuery(sql);
                if (kon.rs.next()){
                    tKdPegawai.setEnabled(false);
                    tKdPegawai.setText(kon.rs.getString("kode_pegawai"));
                    tNamaPegawai.setText(kon.rs.getString("nama_pegawai"));
                    tNip.setText(kon.rs.getString("nip"));
//                    tPassword.setText(kon.rs.getString("password"));
                    tTeleponPegawai.setText(kon.rs.getString("telepon_pegawai"));
                    tLevelPegawai.setText(kon.rs.getString("level_pegawai"));
                    aktif();
                    btTambah.setText("UPDATE");
                }else{
                    tNamaPegawai.requestFocus();
                }
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_tKdPegawaiKeyPressed

    private void tNamaPegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tNamaPegawaiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tNip.requestFocus();
        }
    }//GEN-LAST:event_tNamaPegawaiKeyPressed

    private void tNipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tNipKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tPassword.requestFocus();
            tKdPegawai.setText(generateKodePegawai(tNip.getText()));
        }
        else if(evt.getKeyCode()==KeyEvent.KEY_LOCATION_NUMPAD){
            tKdPegawai.setText(generateKodePegawai(tNip.getText()));
        }
    }//GEN-LAST:event_tNipKeyPressed

    private void tCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tCariKeyTyped
        // TODO add your handling code here:
        kon.setKoneksi();
        BacaTabelMemberCari();
    }//GEN-LAST:event_tCariKeyTyped

    private void tblPegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPegawaiMouseClicked
        SetTabel();
        tKdPegawai.setEnabled(true);
    }//GEN-LAST:event_tblPegawaiMouseClicked

    private void tblPegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblPegawaiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            HapusData();
        }
    }//GEN-LAST:event_tblPegawaiKeyPressed

    private void btTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTambahActionPerformed
        if(btTambah.getText().equals("TAMBAH"))
        {
            btTambah.setText("SIMPAN");
            btKeluar.setText("BATAL");
            Bersih();
            aktif();
            tLevelPegawai.setText("1");
            tStatusPegawai.setText("1");
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

    private void tNipKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tNipKeyTyped
        // TODO add your handling code here:
//        tKdMember.setText(generateKodePegawai(tNim.getText()));
    }//GEN-LAST:event_tNipKeyTyped

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
            java.util.logging.Logger.getLogger(FormMasterPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMasterPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMasterPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMasterPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new FormMasterPegawai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btKeluar;
    private javax.swing.JButton btTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JTextField tKdPegawai;
    private javax.swing.JTextField tLevelPegawai;
    private javax.swing.JTextField tNamaPegawai;
    private javax.swing.JTextField tNip;
    private javax.swing.JTextField tPassword;
    private javax.swing.JTextField tStatusPegawai;
    private javax.swing.JTextField tTeleponPegawai;
    private javax.swing.JTable tblPegawai;
    // End of variables declaration//GEN-END:variables
}
