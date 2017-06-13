package hu.ait.highlowgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    public static final String KEY_RAND = "KEY_RAND";
    private int randomNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_RAND)) {
            randomNum = savedInstanceState.getInt(KEY_RAND);
        } else {
            generateNewRandom();
        }

        final EditText etGuess = (EditText) findViewById(R.id.etGuess);
        final TextView tvStatus = (TextView) findViewById(R.id.tvStatus);

        Button btnGuess = (Button) findViewById(R.id.btnGuess);
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!TextUtils.isEmpty(etGuess.getText().toString())) {
                        int guess = Integer.parseInt(etGuess.getText().toString());

                        if (guess < randomNum) {
                            tvStatus.setText("The number is higher");
                        } else if (guess > randomNum) {
                            tvStatus.setText("The number is lower");
                        } else if (guess == randomNum) {
                            tvStatus.setText("Congratulations!");

                            startActivity(new Intent(GameActivity.this,
                                    ResultActivity.class));
                        }

                        etGuess.setText("");
                    } else {
                        etGuess.setError("Hey, the field can not be empty!");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    etGuess.setError(e.getLocalizedMessage());
                }
            }
        });
    }

    private void generateNewRandom() {
        randomNum = new Random(System.currentTimeMillis()).nextInt(10);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "HAHA you have to win the game to exit!", Toast.LENGTH_SHORT).show();
        
        //super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_RAND, randomNum);
        super.onSaveInstanceState(outState);
    }
}
