import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OgretmenMenü extends JFrame {
    private javax.swing.JPanel JPanel;
    private JButton btn_Geri;
    private JLabel lbl_Baslik;
    private JTextField txt_OgrtNo;
    private JLabel lbl_OgrtNo;
    private JTextField txt_OgrtAd;
    private JLabel lbl_OgrtAd;
    private JTextField ogrt_Soyad;
    private JLabel lbl_OgrtSoyad;
    private JTextField txt_OgrtTelNo;
    private JLabel lbl_TelNo;
    private JTextField txt_eposta;
    private JLabel lbl_eposta;
    private JComboBox cmb_Bolum;
    private JLabel lbl_Bolum;
    private JComboBox cmb_VerdigiDersler;
    private JLabel lbl_VerdigiDers;
    private JTextArea txt_Adres;
    private JLabel lbl_Adres;
    private JButton btn_Kaydet;
    private JTextField txt_Ara;
    private JLabel lbl_Ara;
    private JTable tbl_Ogretmen;

    public OgretmenMenü() {
        //form görüntüsü
        setTitle("ÖĞRETMEN KAYIT MENÜ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 631);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(JPanel);

        DefaultTableModel tableModel = new DefaultTableModel();

        //csv dosyasından verileri oku
        String csvFile = "OgretmenKayit.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // İlk satır başlıklar olduğu için başlıkları ayarla
            String line = br.readLine();
            String[] headers = line.split(",");
            tableModel.setColumnIdentifiers(headers);

            // Geri kalan satırları oku ve tabloya ekle
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                tableModel.addRow(data);
            }

            // DersTablo'ya modeli ata
            tbl_Ogretmen.setModel(tableModel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //verdiği dersler comboboxını oluşturma
        try {
            String[] dersler = getDerslerFromCSV("DersKayit.csv");
            DefaultComboBoxModel<String> dersModel = new DefaultComboBoxModel<>(dersler);
            cmb_VerdigiDersler.setModel(dersModel);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Hata: Dersler yüklenirken bir hata oluştu.");
        }

        //tıklandığında ansayfaya dönme butonu
        btn_Geri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AnaMenü anasayfa = new AnaMenü();
                anasayfa.setVisible(true);

                //ders oluştur sayfasını kapat
                setVisible(false);
            }
        });

        //kaydet butonuna tıklanınca csv ye kaydedecek
        btn_Kaydet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String ogrtNo = new String(txt_OgrtNo.getText());
                    String ad = txt_OgrtAd.getText();
                    String soyad = ogrt_Soyad.getText();
                    String telNo = txt_OgrtTelNo.getText();
                    String eposta = txt_eposta.getText();
                    String adres = txt_Adres.getText();
                    String bolum = cmb_Bolum.getSelectedItem().toString();
                    String verdigiDers = cmb_VerdigiDersler.getSelectedItem().toString();


                    //herhangi bir alan boşsa hata mesajı göster
                    if (ogrtNo.isEmpty() || ad.isEmpty() || soyad.isEmpty() || eposta.isEmpty()  || telNo.isEmpty() ||  adres.isEmpty() ) {
                        JOptionPane.showMessageDialog(OgretmenMenü.this, "Lütfen tüm alanları doldurunuz.");
                        return;
                    }

                    //ad kontrolü
                    if (ad.isEmpty() || !ad.matches("[a-zA-ZçÇğĞıİöÖşŞüÜ ]+")) {
                        JOptionPane.showMessageDialog(OgretmenMenü.this, "Geçersiz ad. Ad sadece harflerden oluşmalıdır.");
                        return;
                    }

                    //soyad kontrolü
                    if (soyad.isEmpty() || !soyad.matches("[a-zA-ZçÇğĞıİöÖşŞüÜ ]+")) {
                        JOptionPane.showMessageDialog(OgretmenMenü.this, "Geçersiz soyad. Soyad sadece harflerden oluşmalıdır.");
                        return;
                    }

                    //öğretmen No kontrolü
                    if (ogrtNo.isEmpty() || !ogrtNo.matches("\\d+")) {
                        JOptionPane.showMessageDialog(OgretmenMenü.this, "Geçersiz öğrenci numarası. Öğrenci numarası sayılardan oluşmalıdır.");
                        return;
                    }

                    //telefon Numarası kontrolü
                    if (!telNo.matches("05\\d{2} \\d{3} \\d{4}")) {
                        JOptionPane.showMessageDialog(OgretmenMenü.this, "Geçersiz telefon numarası.");
                        return;
                    }

                    //eposta kontrolü
                    if (eposta.isEmpty() || !eposta.endsWith("@gmail.com")) {
                        JOptionPane.showMessageDialog(OgretmenMenü.this, "Geçersiz e-posta adresi. E-posta adresi '@gmail.com' ile bitmelidir.");
                        return;
                    }

                    //adres alanı 100 karakteri geçerse hata versin
                    if (adres.length() > 150) {
                        JOptionPane.showMessageDialog(OgretmenMenü.this, "Adres en fazla 150 karakter olmalıdır.");
                        return;
                    }

                    // Verdiği Dersler seçimi kontrolü

                    if ("Ders Adı".equals(verdigiDers)) {
                        JOptionPane.showMessageDialog(OgretmenMenü.this, "Lütfen öğretmenin verdiği dersi seçiniz.");
                        return;
                    }


                    //tüm kontroller başarılı ise, öğretmen bilgilerini kaydet
                    Ogretmen ogretmen = new Ogretmen(ogrtNo, ad, soyad, telNo, eposta, bolum, verdigiDers, adres);

                    try {
                        kaydetOgretmenBilgileri(ogretmen);
                        JOptionPane.showMessageDialog(OgretmenMenü.this, "Öğretmen bilgileri başarıyla kaydedildi!");

                        // Yeni veriyi ekledikten sonra JTable'ı güncelle
                        tableModel.addRow(new String[]{ogrtNo, ad, soyad, telNo, eposta, bolum, verdigiDers, adres});
                        tbl_Ogretmen.setModel(tableModel);

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(OgretmenMenü.this, "Hata: Öğretmen bilgileri kaydedilemedi.");
                        ex.printStackTrace();
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

            }
        });

        //arama yapma
        txt_Ara.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String searchText = txt_Ara.getText().toLowerCase();
                filterTableByOgrtAdi(searchText);
            }
        });
    }

    //tabloyu filtreleyen işlev
    private void filterTableByOgrtAdi(String searchText) {
        DefaultTableModel tableModel = (DefaultTableModel) tbl_Ogretmen.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tbl_Ogretmen.setRowSorter(sorter);

        if (searchText.length() == 0) {
            sorter.setRowFilter(null);
        } else {

            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 0)); //Öğretmen No sütununa göre arama
        }
    }

    //öğretmen bilgilerini kaydetme
    private void kaydetOgretmenBilgileri(Ogretmen ogretmen) throws IOException {
        // CSV dosyasına yazmak için BufferedWriter kullanma
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("OgretmenKayit.csv", true), "UTF-8"))) {

            // öğrenci bilgilerini CSV dosyasına yazma
            writer.write(ogretmen.toCSV());
            writer.newLine();
        }
    }



    private String[] getDerslerFromCSV(String dosyaYolu) throws IOException {
        List<String> dersListesi = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String dersAdi = parts[1]; // Dersin Adı sütunu
                dersListesi.add(dersAdi);
            }
        }

        return dersListesi.toArray(new String[0]);
    }







    public static void main(String[] args) {
        new OgretmenMenü();
    }
}
