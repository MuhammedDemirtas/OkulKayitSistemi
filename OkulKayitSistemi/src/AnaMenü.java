import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnaMenü extends JFrame  {

    private JButton btn_dersOlustur;
    private JButton btn_ogrenciKayit;
    private javax.swing.JPanel JPanel;
    private JLabel LabelD;
    private JLabel LabelO;
    private JLabel LabelM;
    private JButton BtnOgrt;
    private JLabel lblOgrt;

    public AnaMenü(){
        //FORM DÜZENİ
        setTitle("ANA MENÜ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,  500);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(JPanel);

        //Ders Menüye yönlendirme butonu.
        btn_dersOlustur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ders oluşturma sayfasına giriş yap
                DersMenü dersOlusturSayfasi = new DersMenü();
                dersOlusturSayfasi.setVisible(true);

                //AnaMenü kapat
                setVisible(false);

            }
        });

        //Öğrenci Menüye yönlendirme butonu.
        btn_ogrenciKayit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Öğrenci Menüye giriş yap
                OgrenciMenü ogrenciKayitSayfasi = new OgrenciMenü();
                ogrenciKayitSayfasi.setVisible(true);

                //AnaMenü kapat
                setVisible(false);
            }
        });
        BtnOgrt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OgretmenMenü OgretmenKayitSayfasi = new OgretmenMenü();
                OgretmenKayitSayfasi.setVisible(true);

                //AnaMenü kapat
                setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        new AnaMenü();
    }




}


