package hu.ait.sharedprefex;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String TIME = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String lastUsedTimeString = getLastUsedTime();

        TextView tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvStartDate.setText(lastUsedTimeString);

        SharedPreferences lastUsedTime = getSharedPreferences("LAST_USED_TIME", MODE_PRIVATE);
        SharedPreferences.Editor editor = lastUsedTime.edit();
        editor.putString(TIME, new Date(System.currentTimeMillis()).toString());
        editor.commit();
    }

    @NonNull
    private String getLastUsedTime() {
        SharedPreferences lastUsedTime = getSharedPreferences("LAST_USED_TIME", MODE_PRIVATE);
        return lastUsedTime.getString(TIME, "This is the first time you use this app!");
    }


}
