package hu.ait.servicedemolastdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Date;

public class TimeService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean enabled = false;

    private class MyTimeThread extends Thread {
        @Override
        public void run() {
            Handler mainHandler = new Handler(TimeService.this.getMainLooper());

            while (enabled) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TimeService.this, new Date(System.currentTimeMillis()).toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!enabled) {
            enabled = true;
            new MyTimeThread().start();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        enabled = false;
        super.onDestroy();
    }
}
