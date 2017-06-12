package hu.ait.multiactivitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_DATA = "KEY_DATA";
    public static final String KEY_PERSON = "KEY_PERSON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etData = (EditText) findViewById(R.id.etData);
        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStartSecond = new Intent();
                intentStartSecond.setClass(MainActivity.this, SecondActivity.class);

                intentStartSecond.putExtra(KEY_DATA, etData.getText().toString());

                Person personDemo = new Person("John", "Mars", 40);
                intentStartSecond.putExtra(KEY_PERSON, personDemo);

                MyDataManager.getInstance().setPerson(personDemo);

                startActivity(intentStartSecond);

                //finish();
            }
        });

    }

}
