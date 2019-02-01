package io.agora.pk.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import java.lang.ref.WeakReference;

public class KeyboardStatusDetector {
    private static final int SOFT_KEY_BOARD_MIN_HEIGH = 100;

    private KeyboardListener klistener;
    private boolean isVisible = false;

    public void setCallback(KeyboardListener kl) {
        this.klistener = kl;
    }

    public void registerActivity(WeakReference<Activity> activity) {
        final View v = activity.get().getWindow().getDecorView().findViewById(android.R.id.content);
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                v.getWindowVisibleDisplayFrame(r);
                int height = v.getRootView().getHeight() - (r.bottom - r.top);

                if (height > SOFT_KEY_BOARD_MIN_HEIGH) {
                    if (!isVisible) {
                        isVisible = true;
                        if (klistener != null)
                            klistener.onKeyBoardStatusChanged(true, height);
                    } else {
                        isVisible = false;
                        if (klistener != null)
                            klistener.onKeyBoardStatusChanged(true, 0);
                    }
                }
            }
        });
    }

    public interface KeyboardListener {
        void onKeyBoardStatusChanged(boolean v, int height);
    }
}
