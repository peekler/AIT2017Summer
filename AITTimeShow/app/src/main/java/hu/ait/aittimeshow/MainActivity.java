package hu.ait.aittimeshow;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTime = (Button) findViewById(R.id.btnTime);
        final TextView tvTime = (TextView) findViewById(R.id.tvTime);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final LinearLayout layoutContent = (LinearLayout) findViewById(R.id.layoutContent);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG_MAIN", "btnTime was pressed");


                String name = etName.getText().toString();
                if (!TextUtils.isEmpty(name)) {
                    String time = getString(R.string.time_message, name,
                            new Date(System.currentTimeMillis()).toString());


                    //Toast.makeText(MainActivity.this, time,
                    //        Toast.LENGTH_SHORT).show();

                    Snackbar.make(layoutContent, time, Snackbar.LENGTH_LONG).setAction(
                            "Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tvTime.setText("OK");
                                }
                            }
                    ).show();

                    tvTime.setText(time);
                } else {
                    etName.setError(getString(R.string.error_empty));
                }
            }
        });
    }
}
