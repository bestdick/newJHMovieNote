package com.WriteRecyclerViewFunction;

import androidx.recyclerview.widget.RecyclerView;

public interface StartDragListener {
    void requestDrag(RecyclerView.ViewHolder viewHolder);
}
