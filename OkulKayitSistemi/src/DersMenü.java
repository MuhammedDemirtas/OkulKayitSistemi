import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class DersMenü extends JFrame {

    private javax.swing.JPanel JPanel;
    private JLabel baslik;
    private JLabel lbl_DersKodu;
    private JLabel lbl_DersAdi;
    private JTextField txtdrad;
    private JTextField txtdrkd;
    private JLabel lbl_DersKredisi;
    private JTextField txtkrd;
    private JLabel lbl_DersSinifi;
    private JTextField txtsnf;
    private JLabel lbl_Bolum;
    private JTextField txtBlm;
    private JButton btn_Geri;
    private JButton btnKyt;
    private JComboBox cmbOgrt;
    private JLabel lbl_ogrt;
    private JTable tblDrs;
    private JTextField txtAra;
    private JLabel lblara;

    //FORM DÜZENİ
    public DersMenü(){
        setTitle("DERS SEÇİM MENÜ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,  650);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(JPanel);

        DefaultTableModel tableModel = new DefaultTableModel();

        //csv dosyasından verileri oku
        String csvFile = "DersKayit.csv";
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
            tblDrs.setModel(tableModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Ana menüye yönlendirme.
        btn_Geri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //anasayfaya geri dön
                AnaMenü anasayfa = new AnaMenü();
                anasayfa.setVisible(true);

                setVisible(false);

            }
        });
        //verdiği dersler comboboxını oluşturma
        try {
            String[] dersler = getOgretmenFromCsv("OgretmenKayit.csv");
            DefaultComboBoxModel<String> dersModel = new DefaultComboBoxModel<>(dersler);
            cmbOgrt.setModel(dersModel);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Hata: Öğretmenler yüklenirken bir hata oluştu.");
        }
        //Ekle butonuna tıklayınca oluşacak işlemler.
        btnKyt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Boşlukları kontrol eder.
                if (txtdrad.getText().isEmpty() ||
                        txtdrkd.getText().isEmpty() ||
                        txtkrd.getText().isEmpty() ||
                        txtBlm.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(DersMenü.this, "Lütfen Boşluk Bırakmayınız !");
                    return;
                }
                //sınıfı kontrol eder.
                if (lbl_DersSinifi.isEnabled() && txtsnf.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(DersMenü.this, "Ders sınıfı boş kalamaz !");
                    return;
                }
                 //adı kontrol eder.
                String dersAdi = txtdrad.getText();
                if (dersAdi.isEmpty() || !dersAdi.matches("[a-zA-ZçÇğĞıİöÖşŞüÜ ]+")) {
                    JOptionPane.showMessageDialog(DersMenü.this, "Harf dışında tuşlama yapmayınız !");
                    return;
                }
                //kodu kontrol eder.
                String dersKoduStr = txtdrkd.getText();
                if (dersKoduStr.isEmpty() || !dersKoduStr.matches("\\d+")) {
                    JOptionPane.showMessageDialog(DersMenü.this, "Sadece sayılsal veri giriniz(KOD İÇİN) !");
                    return;
                }
                int dersKodu = Integer.parseInt(dersKoduStr);
                //krediyikontrol eder.
                String dersKrediStr = txtkrd.getText();
                if (dersKrediStr.isEmpty() || !dersKrediStr.matches("\\d+(\\.\\d*)?")) {
                    JOptionPane.showMessageDialog(DersMenü.this, "Sadece sayılsal veri giriniz (KREDİ İÇİN) !");
                    return;
                }

                double dersKredi = Double.parseDouble(dersKrediStr);
                //bölümü kontrol eder.
                String bolum = txtBlm.getText();
                if (bolum.isEmpty() || !bolum.matches("[a-zA-ZçÇğĞıİöÖşŞüÜ ]+")) {
                    JOptionPane.showMessageDialog(DersMenü.this, "Bölüm adı sadece harflerden ve boşluklardan oluşmalıdır.");
                    return;
                }

                String dersSinifi = txtsnf.getText();

                String ogretmenVeri = cmbOgrt.getSelectedItem().toString();

                Ders ders = new Ders(dersAdi, dersKodu, dersKredi, dersSinifi, bolum, ogretmenVeri);

                try {
                    kaydetDersBilgileri(ders);
                    JOptionPane.showMessageDialog(DersMenü.this, "Ders bilgileri başarıyla kaydedildi!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(DersMenü.this, "Hata: Ders bilgileri kaydedilemedi.");
                    ex.printStackTrace();
                }

            }

        });

        txtAra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String searchText = txtAra.getText().toLowerCase();
                filterTableByDersNo(searchText);
            }
        });
    }

    private void filterTableByDersNo(String searchText) {
        DefaultTableModel tableModel = (DefaultTableModel) tblDrs.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblDrs.setRowSorter(sorter);

        if (searchText.length() == 0) {
            sorter.setRowFilter(null);
        } else {

            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 1));
        }
    }
    //Dersleri csv ye kayıt eder
    private void kaydetDersBilgileri(Ders ders) throws IOException {
        //csv dosyasına yazmak için BufferedWriter kullanma.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DersKayit.csv", true))) {
            //Ders bilgilerini Constant.CSV dosyasına yazma
            writer.write(ders.toCSV());
            writer.newLine();
        }
    }

    private String[] getOgretmenFromCsv(String dosyaYolu) throws IOException {
        List<String> ogrtListesi = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dosyaYolu))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String ad = parts[0];
                ogrtListesi.add(ad);
            }
        }

        return ogrtListesi.toArray(new String[0]);
    }


    public static void main(String[] args) {

        new DersMenü();
    }
}
