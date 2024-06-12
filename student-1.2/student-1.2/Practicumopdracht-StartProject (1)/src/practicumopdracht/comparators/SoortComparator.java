package practicumopdracht.comparators;

import practicumopdracht.models.Item;

import java.util.Comparator;

public class SoortComparator implements Comparator<Item> {
    private boolean sortDescending;

    public SoortComparator(boolean sortDescending) {
        this.sortDescending = sortDescending;
    }

    @Override
    public int compare(Item o1, Item o2) {
        int soortComparison = o1.getSoort().compareTo(o2.getSoort());

        if (soortComparison == 0) {
            // Als de soorten gelijk zijn, sorteer op kosten van laag naar hoog
            if (!sortDescending) {
                return Double.compare(o1.getKosten(), o2.getKosten());
            } else {
                return Double.compare(o2.getKosten(), o1.getKosten());
            }
        }

        // Sorteer op basis van de soort
        if (!sortDescending) {
            return -soortComparison; // Omgekeerde volgorde als sortDescending true is
        } else {
            return soortComparison;
        }
    }
}