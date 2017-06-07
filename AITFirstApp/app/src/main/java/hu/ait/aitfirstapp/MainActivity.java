package hu.ait.aitfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
    }

    private void setupUI() {
        final TextView tvTime = (TextView) findViewById(R.id.tvTime);
        Button btnTime = (Button) findViewById(R.id.btnTime);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = "THE TIME IS: "+
                        new Date(System.currentTimeMillis()).toString();
                Toast.makeText(MainActivity.this, time, Toast.LENGTH_LONG).show();
                tvTime.setText(time);
            }
        });
    }
}
