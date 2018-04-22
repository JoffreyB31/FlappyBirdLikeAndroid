package network.iut.org.flappydragon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GameMenuOptions extends Activity {
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        // Preferences
        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String pseudo = prefs.getString("pseudo", "Joueur1");
        int difficulty = prefs.getInt("difficulty", 0);
        int fps = prefs.getInt("fps", 30);

        // Buttons
        EditText pseudoBlk = findViewById(R.id.pseudoBlock);
        ToggleButton easyBtn = findViewById(R.id.difficultyEasy);
        ToggleButton mediumBtn = findViewById(R.id.difficultyMedium);
        ToggleButton hardBtn = findViewById(R.id.difficultyHard);
        EditText fpsCounter = findViewById(R.id.fpsCounter);
        Button backBtn = findViewById(R.id.backBtn);

        // Load the pseudo
        pseudoBlk.setText(pseudo);
        // Load FPS
        fpsCounter.setText(String.valueOf(fps));
        // Load difficulty button
        switch (difficulty) {
            case 0:
                checkEasyBtn(true);
                checkMediumBtn(false);
                checkHardBtn(false);
                break;
            case 1:
                checkEasyBtn(false);
                checkMediumBtn(true);
                checkHardBtn(false);
                break;
            case 2:
                checkEasyBtn(false);
                checkMediumBtn(false);
                checkHardBtn(true);
                break;
            default:
                checkEasyBtn(true);
                checkMediumBtn(false);
                checkHardBtn(false);
                break;
        }

        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMediumBtn(false);
                checkHardBtn(false);
                prefs.edit().putInt("difficulty", 0).apply();
            }
        });

        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEasyBtn(false);
                checkHardBtn(false);
                prefs.edit().putInt("difficulty", 1).apply();
            }
        });

        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEasyBtn(false);
                checkMediumBtn(false);
                prefs.edit().putInt("difficulty", 2).apply();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pseudo
                EditText pseudo = findViewById(R.id.pseudoBlock);
                prefs.edit().putString("pseudo", pseudo.getText().toString()).apply();
                // FPS
                EditText fps = findViewById(R.id.fpsCounter);
                int fpsInt = Integer.parseInt(fps.getText().toString());
                prefs.edit().putInt("fps", fpsInt < 10 ? 10 : fpsInt).apply();
                Intent homeIntent = new Intent(getApplicationContext(), GameMenuHome.class);
                startActivity(homeIntent);
            }
        });
    }

    private void checkEasyBtn(boolean isChecked) {
        ToggleButton easyBtn = findViewById(R.id.difficultyEasy);
        easyBtn.setChecked(isChecked);
    }

    private void checkMediumBtn(boolean isChecked) {
        ToggleButton mediumBtn = findViewById(R.id.difficultyMedium);
        mediumBtn.setChecked(isChecked);
    }

    private void checkHardBtn(boolean isChecked) {
        ToggleButton hardBtn = findViewById(R.id.difficultyHard);
        hardBtn.setChecked(isChecked);
    }
}
