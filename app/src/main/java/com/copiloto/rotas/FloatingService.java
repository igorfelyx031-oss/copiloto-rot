package com.copiloto.rotas;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class FloatingService extends Service {

    private WindowManager windowManager;
    private View floatingButton;
    private WindowManager.LayoutParams params;

    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        TextView button = new TextView(this);

        button.setText("🟠");
        button.setTextSize(22);
        button.setGravity(Gravity.CENTER);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(Color.rgb(35, 35, 35));
        button.setPadding(12, 8, 12, 8);

        floatingButton = button;

        params = new WindowManager.LayoutParams(
                70,
                70,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 20;
        params.y = 300;

        button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        initialX = params.x;
                        initialY = params.y;

                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        return true;

                    case MotionEvent.ACTION_MOVE:

                        params.x = initialX +
                                (int) (event.getRawX() - initialTouchX);

                        params.y = initialY +
                                (int) (event.getRawY() - initialTouchY);

                        try {
                            windowManager.updateViewLayout(floatingButton, params);
                        } catch (Exception e) {
                            // ignore if view not attached yet
                        }

                        return true;

                    case MotionEvent.ACTION_UP:
                        // handle click or snap-to-edge if desired
                        return true;
                }

                return false;
            }
        });

        try {
            windowManager.addView(floatingButton, params);
        } catch (Exception e) {
            // ignore if cannot add view (permissions, etc.)
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingButton != null) {
            try {
                windowManager.removeView(floatingButton);
            } catch (Exception e) {
                // ignore
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
