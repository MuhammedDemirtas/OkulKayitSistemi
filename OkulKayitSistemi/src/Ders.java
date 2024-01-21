//Ders kayıt bilgileri sınıfı.
public class Ders {
    private String dersAdi;
    private int dersKodu;
    private double dersKredi;
    private String dersSinifi;
    private String bolum;
    private String ogretmenVeri;

    //Çekip sıralıyor.
    public Ders(String dersAdi, int dersKodu, double dersKredi, String dersSinifi, String bolum, String ogretmenVeri) {
        this.dersAdi = dersAdi;
        this.dersKodu = dersKodu;
        this.dersKredi = dersKredi;
        this.dersSinifi = dersSinifi;
        this.bolum = bolum;
        this.ogretmenVeri = ogretmenVeri;
    }

    public String getDersSinifi() {
        return dersSinifi;
    }

    public void setDersSinifi(String dersSinifi) {
        this.dersSinifi = dersSinifi;
    }

    public String getogretmenVeri() {
        return ogretmenVeri;
    }

    public void setOgretmenAdiSoyadi(String ogretmenVeri) {
        this.ogretmenVeri = ogretmenVeri;
    }

    //Sıralamayı csv ye kayıt yapar.
    public String toCSV() {
        // Kayıt dosyasını (DersKayıt.csv) ye "kod, ad, kredi,  sınıf, bolum" şeklinde kayıt yapar.
        return dersAdi + "," + dersKodu + "," + dersKredi + "," + dersSinifi + "," + bolum + "," + ogretmenVeri ;
    }
}







