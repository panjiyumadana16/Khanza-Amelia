/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * kontribusi dari haris rochmatullah
 */

package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;


/**
 * 
 * @author salimmulyana
 */
public final class RMSkriningResikoJatuhRajal extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private int i=0;
    private String tgl, PengkajianA="Tidak", PengkajianB="Tidak";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMSkriningResikoJatuhRajal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","J.K.","Tgl.Lahir","Alamat","Tanggal Skrining","Pengkajian A","Pengkajian B",
            "Hasil","Penilaian","Tindakan","Keterangan","NIP","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(25);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(115);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(65);
            }else if(i==9){
                column.setPreferredWidth(120);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(120);
            }else if(i==12){
                column.setPreferredWidth(120);
            }else if(i==13){
                column.setPreferredWidth(65);
            }else if(i==14){
                column.setPreferredWidth(120);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));            
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        KetSkrining.setDocument(new batasInput((int)500).getKata(KetSkrining));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){ 
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());   
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        ChkInput.setSelected(false);
        isForm();
        setHasil();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        textBox1 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        AlamatLengkap = new widget.TextBox();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TglLahir = new widget.TextBox();
        jLabel10 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel11 = new widget.Label();
        label14 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        label11 = new widget.Label();
        tglSkrining = new widget.Tanggal();
        label15 = new widget.Label();
        label16 = new widget.Label();
        label17 = new widget.Label();
        label18 = new widget.Label();
        label19 = new widget.Label();
        label20 = new widget.Label();
        label21 = new widget.Label();
        rbYaA = new widget.RadioButton();
        rbTidakA = new widget.RadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        rbYaB = new widget.RadioButton();
        rbTidakB = new widget.RadioButton();
        label22 = new widget.Label();
        HasilSkrining = new widget.TextBox();
        PenilaianSkrining = new widget.TextBox();
        label23 = new widget.Label();
        label24 = new widget.Label();
        KetSkrining = new widget.TextBox();
        label25 = new widget.Label();
        cbTindakan = new widget.ComboBox();
        ChkInput = new widget.CekBox();

        textBox1.setText("textBox1");
        textBox1.setName("textBox1"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Skrining Resiko Jatuh Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObatKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setEnabled(false);
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAll);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21-08-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21-08-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(210, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(400, 400));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 196));
        FormInput.setLayout(null);

        jLabel3.setText("Tanggal Lahir :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 40, 85, 23);

        AlamatLengkap.setEditable(false);
        AlamatLengkap.setHighlighter(null);
        AlamatLengkap.setName("AlamatLengkap"); // NOI18N
        AlamatLengkap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatLengkapKeyPressed(evt);
            }
        });
        FormInput.add(AlamatLengkap);
        AlamatLengkap.setBounds(440, 40, 295, 23);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 85, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(89, 10, 151, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(355, 10, 380, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(242, 10, 111, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(89, 40, 110, 23);

        jLabel10.setText("J.K. :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(208, 40, 30, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(242, 40, 111, 23);

        jLabel11.setText("Alamat :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(366, 40, 70, 23);

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 70, 85, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(89, 70, 110, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(201, 70, 235, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('2');
        BtnPetugas.setToolTipText("Alt+2");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        BtnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugas);
        BtnPetugas.setBounds(440, 70, 28, 23);

        label11.setText("Tanggal Skrining :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(491, 70, 100, 23);

        tglSkrining.setForeground(new java.awt.Color(50, 70, 50));
        tglSkrining.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "21-08-2026 09:22:55" }));
        tglSkrining.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        tglSkrining.setName("tglSkrining"); // NOI18N
        tglSkrining.setOpaque(false);
        tglSkrining.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tglSkriningKeyPressed(evt);
            }
        });
        FormInput.add(tglSkrining);
        tglSkrining.setBounds(595, 70, 140, 23);

        label15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label15.setText("1. PENGKAJIAN");
        label15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(10, 100, 720, 23);

        label16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label16.setText("a. Cara berjalan pasien (salah satu atau lebih)");
        label16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(30, 120, 540, 20);

        label17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label17.setText("1. Apakah anda merasa tidak stabil ketika berdiri atau berjalan?");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(45, 140, 400, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label18.setText("2. Apakah anda khawatir akan jatuh?");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(45, 160, 400, 23);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label19.setText("3. Apakah anda pernah jatuh dalam setahun terakhir?");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(45, 180, 400, 23);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label20.setText("b. Menopang saat akan duduk : tampak memegang pinggiran kursi atau meja,");
        label20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(30, 210, 540, 23);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label21.setText(" atau benda lain sebagai penopang saat akan duduk");
        label21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(40, 225, 530, 23);

        rbYaA.setBorder(null);
        buttonGroup1.add(rbYaA);
        rbYaA.setText("Ya");
        rbYaA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbYaA.setName("rbYaA"); // NOI18N
        rbYaA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbYaAActionPerformed(evt);
            }
        });
        FormInput.add(rbYaA);
        rbYaA.setBounds(590, 130, 55, 70);

        rbTidakA.setBorder(null);
        buttonGroup1.add(rbTidakA);
        rbTidakA.setSelected(true);
        rbTidakA.setText("Tidak");
        rbTidakA.setToolTipText("");
        rbTidakA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbTidakA.setName("rbTidakA"); // NOI18N
        rbTidakA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTidakAActionPerformed(evt);
            }
        });
        FormInput.add(rbTidakA);
        rbTidakA.setBounds(660, 130, 60, 70);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(580, 120, 3, 140);

        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(25, 205, 700, 3);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(650, 120, 3, 140);

        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(25, 259, 700, 3);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(23, 120, 3, 140);

        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(25, 118, 700, 3);

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(724, 120, 3, 140);

        rbYaB.setBorder(null);
        buttonGroup2.add(rbYaB);
        rbYaB.setText("Ya");
        rbYaB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbYaB.setName("rbYaB"); // NOI18N
        rbYaB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbYaBActionPerformed(evt);
            }
        });
        FormInput.add(rbYaB);
        rbYaB.setBounds(590, 210, 55, 45);

        rbTidakB.setBorder(null);
        buttonGroup2.add(rbTidakB);
        rbTidakB.setSelected(true);
        rbTidakB.setText("Tidak");
        rbTidakB.setToolTipText("");
        rbTidakB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbTidakB.setName("rbTidakB"); // NOI18N
        rbTidakB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTidakBActionPerformed(evt);
            }
        });
        FormInput.add(rbTidakB);
        rbTidakB.setBounds(660, 210, 60, 45);

        label22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label22.setText("2. HASIL & TINDAKAN");
        label22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(10, 270, 720, 23);

        HasilSkrining.setEditable(false);
        HasilSkrining.setText("Tidak berisiko");
        HasilSkrining.setName("HasilSkrining"); // NOI18N
        HasilSkrining.setPreferredSize(new java.awt.Dimension(80, 23));
        HasilSkrining.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HasilSkriningActionPerformed(evt);
            }
        });
        HasilSkrining.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilSkriningKeyPressed(evt);
            }
        });
        FormInput.add(HasilSkrining);
        HasilSkrining.setBounds(90, 290, 200, 23);

        PenilaianSkrining.setEditable(false);
        PenilaianSkrining.setText("Tidak ditemukan a & b");
        PenilaianSkrining.setName("PenilaianSkrining"); // NOI18N
        PenilaianSkrining.setPreferredSize(new java.awt.Dimension(80, 23));
        PenilaianSkrining.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenilaianSkriningKeyPressed(evt);
            }
        });
        FormInput.add(PenilaianSkrining);
        PenilaianSkrining.setBounds(300, 290, 430, 23);

        label23.setText("Keterangan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(300, 320, 85, 23);

        label24.setText("Hasil Skrining :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(0, 290, 85, 23);

        KetSkrining.setName("KetSkrining"); // NOI18N
        KetSkrining.setPreferredSize(new java.awt.Dimension(80, 23));
        KetSkrining.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetSkriningKeyPressed(evt);
            }
        });
        FormInput.add(KetSkrining);
        KetSkrining.setBounds(390, 320, 340, 23);

        label25.setText("Tindakan :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(0, 320, 85, 23);

        cbTindakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak ada tindakan", "Edukasi", "Alat Bantu", "Edukasi & Alat Bantu" }));
        cbTindakan.setName("cbTindakan"); // NOI18N
        FormInput.add(cbTindakan);
        cbTindakan.setBounds(90, 320, 200, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        internalFrame1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void AlamatLengkapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatLengkapKeyPressed
       Valid.pindah(evt,TCari,BtnPetugas);
}//GEN-LAST:event_AlamatLengkapKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,BtnPetugas);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw, "Pasien");
        }else if(KdPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas, "Petugas");
        }else{
            if(Sequel.menyimpantf("skrining_resiko_jatuh_rajal","?,?,?,?,?,?,?,?,?","Catatan",9,new String[]{
                    TNoRw.getText(),Valid.SetTgl(tglSkrining.getSelectedItem()+"")+" "+tglSkrining.getSelectedItem().toString().substring(11,19),
                    PengkajianA,PengkajianB,HasilSkrining.getText(),PenilaianSkrining.getText(),cbTindakan.getSelectedItem().toString(),KetSkrining.getText(),KdPetugas.getText()
                })==true){
                
                tampil();
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,KetSkrining,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
        
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw, "Pasien");
        }else if(KdPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas, "Petugas");
        }else{    
            if(tbObat.getSelectedRow()!= -1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPetugas.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            tgl=" mpp_evaluasi_catatan.tgl_implementasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' ";
            if(TCari.getText().trim().equals("")){
                Valid.MyReportqry("rptDataMPPCatatan.jasper","report","::[ Data Evaluasi Catatan MPP ]::",
                     "select mpp_evaluasi_catatan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                     "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat, "+
                     "mpp_evaluasi_catatan.tgl_implementasi,mpp_evaluasi_catatan.masalah,mpp_evaluasi_catatan.tinjut, "+
                     "mpp_evaluasi_catatan.evaluasi,mpp_evaluasi_catatan.nip,petugas.nama "+                  
                     "from mpp_evaluasi_catatan "+
                     "inner join reg_periksa on mpp_evaluasi_catatan.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join petugas on mpp_evaluasi_catatan.nip=petugas.nip "+
                     "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                     "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                     "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                     "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                     "where "+tgl+" order by mpp_evaluasi_catatan.no_rawat",param);
            }else{
                Valid.MyReportqry("rptDataMPPCatatan.jasper","report","::[ Data Evaluasi Catatan MPP ]::",
                     "select mpp_evaluasi_catatan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                     "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat, "+
                     "mpp_evaluasi_catatan.tgl_implementasi,mpp_evaluasi_catatan.masalah,mpp_evaluasi_catatan.tinjut, "+
                     "mpp_evaluasi_catatan.evaluasi,mpp_evaluasi_catatan.nip,petugas.nama "+                  
                     "from mpp_evaluasi_catatan "+
                     "inner join reg_periksa on mpp_evaluasi_catatan.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join petugas on mpp_evaluasi_catatan.nip=petugas.nip "+
                     "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                     "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                     "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                     "inner join propinsi on pasien.kd_prop=propinsi.kd_prop "+
                     "where "+tgl+" and (mpp_evaluasi_catatan.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                     "reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                     "pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or  petugas.nama like '%"+TCari.getText().trim()+"%') "+
                     "order by mpp_evaluasi_catatan.no_rawat",param);
            }
            
        }
        this.setCursor(Cursor.getDefaultCursor());        
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed
   
    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_TNoRMKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
       isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void tbObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyReleased

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void tglSkriningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tglSkriningKeyPressed
        Valid.pindah(evt,TNoRw,KetSkrining);
    }//GEN-LAST:event_tglSkriningKeyPressed

    private void HasilSkriningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilSkriningKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HasilSkriningKeyPressed

    private void PenilaianSkriningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenilaianSkriningKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenilaianSkriningKeyPressed

    private void HasilSkriningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HasilSkriningActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HasilSkriningActionPerformed

    private void KetSkriningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetSkriningKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetSkriningKeyPressed

    private void rbYaAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbYaAActionPerformed
        setHasil();
    }//GEN-LAST:event_rbYaAActionPerformed

    private void rbTidakAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTidakAActionPerformed
        setHasil();
    }//GEN-LAST:event_rbTidakAActionPerformed

    private void rbYaBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbYaBActionPerformed
        setHasil();
    }//GEN-LAST:event_rbYaBActionPerformed

    private void rbTidakBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTidakBActionPerformed
        setHasil();
    }//GEN-LAST:event_rbTidakBActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSkriningResikoJatuhRajal dialog = new RMSkriningResikoJatuhRajal(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox AlamatLengkap;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox HasilSkrining;
    private widget.TextBox Jk;
    private widget.TextBox KdPetugas;
    private widget.TextBox KetSkrining;
    private widget.Label LCount;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox PenilaianSkrining;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TglLahir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private widget.ComboBox cbTindakan;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.RadioButton rbTidakA;
    private widget.RadioButton rbTidakB;
    private widget.RadioButton rbYaA;
    private widget.RadioButton rbYaB;
    private widget.Table tbObat;
    private widget.TextBox textBox1;
    private widget.Tanggal tglSkrining;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            tgl=" skrining_resiko_jatuh_rajal.tgl_skrining between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' ";
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                     "select skrining_resiko_jatuh_rajal.*,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,pasien.alamat ,petugas.nama "+                
                     "from skrining_resiko_jatuh_rajal "+
                     "inner join reg_periksa on skrining_resiko_jatuh_rajal.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join petugas on skrining_resiko_jatuh_rajal.nip=petugas.nip "+
                     "where "+tgl+" order by skrining_resiko_jatuh_rajal.no_rawat");
            }else{
                ps=koneksi.prepareStatement(
                     "select skrining_resiko_jatuh_rajal.*,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,pasien.alamat ,petugas.nama "+                
                     "from skrining_resiko_jatuh_rajal "+
                     "inner join reg_periksa on skrining_resiko_jatuh_rajal.no_rawat=reg_periksa.no_rawat "+
                     "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                     "inner join petugas on skrining_resiko_jatuh_rajal.nip=petugas.nip "+
                     "where "+tgl+" and (skrining_resiko_jatuh_rajal.no_rawat like '%"+TCari.getText().trim()+"%' or reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                     "pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or petugas.nama like '%"+TCari.getText().trim()+"%') "+
                     "order by skrining_resiko_jatuh_rajal.no_rawat");
            }
                
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("tgl_lahir"),rs.getString("alamat"),
                        rs.getString("tgl_skrining"),rs.getString("pengkajian_a"),rs.getString("pengkajian_b"),rs.getString("hasil"),
                        rs.getString("penilaian"),rs.getString("tindakan"),rs.getString("keterangan"),rs.getString("nip"),rs.getString("nama")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : tampil "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    public void emptTeks() {
        tglSkrining.setDate(new Date());
        rbTidakA.setSelected(true);
        rbTidakB.setSelected(true);
        setHasil();
        cbTindakan.setSelectedIndex(0);
        KetSkrining.setText("");
    }
    
    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());  
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());    
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            AlamatLengkap.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()); 
            Valid.SetTgl2(tglSkrining,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            if(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().equals("Ya")){
                rbYaA.setSelected(true);
            } else {
                rbTidakA.setSelected(true);
            }
            
            if(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().equals("Ya")){
                rbYaB.setSelected(true);
            } else {
                rbTidakB.setSelected(true);
            }
            cbTindakan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()); 
            KetSkrining.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            setHasil();
            isRawat();  
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,pasien.alamat "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    AlamatLengkap.setText(rs.getString("alamat"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2); 
        ChkInput.setSelected(true);
        isForm();   
        isRawat();
    }
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,400));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    } 
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getskrining_resiko_jatuh_rajal());
        BtnHapus.setEnabled(akses.getskrining_resiko_jatuh_rajal());
        BtnEdit.setEnabled(akses.getskrining_resiko_jatuh_rajal());
        BtnPrint.setEnabled(akses.getskrining_resiko_jatuh_rajal());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode().trim());
            NmPetugas.setText(petugas.tampil3(KdPetugas.getText()));
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        } 
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from skrining_resiko_jatuh_rajal where no_rawat=? and tgl_skrining=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("skrining_resiko_jatuh_rajal","no_rawat=? and tgl_skrining=?","no_rawat=?,tgl_skrining=?,pengkajian_a=?,pengkajian_b=?,hasil=?,penilaian=?,tindakan=?,keterangan=?,nip=?",11,new String[]{
            TNoRw.getText(),Valid.SetTgl(tglSkrining.getSelectedItem()+"")+" "+tglSkrining.getSelectedItem().toString().substring(11,19),PengkajianA,PengkajianB,HasilSkrining.getText(),PenilaianSkrining.getText(),
            cbTindakan.getSelectedItem().toString(),KetSkrining.getText(), KdPetugas.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()
        })==true){
            tampil();
            emptTeks();
        }
    }
    
    public void setHasil(){
        if(rbYaA.isSelected() && rbYaB.isSelected()){
            HasilSkrining.setText("Berisiko tinggi");
            PenilaianSkrining.setText("Ditemukan a & b");
        } else if((rbYaA.isSelected() && rbTidakB.isSelected()) || (rbTidakA.isSelected() && rbYaB.isSelected())){
            HasilSkrining.setText("Berisiko rendah");
            PenilaianSkrining.setText("Ditemukan salah satu dari a atau b");
        } else {
            HasilSkrining.setText("Tidak berisiko");
            PenilaianSkrining.setText("Tidak ditemukan a & b");
        }
        
        PengkajianA = rbYaA.isSelected() ?  "Ya" : "Tidak";
        PengkajianB = rbYaB.isSelected() ?  "Ya" : "Tidak";
    }
}



