package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tuyaux {
    private Context context;
    private GameView view;
    private int offset = 0;
    private int speed = -10;
    private List<Rect[]> tuyaux;
    private LinearGradient gradient;
    private LinearGradient lastGradient;

    public Tuyaux(Context context, GameView view) {
        this.context = context;
        this.view = view;
        this.offset = view.getWidth() + 10;
        this.tuyaux = new ArrayList<>();
        buildTuyaux();
    }

    public void draw(Canvas canvas, int frames) {
        Paint paint = new Paint();

        // Random background color
        Random r = new Random();
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        gradient = new LinearGradient(0, 0, 0, view.getHeight(), Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256)), Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256)), Shader.TileMode.MIRROR);
        lastGradient = gradient;
        if (frames % 3 == 0) {
            paint.setShader(gradient);
        } else {
            paint.setShader(lastGradient);
        }

        // Draw
        for (Rect[] tuyau : tuyaux) {
            tuyau[0].set(tuyau[0].left + speed, tuyau[0].top, tuyau[0].right + speed, tuyau[0].bottom);
            tuyau[1].set(tuyau[1].left + speed, tuyau[1].top, tuyau[1].right + speed, tuyau[1].bottom);
            canvas.drawRect(tuyau[0], paint);
            canvas.drawRect(tuyau[1], paint);
        }

        // Increase speed every 50frames
        if (frames % 50 == 0) {
            speed += -2;
        }
    }

    public void addTuyau() {
        Log.e("Tuyau", "Ajouté");
        offset += view.getWidth() / 3;

        // Random hole placement
        Random r = new Random();
        int holeSize = view.getHeight() / 4;
        int rectWidth = 40;
        int max = view.getHeight() - holeSize;
        int min = holeSize;
        int topRectHole = r.nextInt((max - min) + 1) + min;

        // Rectangle
        Rect rTop = new Rect(
                0 + offset,
                0,
                rectWidth + offset,
                0 + topRectHole
        );
        Rect rBottom = new Rect(
                0 + offset,
                topRectHole + holeSize,
                rectWidth + offset,
                view.getHeight() + (topRectHole + holeSize)
        );

        // Storage
        Rect[] obs = new Rect[]{rTop, rBottom};
        tuyaux.add(obs);
    }

    public void removeTuyau() {
        tuyaux.remove(0);
    }

    public List<Rect[]> getTuyaux() {
        return this.tuyaux;
    }

    public void buildTuyaux() {
        int nb = 50;
        for (int i = 0, l = nb; i < l; i++) {
            addTuyau();
        }
    }

    public boolean passTuyau() {
        return false;
    }
}
