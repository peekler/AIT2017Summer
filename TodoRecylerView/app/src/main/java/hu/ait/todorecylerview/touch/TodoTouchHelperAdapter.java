package hu.ait.todorecylerview.touch;

public interface TodoTouchHelperAdapter {

    public void onItemDismiss(int position);

    public void onItemMove(int fromPosition,
                           int toPosition);

}
