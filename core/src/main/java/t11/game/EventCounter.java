package t11.game;

public class SimpleEventCounter {
    private int positive = 0;
    private int negative = 0;
    private int hidden   = 0;

    // increment by type
    public void inc(EventType type) {
        switch (type) {
            case POSITIVE -> positive++;
            case NEGATIVE -> negative++;
            case HIDDEN   -> hidden++;
        }
    }

    // direct increments (optional helpers)
    public void incPositive() { positive++; }
    public void incNegative() { negative++; }
    public void incHidden()   { hidden++; }

    // getters
    public int getPositive() { return positive; }
    public int getNegative() { return negative; }
    public int getHidden()   { return hidden; }

    // reset all to zero
    public void reset() {
        positive = negative = hidden = 0;
    }
}
