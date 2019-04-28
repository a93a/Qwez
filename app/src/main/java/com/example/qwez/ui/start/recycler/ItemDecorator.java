package com.example.qwez.ui.start.recycler;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import timber.log.Timber;

/**
 * Custom ItemDecorator class for Recyclerview
 * Adds padding to bottom of items in recyclerView.
 */
public class ItemDecorator extends RecyclerView.ItemDecoration {

    //amont of dp of padding
    private final int space;
    public static final int MARGIN = 5;

    /**
     *
     * @param space amount of space to add as padding at bottom of each recyclerView (In dp as unit).
     */
    public ItemDecorator(int space) {
        this.space = space;
    }

    /**
     * Only set the bottom space if it is not the last item
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        if (parent.getChildAdapterPosition(view) != state.getItemCount()-1) {
            outRect.bottom = space * 2;
        }
    }
}
