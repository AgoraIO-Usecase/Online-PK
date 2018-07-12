package io.agora.pk;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

import io.agora.pk.engine.WorkThread;

public class PKApplication extends Application {
    private WorkThread workThread;
    private PKConfig pkConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        if (pkConfig == null) {
            pkConfig = new PKConfig();
        }
    }

    public synchronized void initWorkThread(){
        if (workThread == null) {
            workThread = new WorkThread(new WeakReference<>(getApplicationContext()));
            workThread.start();
            workThread.waitForReady();
        }
    }

    public synchronized WorkThread getWorkThread() {
        return workThread;
    }

    public synchronized PKConfig getPkConfig() {
        return pkConfig;
    }
}
