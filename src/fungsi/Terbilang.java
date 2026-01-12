package fungsi;

/**
 *
 * @author acer
 */
public class Terbilang {
    private static final String[] satuan = {
        "", "satu", "dua", "tiga", "empat", "lima", "enam", "tujuh", "delapan", "sembilan", "sepuluh", 
        "sebelas", "dua belas", "tiga belas", "empat belas", "lima belas", "enam belas", "tujuh belas", 
        "delapan belas", "sembilan belas"
    };
    
    private static final String[] puluhan = {
        "", "", "dua puluh", "tiga puluh", "empat puluh", "lima puluh", "enam puluh", "tujuh puluh", 
        "delapan puluh", "sembilan puluh"
    };

    private static final String[] ribuan = {
        "", "ribu", "juta"
    };

    public static String convert(int number) {
        if (number == 0) {
            return "nol";
        } else if (number < 20) {
            return satuan[number];
        } else if (number < 100) {
            int puluh = number / 10;
            int sisa = number % 10;
            return puluhan[puluh] + (sisa > 0 ? " " + satuan[sisa] : "");
        } else if (number < 1000) {
            int ratus = number / 100;
            int sisa = number % 100;
            return ratus == 1 ? "seratus" + (sisa > 0 ? " " + convert(sisa) : "") : satuan[ratus] + " ratus" + (sisa > 0 ? " " + convert(sisa) : "");
        } else if (number < 1000000) {
            int ribu = number / 1000;
            int sisa = number % 1000;
            return ribu == 1 ? "seribu" + (sisa > 0 ? " " + convert(sisa) : "") : convert(ribu) + " ribu" + (sisa > 0 ? " " + convert(sisa) : "");
        } else if (number < 1000000000) {  // Untuk jutaan
            int juta = number / 1000000;
            int sisa = number % 1000000;
            return convert(juta) + " juta" + (sisa > 0 ? " " + convert(sisa) : "");
        } else {
            return "Angka terlalu besar";
        }
    }
}
