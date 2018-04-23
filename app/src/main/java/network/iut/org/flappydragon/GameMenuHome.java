package network.iut.org.flappydragon;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GameMenuHome extends Activity {
    private SharedPreferences prefs;
    private Intent musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Music
        startService(new Intent(this, MusicService.class));

        // Pseudo
        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String pseudo = prefs.getString("pseudo", null);
        if (pseudo == null) {
            prefs.edit().putString("pseudo", "Joueur1").apply();
        }

        // Btns
        Button playBtn = (Button) findViewById(R.id.playBtn);
        Button scoreBtn = (Button) findViewById(R.id.scoreBtn);
        Button optionsBtn = (Button) findViewById(R.id.optionsBtn);
        Button aboutBtn = (Button) findViewById(R.id.aboutBtn);

        // Events
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivityIntent = new Intent(getApplicationContext(), GameActivity.class);
                //stopService(new Intent(getApplicationContext(), MusicService.class));
                startActivity(gameActivityIntent);
            }
        });
        scoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreIntent = new Intent(getApplicationContext(), GameMenuScores.class);
                startActivity(scoreIntent);
            }
        });
        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent optionsIntent = new Intent(getApplicationContext(), GameMenuOptions.class);
                startActivity(optionsIntent);
            }
        });
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(getApplicationContext(), GameMenuAbout.class);
                startActivity(aboutIntent);
            }
        });
    }
}
