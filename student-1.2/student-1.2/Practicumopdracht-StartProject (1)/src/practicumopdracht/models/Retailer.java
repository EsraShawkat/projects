package practicumopdracht.models;


import java.io.Serializable;

public class Retailer implements Serializable {
    private String naam;
    private String betaalmethoden;

    public Retailer(String naam, String betaalmethoden) {
        this.naam = naam;
        this.betaalmethoden = betaalmethoden;

    }

    @Override
    public String toString() {
        return String.format("Retailer: %s\nBetaalmethoden: %s", naam, betaalmethoden);
    }

    public String getNaam() {
        return naam;
    }

    public String getBetaalmethoden() {
        return betaalmethoden;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setBetaalmethoden(String betaalmethoden) {
        this.betaalmethoden = betaalmethoden;
    }
}
