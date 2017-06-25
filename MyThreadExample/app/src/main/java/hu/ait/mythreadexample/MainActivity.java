package hu.ait.mythreadexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Timer timer;

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.append("#");
                }
            });
        }
    }

//    private boolean enabled;

    //    class MyThread extends Thread {
//    @Override
//        public void run() {
//            while (enabled) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.append("#");
//                    }
//                });
//                try {
//                    sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tvData);

        Button startButton = (Button) findViewById(R.id.btnStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                enabled = true;
//                new MyThread().start();
                timer = new Timer();
                timer.schedule(new MyTimerTask(), 0, 1000);
            }
        });

        Button stopButton = (Button) findViewById(R.id.btnStop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                enabled = false;
                timer.cancel();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
//        enabled = false;
        timer.cancel();
    }
}
