package hu.ait.todorecylerview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import hu.ait.todorecylerview.adapter.TodoRecyclerAdapter;
import hu.ait.todorecylerview.data.Todo;
import hu.ait.todorecylerview.touch.TodoItemTouchHelperCallback;

public class ScrollingActivity extends AppCompatActivity {

    public static final String KEY_TODO_ID = "KEY_TODO_ID";
    private int todoEditIndex = -1;

    private TodoRecyclerAdapter todoRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        ((MainApplication)getApplication()).openRealm();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                showTodoAddDialog();
            }
        });

        RecyclerView recyclerTodo = (RecyclerView) findViewById(
                R.id.recylerView);
        recyclerTodo.setHasFixedSize(true);
        final LinearLayoutManager layoutManager =
                new LinearLayoutManager(this);
        recyclerTodo.setLayoutManager(layoutManager);
        todoRecyclerAdapter = new TodoRecyclerAdapter(this,
                ((MainApplication)getApplication()).getRealmTodo());
        recyclerTodo.setAdapter(todoRecyclerAdapter);

        ItemTouchHelper.Callback callback = new TodoItemTouchHelperCallback(todoRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerTodo);
    }

    private void showTodoAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter todo here");

        final EditText etTodo = new EditText(this);
        builder.setView(etTodo);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                todoRecyclerAdapter.addTodo(etTodo.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showEditTodo(int index, String todoID) {
        todoEditIndex = index;

        // start edit Activity
        Intent showEditActivity = new Intent();
        showEditActivity.putExtra(KEY_TODO_ID, todoID);
        showEditActivity.setClass(this, EditTodoActivity.class);
        startActivityForResult(showEditActivity, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String todoId = data.getStringExtra(KEY_TODO_ID);

            todoRecyclerAdapter.updateTodo(todoEditIndex, todoId);
        } else {
            Toast.makeText(this, "Edit cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        ((MainApplication)getApplication()).closeRealm();
        super.onDestroy();
    }
}
