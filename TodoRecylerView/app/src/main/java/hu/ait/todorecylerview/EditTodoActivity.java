package hu.ait.todorecylerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import hu.ait.todorecylerview.data.Todo;

public class EditTodoActivity extends AppCompatActivity {

    private EditText etTodoText;
    private CheckBox cbTodoDone;
    private Todo todoToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        etTodoText = (EditText) findViewById(R.id.etTodoText);
        cbTodoDone = (CheckBox) findViewById(R.id.cbTodoDone);



        if (getIntent().hasExtra(ScrollingActivity.KEY_TODO_ID)) {
            String todoId = getIntent().getStringExtra(ScrollingActivity.KEY_TODO_ID);

            todoToEdit = ((MainApplication)getApplication()).getRealmTodo().
                    where(Todo.class).equalTo("todoId", todoId).findFirst();

            etTodoText.setText(todoToEdit.getTodoTitle());
            cbTodoDone.setChecked(todoToEdit.isDone());
        }

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePressed();
            }
        });
    }

    public void savePressed() {
        if (TextUtils.isEmpty(etTodoText.getText().toString())) {
            etTodoText.setError("This field can not be empty");
        } else {

            ((MainApplication)getApplication()).getRealmTodo().beginTransaction();
            todoToEdit.setTodoTitle(etTodoText.getText().toString());
            todoToEdit.setDone(cbTodoDone.isChecked());
            ((MainApplication)getApplication()).getRealmTodo().commitTransaction();

            Intent intentResult = new Intent();
            intentResult.putExtra(ScrollingActivity.KEY_TODO_ID, todoToEdit.getTodoId());
            setResult(RESULT_OK, intentResult);
            finish();
        }
    }
}
