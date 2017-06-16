package hu.ait.todorecylerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.ait.todorecylerview.R;
import hu.ait.todorecylerview.data.Todo;

public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder> {

    private List<Todo> listTodo;

    public TodoRecyclerAdapter() {
        listTodo = new ArrayList<Todo>();
        for (int i = 0; i < 20; i++) {
            listTodo.add(new Todo("Todo"+i,false));
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View todoRow = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.todo_row,  parent, false);
        return new ViewHolder(todoRow);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTodo.setText(listTodo.get(position).getTodoTitle());
        holder.cbDone.setChecked(listTodo.get(position).isDone());
    }

    @Override
    public int getItemCount() {
        return listTodo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbDone;
        private TextView tvTodo;

        public ViewHolder(View itemView) {
            super(itemView);
            cbDone = (CheckBox) itemView.findViewById(R.id.cbDone);
            tvTodo = (TextView) itemView.findViewById(R.id.tvTodo);
        }
    }

}
