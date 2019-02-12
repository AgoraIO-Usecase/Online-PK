package io.agora.pk;

import android.app.Application;

import java.lang.ref.WeakReference;

import io.agora.pk.model.WorkerThread;

public class PKApplication extends Application {
    private WorkerThread mWorkerThread;
    private PKConfig pkConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        if (pkConfig == null) {
            pkConfig = new PKConfig();
        }
    }

    public synchronized void initWorkerThread(){
        if (mWorkerThread == null) {
            mWorkerThread = new WorkerThread(new WeakReference<>(getApplicationContext()));
            mWorkerThread.start();
            mWorkerThread.waitForReady();
        }
    }

    public synchronized WorkerThread getWorkerThread() {
        return mWorkerThread;
    }

    public synchronized PKConfig getPkConfig() {
        return pkConfig;
    }
}
