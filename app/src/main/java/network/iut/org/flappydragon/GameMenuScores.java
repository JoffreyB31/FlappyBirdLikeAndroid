package network.iut.org.flappydragon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GameMenuScores extends Activity {
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores);
        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        // Display scores
        showSavedScores();

        // Back btn
        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), GameMenuHome.class);
                startActivity(homeIntent);
            }
        });

        // Clear btn
        Button resetBtn = (Button) findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().remove("jsonSavedScores").apply();
                showSavedScores();
            }
        });
    }

    public void showSavedScores() {
        // Get stored scores
        Gson gson = new Gson();
        String json = prefs.getString("jsonSavedScores", null);
        ArrayList<Score> savedScores = gson.fromJson(json, new TypeToken<ArrayList<Score>>() {}.getType());
        ArrayList<String> values = new ArrayList<>();

        if (savedScores != null) {
            // Sort scores descending
            Collections.sort(savedScores, new Comparator<Score>() {
                @Override
                public int compare(Score s1, Score s2) {
                    return s2.getScore() - s1.getScore();
                }
            });

            // Display
            for (int i = 0, l = savedScores.size(); i < l; i++) {
                Score score = (Score)savedScores.get(i);
                String scoreStr = score.getScoreDisplay();
                values.add(scoreStr);
            }
        } else {
            values.add("Aucune donnée enregistré.");
        }

        ListView scoreListView = (ListView) findViewById(R.id.savedScoresListView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(GameMenuScores.this,
                android.R.layout.simple_list_item_1, values);
        scoreListView.setAdapter(adapter);
    }
}
