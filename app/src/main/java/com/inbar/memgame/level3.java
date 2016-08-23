package com.inbar.memgame;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class level3 extends level {

    public level3() {
        setLevel(3);
        setTotalCards(12);
        setColumns(3);
        setLevelContext(this);
    }

    protected void initCards() {
        setCards(new ArrayList<>(Arrays.asList(
                R.drawable.card01, R.drawable.card01,
                R.drawable.card02, R.drawable.card02,
                R.drawable.card03, R.drawable.card03,
                R.drawable.card04, R.drawable.card04,
                R.drawable.card05, R.drawable.card05,
                R.drawable.card06, R.drawable.card06)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3);
        init();

    }
}
