package hu.ait.animationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Animation anim = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.anim_button);
        final Animation animSend = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.anim_send);
        animSend.setFillAfter(true);
        animSend.setFillEnabled(true);

        animSend.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(MainActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        final LinearLayout layoutContent = (LinearLayout) findViewById(R.id.layoutContent);
        final TextView tvMessage = (TextView) findViewById(R.id.tvMessage);

        final Button btnPress = (Button) findViewById(R.id.btnPress);
        btnPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btnPress.startAnimation(anim);
                //layoutContent.startAnimation(anim);
                tvMessage.startAnimation(animSend);
            }
        });
    }
}
