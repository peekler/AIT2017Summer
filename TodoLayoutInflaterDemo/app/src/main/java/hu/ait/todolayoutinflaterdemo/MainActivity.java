package hu.ait.todolayoutinflaterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etTodo)
    EditText etTodo;
    @BindView(R.id.layoutContent)
    LinearLayout layoutContent;
    @BindView(R.id.cbImportant)
    CheckBox cbImportant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSave)
    public void saveClicked(Button btn) {
        final View rowTodo = getLayoutInflater().inflate(R.layout.todo_row, null);

        TextView tvTitle = (TextView) rowTodo.findViewById(R.id.tvTodoTitle);
        tvTitle.setText(etTodo.getText().toString());

        ImageView ivIcon = (ImageView) rowTodo.findViewById(R.id.ivIcon);
        if (cbImportant.isChecked()) {
            ivIcon.setImageResource(R.drawable.ic_eject);
        } else {
            ivIcon.setImageResource(R.drawable.ic_home);
        }

        Button btnDel = (Button) rowTodo.findViewById(R.id.btnDelete);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutContent.removeView(rowTodo);
            }
        });

        layoutContent.addView(rowTodo,0);
    }

}
