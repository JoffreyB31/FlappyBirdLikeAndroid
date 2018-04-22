package network.iut.org.flappydragon;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Player {
    /**
     * Static bitmap to reduce memory usage.
     */
    public static Bitmap globalBitmap;
    private Bitmap bitmap;
    private final Bitmap bitmapUp;
    private final Bitmap bitmapDown;
    private final Bitmap bitmapTap;
    private final byte frameTime;
    private int frameTimeCounter;
    private final int width;
    private final int height;
    private int x;
    private int y;
    private float speedX;
    private float speedY;
    private Context context;
    private GameView view;

    public Player(Context context, GameView view) {
        this.context = context;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        int width = context.getResources().getDisplayMetrics().widthPixels;
        if (globalBitmap == null) {
            globalBitmap = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.frame1, Float.valueOf(height / 10f).intValue(), Float.valueOf(width / 10f).intValue());
        }
        this.bitmap = globalBitmap;
        this.bitmapUp = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.frame3, Float.valueOf(height / 10f).intValue(), Float.valueOf(width / 10f).intValue());
        ;
        this.bitmapDown = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.frame1, Float.valueOf(height / 10f).intValue(), Float.valueOf(width / 10f).intValue());
        ;
        this.bitmapTap = Util.decodeSampledBitmapFromResource(context.getResources(), R.drawable.frame2, Float.valueOf(height / 10f).intValue(), Float.valueOf(width / 10f).intValue());
        ;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
        this.frameTime = 3;
        this.y = context.getResources().getDisplayMetrics().heightPixels / 2; // Start middle of the screen

        this.view = view;
        this.x = 0;
        this.speedX = 0;
    }

    public void onTap() {
        changePlayerImage("TAP");
        this.speedY = getTabSpeed();
        this.y += getPosTabIncrease();
    }

    private float getPosTabIncrease() {
        return -view.getHeight() / 100;
    }

    private float getTabSpeed() {
        return -view.getHeight() / 16f;
    }

    public void move() {
        changeToNextFrame();

        if (speedY < 0) {
            // The character is moving up
            Log.i("Move", "Moving up");
            changePlayerImage("UP");
            speedY = speedY * 2 / 3 + getSpeedTimeDecrease() / 2;
        } else {
            // the character is moving down
            Log.i("Move", "Moving down");
            changePlayerImage("DOWN");
            this.speedY += getSpeedTimeDecrease();
        }

        if (this.speedY > getMaxSpeed()) {
            // speed limit
            this.speedY = getMaxSpeed();
        }
        this.x += speedX;

        // Next position height
        float nextHeight = this.y + speedY;
        // Home bar height
        int homeHeight = Math.round(getHomeBarHeight(this.context));

        // Prevent the dragon to overflow the screen
        if (nextHeight > view.getHeight() - homeHeight) { // Bottom
            // Next position = bottom - homeBarHeight
            this.y = view.getHeight() - (homeHeight - 10);
            view.gameOver();
        } else if (nextHeight < 0) { // Top
            // Next position = top
            this.y = 0;
        } else {
            this.y += speedY;
        }
    }

    protected float getHomeBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    protected void changeToNextFrame() {
        this.frameTimeCounter++;
        if (this.frameTimeCounter >= this.frameTime) {
            //TODO Change frame
            this.frameTimeCounter = 0;
        }
    }

    private float getSpeedTimeDecrease() {
        return view.getHeight() / 250;
    }

    private float getMaxSpeed() {
        return view.getHeight() / 51.2f;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public void changePlayerImage(String type) {
        switch (type) {
            case "UP":
                this.bitmap = this.bitmapUp;
                break;
            case "DOWN":
                this.bitmap = this.bitmapDown;
                break;
            case "TAP":
                this.bitmap = this.bitmapTap;
                break;
        }
    }

    public Rect getPosition() {
        return new Rect(x, y, x + this.bitmap.getWidth(), y + this.bitmap.getHeight());
    }
}
