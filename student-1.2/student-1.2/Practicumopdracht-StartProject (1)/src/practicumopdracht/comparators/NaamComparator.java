package practicumopdracht.comparators;

import practicumopdracht.models.Retailer;

import java.util.Comparator;

public class NaamComparator implements Comparator<Retailer> {
    private boolean sortDescending;

    public NaamComparator(boolean sortDescending) {
        this.sortDescending = sortDescending;
    }

    @Override
    public int compare(Retailer o1, Retailer o2) {

        if (!sortDescending) {
            return o2.getNaam().compareTo(o1.getNaam());

        } else {
            return o1.getNaam().compareTo(o2.getNaam());
        }
    }
}


