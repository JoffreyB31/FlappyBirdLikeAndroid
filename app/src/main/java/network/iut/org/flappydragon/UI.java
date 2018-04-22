package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public class UI {
    private int height;
    private int width;
    private GameView view;
    private Bitmap pause;
    private Rect destRect;

    public UI(Context context, GameView view) {
        height = context.getResources().getDisplayMetrics().heightPixels;
        width = context.getResources().getDisplayMetrics().widthPixels;
        int downsizeRatio = 3;
        pause = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.pause, width / downsizeRatio, height / downsizeRatio);
        this.view = view;
    }

    public void draw(Canvas canvas) {
        int imgWidth = pause.getWidth() / 4;
        int imgHeight = pause.getHeight() / 4;
        destRect = new Rect(width - imgWidth, 15, width - 15, imgHeight);
        canvas.drawBitmap(pause, new Rect(0, 0, pause.getWidth(), pause.getHeight()), destRect, null);
    }

    public void onTap(MotionEvent evt) {
        float x = evt.getX();
        float y = evt.getY();

        if (destRect.contains((int) x,(int) y)) {
            view.pause();
        }
    }
}