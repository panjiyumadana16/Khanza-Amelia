/*
 * By Panji
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPegawai;
import widget.Label;


/**
 *
 * @author perpustakaan
 */
public final class RMPenilaianStrokeNIHSS extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private StringBuilder htmlContent;
    private String finger="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianStrokeNIHSS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Kode Penilai","Nama Penilai","Tgl.Pemeriksaan","Total Skor","Defisit Neurologis","1 A","1 B","1 C",
            "2","3","4","5 Kanan","Pnj. 5 Kanan","5 Kiri","Pnj. 5 Kiri","6 Kanan","Pnj. 6 Kanan","6 Kiri","Pnj. 6 Kiri","7","Pnj. 7","8","9","10","Pnj. 10","11"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 31; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(50);
            }else if(i==12){
                column.setPreferredWidth(50);
            }else if(i==13){
                column.setPreferredWidth(50);
            }else if(i==14){
                column.setPreferredWidth(50);
            }else if(i==15){
                column.setPreferredWidth(50);
            }else if(i==16){
                column.setPreferredWidth(50);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else if(i==18){
                column.setPreferredWidth(50);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(50);
            }else if(i==21){
                column.setPreferredWidth(100);
            }else if(i==22){
                column.setPreferredWidth(50);
            }else if(i==23){
                column.setPreferredWidth(100);
            }else if(i==24){
                column.setPreferredWidth(50);
            }else if(i==25){
                column.setPreferredWidth(100);
            }else if(i==26){
                column.setPreferredWidth(50);
            }else if(i==27){
                column.setPreferredWidth(50);
            }else if(i==28){
                column.setPreferredWidth(50);
            }else if(i==29){
                column.setPreferredWidth(100);
            }else if(i==30){
                column.setPreferredWidth(50);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){
                    KdPenilai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    NmPenilai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                    KdPenilai.requestFocus();
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
        
        Label[] daftarLabel = {
            lblSkor1A, lblSkor1B, lblSkor1C, lblSkor2, lblSkor3, lblSkor4, lblSkor5Kanan, lblSkor5Kiri,
            lblSkor6Kanan, lblSkor6Kiri, lblSkor7, lblSkor8, lblSkor9, lblSkor10, lblSkor11
        };

        java.beans.PropertyChangeListener listenerKalkulasiSkor = new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                kalkulasiSkor();
            }
        };

        for (Label lbl : daftarLabel) {
            lbl.addPropertyChangeListener("text", listenerKalkulasiSkor);
        }
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPenilaianStrokeNIHSS = new javax.swing.JMenuItem();
        btnG1a = new javax.swing.ButtonGroup();
        btnG1b = new javax.swing.ButtonGroup();
        btnG1c = new javax.swing.ButtonGroup();
        btnG2 = new javax.swing.ButtonGroup();
        btnG3 = new javax.swing.ButtonGroup();
        btnG4 = new javax.swing.ButtonGroup();
        btnG5Kanan = new javax.swing.ButtonGroup();
        btnG5Kiri = new javax.swing.ButtonGroup();
        btnG6Kanan = new javax.swing.ButtonGroup();
        btnG6Kiri = new javax.swing.ButtonGroup();
        btnG7 = new javax.swing.ButtonGroup();
        btnG8 = new javax.swing.ButtonGroup();
        btnG9 = new javax.swing.ButtonGroup();
        btnG10 = new javax.swing.ButtonGroup();
        btnG11 = new javax.swing.ButtonGroup();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdPenilai = new widget.TextBox();
        NmPenilai = new widget.TextBox();
        BtnPenilai = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        label23 = new widget.Label();
        jrb1ANilai0 = new javax.swing.JRadioButton();
        jrb1ANilai1 = new javax.swing.JRadioButton();
        jrb1ANilai2 = new javax.swing.JRadioButton();
        jrb1ANilai3 = new javax.swing.JRadioButton();
        label15 = new widget.Label();
        label13 = new widget.Label();
        label12 = new widget.Label();
        label16 = new widget.Label();
        jrb1BNilai0 = new javax.swing.JRadioButton();
        jrb1BNilai1 = new javax.swing.JRadioButton();
        jrb1BNilai2 = new javax.swing.JRadioButton();
        label17 = new widget.Label();
        jrb1CNilai0 = new javax.swing.JRadioButton();
        jrb1CNilai1 = new javax.swing.JRadioButton();
        jrb1CNilai2 = new javax.swing.JRadioButton();
        label18 = new widget.Label();
        jrb2Nilai0 = new javax.swing.JRadioButton();
        jrb2Nilai1 = new javax.swing.JRadioButton();
        jrb2Nilai2 = new javax.swing.JRadioButton();
        label19 = new widget.Label();
        jrb3Nilai0 = new javax.swing.JRadioButton();
        jrb3Nilai1 = new javax.swing.JRadioButton();
        jrb3Nilai2 = new javax.swing.JRadioButton();
        jrb3Nilai3 = new javax.swing.JRadioButton();
        label20 = new widget.Label();
        jrb4Nilai0 = new javax.swing.JRadioButton();
        jrb4Nilai1 = new javax.swing.JRadioButton();
        jrb4Nilai2 = new javax.swing.JRadioButton();
        jrb4Nilai3 = new javax.swing.JRadioButton();
        label21 = new widget.Label();
        label22 = new widget.Label();
        jrb5KananNilai0 = new javax.swing.JRadioButton();
        jrb5KananNilai1 = new javax.swing.JRadioButton();
        jrb5KananNilai2 = new javax.swing.JRadioButton();
        jrb5KananNilai3 = new javax.swing.JRadioButton();
        jrb5KananNilai4 = new javax.swing.JRadioButton();
        jrb5KananNilaiUN = new javax.swing.JRadioButton();
        label24 = new widget.Label();
        tb5KananUN = new widget.TextBox();
        label25 = new widget.Label();
        jrb5KiriNilai0 = new javax.swing.JRadioButton();
        jrb5KiriNilai1 = new javax.swing.JRadioButton();
        jrb5KiriNilai2 = new javax.swing.JRadioButton();
        jrb5KiriNilai3 = new javax.swing.JRadioButton();
        jrb5KiriNilai4 = new javax.swing.JRadioButton();
        jrb5KiriNilaiUN = new javax.swing.JRadioButton();
        label26 = new widget.Label();
        tb5KiriUN = new widget.TextBox();
        label28 = new widget.Label();
        label29 = new widget.Label();
        jrb6KananNilai0 = new javax.swing.JRadioButton();
        jrb6KananNilai1 = new javax.swing.JRadioButton();
        jrb6KananNilai2 = new javax.swing.JRadioButton();
        jrb6KananNilai3 = new javax.swing.JRadioButton();
        jrb6KananNilai4 = new javax.swing.JRadioButton();
        jrb6KananNilaiUN = new javax.swing.JRadioButton();
        label30 = new widget.Label();
        tb6KananUN = new widget.TextBox();
        label31 = new widget.Label();
        jrb6KiriNilai0 = new javax.swing.JRadioButton();
        jrb6KiriNilai1 = new javax.swing.JRadioButton();
        jrb6KiriNilai2 = new javax.swing.JRadioButton();
        jrb6KiriNilai3 = new javax.swing.JRadioButton();
        jrb6KiriNilai4 = new javax.swing.JRadioButton();
        jrb6KiriNilaiUN = new javax.swing.JRadioButton();
        label32 = new widget.Label();
        tb6KiriUN = new widget.TextBox();
        label33 = new widget.Label();
        jrb7Nilai0 = new javax.swing.JRadioButton();
        jrb7Nilai1 = new javax.swing.JRadioButton();
        jrb7Nilai2 = new javax.swing.JRadioButton();
        jrb7NilaiUN = new javax.swing.JRadioButton();
        label34 = new widget.Label();
        tb7UN = new widget.TextBox();
        label35 = new widget.Label();
        jrb8Nilai0 = new javax.swing.JRadioButton();
        jrb8Nilai1 = new javax.swing.JRadioButton();
        jrb8Nilai2 = new javax.swing.JRadioButton();
        label36 = new widget.Label();
        jrb9Nilai0 = new javax.swing.JRadioButton();
        jrb9Nilai1 = new javax.swing.JRadioButton();
        jrb9Nilai2 = new javax.swing.JRadioButton();
        jrb9Nilai3 = new javax.swing.JRadioButton();
        label37 = new widget.Label();
        jrb10Nilai0 = new javax.swing.JRadioButton();
        jrb10Nilai1 = new javax.swing.JRadioButton();
        jrb10Nilai2 = new javax.swing.JRadioButton();
        jrb10NilaiUN = new javax.swing.JRadioButton();
        label38 = new widget.Label();
        tb10UN = new widget.TextBox();
        label39 = new widget.Label();
        jrb11Nilai0 = new javax.swing.JRadioButton();
        jrb11Nilai1 = new javax.swing.JRadioButton();
        jrb11Nilai2 = new javax.swing.JRadioButton();
        label27 = new widget.Label();
        lblSkor1A = new widget.Label();
        lblSkor1B = new widget.Label();
        lblSkor1C = new widget.Label();
        lblSkor2 = new widget.Label();
        lblSkor3 = new widget.Label();
        lblSkor4 = new widget.Label();
        lblSkor5Kanan = new widget.Label();
        lblSkor5Kiri = new widget.Label();
        lblSkor6Kanan = new widget.Label();
        lblSkor6Kiri = new widget.Label();
        lblSkor7 = new widget.Label();
        lblSkor8 = new widget.Label();
        lblSkor9 = new widget.Label();
        lblSkor10 = new widget.Label();
        lblSkor11 = new widget.Label();
        lblTotalSkor = new widget.Label();
        label40 = new widget.Label();
        label41 = new widget.Label();
        label42 = new widget.Label();
        label43 = new widget.Label();
        label44 = new widget.Label();
        label45 = new widget.Label();
        label46 = new widget.Label();
        label47 = new widget.Label();
        label48 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        lblKetSkortxt = new widget.Label();
        lblKetSkor = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
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

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianStrokeNIHSS.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianStrokeNIHSS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianStrokeNIHSS.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianStrokeNIHSS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianStrokeNIHSS.setText("Laporan Penilaian Stroke NIHSS\n");
        MnPenilaianStrokeNIHSS.setName("MnPenilaianStrokeNIHSS"); // NOI18N
        MnPenilaianStrokeNIHSS.setPreferredSize(new java.awt.Dimension(260, 26));
        MnPenilaianStrokeNIHSS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianStrokeNIHSSActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianStrokeNIHSS);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Stoke NIHSS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(467, 500));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.setPreferredSize(new java.awt.Dimension(457, 480));
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(954, 2620));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Penilai :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 70, 23);

        KdPenilai.setEditable(false);
        KdPenilai.setName("KdPenilai"); // NOI18N
        KdPenilai.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdPenilai);
        KdPenilai.setBounds(74, 40, 130, 23);

        NmPenilai.setEditable(false);
        NmPenilai.setName("NmPenilai"); // NOI18N
        NmPenilai.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPenilai);
        NmPenilai.setBounds(206, 40, 330, 23);

        BtnPenilai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPenilai.setMnemonic('2');
        BtnPenilai.setToolTipText("Alt+2");
        BtnPenilai.setName("BtnPenilai"); // NOI18N
        BtnPenilai.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPenilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenilaiActionPerformed(evt);
            }
        });
        BtnPenilai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPenilaiKeyPressed(evt);
            }
        });
        FormInput.add(BtnPenilai);
        BtnPenilai.setBounds(540, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(774, 10, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(740, 10, 30, 23);

        label11.setText("Tanggal Pemeriksaan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(592, 40, 110, 23);

        TglAsuhan.setForeground(new java.awt.Color(50, 70, 50));
        TglAsuhan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2026-11-10 08:21:41" }));
        TglAsuhan.setDisplayFormat("yyyy-MM-dd HH:mm:ss");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setOpaque(false);
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(710, 40, 140, 23);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setName("jSeparator2"); // NOI18N
        jSeparator2.setOpaque(true);
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(790, 70, 2, 2531);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setName("jSeparator3"); // NOI18N
        jSeparator3.setOpaque(true);
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(2, 70, 2, 2531);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setName("jSeparator4"); // NOI18N
        jSeparator4.setOpaque(true);
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(949, 70, 2, 2531);

        label23.setBackground(new java.awt.Color(244, 244, 244));
        label23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        label23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label23.setText("SKOR");
        label23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label23.setName("label23"); // NOI18N
        label23.setOpaque(true);
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(791, 70, 160, 30);

        jrb1ANilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG1a.add(jrb1ANilai0);
        jrb1ANilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb1ANilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb1ANilai0.setSelected(true);
        jrb1ANilai0.setText("<html>\nSadar penuh\n</html>");
        jrb1ANilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb1ANilai0.setBorderPainted(true);
        jrb1ANilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb1ANilai0.setFocusPainted(false);
        jrb1ANilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb1ANilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb1ANilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb1ANilai0.setIconTextGap(2);
        jrb1ANilai0.setName("jrb1ANilai0"); // NOI18N
        jrb1ANilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb1ANilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb1ANilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb1ANilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb1AActionPerformed(evt);
            }
        });
        FormInput.add(jrb1ANilai0);
        jrb1ANilai0.setBounds(40, 140, 140, 90);

        jrb1ANilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG1a.add(jrb1ANilai1);
        jrb1ANilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb1ANilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb1ANilai1.setText("<html>\nTidak sadar penuh; dapat dibangunkan dengan stimulasi minor (suara)\n</html>");
        jrb1ANilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb1ANilai1.setBorderPainted(true);
        jrb1ANilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb1ANilai1.setFocusPainted(false);
        jrb1ANilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb1ANilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb1ANilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb1ANilai1.setIconTextGap(2);
        jrb1ANilai1.setName("jrb1ANilai1"); // NOI18N
        jrb1ANilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb1ANilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb1ANilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb1ANilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb1AActionPerformed(evt);
            }
        });
        FormInput.add(jrb1ANilai1);
        jrb1ANilai1.setBounds(190, 140, 140, 90);

        jrb1ANilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG1a.add(jrb1ANilai2);
        jrb1ANilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb1ANilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb1ANilai2.setText("<html>\nTidak sadar penuh; dapat berespon dengan stimulasi berulang atau stimulasi nyeri\n</html>");
        jrb1ANilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb1ANilai2.setBorderPainted(true);
        jrb1ANilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb1ANilai2.setFocusPainted(false);
        jrb1ANilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb1ANilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb1ANilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb1ANilai2.setIconTextGap(2);
        jrb1ANilai2.setName("jrb1ANilai2"); // NOI18N
        jrb1ANilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb1ANilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb1ANilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb1ANilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb1AActionPerformed(evt);
            }
        });
        FormInput.add(jrb1ANilai2);
        jrb1ANilai2.setBounds(340, 140, 140, 90);

        jrb1ANilai3.setBackground(new java.awt.Color(247, 247, 247));
        btnG1a.add(jrb1ANilai3);
        jrb1ANilai3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb1ANilai3.setForeground(new java.awt.Color(51, 51, 51));
        jrb1ANilai3.setText("<html>\nKoma; tidak sadar dan tidak berespon dengan stimulasi apapun\n</html>");
        jrb1ANilai3.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb1ANilai3.setBorderPainted(true);
        jrb1ANilai3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb1ANilai3.setFocusPainted(false);
        jrb1ANilai3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb1ANilai3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb1ANilai3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb1ANilai3.setIconTextGap(2);
        jrb1ANilai3.setName("jrb1ANilai3"); // NOI18N
        jrb1ANilai3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb1ANilai3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb1ANilai3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb1ANilai3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb1AActionPerformed(evt);
            }
        });
        FormInput.add(jrb1ANilai3);
        jrb1ANilai3.setBounds(490, 140, 140, 90);

        label15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label15.setText("b. Menjawab Pertanyaan");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(20, 240, 690, 23);

        label13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label13.setText("1. RESPON KESADARAN");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label13);
        label13.setBounds(10, 100, 700, 23);

        label12.setBackground(new java.awt.Color(244, 244, 244));
        label12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("PARAMETER & SKALA PEMERIKSAAN");
        label12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label12.setName("label12"); // NOI18N
        label12.setOpaque(true);
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(3, 70, 789, 30);

        label16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label16.setText("a. Tingkat Kesadaran");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(20, 120, 690, 23);

        jrb1BNilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG1b.add(jrb1BNilai0);
        jrb1BNilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb1BNilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb1BNilai0.setSelected(true);
        jrb1BNilai0.setText("<html> Benar semua </html>");
        jrb1BNilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb1BNilai0.setBorderPainted(true);
        jrb1BNilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb1BNilai0.setFocusPainted(false);
        jrb1BNilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb1BNilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb1BNilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb1BNilai0.setIconTextGap(2);
        jrb1BNilai0.setName("jrb1BNilai0"); // NOI18N
        jrb1BNilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb1BNilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb1BNilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb1BNilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb1BActionPerformed(evt);
            }
        });
        FormInput.add(jrb1BNilai0);
        jrb1BNilai0.setBounds(40, 260, 140, 60);

        jrb1BNilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG1b.add(jrb1BNilai1);
        jrb1BNilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb1BNilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb1BNilai1.setText("<html> 1 Benar/ ETT / Disartria </html>");
        jrb1BNilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb1BNilai1.setBorderPainted(true);
        jrb1BNilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb1BNilai1.setFocusPainted(false);
        jrb1BNilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb1BNilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb1BNilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb1BNilai1.setIconTextGap(2);
        jrb1BNilai1.setName("jrb1BNilai1"); // NOI18N
        jrb1BNilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb1BNilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb1BNilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb1BNilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb1BActionPerformed(evt);
            }
        });
        FormInput.add(jrb1BNilai1);
        jrb1BNilai1.setBounds(190, 260, 140, 60);

        jrb1BNilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG1b.add(jrb1BNilai2);
        jrb1BNilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb1BNilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb1BNilai2.setText("<html> Salah semua / Afasia / Stupor/ Koma </html>");
        jrb1BNilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb1BNilai2.setBorderPainted(true);
        jrb1BNilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb1BNilai2.setFocusPainted(false);
        jrb1BNilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb1BNilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb1BNilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb1BNilai2.setIconTextGap(2);
        jrb1BNilai2.setName("jrb1BNilai2"); // NOI18N
        jrb1BNilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb1BNilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb1BNilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb1BNilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb1BActionPerformed(evt);
            }
        });
        FormInput.add(jrb1BNilai2);
        jrb1BNilai2.setBounds(340, 260, 140, 60);

        label17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label17.setText("c. Mengikuti Perintah");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(20, 330, 690, 23);

        jrb1CNilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG1c.add(jrb1CNilai0);
        jrb1CNilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb1CNilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb1CNilai0.setSelected(true);
        jrb1CNilai0.setText("<html> Mampu melakukan 2 perintah </html>");
        jrb1CNilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb1CNilai0.setBorderPainted(true);
        jrb1CNilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb1CNilai0.setFocusPainted(false);
        jrb1CNilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb1CNilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb1CNilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb1CNilai0.setIconTextGap(2);
        jrb1CNilai0.setName("jrb1CNilai0"); // NOI18N
        jrb1CNilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb1CNilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb1CNilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb1CNilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb1CActionPerformed(evt);
            }
        });
        FormInput.add(jrb1CNilai0);
        jrb1CNilai0.setBounds(40, 350, 140, 60);

        jrb1CNilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG1c.add(jrb1CNilai1);
        jrb1CNilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb1CNilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb1CNilai1.setText("<html> Mampu melakukan 1 perintah </html>");
        jrb1CNilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb1CNilai1.setBorderPainted(true);
        jrb1CNilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb1CNilai1.setFocusPainted(false);
        jrb1CNilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb1CNilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb1CNilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb1CNilai1.setIconTextGap(2);
        jrb1CNilai1.setName("jrb1CNilai1"); // NOI18N
        jrb1CNilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb1CNilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb1CNilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb1CNilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb1CActionPerformed(evt);
            }
        });
        FormInput.add(jrb1CNilai1);
        jrb1CNilai1.setBounds(190, 350, 140, 60);

        jrb1CNilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG1c.add(jrb1CNilai2);
        jrb1CNilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb1CNilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb1CNilai2.setText("<html> Tidak mampu melakukan perintah </html>");
        jrb1CNilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb1CNilai2.setBorderPainted(true);
        jrb1CNilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb1CNilai2.setFocusPainted(false);
        jrb1CNilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb1CNilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb1CNilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb1CNilai2.setIconTextGap(2);
        jrb1CNilai2.setName("jrb1CNilai2"); // NOI18N
        jrb1CNilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb1CNilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb1CNilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb1CNilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb1CActionPerformed(evt);
            }
        });
        FormInput.add(jrb1CNilai2);
        jrb1CNilai2.setBounds(340, 350, 140, 60);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label18.setText("2. GAZE : GERAKAN MATA KONYUGAT HORIZONTAL");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(10, 430, 700, 23);

        jrb2Nilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG2.add(jrb2Nilai0);
        jrb2Nilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb2Nilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb2Nilai0.setSelected(true);
        jrb2Nilai0.setText("<html> Normal </html>");
        jrb2Nilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb2Nilai0.setBorderPainted(true);
        jrb2Nilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb2Nilai0.setFocusPainted(false);
        jrb2Nilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb2Nilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb2Nilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb2Nilai0.setIconTextGap(2);
        jrb2Nilai0.setName("jrb2Nilai0"); // NOI18N
        jrb2Nilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb2Nilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb2Nilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb2Nilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb2ActionPerformed(evt);
            }
        });
        FormInput.add(jrb2Nilai0);
        jrb2Nilai0.setBounds(40, 450, 140, 120);

        jrb2Nilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG2.add(jrb2Nilai1);
        jrb2Nilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb2Nilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb2Nilai1.setText("<html> Paresis gaze parsial pada 1 atau 2 mata, terdapat abnormal gaze namun forced deviation atau paresis gaze total tidak ada </html>");
        jrb2Nilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb2Nilai1.setBorderPainted(true);
        jrb2Nilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb2Nilai1.setFocusPainted(false);
        jrb2Nilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb2Nilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb2Nilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb2Nilai1.setIconTextGap(2);
        jrb2Nilai1.setName("jrb2Nilai1"); // NOI18N
        jrb2Nilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb2Nilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb2Nilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb2Nilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb2ActionPerformed(evt);
            }
        });
        FormInput.add(jrb2Nilai1);
        jrb2Nilai1.setBounds(190, 450, 140, 120);

        jrb2Nilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG2.add(jrb2Nilai2);
        jrb2Nilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb2Nilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb2Nilai2.setText("<html> Forced deviation, atau paresis gaze total tidak dapat diatasi dengan maneuver okulosefalik </html>");
        jrb2Nilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb2Nilai2.setBorderPainted(true);
        jrb2Nilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb2Nilai2.setFocusPainted(false);
        jrb2Nilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb2Nilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb2Nilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb2Nilai2.setIconTextGap(2);
        jrb2Nilai2.setName("jrb2Nilai2"); // NOI18N
        jrb2Nilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb2Nilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb2Nilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb2Nilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb2ActionPerformed(evt);
            }
        });
        FormInput.add(jrb2Nilai2);
        jrb2Nilai2.setBounds(340, 450, 140, 120);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label19.setText("3. VISUAL : LAPANG PANDANG PADA TES KONFRONTASI");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(10, 580, 700, 23);

        jrb3Nilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG3.add(jrb3Nilai0);
        jrb3Nilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb3Nilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb3Nilai0.setSelected(true);
        jrb3Nilai0.setText("<html> Tidak ada gangguan </html>");
        jrb3Nilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb3Nilai0.setBorderPainted(true);
        jrb3Nilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb3Nilai0.setFocusPainted(false);
        jrb3Nilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb3Nilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb3Nilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb3Nilai0.setIconTextGap(2);
        jrb3Nilai0.setName("jrb3Nilai0"); // NOI18N
        jrb3Nilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb3Nilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb3Nilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb3Nilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb3ActionPerformed(evt);
            }
        });
        FormInput.add(jrb3Nilai0);
        jrb3Nilai0.setBounds(40, 600, 140, 100);

        jrb3Nilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG3.add(jrb3Nilai1);
        jrb3Nilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb3Nilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb3Nilai1.setText("<html> Paralisis minor (suclus nasolabial rata, asimetri saat tersenyum) </html>");
        jrb3Nilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb3Nilai1.setBorderPainted(true);
        jrb3Nilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb3Nilai1.setFocusPainted(false);
        jrb3Nilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb3Nilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb3Nilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb3Nilai1.setIconTextGap(2);
        jrb3Nilai1.setName("jrb3Nilai1"); // NOI18N
        jrb3Nilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb3Nilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb3Nilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb3Nilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb3ActionPerformed(evt);
            }
        });
        FormInput.add(jrb3Nilai1);
        jrb3Nilai1.setBounds(190, 600, 140, 100);

        jrb3Nilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG3.add(jrb3Nilai2);
        jrb3Nilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb3Nilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb3Nilai2.setText("<html> Paralisi parsial (paralisis total atau near-total dari wajah bagian bawah) </html>");
        jrb3Nilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb3Nilai2.setBorderPainted(true);
        jrb3Nilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb3Nilai2.setFocusPainted(false);
        jrb3Nilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb3Nilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb3Nilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb3Nilai2.setIconTextGap(2);
        jrb3Nilai2.setName("jrb3Nilai2"); // NOI18N
        jrb3Nilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb3Nilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb3Nilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb3Nilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb3ActionPerformed(evt);
            }
        });
        FormInput.add(jrb3Nilai2);
        jrb3Nilai2.setBounds(340, 600, 140, 100);

        jrb3Nilai3.setBackground(new java.awt.Color(247, 247, 247));
        btnG3.add(jrb3Nilai3);
        jrb3Nilai3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb3Nilai3.setForeground(new java.awt.Color(51, 51, 51));
        jrb3Nilai3.setText("<html> Paralisis komplit dari satu atau kedua sisi wajah (tidak ada gerakan pada sisi wajah atas maupun bawah) </html>");
        jrb3Nilai3.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb3Nilai3.setBorderPainted(true);
        jrb3Nilai3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb3Nilai3.setFocusPainted(false);
        jrb3Nilai3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb3Nilai3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb3Nilai3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb3Nilai3.setIconTextGap(2);
        jrb3Nilai3.setName("jrb3Nilai3"); // NOI18N
        jrb3Nilai3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb3Nilai3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb3Nilai3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb3Nilai3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb3ActionPerformed(evt);
            }
        });
        FormInput.add(jrb3Nilai3);
        jrb3Nilai3.setBounds(490, 600, 140, 100);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label20.setText("4. PARESIS WAJAH");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(10, 710, 700, 23);

        jrb4Nilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG4.add(jrb4Nilai0);
        jrb4Nilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb4Nilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb4Nilai0.setSelected(true);
        jrb4Nilai0.setText("<html> Normal </html>");
        jrb4Nilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb4Nilai0.setBorderPainted(true);
        jrb4Nilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb4Nilai0.setFocusPainted(false);
        jrb4Nilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb4Nilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb4Nilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb4Nilai0.setIconTextGap(2);
        jrb4Nilai0.setName("jrb4Nilai0"); // NOI18N
        jrb4Nilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb4Nilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb4Nilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb4Nilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb4ActionPerformed(evt);
            }
        });
        FormInput.add(jrb4Nilai0);
        jrb4Nilai0.setBounds(40, 730, 140, 100);

        jrb4Nilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG4.add(jrb4Nilai1);
        jrb4Nilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb4Nilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb4Nilai1.setText("<html> Paralisis minor (suclus nasolabial rata, asimetri saat tersenyum) </html>");
        jrb4Nilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb4Nilai1.setBorderPainted(true);
        jrb4Nilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb4Nilai1.setFocusPainted(false);
        jrb4Nilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb4Nilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb4Nilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb4Nilai1.setIconTextGap(2);
        jrb4Nilai1.setName("jrb4Nilai1"); // NOI18N
        jrb4Nilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb4Nilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb4Nilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb4Nilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb4ActionPerformed(evt);
            }
        });
        FormInput.add(jrb4Nilai1);
        jrb4Nilai1.setBounds(190, 730, 140, 100);

        jrb4Nilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG4.add(jrb4Nilai2);
        jrb4Nilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb4Nilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb4Nilai2.setText("<html> Paralisi parsial (paralisis total atau near-total dari wajah bagian bawah) </html>");
        jrb4Nilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb4Nilai2.setBorderPainted(true);
        jrb4Nilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb4Nilai2.setFocusPainted(false);
        jrb4Nilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb4Nilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb4Nilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb4Nilai2.setIconTextGap(2);
        jrb4Nilai2.setName("jrb4Nilai2"); // NOI18N
        jrb4Nilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb4Nilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb4Nilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb4Nilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb4ActionPerformed(evt);
            }
        });
        FormInput.add(jrb4Nilai2);
        jrb4Nilai2.setBounds(340, 730, 140, 100);

        jrb4Nilai3.setBackground(new java.awt.Color(247, 247, 247));
        btnG4.add(jrb4Nilai3);
        jrb4Nilai3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb4Nilai3.setForeground(new java.awt.Color(51, 51, 51));
        jrb4Nilai3.setText("<html> Paralisis komplit dari satu atau kedua sisi wajah (tidak ada gerakan pada sisi wajah atas maupun bawah) </html>");
        jrb4Nilai3.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb4Nilai3.setBorderPainted(true);
        jrb4Nilai3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb4Nilai3.setFocusPainted(false);
        jrb4Nilai3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb4Nilai3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb4Nilai3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb4Nilai3.setIconTextGap(2);
        jrb4Nilai3.setName("jrb4Nilai3"); // NOI18N
        jrb4Nilai3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb4Nilai3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb4Nilai3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb4Nilai3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb4ActionPerformed(evt);
            }
        });
        FormInput.add(jrb4Nilai3);
        jrb4Nilai3.setBounds(490, 730, 140, 100);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label21.setText("5. MOTORIK LENGAN");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(10, 840, 700, 23);

        label22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label22.setText("a. Tungkai Kanan");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(20, 1290, 690, 23);

        jrb5KananNilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG5Kanan.add(jrb5KananNilai0);
        jrb5KananNilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb5KananNilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb5KananNilai0.setSelected(true);
        jrb5KananNilai0.setText("<html> Tidak ada drift; lengan dapat diangkat 90 (45)°, selama minimal 10 detik penuh </html>");
        jrb5KananNilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb5KananNilai0.setBorderPainted(true);
        jrb5KananNilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb5KananNilai0.setFocusPainted(false);
        jrb5KananNilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb5KananNilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb5KananNilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb5KananNilai0.setIconTextGap(2);
        jrb5KananNilai0.setName("jrb5KananNilai0"); // NOI18N
        jrb5KananNilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb5KananNilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb5KananNilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb5KananNilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb5KananActionPerformed(evt);
            }
        });
        FormInput.add(jrb5KananNilai0);
        jrb5KananNilai0.setBounds(40, 880, 140, 120);

        jrb5KananNilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG5Kanan.add(jrb5KananNilai1);
        jrb5KananNilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb5KananNilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb5KananNilai1.setText("<html> Drift; lengan dapat diangkat 90 (45)° namun turun sebelum 10 detik, tidak mengenai tempat tidur </html>");
        jrb5KananNilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb5KananNilai1.setBorderPainted(true);
        jrb5KananNilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb5KananNilai1.setFocusPainted(false);
        jrb5KananNilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb5KananNilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb5KananNilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb5KananNilai1.setIconTextGap(2);
        jrb5KananNilai1.setName("jrb5KananNilai1"); // NOI18N
        jrb5KananNilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb5KananNilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb5KananNilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb5KananNilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb5KananActionPerformed(evt);
            }
        });
        FormInput.add(jrb5KananNilai1);
        jrb5KananNilai1.setBounds(190, 880, 140, 120);

        jrb5KananNilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG5Kanan.add(jrb5KananNilai2);
        jrb5KananNilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb5KananNilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb5KananNilai2.setText("<html> Ada upaya melawan gravitasi; lengan tidak dapat diangkat atau dipertahankan dalam posisi 90 (40)°, jatuh mengenai tempat tidur </html>");
        jrb5KananNilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb5KananNilai2.setBorderPainted(true);
        jrb5KananNilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb5KananNilai2.setFocusPainted(false);
        jrb5KananNilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb5KananNilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb5KananNilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb5KananNilai2.setIconTextGap(2);
        jrb5KananNilai2.setName("jrb5KananNilai2"); // NOI18N
        jrb5KananNilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb5KananNilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb5KananNilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb5KananNilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb5KananActionPerformed(evt);
            }
        });
        FormInput.add(jrb5KananNilai2);
        jrb5KananNilai2.setBounds(340, 880, 140, 120);

        jrb5KananNilai3.setBackground(new java.awt.Color(247, 247, 247));
        btnG5Kanan.add(jrb5KananNilai3);
        jrb5KananNilai3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb5KananNilai3.setForeground(new java.awt.Color(51, 51, 51));
        jrb5KananNilai3.setText("<html> Tidak ada upaya melawan gravitasi, tidak mampu mengangkat, hanya bergeser </html>");
        jrb5KananNilai3.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb5KananNilai3.setBorderPainted(true);
        jrb5KananNilai3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb5KananNilai3.setFocusPainted(false);
        jrb5KananNilai3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb5KananNilai3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb5KananNilai3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb5KananNilai3.setIconTextGap(2);
        jrb5KananNilai3.setName("jrb5KananNilai3"); // NOI18N
        jrb5KananNilai3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb5KananNilai3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb5KananNilai3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb5KananNilai3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb5KananActionPerformed(evt);
            }
        });
        FormInput.add(jrb5KananNilai3);
        jrb5KananNilai3.setBounds(490, 880, 140, 120);

        jrb5KananNilai4.setBackground(new java.awt.Color(247, 247, 247));
        btnG5Kanan.add(jrb5KananNilai4);
        jrb5KananNilai4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb5KananNilai4.setForeground(new java.awt.Color(51, 51, 51));
        jrb5KananNilai4.setText("<html> Tidak ada gerakan </html>");
        jrb5KananNilai4.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb5KananNilai4.setBorderPainted(true);
        jrb5KananNilai4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb5KananNilai4.setFocusPainted(false);
        jrb5KananNilai4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb5KananNilai4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb5KananNilai4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb5KananNilai4.setIconTextGap(2);
        jrb5KananNilai4.setName("jrb5KananNilai4"); // NOI18N
        jrb5KananNilai4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb5KananNilai4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb5KananNilai4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb5KananNilai4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb5KananActionPerformed(evt);
            }
        });
        FormInput.add(jrb5KananNilai4);
        jrb5KananNilai4.setBounds(640, 880, 140, 120);

        jrb5KananNilaiUN.setBackground(new java.awt.Color(247, 247, 247));
        btnG5Kanan.add(jrb5KananNilaiUN);
        jrb5KananNilaiUN.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb5KananNilaiUN.setForeground(new java.awt.Color(51, 51, 51));
        jrb5KananNilaiUN.setText("<html> Amputasi atau fusi sendi, jelaskan... </html>");
        jrb5KananNilaiUN.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb5KananNilaiUN.setBorderPainted(true);
        jrb5KananNilaiUN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb5KananNilaiUN.setFocusPainted(false);
        jrb5KananNilaiUN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb5KananNilaiUN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb5KananNilaiUN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb5KananNilaiUN.setIconTextGap(2);
        jrb5KananNilaiUN.setName("jrb5KananNilaiUN"); // NOI18N
        jrb5KananNilaiUN.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb5KananNilaiUN.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb5KananNilaiUN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb5KananNilaiUN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb5KananActionPerformed(evt);
            }
        });
        FormInput.add(jrb5KananNilaiUN);
        jrb5KananNilaiUN.setBounds(40, 1010, 210, 44);

        label24.setText("Jelaskan :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(250, 1030, 80, 23);

        tb5KananUN.setEnabled(false);
        tb5KananUN.setName("tb5KananUN"); // NOI18N
        tb5KananUN.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(tb5KananUN);
        tb5KananUN.setBounds(340, 1030, 440, 23);

        label25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label25.setText("b. Lengan Kiri");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(20, 1060, 690, 23);

        jrb5KiriNilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG5Kiri.add(jrb5KiriNilai0);
        jrb5KiriNilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb5KiriNilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb5KiriNilai0.setSelected(true);
        jrb5KiriNilai0.setText("<html> Tidak ada drift; lengan dapat diangkat 90 (45)°, selama minimal 10 detik penuh </html>");
        jrb5KiriNilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb5KiriNilai0.setBorderPainted(true);
        jrb5KiriNilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb5KiriNilai0.setFocusPainted(false);
        jrb5KiriNilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb5KiriNilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb5KiriNilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb5KiriNilai0.setIconTextGap(2);
        jrb5KiriNilai0.setName("jrb5KiriNilai0"); // NOI18N
        jrb5KiriNilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb5KiriNilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb5KiriNilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb5KiriNilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb5KiriActionPerformed(evt);
            }
        });
        FormInput.add(jrb5KiriNilai0);
        jrb5KiriNilai0.setBounds(40, 1080, 140, 120);

        jrb5KiriNilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG5Kiri.add(jrb5KiriNilai1);
        jrb5KiriNilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb5KiriNilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb5KiriNilai1.setText("<html> Drift; lengan dapat diangkat 90 (45)° namun turun sebelum 10 detik, tidak mengenai tempat tidur </html>");
        jrb5KiriNilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb5KiriNilai1.setBorderPainted(true);
        jrb5KiriNilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb5KiriNilai1.setFocusPainted(false);
        jrb5KiriNilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb5KiriNilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb5KiriNilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb5KiriNilai1.setIconTextGap(2);
        jrb5KiriNilai1.setName("jrb5KiriNilai1"); // NOI18N
        jrb5KiriNilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb5KiriNilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb5KiriNilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb5KiriNilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb5KiriActionPerformed(evt);
            }
        });
        FormInput.add(jrb5KiriNilai1);
        jrb5KiriNilai1.setBounds(190, 1080, 140, 120);

        jrb5KiriNilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG5Kiri.add(jrb5KiriNilai2);
        jrb5KiriNilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb5KiriNilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb5KiriNilai2.setText("<html> Ada upaya melawan gravitasi; lengan tidak dapat diangkat atau dipertahankan dalam posisi 90 (40)°, jatuh mengenai tempat tidur </html>");
        jrb5KiriNilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb5KiriNilai2.setBorderPainted(true);
        jrb5KiriNilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb5KiriNilai2.setFocusPainted(false);
        jrb5KiriNilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb5KiriNilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb5KiriNilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb5KiriNilai2.setIconTextGap(2);
        jrb5KiriNilai2.setName("jrb5KiriNilai2"); // NOI18N
        jrb5KiriNilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb5KiriNilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb5KiriNilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb5KiriNilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb5KiriActionPerformed(evt);
            }
        });
        FormInput.add(jrb5KiriNilai2);
        jrb5KiriNilai2.setBounds(340, 1080, 140, 120);

        jrb5KiriNilai3.setBackground(new java.awt.Color(247, 247, 247));
        btnG5Kiri.add(jrb5KiriNilai3);
        jrb5KiriNilai3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb5KiriNilai3.setForeground(new java.awt.Color(51, 51, 51));
        jrb5KiriNilai3.setText("<html> Tidak ada upaya melawan gravitasi, tidak mampu mengangkat, hanya bergeser </html>");
        jrb5KiriNilai3.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb5KiriNilai3.setBorderPainted(true);
        jrb5KiriNilai3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb5KiriNilai3.setFocusPainted(false);
        jrb5KiriNilai3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb5KiriNilai3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb5KiriNilai3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb5KiriNilai3.setIconTextGap(2);
        jrb5KiriNilai3.setName("jrb5KiriNilai3"); // NOI18N
        jrb5KiriNilai3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb5KiriNilai3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb5KiriNilai3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb5KiriNilai3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb5KiriActionPerformed(evt);
            }
        });
        FormInput.add(jrb5KiriNilai3);
        jrb5KiriNilai3.setBounds(490, 1080, 140, 120);

        jrb5KiriNilai4.setBackground(new java.awt.Color(247, 247, 247));
        btnG5Kiri.add(jrb5KiriNilai4);
        jrb5KiriNilai4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb5KiriNilai4.setForeground(new java.awt.Color(51, 51, 51));
        jrb5KiriNilai4.setText("<html> Tidak ada gerakan </html>");
        jrb5KiriNilai4.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb5KiriNilai4.setBorderPainted(true);
        jrb5KiriNilai4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb5KiriNilai4.setFocusPainted(false);
        jrb5KiriNilai4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb5KiriNilai4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb5KiriNilai4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb5KiriNilai4.setIconTextGap(2);
        jrb5KiriNilai4.setName("jrb5KiriNilai4"); // NOI18N
        jrb5KiriNilai4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb5KiriNilai4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb5KiriNilai4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb5KiriNilai4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb5KiriActionPerformed(evt);
            }
        });
        FormInput.add(jrb5KiriNilai4);
        jrb5KiriNilai4.setBounds(640, 1080, 140, 120);

        jrb5KiriNilaiUN.setBackground(new java.awt.Color(247, 247, 247));
        btnG5Kiri.add(jrb5KiriNilaiUN);
        jrb5KiriNilaiUN.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb5KiriNilaiUN.setForeground(new java.awt.Color(51, 51, 51));
        jrb5KiriNilaiUN.setText("<html> Amputasi atau fusi sendi, jelaskan... </html>");
        jrb5KiriNilaiUN.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb5KiriNilaiUN.setBorderPainted(true);
        jrb5KiriNilaiUN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb5KiriNilaiUN.setFocusPainted(false);
        jrb5KiriNilaiUN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb5KiriNilaiUN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb5KiriNilaiUN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb5KiriNilaiUN.setIconTextGap(2);
        jrb5KiriNilaiUN.setName("jrb5KiriNilaiUN"); // NOI18N
        jrb5KiriNilaiUN.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb5KiriNilaiUN.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb5KiriNilaiUN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb5KiriNilaiUN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb5KiriActionPerformed(evt);
            }
        });
        FormInput.add(jrb5KiriNilaiUN);
        jrb5KiriNilaiUN.setBounds(40, 1210, 210, 44);

        label26.setText("Jelaskan :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(250, 1230, 80, 23);

        tb5KiriUN.setEnabled(false);
        tb5KiriUN.setName("tb5KiriUN"); // NOI18N
        tb5KiriUN.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(tb5KiriUN);
        tb5KiriUN.setBounds(340, 1230, 440, 23);

        label28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label28.setText("6. MOTORIK TUNGKAI");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(10, 1270, 700, 23);

        label29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label29.setText("a. Lengan Kanan");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(20, 860, 690, 23);

        jrb6KananNilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG6Kanan.add(jrb6KananNilai0);
        jrb6KananNilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb6KananNilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb6KananNilai0.setSelected(true);
        jrb6KananNilai0.setText("<html> Tidak ada drift; tungkai dapat dipertahankan dalam posisi 30°, minimal 5 detik </html>");
        jrb6KananNilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb6KananNilai0.setBorderPainted(true);
        jrb6KananNilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb6KananNilai0.setFocusPainted(false);
        jrb6KananNilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb6KananNilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb6KananNilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb6KananNilai0.setIconTextGap(2);
        jrb6KananNilai0.setName("jrb6KananNilai0"); // NOI18N
        jrb6KananNilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb6KananNilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb6KananNilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb6KananNilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb6KananActionPerformed(evt);
            }
        });
        FormInput.add(jrb6KananNilai0);
        jrb6KananNilai0.setBounds(40, 1310, 140, 120);

        jrb6KananNilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG6Kanan.add(jrb6KananNilai1);
        jrb6KananNilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb6KananNilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb6KananNilai1.setText("<html> Drift; tungkai jatuh persis 5 detik, namun tidak mengenai tempat tidur </html>");
        jrb6KananNilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb6KananNilai1.setBorderPainted(true);
        jrb6KananNilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb6KananNilai1.setFocusPainted(false);
        jrb6KananNilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb6KananNilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb6KananNilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb6KananNilai1.setIconTextGap(2);
        jrb6KananNilai1.setName("jrb6KananNilai1"); // NOI18N
        jrb6KananNilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb6KananNilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb6KananNilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb6KananNilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb6KananActionPerformed(evt);
            }
        });
        FormInput.add(jrb6KananNilai1);
        jrb6KananNilai1.setBounds(190, 1310, 140, 120);

        jrb6KananNilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG6Kanan.add(jrb6KananNilai2);
        jrb6KananNilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb6KananNilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb6KananNilai2.setText("<html> Ada upaya melawan gravitasi; tungkai jatuh mengenai tempat tidur dalam 5 detik, namun ada upaya melawan gravitasi</html>");
        jrb6KananNilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb6KananNilai2.setBorderPainted(true);
        jrb6KananNilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb6KananNilai2.setFocusPainted(false);
        jrb6KananNilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb6KananNilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb6KananNilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb6KananNilai2.setIconTextGap(2);
        jrb6KananNilai2.setName("jrb6KananNilai2"); // NOI18N
        jrb6KananNilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb6KananNilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb6KananNilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb6KananNilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb6KananActionPerformed(evt);
            }
        });
        FormInput.add(jrb6KananNilai2);
        jrb6KananNilai2.setBounds(340, 1310, 140, 120);

        jrb6KananNilai3.setBackground(new java.awt.Color(247, 247, 247));
        btnG6Kanan.add(jrb6KananNilai3);
        jrb6KananNilai3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb6KananNilai3.setForeground(new java.awt.Color(51, 51, 51));
        jrb6KananNilai3.setText("<html> Tidak ada upaya melawan gravitasi </html>");
        jrb6KananNilai3.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb6KananNilai3.setBorderPainted(true);
        jrb6KananNilai3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb6KananNilai3.setFocusPainted(false);
        jrb6KananNilai3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb6KananNilai3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb6KananNilai3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb6KananNilai3.setIconTextGap(2);
        jrb6KananNilai3.setName("jrb6KananNilai3"); // NOI18N
        jrb6KananNilai3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb6KananNilai3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb6KananNilai3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb6KananNilai3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb6KananActionPerformed(evt);
            }
        });
        FormInput.add(jrb6KananNilai3);
        jrb6KananNilai3.setBounds(490, 1310, 140, 120);

        jrb6KananNilai4.setBackground(new java.awt.Color(247, 247, 247));
        btnG6Kanan.add(jrb6KananNilai4);
        jrb6KananNilai4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb6KananNilai4.setForeground(new java.awt.Color(51, 51, 51));
        jrb6KananNilai4.setText("<html> Tidak ada gerakan </html>");
        jrb6KananNilai4.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb6KananNilai4.setBorderPainted(true);
        jrb6KananNilai4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb6KananNilai4.setFocusPainted(false);
        jrb6KananNilai4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb6KananNilai4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb6KananNilai4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb6KananNilai4.setIconTextGap(2);
        jrb6KananNilai4.setName("jrb6KananNilai4"); // NOI18N
        jrb6KananNilai4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb6KananNilai4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb6KananNilai4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb6KananNilai4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb6KananActionPerformed(evt);
            }
        });
        FormInput.add(jrb6KananNilai4);
        jrb6KananNilai4.setBounds(640, 1310, 140, 120);

        jrb6KananNilaiUN.setBackground(new java.awt.Color(247, 247, 247));
        btnG6Kanan.add(jrb6KananNilaiUN);
        jrb6KananNilaiUN.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb6KananNilaiUN.setForeground(new java.awt.Color(51, 51, 51));
        jrb6KananNilaiUN.setText("<html> Amputasi atau fusi sendi, jelaskan... </html>");
        jrb6KananNilaiUN.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb6KananNilaiUN.setBorderPainted(true);
        jrb6KananNilaiUN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb6KananNilaiUN.setFocusPainted(false);
        jrb6KananNilaiUN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb6KananNilaiUN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb6KananNilaiUN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb6KananNilaiUN.setIconTextGap(2);
        jrb6KananNilaiUN.setName("jrb6KananNilaiUN"); // NOI18N
        jrb6KananNilaiUN.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb6KananNilaiUN.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb6KananNilaiUN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb6KananNilaiUN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb6KananActionPerformed(evt);
            }
        });
        FormInput.add(jrb6KananNilaiUN);
        jrb6KananNilaiUN.setBounds(40, 1440, 210, 44);

        label30.setText("Jelaskan :");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label30);
        label30.setBounds(250, 1460, 80, 23);

        tb6KananUN.setEnabled(false);
        tb6KananUN.setName("tb6KananUN"); // NOI18N
        tb6KananUN.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(tb6KananUN);
        tb6KananUN.setBounds(340, 1460, 440, 23);

        label31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label31.setText("b. Tungkai Kiri");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label31);
        label31.setBounds(20, 1490, 690, 23);

        jrb6KiriNilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG6Kiri.add(jrb6KiriNilai0);
        jrb6KiriNilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb6KiriNilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb6KiriNilai0.setSelected(true);
        jrb6KiriNilai0.setText("<html> Tidak ada drift; tungkai dapat dipertahankan dalam posisi 30°, minimal 5 detik </html>");
        jrb6KiriNilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb6KiriNilai0.setBorderPainted(true);
        jrb6KiriNilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb6KiriNilai0.setFocusPainted(false);
        jrb6KiriNilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb6KiriNilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb6KiriNilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb6KiriNilai0.setIconTextGap(2);
        jrb6KiriNilai0.setName("jrb6KiriNilai0"); // NOI18N
        jrb6KiriNilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb6KiriNilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb6KiriNilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb6KiriNilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb6KiriActionPerformed(evt);
            }
        });
        FormInput.add(jrb6KiriNilai0);
        jrb6KiriNilai0.setBounds(40, 1510, 140, 120);

        jrb6KiriNilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG6Kiri.add(jrb6KiriNilai1);
        jrb6KiriNilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb6KiriNilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb6KiriNilai1.setText("<html> Drift; tungkai jatuh persis 5 detik, namun tidak mengenai tempat tidur </html>");
        jrb6KiriNilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb6KiriNilai1.setBorderPainted(true);
        jrb6KiriNilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb6KiriNilai1.setFocusPainted(false);
        jrb6KiriNilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb6KiriNilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb6KiriNilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb6KiriNilai1.setIconTextGap(2);
        jrb6KiriNilai1.setName("jrb6KiriNilai1"); // NOI18N
        jrb6KiriNilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb6KiriNilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb6KiriNilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb6KiriNilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb6KiriActionPerformed(evt);
            }
        });
        FormInput.add(jrb6KiriNilai1);
        jrb6KiriNilai1.setBounds(190, 1510, 140, 120);

        jrb6KiriNilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG6Kiri.add(jrb6KiriNilai2);
        jrb6KiriNilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb6KiriNilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb6KiriNilai2.setText("<html> Ada upaya melawan gravitasi; tungkai jatuh mengenai tempat tidur dalam 5 detik, namun ada upaya melawan gravitasi</html>");
        jrb6KiriNilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb6KiriNilai2.setBorderPainted(true);
        jrb6KiriNilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb6KiriNilai2.setFocusPainted(false);
        jrb6KiriNilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb6KiriNilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb6KiriNilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb6KiriNilai2.setIconTextGap(2);
        jrb6KiriNilai2.setName("jrb6KiriNilai2"); // NOI18N
        jrb6KiriNilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb6KiriNilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb6KiriNilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb6KiriNilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb6KiriActionPerformed(evt);
            }
        });
        FormInput.add(jrb6KiriNilai2);
        jrb6KiriNilai2.setBounds(340, 1510, 140, 120);

        jrb6KiriNilai3.setBackground(new java.awt.Color(247, 247, 247));
        btnG6Kiri.add(jrb6KiriNilai3);
        jrb6KiriNilai3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb6KiriNilai3.setForeground(new java.awt.Color(51, 51, 51));
        jrb6KiriNilai3.setText("<html> Tidak ada upaya melawan gravitasi </html>");
        jrb6KiriNilai3.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb6KiriNilai3.setBorderPainted(true);
        jrb6KiriNilai3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb6KiriNilai3.setFocusPainted(false);
        jrb6KiriNilai3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb6KiriNilai3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb6KiriNilai3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb6KiriNilai3.setIconTextGap(2);
        jrb6KiriNilai3.setName("jrb6KiriNilai3"); // NOI18N
        jrb6KiriNilai3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb6KiriNilai3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb6KiriNilai3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb6KiriNilai3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb6KiriActionPerformed(evt);
            }
        });
        FormInput.add(jrb6KiriNilai3);
        jrb6KiriNilai3.setBounds(490, 1510, 140, 120);

        jrb6KiriNilai4.setBackground(new java.awt.Color(247, 247, 247));
        btnG6Kiri.add(jrb6KiriNilai4);
        jrb6KiriNilai4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb6KiriNilai4.setForeground(new java.awt.Color(51, 51, 51));
        jrb6KiriNilai4.setText("<html> Tidak ada gerakan </html>");
        jrb6KiriNilai4.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb6KiriNilai4.setBorderPainted(true);
        jrb6KiriNilai4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb6KiriNilai4.setFocusPainted(false);
        jrb6KiriNilai4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb6KiriNilai4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb6KiriNilai4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb6KiriNilai4.setIconTextGap(2);
        jrb6KiriNilai4.setName("jrb6KiriNilai4"); // NOI18N
        jrb6KiriNilai4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb6KiriNilai4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb6KiriNilai4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb6KiriNilai4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb6KiriActionPerformed(evt);
            }
        });
        FormInput.add(jrb6KiriNilai4);
        jrb6KiriNilai4.setBounds(640, 1510, 140, 120);

        jrb6KiriNilaiUN.setBackground(new java.awt.Color(247, 247, 247));
        btnG6Kiri.add(jrb6KiriNilaiUN);
        jrb6KiriNilaiUN.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb6KiriNilaiUN.setForeground(new java.awt.Color(51, 51, 51));
        jrb6KiriNilaiUN.setText("<html> Amputasi atau fusi sendi, jelaskan... </html>");
        jrb6KiriNilaiUN.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb6KiriNilaiUN.setBorderPainted(true);
        jrb6KiriNilaiUN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb6KiriNilaiUN.setFocusPainted(false);
        jrb6KiriNilaiUN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb6KiriNilaiUN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb6KiriNilaiUN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb6KiriNilaiUN.setIconTextGap(2);
        jrb6KiriNilaiUN.setName("jrb6KiriNilaiUN"); // NOI18N
        jrb6KiriNilaiUN.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb6KiriNilaiUN.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb6KiriNilaiUN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb6KiriNilaiUN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb6KiriActionPerformed(evt);
            }
        });
        FormInput.add(jrb6KiriNilaiUN);
        jrb6KiriNilaiUN.setBounds(40, 1640, 210, 44);

        label32.setText("Jelaskan :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label32);
        label32.setBounds(250, 1660, 80, 23);

        tb6KiriUN.setEnabled(false);
        tb6KiriUN.setName("tb6KiriUN"); // NOI18N
        tb6KiriUN.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(tb6KiriUN);
        tb6KiriUN.setBounds(340, 1660, 440, 23);

        label33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label33.setText("7. ATAKSIA ANGGOTA GERAK");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label33);
        label33.setBounds(10, 1700, 700, 23);

        jrb7Nilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG7.add(jrb7Nilai0);
        jrb7Nilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb7Nilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb7Nilai0.setSelected(true);
        jrb7Nilai0.setText("<html> Tidak ada ataksia </html>");
        jrb7Nilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb7Nilai0.setBorderPainted(true);
        jrb7Nilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb7Nilai0.setFocusPainted(false);
        jrb7Nilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb7Nilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb7Nilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb7Nilai0.setIconTextGap(2);
        jrb7Nilai0.setName("jrb7Nilai0"); // NOI18N
        jrb7Nilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb7Nilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb7Nilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb7Nilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb7ActionPerformed(evt);
            }
        });
        FormInput.add(jrb7Nilai0);
        jrb7Nilai0.setBounds(40, 1720, 140, 60);

        jrb7Nilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG7.add(jrb7Nilai1);
        jrb7Nilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb7Nilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb7Nilai1.setText("<html> Ataksia pada satu ekstremitas </html>");
        jrb7Nilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb7Nilai1.setBorderPainted(true);
        jrb7Nilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb7Nilai1.setFocusPainted(false);
        jrb7Nilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb7Nilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb7Nilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb7Nilai1.setIconTextGap(2);
        jrb7Nilai1.setName("jrb7Nilai1"); // NOI18N
        jrb7Nilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb7Nilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb7Nilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb7Nilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb7ActionPerformed(evt);
            }
        });
        FormInput.add(jrb7Nilai1);
        jrb7Nilai1.setBounds(190, 1720, 140, 60);

        jrb7Nilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG7.add(jrb7Nilai2);
        jrb7Nilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb7Nilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb7Nilai2.setText("<html> Ataksia pada 2 atau lebih ekstremitas</html>");
        jrb7Nilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb7Nilai2.setBorderPainted(true);
        jrb7Nilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb7Nilai2.setFocusPainted(false);
        jrb7Nilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb7Nilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb7Nilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb7Nilai2.setIconTextGap(2);
        jrb7Nilai2.setName("jrb7Nilai2"); // NOI18N
        jrb7Nilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb7Nilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb7Nilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb7Nilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb7ActionPerformed(evt);
            }
        });
        FormInput.add(jrb7Nilai2);
        jrb7Nilai2.setBounds(340, 1720, 140, 60);

        jrb7NilaiUN.setBackground(new java.awt.Color(247, 247, 247));
        btnG7.add(jrb7NilaiUN);
        jrb7NilaiUN.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb7NilaiUN.setForeground(new java.awt.Color(51, 51, 51));
        jrb7NilaiUN.setText("<html> Amputasi atau fusi sendi, jelaskan... </html>");
        jrb7NilaiUN.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb7NilaiUN.setBorderPainted(true);
        jrb7NilaiUN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb7NilaiUN.setFocusPainted(false);
        jrb7NilaiUN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb7NilaiUN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb7NilaiUN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb7NilaiUN.setIconTextGap(2);
        jrb7NilaiUN.setName("jrb7NilaiUN"); // NOI18N
        jrb7NilaiUN.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb7NilaiUN.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb7NilaiUN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb7NilaiUN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb7ActionPerformed(evt);
            }
        });
        FormInput.add(jrb7NilaiUN);
        jrb7NilaiUN.setBounds(40, 1790, 210, 44);

        label34.setText("Jelaskan :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label34);
        label34.setBounds(250, 1810, 80, 23);

        tb7UN.setEnabled(false);
        tb7UN.setName("tb7UN"); // NOI18N
        tb7UN.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(tb7UN);
        tb7UN.setBounds(340, 1810, 440, 23);

        label35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label35.setText("8. SENSORIK");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label35);
        label35.setBounds(10, 1850, 700, 23);

        jrb8Nilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG8.add(jrb8Nilai0);
        jrb8Nilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb8Nilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb8Nilai0.setSelected(true);
        jrb8Nilai0.setText("<html> Normal; Tidak ada gangguan sensorik </html>");
        jrb8Nilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb8Nilai0.setBorderPainted(true);
        jrb8Nilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb8Nilai0.setFocusPainted(false);
        jrb8Nilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb8Nilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb8Nilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb8Nilai0.setIconTextGap(2);
        jrb8Nilai0.setName("jrb8Nilai0"); // NOI18N
        jrb8Nilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb8Nilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb8Nilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb8Nilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb8ActionPerformed(evt);
            }
        });
        FormInput.add(jrb8Nilai0);
        jrb8Nilai0.setBounds(40, 1870, 140, 100);

        jrb8Nilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG8.add(jrb8Nilai1);
        jrb8Nilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb8Nilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb8Nilai1.setText("<html> Gangguan sensorik tingan-sedang; sensasi disentuh atau nyeri berkurang namun masih terasa disentuh </html>");
        jrb8Nilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb8Nilai1.setBorderPainted(true);
        jrb8Nilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb8Nilai1.setFocusPainted(false);
        jrb8Nilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb8Nilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb8Nilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb8Nilai1.setIconTextGap(2);
        jrb8Nilai1.setName("jrb8Nilai1"); // NOI18N
        jrb8Nilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb8Nilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb8Nilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb8Nilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb8ActionPerformed(evt);
            }
        });
        FormInput.add(jrb8Nilai1);
        jrb8Nilai1.setBounds(190, 1870, 140, 100);

        jrb8Nilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG8.add(jrb8Nilai2);
        jrb8Nilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb8Nilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb8Nilai2.setText("<html> Gangguan sensorik berat; tidak merasakan sentuhan di wajah, lengan, atau tungkai</html>");
        jrb8Nilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb8Nilai2.setBorderPainted(true);
        jrb8Nilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb8Nilai2.setFocusPainted(false);
        jrb8Nilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb8Nilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb8Nilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb8Nilai2.setIconTextGap(2);
        jrb8Nilai2.setName("jrb8Nilai2"); // NOI18N
        jrb8Nilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb8Nilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb8Nilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb8Nilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb8ActionPerformed(evt);
            }
        });
        FormInput.add(jrb8Nilai2);
        jrb8Nilai2.setBounds(340, 1870, 140, 100);

        label36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label36.setText("9. BAHASA TERBALIK");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label36);
        label36.setBounds(10, 1980, 700, 23);

        jrb9Nilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG9.add(jrb9Nilai0);
        jrb9Nilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb9Nilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb9Nilai0.setSelected(true);
        jrb9Nilai0.setText("<html> Normal; Tidak ada afasia </html>");
        jrb9Nilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb9Nilai0.setBorderPainted(true);
        jrb9Nilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb9Nilai0.setFocusPainted(false);
        jrb9Nilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb9Nilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb9Nilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb9Nilai0.setIconTextGap(2);
        jrb9Nilai0.setName("jrb9Nilai0"); // NOI18N
        jrb9Nilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb9Nilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb9Nilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb9Nilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb9ActionPerformed(evt);
            }
        });
        FormInput.add(jrb9Nilai0);
        jrb9Nilai0.setBounds(40, 2000, 140, 140);

        jrb9Nilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG9.add(jrb9Nilai1);
        jrb9Nilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb9Nilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb9Nilai1.setText("<html> Afasia ringan-sedang; dapat berkomunikasi namun terbatas. Masih dapat mengenali benda namun kesulitan bicara percakapan dan mengerti percakapan </html>");
        jrb9Nilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb9Nilai1.setBorderPainted(true);
        jrb9Nilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb9Nilai1.setFocusPainted(false);
        jrb9Nilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb9Nilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb9Nilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb9Nilai1.setIconTextGap(2);
        jrb9Nilai1.setName("jrb9Nilai1"); // NOI18N
        jrb9Nilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb9Nilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb9Nilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb9Nilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb9ActionPerformed(evt);
            }
        });
        FormInput.add(jrb9Nilai1);
        jrb9Nilai1.setBounds(190, 2000, 140, 140);

        jrb9Nilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG9.add(jrb9Nilai2);
        jrb9Nilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb9Nilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb9Nilai2.setText("<html> Afasia berat; seluruh komunikasi melalui ekspresi yang terfragmentasi, dikira-kira dan pemeriksaan tidak dapat memahami respon pasien </html>");
        jrb9Nilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb9Nilai2.setBorderPainted(true);
        jrb9Nilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb9Nilai2.setFocusPainted(false);
        jrb9Nilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb9Nilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb9Nilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb9Nilai2.setIconTextGap(2);
        jrb9Nilai2.setName("jrb9Nilai2"); // NOI18N
        jrb9Nilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb9Nilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb9Nilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb9Nilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb9ActionPerformed(evt);
            }
        });
        FormInput.add(jrb9Nilai2);
        jrb9Nilai2.setBounds(340, 2000, 140, 140);

        jrb9Nilai3.setBackground(new java.awt.Color(247, 247, 247));
        btnG9.add(jrb9Nilai3);
        jrb9Nilai3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb9Nilai3.setForeground(new java.awt.Color(51, 51, 51));
        jrb9Nilai3.setText("<html> Mutisme, afasia global; tidak ada kata-kata yang keluar maupun pengertian akan kata-kata </html>");
        jrb9Nilai3.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb9Nilai3.setBorderPainted(true);
        jrb9Nilai3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb9Nilai3.setFocusPainted(false);
        jrb9Nilai3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb9Nilai3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb9Nilai3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb9Nilai3.setIconTextGap(2);
        jrb9Nilai3.setName("jrb9Nilai3"); // NOI18N
        jrb9Nilai3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb9Nilai3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb9Nilai3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb9Nilai3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb9ActionPerformed(evt);
            }
        });
        FormInput.add(jrb9Nilai3);
        jrb9Nilai3.setBounds(490, 2000, 140, 140);

        label37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label37.setText("10. DISARTRIA");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label37);
        label37.setBounds(10, 2150, 700, 23);

        jrb10Nilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG10.add(jrb10Nilai0);
        jrb10Nilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb10Nilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb10Nilai0.setSelected(true);
        jrb10Nilai0.setText("<html> Normal </html>");
        jrb10Nilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb10Nilai0.setBorderPainted(true);
        jrb10Nilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb10Nilai0.setFocusPainted(false);
        jrb10Nilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb10Nilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb10Nilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb10Nilai0.setIconTextGap(2);
        jrb10Nilai0.setName("jrb10Nilai0"); // NOI18N
        jrb10Nilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb10Nilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb10Nilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb10Nilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb10ActionPerformed(evt);
            }
        });
        FormInput.add(jrb10Nilai0);
        jrb10Nilai0.setBounds(40, 2170, 140, 120);

        jrb10Nilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG10.add(jrb10Nilai1);
        jrb10Nilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb10Nilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb10Nilai1.setText("<html> Disartria ringan-sedang; pasien pelo setidaknya pada beberapa kata namun meski berat dapat dimengerti </html>");
        jrb10Nilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb10Nilai1.setBorderPainted(true);
        jrb10Nilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb10Nilai1.setFocusPainted(false);
        jrb10Nilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb10Nilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb10Nilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb10Nilai1.setIconTextGap(2);
        jrb10Nilai1.setName("jrb10Nilai1"); // NOI18N
        jrb10Nilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb10Nilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb10Nilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb10Nilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb10ActionPerformed(evt);
            }
        });
        FormInput.add(jrb10Nilai1);
        jrb10Nilai1.setBounds(190, 2170, 140, 120);

        jrb10Nilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG10.add(jrb10Nilai2);
        jrb10Nilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb10Nilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb10Nilai2.setText("<html> Disartria berat; bicara pasien sangat pelo namun tidak afasia </html>");
        jrb10Nilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb10Nilai2.setBorderPainted(true);
        jrb10Nilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb10Nilai2.setFocusPainted(false);
        jrb10Nilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb10Nilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb10Nilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb10Nilai2.setIconTextGap(2);
        jrb10Nilai2.setName("jrb10Nilai2"); // NOI18N
        jrb10Nilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb10Nilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb10Nilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb10Nilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb10ActionPerformed(evt);
            }
        });
        FormInput.add(jrb10Nilai2);
        jrb10Nilai2.setBounds(340, 2170, 140, 120);

        jrb10NilaiUN.setBackground(new java.awt.Color(247, 247, 247));
        btnG10.add(jrb10NilaiUN);
        jrb10NilaiUN.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb10NilaiUN.setForeground(new java.awt.Color(51, 51, 51));
        jrb10NilaiUN.setText("<html> Intubasi atau hambatan fisik lain, jelaskan... </html>");
        jrb10NilaiUN.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb10NilaiUN.setBorderPainted(true);
        jrb10NilaiUN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb10NilaiUN.setFocusPainted(false);
        jrb10NilaiUN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb10NilaiUN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb10NilaiUN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb10NilaiUN.setIconTextGap(2);
        jrb10NilaiUN.setName("jrb10NilaiUN"); // NOI18N
        jrb10NilaiUN.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb10NilaiUN.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb10NilaiUN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb10NilaiUN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb10ActionPerformed(evt);
            }
        });
        FormInput.add(jrb10NilaiUN);
        jrb10NilaiUN.setBounds(40, 2300, 235, 44);

        label38.setText("Jelaskan :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label38);
        label38.setBounds(270, 2320, 60, 23);

        tb10UN.setEnabled(false);
        tb10UN.setName("tb10UN"); // NOI18N
        tb10UN.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(tb10UN);
        tb10UN.setBounds(340, 2320, 440, 23);

        label39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label39.setText("11. PENGABAIAN & INATENSI (NEGLECT)");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label39);
        label39.setBounds(10, 2360, 700, 23);

        jrb11Nilai0.setBackground(new java.awt.Color(247, 247, 247));
        btnG11.add(jrb11Nilai0);
        jrb11Nilai0.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb11Nilai0.setForeground(new java.awt.Color(51, 51, 51));
        jrb11Nilai0.setSelected(true);
        jrb11Nilai0.setText("<html> Tidak ada neglect </html>");
        jrb11Nilai0.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb11Nilai0.setBorderPainted(true);
        jrb11Nilai0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb11Nilai0.setFocusPainted(false);
        jrb11Nilai0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb11Nilai0.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb11Nilai0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb11Nilai0.setIconTextGap(2);
        jrb11Nilai0.setName("jrb11Nilai0"); // NOI18N
        jrb11Nilai0.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb11Nilai0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb11Nilai0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb11Nilai0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb11ActionPerformed(evt);
            }
        });
        FormInput.add(jrb11Nilai0);
        jrb11Nilai0.setBounds(40, 2380, 140, 100);

        jrb11Nilai1.setBackground(new java.awt.Color(247, 247, 247));
        btnG11.add(jrb11Nilai1);
        jrb11Nilai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb11Nilai1.setForeground(new java.awt.Color(51, 51, 51));
        jrb11Nilai1.setText("<html> Tidak ada atensi pada salah satu modalitas berikut; visual, tactile, auditory, spatial, or personal inattention </html>");
        jrb11Nilai1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb11Nilai1.setBorderPainted(true);
        jrb11Nilai1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb11Nilai1.setFocusPainted(false);
        jrb11Nilai1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb11Nilai1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb11Nilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb11Nilai1.setIconTextGap(2);
        jrb11Nilai1.setName("jrb11Nilai1"); // NOI18N
        jrb11Nilai1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb11Nilai1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb11Nilai1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb11Nilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb11ActionPerformed(evt);
            }
        });
        FormInput.add(jrb11Nilai1);
        jrb11Nilai1.setBounds(190, 2380, 140, 100);

        jrb11Nilai2.setBackground(new java.awt.Color(247, 247, 247));
        btnG11.add(jrb11Nilai2);
        jrb11Nilai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jrb11Nilai2.setForeground(new java.awt.Color(51, 51, 51));
        jrb11Nilai2.setText("<html> Tidak ada atensi pada lebih dari satu modalitas </html>");
        jrb11Nilai2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        jrb11Nilai2.setBorderPainted(true);
        jrb11Nilai2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jrb11Nilai2.setFocusPainted(false);
        jrb11Nilai2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jrb11Nilai2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jrb11Nilai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconkosong.png"))); // NOI18N
        jrb11Nilai2.setIconTextGap(2);
        jrb11Nilai2.setName("jrb11Nilai2"); // NOI18N
        jrb11Nilai2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        jrb11Nilai2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jrb11Nilai2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jrb11Nilai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb11ActionPerformed(evt);
            }
        });
        FormInput.add(jrb11Nilai2);
        jrb11Nilai2.setBounds(340, 2380, 140, 100);

        label27.setBackground(new java.awt.Color(244, 244, 244));
        label27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        label27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label27.setText("TOTAL");
        label27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label27.setName("label27"); // NOI18N
        label27.setOpaque(true);
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(3, 2490, 789, 30);

        lblSkor1A.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor1A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor1A.setText("0");
        lblSkor1A.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor1A.setName("lblSkor1A"); // NOI18N
        lblSkor1A.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor1A);
        lblSkor1A.setBounds(791, 140, 160, 90);

        lblSkor1B.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor1B.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor1B.setText("0");
        lblSkor1B.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor1B.setName("lblSkor1B"); // NOI18N
        lblSkor1B.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor1B);
        lblSkor1B.setBounds(791, 260, 160, 60);

        lblSkor1C.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor1C.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor1C.setText("0");
        lblSkor1C.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor1C.setName("lblSkor1C"); // NOI18N
        lblSkor1C.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor1C);
        lblSkor1C.setBounds(791, 360, 160, 60);

        lblSkor2.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor2.setText("0");
        lblSkor2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor2.setName("lblSkor2"); // NOI18N
        lblSkor2.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor2);
        lblSkor2.setBounds(791, 450, 160, 120);

        lblSkor3.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor3.setText("0");
        lblSkor3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor3.setName("lblSkor3"); // NOI18N
        lblSkor3.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor3);
        lblSkor3.setBounds(791, 600, 160, 100);

        lblSkor4.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor4.setText("0");
        lblSkor4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor4.setName("lblSkor4"); // NOI18N
        lblSkor4.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor4);
        lblSkor4.setBounds(791, 730, 160, 100);

        lblSkor5Kanan.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor5Kanan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor5Kanan.setText("0");
        lblSkor5Kanan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor5Kanan.setName("lblSkor5Kanan"); // NOI18N
        lblSkor5Kanan.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor5Kanan);
        lblSkor5Kanan.setBounds(791, 880, 160, 180);

        lblSkor5Kiri.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor5Kiri.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor5Kiri.setText("0");
        lblSkor5Kiri.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor5Kiri.setName("lblSkor5Kiri"); // NOI18N
        lblSkor5Kiri.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor5Kiri);
        lblSkor5Kiri.setBounds(791, 1080, 160, 180);

        lblSkor6Kanan.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor6Kanan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor6Kanan.setText("0");
        lblSkor6Kanan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor6Kanan.setName("lblSkor6Kanan"); // NOI18N
        lblSkor6Kanan.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor6Kanan);
        lblSkor6Kanan.setBounds(791, 1310, 160, 180);

        lblSkor6Kiri.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor6Kiri.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor6Kiri.setText("0");
        lblSkor6Kiri.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor6Kiri.setName("lblSkor6Kiri"); // NOI18N
        lblSkor6Kiri.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor6Kiri);
        lblSkor6Kiri.setBounds(791, 1510, 160, 180);

        lblSkor7.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor7.setText("0");
        lblSkor7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor7.setName("lblSkor7"); // NOI18N
        lblSkor7.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor7);
        lblSkor7.setBounds(791, 1720, 160, 120);

        lblSkor8.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor8.setText("0");
        lblSkor8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor8.setName("lblSkor8"); // NOI18N
        lblSkor8.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor8);
        lblSkor8.setBounds(791, 1870, 160, 100);

        lblSkor9.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor9.setText("0");
        lblSkor9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor9.setName("lblSkor9"); // NOI18N
        lblSkor9.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor9);
        lblSkor9.setBounds(791, 2000, 160, 140);

        lblSkor10.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor10.setText("0");
        lblSkor10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor10.setName("lblSkor10"); // NOI18N
        lblSkor10.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor10);
        lblSkor10.setBounds(791, 2170, 160, 180);

        lblSkor11.setBackground(new java.awt.Color(244, 244, 244));
        lblSkor11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSkor11.setText("0");
        lblSkor11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSkor11.setName("lblSkor11"); // NOI18N
        lblSkor11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblSkor11);
        lblSkor11.setBounds(791, 2380, 160, 100);

        lblTotalSkor.setBackground(new java.awt.Color(239, 252, 240));
        lblTotalSkor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lblTotalSkor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalSkor.setText("0");
        lblTotalSkor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotalSkor.setName("lblTotalSkor"); // NOI18N
        lblTotalSkor.setOpaque(true);
        lblTotalSkor.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblTotalSkor);
        lblTotalSkor.setBounds(791, 2490, 160, 30);

        label40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label40.setText("Keterangan :");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label40);
        label40.setBounds(10, 2535, 120, 23);

        label41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label41.setText("Skor >= 25");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label41);
        label41.setBounds(250, 2565, 70, 23);

        label42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label42.setText(": Defisit Neurologis Ringan");
        label42.setName("label42"); // NOI18N
        label42.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label42);
        label42.setBounds(90, 2550, 180, 23);

        label43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label43.setText("Skor 6 - 14");
        label43.setName("label43"); // NOI18N
        label43.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label43);
        label43.setBounds(20, 2565, 70, 23);

        label44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label44.setText("Skor 15 - 24");
        label44.setName("label44"); // NOI18N
        label44.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label44);
        label44.setBounds(250, 2550, 70, 23);

        label45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label45.setText("Skor <= 5");
        label45.setName("label45"); // NOI18N
        label45.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label45);
        label45.setBounds(20, 2550, 70, 23);

        label46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label46.setText(": Defisit Neurologis Sangat Berat");
        label46.setName("label46"); // NOI18N
        label46.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label46);
        label46.setBounds(320, 2565, 170, 23);

        label47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label47.setText(": Defisit Neurologis Sedang");
        label47.setName("label47"); // NOI18N
        label47.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label47);
        label47.setBounds(90, 2565, 180, 23);

        label48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label48.setText(": Defisit Neurologis Berat");
        label48.setName("label48"); // NOI18N
        label48.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label48);
        label48.setBounds(320, 2550, 170, 23);

        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(3, 2600, 948, 5);

        lblKetSkortxt.setBackground(new java.awt.Color(254, 255, 242));
        lblKetSkortxt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        lblKetSkortxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKetSkortxt.setText("Defisit Neurologis");
        lblKetSkortxt.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblKetSkortxt.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        lblKetSkortxt.setName("lblKetSkortxt"); // NOI18N
        lblKetSkortxt.setOpaque(true);
        lblKetSkortxt.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblKetSkortxt);
        lblKetSkortxt.setBounds(791, 2519, 160, 40);

        lblKetSkor.setBackground(new java.awt.Color(254, 255, 242));
        lblKetSkor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        lblKetSkor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKetSkor.setText("Ringan");
        lblKetSkor.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblKetSkor.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        lblKetSkor.setName("lblKetSkor"); // NOI18N
        lblKetSkor.setOpaque(true);
        lblKetSkor.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(lblKetSkor);
        lblKetSkor.setBounds(791, 2560, 160, 41);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Penilaian", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-11-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-11-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
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
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Penilaian", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPenilai.getText().trim().equals("")){
            Valid.textKosong(BtnPenilai,"Penilai");
        }else{
            if(Sequel.menyimpantf("penilaian_nihss","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat dan Tgl.Pemeriksaan",26,new String[]{
                    TNoRw.getText(),TglAsuhan.getSelectedItem().toString(),KdPenilai.getText(),lblSkor1A.getText().trim(),lblSkor1B.getText().trim(),lblSkor1C.getText().trim(),
                    lblSkor2.getText().trim(),lblSkor3.getText().trim(),lblSkor4.getText().trim(),lblSkor5Kanan.getText().trim(),lblSkor5Kiri.getText().trim(),lblSkor6Kanan.getText().trim(),lblSkor6Kiri.getText().trim(),
                    lblSkor7.getText().trim(),lblSkor8.getText().trim(),lblSkor9.getText().trim(),lblSkor10.getText().trim(),lblSkor11.getText().trim(),tb5KananUN.getText().trim(),tb5KiriUN.getText().trim(),
                    tb6KananUN.getText().trim(),tb6KiriUN.getText().trim(),tb7UN.getText().trim(),tb10UN.getText().trim(),lblTotalSkor.getText().trim(),lblKetSkor.getText().trim()
                })==true){
                    tampil();
                    emptTeks();
            }
        }
    
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt, TglAsuhan, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdPenilai.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPenilai.getText().trim().equals("")){
            Valid.textKosong(BtnPenilai,"Penilai");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdPenilai.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
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
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
                htmlContent = new StringBuilder();
                htmlContent.append(
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>No.Rawat</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>No.RM</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>Nama Pasien</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>Tgl.Lahir</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>J.K.</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>Kode Penilai</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>Nama Penilai</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>Tgl.Pemeriksaan</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>Ttl Skor</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>Defisit Neurologis</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>1A</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>1B</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>1C</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>2</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>3</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>4</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>5 Kanan</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>Pnj. 5 Kanan</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>5 Kiri</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>Pnj. 5 Kiri</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>6 Kanan</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>Pnj. 6 Kanan</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>6 Kiri</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>Pnj. 6 Kiri</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>7</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>Pnj. 7</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>8</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>9</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>10</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center'>Pnj. 10</td>"+
                        "<td valign='middle' bgcolor='#FFFFF8' align='center' width='2%'>11</td>"+
                    "</tr>"
                );
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                        "<tr class='isi'>"+
                           "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,20).toString()+"</td>"+ 
                            "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,23).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,24).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,27).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,28).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,29).toString()+"</td>"+
                            "<td valign='top' align='center'>"+tbObat.getValueAt(i,30).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                    ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                    ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                    ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                    ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                );
                bg.close();

                File f = new File("DataPenilaianPasienStrokeNIHSS.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENILAIAN PASIEN STROKE DENGAN NIHSS<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());

            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
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
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void MnPenilaianStrokeNIHSSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianStrokeNIHSSActionPerformed
        if(tbObat.getSelectedRow()>-1){
            try{
                rs=koneksi.prepareStatement(
                            "select penilaian_nihss.*,pegawai.nama from penilaian_nihss "+
                            "inner join pegawai on penilaian_nihss.kd_penilai=pegawai.nik where "+
                            "penilaian_nihss.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' order by penilaian_nihss.tgl_pemeriksaan").executeQuery();
                    htmlContent = new StringBuilder();
                    htmlContent.append(
                        "<tr class='isi-head'>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='8%' rowspan='3'>Tgl Penilaian</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='12%' rowspan='3'>Petugas Penilai</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='60%' colspan='15'>Skor Penilaian</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='8%' rowspan='3'>Total Skor</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='12%' rowspan='3'>Keterangan</td>"+
                        "</tr>"+
                        "<tr class='isi-head'>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%' rowspan='2'>1A</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%' rowspan='2'>1B</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%' rowspan='2'>1C</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%' rowspan='2'>2</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%' rowspan='2'>3</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%' rowspan='2'>4</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='8%' colspan='2'>5</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='8%' colspan='2'>6</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%' rowspan='2'>7</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%' rowspan='2'>8</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%' rowspan='2'>9</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%' rowspan='2'>10</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%' rowspan='2'>11</td>"+
                        "</tr>"+
                        "<tr class='isi-head'>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%'>Kanan</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%'>Kiri</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%'>Kanan</td>"+
                            "<td valign='middle' bgcolor='#FFFFF8' align='center' width='4%'>Kiri</td>"+
                        "</tr>");
                    while(rs.next()){
                                htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td align='center'>"+rs.getString("tgl_pemeriksaan")+"</td>"+
                                    "<td align='center'>"+rs.getString("kd_penilai")+"<br>"+rs.getString("nama")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_1a")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_1b")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_1c")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_2")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_3")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_4")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_5kanan")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_5kiri")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_6kanan")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_6kiri")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_7")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_8")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_9")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_10")+"</td>"+
                                    "<td align='center'>"+rs.getString("p_11")+"</td>"+
                                    "<td align='center'>"+rs.getString("total_skor")+"</td>"+
                                    "<td align='center'>Defisit Neurologis "+rs.getString("defisit_neurologis")+"</td>"+
                                "</tr>"
                                );

                                if (!rs.getString("j_5kanan").trim().isEmpty() || !rs.getString("j_5kiri").trim().isEmpty() ||
                                    !rs.getString("j_6kanan").trim().isEmpty() || !rs.getString("j_6kiri").trim().isEmpty() ||
                                    !rs.getString("j_7").trim().isEmpty() || !rs.getString("j_10").trim().isEmpty()) {
                                    htmlContent.append(
                                    "<tr class='isi'>"+
                                        "<td align='right' colspan='2'>Jelaskan Nilai UN</td>"+
                                        "<td align='left' colspan='17'>"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'><tr class='isi2'>"+
                                                (rs.getString("j_5kanan").isEmpty()?"":"<td align='right' width='7%'>5 Kanan</td><td> : "+rs.getString("j_5kanan")+"</td>")+
                                                (rs.getString("j_5kiri").isEmpty()?"":"<td align='right' width='7%'>5 Kiri</td><td> : "+rs.getString("j_5kiri")+"</td>")+
                                                (rs.getString("j_6kanan").isEmpty()?"":"<td align='right' width='7%'>6 Kanan</td><td> : "+rs.getString("j_6kanan")+"</td>")+
                                                (rs.getString("j_6kiri").isEmpty()?"":"<td align='right' width='7%'>6 Kiri</td><td> : "+rs.getString("j_6kiri")+"</td>")+
                                                (rs.getString("j_7").isEmpty()?"":"<td align='right' width='7%'>7</td><td> : "+rs.getString("j_7")+"</td>")+
                                                (rs.getString("j_10").isEmpty()?"":"<td align='right' width='7%'>10</td><td> : "+rs.getString("j_10")+"</td>")+
                                            "</tr></table>"+
                                        "</td>"+
                                    "</tr>");
                                }
                            }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                           htmlContent.toString()+
                          "</table>"+
                        "</html>"
                    );

                    File g = new File("file2.css");            
                    BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                    bg.write(
                        ".isi-head td{border: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;background: #fffff8;color:#323232;}"+
                        ".isi td{border: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                        ".isi2 td{border:none; font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                        ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                        ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                        ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                        ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                    );
                    bg.close();

                    File f = new File("PenilaianStrokeNIHSS_"+TNoRw.getText().replaceAll("/", "")+".html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                        "<td valign='top' align='center' width='75%'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+
                                        "</td>"+
                                        "<td valign='top' width='25%' rowspan='2'>"+
                                            "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' width='15%'>No.RM</td>"+
                                                    "<td valign='top'>: "+TNoRM.getText()+"</td>"+
                                                "</tr>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' width='15%'>Nama</td>"+
                                                    "<td valign='top'>: "+TPasien.getText()+"</td>"+
                                                "</tr>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' width='15%'>Tgl.Lahir</td>"+
                                                    "<td valign='top'>: "+TglLahir.getText()+"</td>"+
                                                "</tr>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' width='15%'>Ruang</td>"+
                                                    "<td valign='top'>: "+Sequel.cariIsi("select kd_kamar from kamar_inap where no_rawat=? order by tgl_masuk desc, jam_masuk desc limit 1", TNoRw.getText())+"</td>"+
                                                "</tr>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' width='15%'>Alamat</td>"+
                                                    "<td valign='top'>: "+Sequel.cariIsi("select alamat from pasien where no_rkm_medis=?", TNoRM.getText())+"</td>"+
                                                "</tr>"+
                                            "</table>"+
                                        "</td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='2' face='Tahoma'>PENILAIAN PASIEN STROKE DENGAN NIHSS</font>"+    
                                        "</td>"+
                                    "</tr>"+
                                "</table><br>")
                    );
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());

            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                try {
                    if(rs != null){
                        rs.close();
                    }
                }catch (Exception ex) {
                    System.out.println("Notif Penilaian NIHSS : "+ex);
                }
            }
        }
    }//GEN-LAST:event_MnPenilaianStrokeNIHSSActionPerformed

    private void BtnPenilaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPenilaiKeyPressed
        //Valid.pindah(evt,DiagnosaTambahan,TglAsuhan);
    }//GEN-LAST:event_BtnPenilaiKeyPressed

    private void BtnPenilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenilaiActionPerformed
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnPenilaiActionPerformed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnPenilai);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void jrb1AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb1AActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor1A.setText(nilai);
        }
    }//GEN-LAST:event_jrb1AActionPerformed

    private void jrb1BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb1BActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor1B.setText(nilai);
        }
    }//GEN-LAST:event_jrb1BActionPerformed

    private void jrb1CActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb1CActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor1C.setText(nilai);
        }
    }//GEN-LAST:event_jrb1CActionPerformed

    private void jrb2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb2ActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor2.setText(nilai);
        }
    }//GEN-LAST:event_jrb2ActionPerformed

    private void jrb3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb3ActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor3.setText(nilai);
        }
    }//GEN-LAST:event_jrb3ActionPerformed

    private void jrb4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb4ActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor4.setText(nilai);
        }
    }//GEN-LAST:event_jrb4ActionPerformed

    private void jrb5KananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb5KananActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor5Kanan.setText(nilai);
            if(nilai.equals("UN")){
                tb5KananUN.setEnabled(true);
            } else {
                tb5KananUN.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jrb5KananActionPerformed

    private void jrb5KiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb5KiriActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor5Kiri.setText(nilai);
            if(nilai.equals("UN")){
                tb5KiriUN.setEnabled(true);
            } else {
                tb5KiriUN.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jrb5KiriActionPerformed

    private void jrb6KananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb6KananActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor6Kanan.setText(nilai);
            if(nilai.equals("UN")){
                tb6KananUN.setEnabled(true);
            } else {
                tb6KananUN.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jrb6KananActionPerformed

    private void jrb6KiriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb6KiriActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor6Kiri.setText(nilai);
            if(nilai.equals("UN")){
                tb6KiriUN.setEnabled(true);
            } else {
                tb6KiriUN.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jrb6KiriActionPerformed

    private void jrb7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb7ActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor7.setText(nilai);
            if(nilai.equals("UN")){
                tb7UN.setEnabled(true);
            } else {
                tb7UN.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jrb7ActionPerformed

    private void jrb8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb8ActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor8.setText(nilai);
        }
    }//GEN-LAST:event_jrb8ActionPerformed

    private void jrb9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb9ActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor9.setText(nilai);
        }
    }//GEN-LAST:event_jrb9ActionPerformed

    private void jrb10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb10ActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor10.setText(nilai);
            if(nilai.equals("UN")){
                tb10UN.setEnabled(true);
            } else {
                tb10UN.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jrb10ActionPerformed

    private void jrb11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb11ActionPerformed
        javax.swing.JRadioButton selectedRadio = (javax.swing.JRadioButton) evt.getSource();
        String varName = selectedRadio.getName();
    
        if (varName != null && varName.contains("Nilai")) {
            String nilai = varName.substring(varName.indexOf("Nilai") + 5);
            lblSkor11.setText(nilai);
        }
    }//GEN-LAST:event_jrb11ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianStrokeNIHSS dialog = new RMPenilaianStrokeNIHSS(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPenilai;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Jk;
    private widget.TextBox KdPenilai;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnPenilaianStrokeNIHSS;
    private widget.TextBox NmPenilai;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private javax.swing.ButtonGroup btnG10;
    private javax.swing.ButtonGroup btnG11;
    private javax.swing.ButtonGroup btnG1a;
    private javax.swing.ButtonGroup btnG1b;
    private javax.swing.ButtonGroup btnG1c;
    private javax.swing.ButtonGroup btnG2;
    private javax.swing.ButtonGroup btnG3;
    private javax.swing.ButtonGroup btnG4;
    private javax.swing.ButtonGroup btnG5Kanan;
    private javax.swing.ButtonGroup btnG5Kiri;
    private javax.swing.ButtonGroup btnG6Kanan;
    private javax.swing.ButtonGroup btnG6Kiri;
    private javax.swing.ButtonGroup btnG7;
    private javax.swing.ButtonGroup btnG8;
    private javax.swing.ButtonGroup btnG9;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JRadioButton jrb10Nilai0;
    private javax.swing.JRadioButton jrb10Nilai1;
    private javax.swing.JRadioButton jrb10Nilai2;
    private javax.swing.JRadioButton jrb10NilaiUN;
    private javax.swing.JRadioButton jrb11Nilai0;
    private javax.swing.JRadioButton jrb11Nilai1;
    private javax.swing.JRadioButton jrb11Nilai2;
    private javax.swing.JRadioButton jrb1ANilai0;
    private javax.swing.JRadioButton jrb1ANilai1;
    private javax.swing.JRadioButton jrb1ANilai2;
    private javax.swing.JRadioButton jrb1ANilai3;
    private javax.swing.JRadioButton jrb1BNilai0;
    private javax.swing.JRadioButton jrb1BNilai1;
    private javax.swing.JRadioButton jrb1BNilai2;
    private javax.swing.JRadioButton jrb1CNilai0;
    private javax.swing.JRadioButton jrb1CNilai1;
    private javax.swing.JRadioButton jrb1CNilai2;
    private javax.swing.JRadioButton jrb2Nilai0;
    private javax.swing.JRadioButton jrb2Nilai1;
    private javax.swing.JRadioButton jrb2Nilai2;
    private javax.swing.JRadioButton jrb3Nilai0;
    private javax.swing.JRadioButton jrb3Nilai1;
    private javax.swing.JRadioButton jrb3Nilai2;
    private javax.swing.JRadioButton jrb3Nilai3;
    private javax.swing.JRadioButton jrb4Nilai0;
    private javax.swing.JRadioButton jrb4Nilai1;
    private javax.swing.JRadioButton jrb4Nilai2;
    private javax.swing.JRadioButton jrb4Nilai3;
    private javax.swing.JRadioButton jrb5KananNilai0;
    private javax.swing.JRadioButton jrb5KananNilai1;
    private javax.swing.JRadioButton jrb5KananNilai2;
    private javax.swing.JRadioButton jrb5KananNilai3;
    private javax.swing.JRadioButton jrb5KananNilai4;
    private javax.swing.JRadioButton jrb5KananNilaiUN;
    private javax.swing.JRadioButton jrb5KiriNilai0;
    private javax.swing.JRadioButton jrb5KiriNilai1;
    private javax.swing.JRadioButton jrb5KiriNilai2;
    private javax.swing.JRadioButton jrb5KiriNilai3;
    private javax.swing.JRadioButton jrb5KiriNilai4;
    private javax.swing.JRadioButton jrb5KiriNilaiUN;
    private javax.swing.JRadioButton jrb6KananNilai0;
    private javax.swing.JRadioButton jrb6KananNilai1;
    private javax.swing.JRadioButton jrb6KananNilai2;
    private javax.swing.JRadioButton jrb6KananNilai3;
    private javax.swing.JRadioButton jrb6KananNilai4;
    private javax.swing.JRadioButton jrb6KananNilaiUN;
    private javax.swing.JRadioButton jrb6KiriNilai0;
    private javax.swing.JRadioButton jrb6KiriNilai1;
    private javax.swing.JRadioButton jrb6KiriNilai2;
    private javax.swing.JRadioButton jrb6KiriNilai3;
    private javax.swing.JRadioButton jrb6KiriNilai4;
    private javax.swing.JRadioButton jrb6KiriNilaiUN;
    private javax.swing.JRadioButton jrb7Nilai0;
    private javax.swing.JRadioButton jrb7Nilai1;
    private javax.swing.JRadioButton jrb7Nilai2;
    private javax.swing.JRadioButton jrb7NilaiUN;
    private javax.swing.JRadioButton jrb8Nilai0;
    private javax.swing.JRadioButton jrb8Nilai1;
    private javax.swing.JRadioButton jrb8Nilai2;
    private javax.swing.JRadioButton jrb9Nilai0;
    private javax.swing.JRadioButton jrb9Nilai1;
    private javax.swing.JRadioButton jrb9Nilai2;
    private javax.swing.JRadioButton jrb9Nilai3;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
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
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label29;
    private widget.Label label30;
    private widget.Label label31;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label40;
    private widget.Label label41;
    private widget.Label label42;
    private widget.Label label43;
    private widget.Label label44;
    private widget.Label label45;
    private widget.Label label46;
    private widget.Label label47;
    private widget.Label label48;
    private widget.Label lblKetSkor;
    private widget.Label lblKetSkortxt;
    private widget.Label lblSkor10;
    private widget.Label lblSkor11;
    private widget.Label lblSkor1A;
    private widget.Label lblSkor1B;
    private widget.Label lblSkor1C;
    private widget.Label lblSkor2;
    private widget.Label lblSkor3;
    private widget.Label lblSkor4;
    private widget.Label lblSkor5Kanan;
    private widget.Label lblSkor5Kiri;
    private widget.Label lblSkor6Kanan;
    private widget.Label lblSkor6Kiri;
    private widget.Label lblSkor7;
    private widget.Label lblSkor8;
    private widget.Label lblSkor9;
    private widget.Label lblTotalSkor;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.TextBox tb10UN;
    private widget.TextBox tb5KananUN;
    private widget.TextBox tb5KiriUN;
    private widget.TextBox tb6KananUN;
    private widget.TextBox tb6KiriUN;
    private widget.TextBox tb7UN;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                        "penilaian_nihss.*,pegawai.nama from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_nihss on reg_periksa.no_rawat=penilaian_nihss.no_rawat "+
                        "inner join pegawai on penilaian_nihss.kd_penilai=pegawai.nik where "+
                        "penilaian_nihss.tgl_pemeriksaan between ? and ? order by penilaian_nihss.tgl_pemeriksaan");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,"+
                        "penilaian_nihss.*,pegawai.nama from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join penilaian_nihss on reg_periksa.no_rawat=penilaian_nihss.no_rawat "+
                        "inner join pegawai on penilaian_nihss.kd_penilai=pegawai.nik where "+
                        "penilaian_nihss.tgl_pemeriksaan between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "penilaian_nihss.kd_penilai like ? or pegawai.nama like ?) order by penilaian_nihss.tgl_pemeriksaan");
            }
                
            try {
                if(TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("kd_penilai"),rs.getString("nama"),rs.getString("tgl_pemeriksaan"),
                        rs.getString("total_skor"),rs.getString("defisit_neurologis"),rs.getString("p_1a"),rs.getString("p_1b"),rs.getString("p_1c"),rs.getString("p_2"),rs.getString("p_3"),rs.getString("p_4"),rs.getString("p_5kanan"),
                        rs.getString("j_5kanan"),rs.getString("p_5kiri"),rs.getString("j_5kiri"),rs.getString("p_6kanan"),rs.getString("j_6kanan"),rs.getString("p_6kiri"),rs.getString("j_6kiri"),
                        rs.getString("p_7"),rs.getString("j_7"),rs.getString("p_8"),rs.getString("p_9"),rs.getString("p_10"),rs.getString("j_10"),rs.getString("p_11")
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
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TglAsuhan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        tb5KananUN.setText("");
        tb5KiriUN.setText("");
        tb6KananUN.setText("");
        tb6KiriUN.setText("");
        tb7UN.setText("");
        tb10UN.setText("");
        jrb1ANilai0.doClick();
        jrb1BNilai0.doClick();
        jrb1CNilai0.doClick();
        jrb2Nilai0.doClick();
        jrb3Nilai0.doClick();
        jrb4Nilai0.doClick();
        jrb5KananNilai0.doClick();
        jrb5KiriNilai0.doClick();
        jrb6KananNilai0.doClick();
        jrb6KiriNilai0.doClick();
        jrb7Nilai0.doClick();
        jrb8Nilai0.doClick();
        jrb9Nilai0.doClick();
        jrb10Nilai0.doClick();
        jrb11Nilai0.doClick();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Valid.SetTgl2(TglAsuhan,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            lblTotalSkor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            lblKetSkor.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            selectRadioButton("1A", tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            selectRadioButton("1B", tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            selectRadioButton("1C", tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            selectRadioButton("2", tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            selectRadioButton("3", tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            selectRadioButton("4", tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            selectRadioButton("5Kanan", tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            selectRadioButton("5Kiri", tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            selectRadioButton("6Kanan", tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            selectRadioButton("6Kiri", tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            selectRadioButton("7", tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            selectRadioButton("8", tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            selectRadioButton("9", tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            selectRadioButton("10", tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            selectRadioButton("11", tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            tb5KananUN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            tb5KiriUN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            tb6KananUN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            tb6KiriUN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            tb7UN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            tb10UN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
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
 
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari1.setDate(tgl2);  
        isRawat(); 
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getpenilaian_nihss());
        BtnHapus.setEnabled(akses.getpenilaian_nihss());
        BtnEdit.setEnabled(akses.getpenilaian_nihss());
        if(akses.getjml2()>=1){
            KdPenilai.setEditable(false);
            BtnPenilai.setEnabled(false);
            KdPenilai.setText(akses.getkode());
            NmPenilai.setText(pegawai.tampil3(KdPenilai.getText()));
        }            
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_nihss where no_rawat=? and tgl_pemeriksaan=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_nihss","no_rawat=? and tgl_pemeriksaan=?","no_rawat=?,tgl_pemeriksaan=?,kd_penilai=?,p_1a=?,p_1b=?,p_1c=?,p_2=?,p_3=?,p_4=?,"+
                "p_5kanan=?,p_5kiri=?,p_6kanan=?,p_6kiri=?,p_7=?,p_8=?,p_9=?,p_10=?,p_11=?,j_5kanan=?,j_5kiri=?,j_6kanan=?,j_6kiri=?,j_7=?,j_10=?,total_skor=?,defisit_neurologis=?",28,new String[]{
                    TNoRw.getText(),TglAsuhan.getSelectedItem().toString(),KdPenilai.getText(),lblSkor1A.getText().trim(),lblSkor1B.getText().trim(),lblSkor1C.getText().trim(),
                    lblSkor2.getText().trim(),lblSkor3.getText().trim(),lblSkor4.getText().trim(),lblSkor5Kanan.getText().trim(),lblSkor5Kiri.getText().trim(),lblSkor6Kanan.getText().trim(),lblSkor6Kiri.getText().trim(),
                    lblSkor7.getText().trim(),lblSkor8.getText().trim(),lblSkor9.getText().trim(),lblSkor10.getText().trim(),lblSkor11.getText().trim(),tb5KananUN.getText().trim(),tb5KiriUN.getText().trim(),
                    tb6KananUN.getText().trim(),tb6KiriUN.getText().trim(),tb7UN.getText().trim(),tb10UN.getText().trim(),lblTotalSkor.getText().trim(),lblKetSkor.getText().trim(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),7).toString()
            })==true){
                TabRawat.setSelectedIndex(1);
                tampil();
                emptTeks();
        }
    }
    
    private void kalkulasiSkor(){
        int totalSkor = 0;
        Label[] skorLbl = {
            lblSkor1A, lblSkor1B, lblSkor1C, lblSkor2, lblSkor3, lblSkor4, lblSkor5Kanan, lblSkor5Kiri,
            lblSkor6Kanan, lblSkor6Kiri, lblSkor7, lblSkor8, lblSkor9, lblSkor10, lblSkor11
        };
        
        for (Label lbl : skorLbl) {
            if(!lbl.getText().equals("UN")){
                totalSkor += Integer.parseInt(lbl.getText());
                if (lbl == lblSkor5Kanan) tb5KananUN.setText("");
                if (lbl == lblSkor5Kiri) tb5KiriUN.setText("");
                if (lbl == lblSkor6Kanan) tb6KananUN.setText("");
                if (lbl == lblSkor6Kiri) tb6KiriUN.setText("");
                if (lbl == lblSkor7) tb7UN.setText("");
                if (lbl == lblSkor10) tb10UN.setText("");
            }
        }
        
        lblTotalSkor.setText(totalSkor+"");
        if(totalSkor <= 5) {
            lblKetSkor.setText("Ringan");
        }else if(totalSkor >= 6 && totalSkor <= 14){
            lblKetSkor.setText("Sedang");
        }else if(totalSkor >= 15 && totalSkor <= 24){
            lblKetSkor.setText("Berat");
        }else if(totalSkor >= 25){
            lblKetSkor.setText("Sangat Berat");
        }
    }
    
    public void selectRadioButton(String kategori, String nilai) {
        String targetName = "jrb" + kategori + "Nilai" + nilai;

        for (java.awt.Component comp : FormInput.getComponents()) {
            if (comp instanceof JRadioButton) {
                JRadioButton jrb = (JRadioButton) comp;
                // Cek apakah nama komponen cocok
                if (targetName.equals(jrb.getName())) {
                    jrb.doClick();
                    break;
                }
            }
        }
    }
}
