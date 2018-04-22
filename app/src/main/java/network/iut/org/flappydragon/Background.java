package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {
    // Screen properties
    private int height;
    private int width;

    // Used for the parralax
    private int offsetB1 = 0;
    private int offsetB2 = 0;
    private int offsetB3 = 0;
    private int offsetB4 = 0;
    private int offsetB5 = 0;

    private GameView view;
    private Bitmap background1;
    private Bitmap background2;
    private Bitmap background3;
    private Bitmap background4;
    private Bitmap background5;

    public Background(Context context, GameView view) {
        this.view = view;
        height = context.getResources().getDisplayMetrics().heightPixels;
        width = context.getResources().getDisplayMetrics().widthPixels;

        background1 = Util.getScaledBitmapAlpha8(context, R.drawable.layer1);
        background2 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer2, width, height);
        background3 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer3, width, height);
        background4 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer4, width, height);
        background5 = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.layer5, width, height);
    }

    public int getFloorHeight() {
        return background1.getHeight();
    }

    public void draw(Canvas canvas) {
        // Reset the parralax vars when it reach the screen width
        if (offsetB1 <= -background1.getWidth()) {
            offsetB1 = 0;
        }
        if (offsetB2 <= -background2.getWidth()) {
            offsetB2 = 0;
        }
        if (offsetB3 <= -background3.getWidth()) {
            offsetB3 = 0;
        }
        if (offsetB4 <= -background4.getWidth()) {
            offsetB4 = 0;
        }
        if (offsetB5 <= -background5.getWidth()) {
            offsetB5 = 0;
        }

        canvas.drawBitmap(background1, offsetB1, 0, null);
        canvas.drawBitmap(background1, offsetB1 + background1.getWidth(), 0, null);
        canvas.drawBitmap(background2, offsetB2, view.getHeight() - background2.getHeight(), null);
        canvas.drawBitmap(background2, offsetB2 + background2.getWidth(), view.getHeight() - background2.getHeight(), null);
        canvas.drawBitmap(background3, offsetB3, view.getHeight() - background3.getHeight(), null);
        canvas.drawBitmap(background3, offsetB3 + background3.getWidth(), view.getHeight() - background3.getHeight(), null);
        canvas.drawBitmap(background4, offsetB4, view.getHeight() - background4.getHeight(), null);
        canvas.drawBitmap(background4, offsetB4 + background4.getWidth(), view.getHeight() - background4.getHeight(), null);
        canvas.drawBitmap(background5, offsetB5, view.getHeight() - background5.getHeight(), null);
        canvas.drawBitmap(background5, offsetB5 + background5.getWidth(), view.getHeight() - background5.getHeight(), null);

        offsetB1--;
        offsetB2 -= 2;
        offsetB3 -= 3;
        offsetB4 -= 4;
        offsetB5 -= 5;
    }
}