package com.example.inbar.memgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class lobby extends AppCompatActivity {

    private EditText et_name = (EditText) findViewById(R.id.et_name);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
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
        TextView tv_level_select = (TextView) findViewById(R.id.tv_select_level);
        tv_level_select.setVisibility(View.INVISIBLE);

        // Disable buttons
        Button btn_lvl_1 = (Button) findViewById(R.id.btn_lvl_1);
        Button btn_lvl_2 = (Button) findViewById(R.id.btn_lvl_2);
        Button btn_lvl_3 = (Button) findViewById(R.id.btn_lvl_3);

        btn_lvl_1.setEnabled(false);
        btn_lvl_2.setEnabled(false);
        btn_lvl_3.setEnabled(false);
    }

    private void checkNameFill() {
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_name.getText().length() >= 3)
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
        TextView tv_level_select = (TextView) findViewById(R.id.tv_select_level);
        tv_level_select.setVisibility(View.VISIBLE);

        // Enable buttons
        Button btn_lvl_1 = (Button) findViewById(R.id.btn_lvl_1);
        Button btn_lvl_2 = (Button) findViewById(R.id.btn_lvl_2);
        Button btn_lvl_3 = (Button) findViewById(R.id.btn_lvl_3);

        btn_lvl_1.setEnabled(true);
        btn_lvl_2.setEnabled(true);
        btn_lvl_3.setEnabled(true);
    }

    public void dispHiScore(View view) {

        // TODO:go to high score page
        /*Intent goToScore = new Intent(this,High_score.class);
        goToScore.putExtra("STATE","fromMenu");
        startActivity(goToScore);
*/
    }

    public void dispLevel1(View view) {

        // Make sure name is filled
        if (et_name.getText().length() < 3)
            et_name.setError( "First name is required!" );
        else {
            Toast.makeText(lobby.this, "The id is: " + view.getId(), Toast.LENGTH_SHORT).show();

            Intent startLevel = new Intent(this,level1.class);
            startLevel.putExtra("USER_NAME",et_name.getText().toString());
//            startActivity(startLevel);
        }
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
