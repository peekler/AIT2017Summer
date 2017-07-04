package hu.ait.intentdemo;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestNeededPermission();

        Button btnIntent = (Button) findViewById(R.id.btnIntent);
        btnIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intentStartPhoneCall();
                //intentSearch("Balaton");
                //intentShare("Demo content to share");
                intentSendEmail();
            }
        });
    }

    private void intentStartPhoneCall() {
        Intent intentCall = new Intent();
        intentCall.setAction(Intent.ACTION_CALL);
        intentCall.setData(Uri.parse("tel:123456789"));
        startActivity(intentCall);
    }

    private void intentSearch(String search) {
        Intent intentSearch = new Intent();
        intentSearch.setAction(Intent.ACTION_WEB_SEARCH);
        intentSearch.putExtra(SearchManager.QUERY, search);

        startActivity(intentSearch);
    }

    private void intentShare(String text) {
        Intent intentShare = new Intent();
        intentShare.setAction(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, text);

        intentShare.setPackage("com.facebook.katana");

        startActivity(intentShare);
    }

    public void intentSendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  ,
                new String[]{"peter.ekler@aut.bme.hu"});
        i.putExtra(Intent.EXTRA_SUBJECT, "AIT subject");
        i.putExtra(Intent.EXTRA_TEXT   , "You have a lesson now");
        try {
            startActivity(Intent.createChooser(i,
                    "Send email."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no mail clients.", Toast.LENGTH_SHORT).show();
        }
    }

    public void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                Toast.makeText(MainActivity.this,
                        "I need it for call", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    101);
        } else {
            // already have permission
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "CALL perm granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "CALL perm NOT granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }





}
