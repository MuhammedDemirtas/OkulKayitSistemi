public class Ogretmen {
    private String ogretmenNo;
    private String ogretmenAdi;
    private String ogretmenSoyadi;
    private String telefonNumarasi;
    private String ePostaAdresi;
    private String bolumu;
    private String verdigiDers;
    private String adres;

    // Constructor
    public Ogretmen(String ogretmenNo, String ogretmenAdi, String ogretmenSoyadi,
                    String telefonNumarasi, String ePostaAdresi, String bolumu,
                    String verdigiDers, String adres) {
        this.ogretmenNo = ogretmenNo;
        this.ogretmenAdi = ogretmenAdi;
        this.ogretmenSoyadi = ogretmenSoyadi;
        this.telefonNumarasi = telefonNumarasi;
        this.ePostaAdresi = ePostaAdresi;
        this.bolumu = bolumu;
        this.verdigiDers = verdigiDers;
        this.adres = adres;
    }

    // CSV formatında bir satırı temsil eden metot
    public String toCSV() {
        return ogretmenNo + "," + ogretmenAdi + "," + ogretmenSoyadi + "," +
                telefonNumarasi + "," + ePostaAdresi + "," + bolumu + "," +
                verdigiDers + "," + adres;
    }
}

