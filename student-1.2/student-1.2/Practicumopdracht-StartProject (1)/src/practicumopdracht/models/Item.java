package practicumopdracht.models;

import java.io.Serializable;

import java.time.LocalDate;

public class Item implements Serializable {
    private transient Retailer hoortBij;
    private String soort;
    private int artikelnummer;
    private double kosten;
    private boolean gekocht;
    private LocalDate gekochtOp;


    public Item( String soort, int artikelnummer, double kosten, boolean gekocht, LocalDate gekochtOp, Retailer hoortBij) {

        this.soort = soort;
        this.artikelnummer = artikelnummer;
        this.kosten = kosten;
        this.gekocht = gekocht;
        this.gekochtOp = gekochtOp;
        this.hoortBij = hoortBij;
    }
    @Override
    public String toString() {
        String isGekocht = "";
        if(gekocht){
            isGekocht = "gekocht";
        }
        else {
            isGekocht = "niet gekocht";
        }
        return String.format("soort: %s\nkosten: %.2f\nartikelnummer: %d\ngekocht: %s\ngekocht op: %s",
                soort, kosten, artikelnummer, isGekocht, gekochtOp);
    }

    public Retailer getHoortBij() {
        return hoortBij;
    }

    public String getSoort() {
        return soort;
    }

    public int getArtikelnummer() {
        return artikelnummer;
    }

    public double getKosten() {
        return kosten;
    }

    public boolean isGekocht() {
        return gekocht;
    }

    public LocalDate getGekochtOp() {
        return gekochtOp;
    }

    public void setHoortBij(Retailer hoortBij) {
        this.hoortBij = hoortBij;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    public void setArtikelnummer(int artikelnummer) {
        this.artikelnummer = artikelnummer;
    }

    public void setKosten(double kosten) {
        this.kosten = kosten;
    }

    public void setGekocht(boolean gekocht) {
        this.gekocht = gekocht;
    }

    public void setGekochtOp(LocalDate gekochtOp) {
        this.gekochtOp = gekochtOp;
    }
}
