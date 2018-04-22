package network.iut.org.flappydragon;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements Runnable {
    public static final long UPDATE_INTERVAL = 15;
    private SurfaceHolder holder;
    private boolean paused = true;
    private boolean isGameOver = false;
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private Player player;
    private Background background;
    private Tuyaux tuyaux;
    private int score;
    private ArrayList savedScores;
    private int highScore;
    private int framesDraw;
    private UI ui;
    private Context context;
    private Paint paint;
    private SharedPreferences prefs;

    public GameView(Context context) {
        super(context);
        this.context = context;

        // Preferences
        prefs = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);

        player = new Player(context, this);
        ui = new UI(context, this);
        background = new Background(context, this);
        tuyaux = new Tuyaux(context, this);
        holder = getHolder();

        score = 0;
        highScore = 0;

        // Saved scores
        Gson gson = new Gson();
        String json = prefs.getString("jsonSavedScores", null);
        if (json != null) {
            savedScores = gson.fromJson(json, new TypeToken<ArrayList<Score>>() {}.getType());
        } else {
            savedScores = new ArrayList<Score>();
        }

        paint = new Paint();
        paint.setTextSize(25f);
        paint.setColor(Color.BLACK);

        new Thread(new Runnable() {
            @Override
            public void run() {
                GameView.this.run();
            }
        }).start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isGameOver) {
                isGameOver = false;
                resume();
                restart();
            } else if (paused) {
                resume();
            } else {
                Log.i("PLAYER", "PLAYER TAPPED");
                this.player.onTap();
                this.ui.onTap(event);
            }
        }
        return true;
    }

    private void restart() {
        if (score > highScore) {
            highScore = score;
        }
        score = 0;
        tuyaux = new Tuyaux(context, this);
        player = new Player(context, this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                GameView.this.run();
            }
        }).start();
    }

    private void resume() {
        paused = false;
        startTimer();
    }

    public void pause() {
        paused = true;
        stopTimer();
    }

    private void startTimer() {
        Log.i("TIMER", "START TIMER");
        setUpTimerTask();
        timer = new Timer();
        timer.schedule(timerTask, UPDATE_INTERVAL, UPDATE_INTERVAL);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    private void setUpTimerTask() {
        stopTimer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                GameView.this.run();
            }
        };
    }

    @Override
    public void run() {
        player.move();
        draw();
        checkCollision();
    }

    private void draw() {
        while (!holder.getSurface().isValid()) {
            /*wait*/
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            drawCanvas(canvas);
        }
        try {
            holder.unlockCanvasAndPost(canvas);
        } catch (IllegalStateException e) {
            // Already unlocked
        }
    }

    private void drawCanvas(Canvas canvas) {
        framesDraw++;
        background.draw(canvas);
        player.draw(canvas);
        tuyaux.draw(canvas, framesDraw);

        if (isGameOver) {
            pause();
            canvas.drawText("GAME OVER", canvas.getWidth() / 2 - paint.getTextSize(), canvas.getHeight() / 2, paint);
        } else if (paused) {
            canvas.drawText("PAUSED", canvas.getWidth() / 2 - paint.getTextSize(), canvas.getHeight() / 2, paint);
        } else {
            ui.draw(canvas);
            increaseScore(canvas);
        }
    }

    public void checkCollision() {
        for (Rect[] tuyau : tuyaux.getTuyaux()) {
            for (Rect rect : tuyau) {
                if (Rect.intersects(rect, player.getPosition())) {
                    this.gameOver();
                }
            }
        }
    }

    public void gameOver() {
        stopTimer();
        isGameOver = true;
        if (score > 1) {
            String pseudo = prefs.getString("pseudo", null);
            if (pseudo == null) {
                pseudo = "Joueur1";
            }
            Score scoreData = new Score(score, pseudo, Calendar.getInstance().getTime());
            savedScores.add(scoreData);

            Gson gson = new Gson();
            String json = gson.toJson(savedScores, new TypeToken<ArrayList<Score>>(){}.getType());
            prefs.edit().putString("jsonSavedScores", json).apply();
        }
    }

    public void increaseScore(Canvas canvas) {
        score++;
        canvas.drawText("RECORD : " + highScore, 5, 30, paint);
        canvas.drawText("SCORE : " + score, 5, 60, paint);
    }
}
