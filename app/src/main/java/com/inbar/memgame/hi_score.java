package com.inbar.memgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

public class hi_score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hi_score);

        TableLayout scoresList = (TableLayout)findViewById(R.id.tl_hi_scores);

        // Retrieve high scores from shared prefs
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.high_score_pref), Context.MODE_PRIVATE);
        Map<String, HighScore> highScores = sortHSByValue((Map<String, String>)sharedPref.getAll());

        // Iterate through all high scores and add them to the scores table
        for (Map.Entry<String, ?> entry : highScores.entrySet())
        {
            // Make sure key and value not empty
            if ((entry.getKey() != null) && (entry.getValue() != null)) {
                TableRow currUser = new TableRow(this);
                TextView userName = new TextView(this);
                TextView level = new TextView(this);
                TextView time = new TextView(this);
                HighScore hs = (HighScore)entry.getValue();

                userName.setTextColor(Color.parseColor("#ffffff"));
                userName.setText(entry.getKey());
                userName.setGravity(Gravity.CENTER);
                userName.setPadding(5, 5, 5, 5);

                level.setTextColor(Color.parseColor("#ffffff"));
                level.setText(String.format(Locale.getDefault(), "Level %d", hs.getLevel()));
                level.setGravity(Gravity.CENTER);
                level.setPadding(5, 5, 5, 5);

                time.setTextColor(Color.parseColor("#ffffff"));
                time.setText(new SimpleDateFormat("mm:ss", Locale.getDefault()).format(new Date(hs.getTime())));
                time.setGravity(Gravity.CENTER);
                time.setPadding(5, 5, 5, 5);

                currUser.addView(userName);
                currUser.addView(level);
                currUser.addView(time);
                currUser.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f));
                currUser.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
                scoresList.addView(currUser);
            }
        }
    }

    public static <String, V extends Comparable<? super V>> Map<String, V>
    sortHSByValue( Map<String, String> map ) {
        HighScore hs;
        Gson gson = new Gson();
        java.lang.String json;

        HashMap<String, V> hm =
                new HashMap<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            json = (java.lang.String)entry.getValue();
            hs = gson.fromJson(json, HighScore.class);
            hm.put(entry.getKey(), (V) hs);
        }

        LinkedList<Map.Entry<String, V>> list =
                new LinkedList<>( hm.entrySet() );

        Collections.sort(list, new Comparator<Map.Entry<String, V>>() {
            public int compare(Map.Entry<String, V> o1, Map.Entry<String, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<String, V> result = new LinkedHashMap<>();
        for (Map.Entry<String, V> entry : hm.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public void returnHome(View view) {
        Intent lobby = new Intent(this, lobby.class);
        startActivity(lobby);
    }
}
