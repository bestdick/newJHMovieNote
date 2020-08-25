package com.WriteRecyclerViewFunction;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemMoveCallback extends ItemTouchHelper.Callback {
    private final int TYPE_TITLE = 1001;
    private final int TYPE_ADD_MORE = 1002;
    private final int TYPE_TEXT = 1003;
    private final int TYPE_IMAGE =1004;



    private final ItemTouchHelperContract mAdapter;

    public ItemMoveCallback(ItemTouchHelperContract adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }



    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final int position = viewHolder.getAdapterPosition();
//        if(position == 0 || mAdapter.getItemCount() == (position-1)){
//            setSwiperBoolean(false);
//        }else{
            mAdapter.removeItem(position);
//        }



        
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if(viewHolder.getItemViewType() == TYPE_TITLE || viewHolder.getItemViewType() == TYPE_ADD_MORE){
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(0,0);
        }else{
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mAdapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder,
                                  int actionState) {


        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof RecyclerViewAdapter.MyViewHolder) {
                RecyclerViewAdapter.MyViewHolder myViewHolder=
                        (RecyclerViewAdapter.MyViewHolder) viewHolder;
                mAdapter.onRowSelected(myViewHolder);

            }

        }

        super.onSelectedChanged(viewHolder, actionState);
    }
    @Override
    public void clearView(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof RecyclerViewAdapter.MyViewHolder) {
            RecyclerViewAdapter.MyViewHolder myViewHolder=
                    (RecyclerViewAdapter.MyViewHolder) viewHolder;
            mAdapter.onRowClear(myViewHolder);
        }
    }

    public interface ItemTouchHelperContract {

        void onRowMoved(int fromPosition, int toPosition);
        void onRowSelected(RecyclerViewAdapter.MyViewHolder myViewHolder);
        void onRowClear(RecyclerViewAdapter.MyViewHolder myViewHolder);

        void removeItem(int position);
        int getItemCount();

    }

}
