package com.caffeinatedbliss.grillbuddy;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeatTemperatureModel {
    enum MEAT {
        BEEF(R.string.beef), LAMB(R.string.lamb), PORK(R.string.pork), CHICKEN(R.string.chicken), TURKEY(R.string.turkey);

        private int desc;

        MEAT(int desc) {
            this.desc = desc;
        }

        public String getDescription(Context context) {
            return context.getString(desc);
        }
    }

    enum DONE {
        UNCOOKED(R.string.uncooked), RARE(R.string.rare),
        MEDIUM_RARE(R.string.medium_rare), MEDIUM(R.string.medium),
        MEDIUM_WELL(R.string.medium_well), WELL_DONE(R.string.well_done),
        DONE(R.string.done);

        private int desc;

        DONE(int desc) {
            this.desc = desc;
        }

        public String getDescription(Context context) {
            return context.getString(desc);
        }
    }

    private Map<MEAT, List<Range>> rangeMap;
    private int meat;
    private int done;

    public MeatTemperatureModel() {
        rangeMap = new HashMap<MEAT, List<Range>>();

        rangeMap.put(MEAT.BEEF, Arrays.asList(
                new Range(DONE.UNCOOKED, Integer.MIN_VALUE, 120),
                new Range(DONE.RARE, 120, 130),
                new Range(DONE.MEDIUM_RARE, 130, 140),
                new Range(DONE.MEDIUM, 140, 150),
                new Range(DONE.MEDIUM_WELL, 150, 160),
                new Range(DONE.WELL_DONE, 160, Integer.MAX_VALUE)
        ));

        rangeMap.put(MEAT.LAMB, Arrays.asList(
                new Range(DONE.UNCOOKED, Integer.MIN_VALUE, 130),
                new Range(DONE.RARE, 130, 140),
                new Range(DONE.MEDIUM_RARE, 140, 150),
                new Range(DONE.MEDIUM, 150, 160),
                new Range(DONE.MEDIUM_WELL, 160, 165),
                new Range(DONE.WELL_DONE, 165, Integer.MAX_VALUE)
        ));

        rangeMap.put(MEAT.CHICKEN, Arrays.asList(
                new Range(DONE.UNCOOKED, Integer.MIN_VALUE, 165),
                new Range(DONE.DONE, 165, 175)
        ));

        rangeMap.put(MEAT.TURKEY, Arrays.asList(
                new Range(DONE.UNCOOKED, Integer.MIN_VALUE, 165),
                new Range(DONE.DONE, 165, 175)
        ));

        rangeMap.put(MEAT.PORK, Arrays.asList(
                new Range(DONE.UNCOOKED, Integer.MIN_VALUE, 145),
                new Range(DONE.DONE, 145, 165)
        ));

        this.meat = 0;
        this.done = 3;
    }

    public MEAT getMeat() {
        return MEAT.values()[meat];
    }

    public void nextMeat() {
        DONE oldDone = getDone();
        meat = (meat + 1) % MEAT.values().length;
        if (DONE.DONE.equals(oldDone) && (MEAT.LAMB.equals(getMeat()) || MEAT.BEEF.equals(getMeat()))) {
            done = 3;
        } else if (done >= rangeMap.get(getMeat()).size()) {
            done = rangeMap.get(getMeat()).size()-1;
        }
    }

    public void prevMeat() {
        DONE oldDone = getDone();
        meat = (meat + MEAT.values().length - 1) % MEAT.values().length;
        if (DONE.DONE.equals(oldDone) && (MEAT.LAMB.equals(getMeat()) || MEAT.BEEF.equals(getMeat()))) {
            done = 3;
        } else if (done >= rangeMap.get(getMeat()).size()) {
            done = rangeMap.get(getMeat()).size()-1;
        }
    }

    public DONE getDone() {
        final MEAT theMeat = getMeat();
        return rangeMap.get(theMeat).get(done).getDone();
    }

    public void nextDone() {
        final List<Range> ranges = rangeMap.get(getMeat());
        done = (done + 1) % ranges.size();
    }

    public void prevDone() {
        final List<Range> ranges = rangeMap.get(getMeat());
        done = (done + ranges.size() - 1) % ranges.size();
    }

    public Range getRange() {
        return rangeMap.get(getMeat()).get(done);
    }

    public class MeatAndDone {
        private MEAT meat;
        private DONE done;

        public MeatAndDone(MEAT meat, DONE done) {
            this.meat = meat;
            this.done = done;
        }

        public MEAT getMeat() {
            return meat;
        }

        public void setMeat(MEAT meat) {
            this.meat = meat;
        }

        public DONE getDone() {
            return done;
        }

        public void setDone(DONE done) {
            this.done = done;
        }

        public boolean contains(MEAT meat) {
            return this.meat.equals(meat);
        }

        public boolean contains(DONE done) {
            return this.done.equals(done);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MeatAndDone that = (MeatAndDone) o;

            return done == that.done && meat == that.meat;
        }

        @Override
        public int hashCode() {
            int result = meat.hashCode();
            result = 31 * result + done.hashCode();
            return result;
        }
    }

    public class Range {
        private DONE done;
        private int min;
        private int max;

        public Range(DONE done, int min, int max) {
            this.done = done;
            this.min = min;
            this.max = max;
        }

        public DONE getDone() {
            return done;
        }

        public void setDone(DONE done) {
            this.done = done;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public boolean contains(int temp) {
            return min <= temp && max > temp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Range range = (Range) o;

            return max == range.max && min == range.min;
        }

        @Override
        public int hashCode() {
            int result = min;
            result = 31 * result + max;
            return result;
        }

        public String getDescription(Context context) {
            if (min == Integer.MIN_VALUE) {
                return String.format(context.getString(R.string.upper_bound), max);
            } else if (max == Integer.MAX_VALUE) {
                return String.format(context.getString(R.string.lower_bound), min);
            } else {
                return String.format(context.getString(R.string.bounded), min, max);
            }
        }
    }
}