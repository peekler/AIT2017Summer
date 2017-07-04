package demo.android.app.hu.broadcastreceivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AirPlaneReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean airPlaneState = intent.getBooleanExtra("state", false);

        Toast.makeText(context, "Airplane state: "+airPlaneState, Toast.LENGTH_LONG).show();

        Intent i = new Intent(context,MyActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}


