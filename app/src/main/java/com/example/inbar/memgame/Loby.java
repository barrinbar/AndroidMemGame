package com.example.inbar.memgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.log;

public class Loby extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loby);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Load current state?
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Load current state?
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Save current state??
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
