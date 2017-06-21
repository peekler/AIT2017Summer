package hu.ait.todorecylerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import hu.ait.todorecylerview.R;
import hu.ait.todorecylerview.ScrollingActivity;
import hu.ait.todorecylerview.data.Todo;
import hu.ait.todorecylerview.touch.TodoTouchHelperAdapter;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder>
  implements TodoTouchHelperAdapter {

    private List<Todo> listTodo;
    private Realm realmTodo;

    private Context context;

    public TodoRecyclerAdapter(Context context, Realm realmTodo) {
        this.context = context;

        this.realmTodo = realmTodo;

        RealmResults<Todo> todoResult =
                realmTodo.where(Todo.class).findAll().sort("todoTitle", Sort.ASCENDING);

        listTodo = new ArrayList<Todo>();
        for (int i = 0; i < todoResult.size(); i++) {
            listTodo.add(todoResult.get(i));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View todoRow = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.todo_row,  parent, false);
        return new ViewHolder(todoRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvTodo.setText(listTodo.get(position).getTodoTitle());
        holder.cbDone.setChecked(listTodo.get(position).isDone());

        holder.cbDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                realmTodo.beginTransaction();
                listTodo.get(holder.getAdapterPosition()).setDone(isChecked);
                realmTodo.commitTransaction();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScrollingActivity)context).showEditTodo(holder.getAdapterPosition(),
                        listTodo.get(holder.getAdapterPosition()).getTodoId()
                        );
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTodo.size();
    }

    @Override
    public void onItemDismiss(int position) {
        realmTodo.beginTransaction();
        listTodo.get(position).deleteFromRealm();
        realmTodo.commitTransaction();

        listTodo.remove(position);

        //notifyDataSetChanged();
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(listTodo, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(listTodo, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    public void addTodo(String todoTitle) {
        realmTodo.beginTransaction();
        Todo newTodo = realmTodo.createObject(Todo.class, UUID.randomUUID().toString());
        newTodo.setTodoTitle(todoTitle);
        newTodo.setDone(false);
        realmTodo.commitTransaction();

        listTodo.add(newTodo);

        notifyDataSetChanged();
        //notifyItemInserted(listTodo.size());
    }

    public void updateTodo(int todoEditIndex, String todoId) {
        Todo changedTodo = realmTodo.where(Todo.class).equalTo(
                "todoId", todoId).findFirst();

        listTodo.set(todoEditIndex, changedTodo);

        notifyItemChanged(todoEditIndex);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbDone;
        private TextView tvTodo;
        private Button btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            cbDone = (CheckBox) itemView.findViewById(R.id.cbDone);
            tvTodo = (TextView) itemView.findViewById(R.id.tvTodo);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
        }
    }

}
