package com.inbar.memgame;

import java.util.Date;

/**
 * Created by Inbar on 8/19/2016.
 */
public class HighScore extends Object implements Comparable<HighScore> {

    private int level;
    private long time;

    public HighScore(int level, long time) {
        this.level = level;
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Level " + level + " done in " + new Date(time).toString();
    }

    @Override
    public boolean equals(Object obj) {
        HighScore other = (HighScore)obj;
        return (other.getLevel() == level &&
                other.getTime() == time);
    }

    @Override
    public int compareTo(HighScore other) {
        if (other == null)
            throw new NullPointerException();
        else if (equals(other))
            return 0;
        // Compare levels
        else if (level > other.getLevel())
            return 1;
        else if (level < other.getLevel())
            return -1;
        // Same level compare time
        else if (new Date(time).before(new Date(other.getTime())))
            return 1;
        else
            return -1;
    }
}
