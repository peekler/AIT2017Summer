package hu.ait.multiactivitydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView tvStatus = (TextView) findViewById(R.id.tvStatus);

        if (getIntent().hasExtra(MainActivity.KEY_DATA) &&
                getIntent().hasExtra(MainActivity.KEY_PERSON)) {
            String data = getIntent().getStringExtra(MainActivity.KEY_DATA);
            tvStatus.setText(data);

            Person personTmp= (Person) getIntent().getSerializableExtra(MainActivity.KEY_PERSON);
            Person personTmp2 = MyDataManager.getInstance().getPerson();

            tvStatus.append(personTmp.getName());

            tvStatus.append("\n"+personTmp2.getName());
        }

    }
}
