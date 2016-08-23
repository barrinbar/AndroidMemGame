package com.inbar.memgame;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class level2 extends level {

    public level2() {
        setLevel(2);
        setTotalCards(8);
        setColumns(3);
        setLevelContext(this);
    }

    protected void initCards() {
        setCards(new ArrayList<>(Arrays.asList(
                R.drawable.card01, R.drawable.card01,
                R.drawable.card02, R.drawable.card02,
                R.drawable.card03, R.drawable.card03,
                R.drawable.card04, R.drawable.card04)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);
        init();

    }
}
