package hu.ait.todorecylerview.data;

public class Todo {
    private String todoTitle;
    private boolean done;

    public Todo(String todoTitle, boolean done) {
        this.todoTitle = todoTitle;
        this.done = done;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
