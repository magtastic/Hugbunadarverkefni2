package project.hugbunadarverkefni2;

/**
 * Created by svein on 4.4.2017.
 */

public class Filter {
    // temp values
    int minAttendees = 0;
    int maxAttendees = 999;
    int minDaysUntil = 0;
    int maxDaysUntil = 999;

    public Filter(int minAttendees, int maxAttendees, int minDaysUntil, int maxDaysUntil) {
        this.minAttendees = minAttendees;
        this.maxAttendees = maxAttendees;
        this.minDaysUntil = minDaysUntil;
        this.maxDaysUntil = maxDaysUntil;
    }

    public int getMinAttendees() {
        return minAttendees;
    }

    public int getMaxAttendees() {
        return maxAttendees;
    }

    public int getMinDaysUntil() {
        return minDaysUntil;
    }

    public int getMaxDaysUntil() {
        return maxDaysUntil;
    }

    public void setMinAttendees(int minAttendees) {
        this.minAttendees = minAttendees;
    }

    public void setMaxAttendees(int maxAttendees) {
        this.maxAttendees = maxAttendees;
    }

    public void setMinDaysUntil(int minDaysUntil) {
        this.minDaysUntil = minDaysUntil;
    }

    public void setMaxDaysUntil(int maxDaysUntil) {
        this.maxDaysUntil = maxDaysUntil;
    }

}
