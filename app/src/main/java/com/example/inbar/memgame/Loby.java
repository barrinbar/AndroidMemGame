package com.example.inbar.memgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Loby extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loby);
    }

    @Override
    protected void onStart() {
        super.onStart();

        hideLevels();

        checkNameFill();

        // Load current state?
    }

    private void hideLevels() {
        // Hide level selection label
        TextView tv_level_select = (TextView)findViewById(R.id.tv_select_level);
        tv_level_select.setVisibility(View.GONE);

        // Disable buttons
        Button btn_lvl_1 = (Button)findViewById(R.id.btn_lvl_1);
        Button btn_lvl_2 = (Button)findViewById(R.id.btn_lvl_1);
        Button btn_lvl_3 = (Button)findViewById(R.id.btn_lvl_1);

        btn_lvl_1.setEnabled(false);
        btn_lvl_2.setEnabled(false);
        btn_lvl_3.setEnabled(false);
    }

    private boolean checkNameFill() {
        EditText et_name = (EditText)findViewById(R.id.et_name);
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 3)
                    showLevels();
                else
                    hideLevels();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void showLevels() {
        // Show level selection label
        TextView tv_level_select = (TextView)findViewById(R.id.tv_select_level);
        tv_level_select.setVisibility(View.VISIBLE);

        // Enable buttons
        Button btn_lvl_1 = (Button)findViewById(R.id.btn_lvl_1);
        Button btn_lvl_2 = (Button)findViewById(R.id.btn_lvl_1);
        Button btn_lvl_3 = (Button)findViewById(R.id.btn_lvl_1);

        btn_lvl_1.setEnabled(true);
        btn_lvl_2.setEnabled(true);
        btn_lvl_3.setEnabled(true);
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
