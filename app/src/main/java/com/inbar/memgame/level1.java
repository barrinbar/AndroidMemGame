package com.inbar.memgame;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Level Specific implementation for level 1
 * Created by Barr Inbar on 8/21/2016.
 */
public class level1 extends level {

    public level1() {
        setLevel(1);
        setTotalCards(4);
        setColumns(2);
        setLevelContext(this);
    }

    protected void initCards() {
        setCards(new ArrayList<>(Arrays.asList(
                R.drawable.card01, R.drawable.card02,
                R.drawable.card01, R.drawable.card02)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        init();
    }
}
