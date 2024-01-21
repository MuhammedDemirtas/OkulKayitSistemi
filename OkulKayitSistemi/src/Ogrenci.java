//Öğrenci kayıt bilgileri sınıfı.
public class Ogrenci {
    private String ad;
    private String soyad;
    private String sinif;
    private String ders;
    private String ogrNo;

    //Çekip sıralıyor.
    public Ogrenci(String ad, String soyad, String sinif, String ders, String ogrNo) {
        this.ad = ad;
        this.soyad = soyad;
        this.sinif = sinif;
        this.ders = ders;
        this.ogrNo = ogrNo;
    }

    //Sıralamayı csv ye kayıt yapar.
    public String toCSV() {
        // Kayıt dosyasını (OgrenciKayıt.csv) ye "Ad, Soyad, Sınıf,  Ders, Öğrenci No" şeklinde kayıt yapar.
        return ad + "," + soyad + "," + ogrNo + "," + sinif + "," + ders;
    }
}