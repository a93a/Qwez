package com.example.qwez.ui.start.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qwez.R;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class SwipeDeleteHelper extends ItemTouchHelper.SimpleCallback {

    private GameAdapter adapter;
    private Drawable icon;
    private final ColorDrawable background;
    private static final int LIMIT_SWIPE_LENGTH = 10;


    public SwipeDeleteHelper(GameAdapter adapter, Context context) {
        super(0, ItemTouchHelper.LEFT);
        this.adapter = adapter;
        icon = ContextCompat.getDrawable(adapter.getContext(),
                R.drawable.delete);
        background = new ColorDrawable(context.getColor(R.color.colorSwipeDelete));
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.2f;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        adapter.deleteItem(position);
    }

    @Override
    public void onChildDraw(@NotNull Canvas c, @NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        int iconMargin = ((itemView.getHeight() - icon.getIntrinsicHeight()) / 2);
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if(dX < 0) { //left swipe
            int iconRight = itemView.getRight() - iconMargin;
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            Timber.d("left is %s and right is %s", iconLeft,iconRight);

            if(dX <= -72*3+iconMargin){//NO IDEA WHere 72 is from except from logging
                dX = -72*3+iconMargin;
            }

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { //unswiped
            background.setBounds(0, 0, 0, 0);
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX,
                dY, actionState, isCurrentlyActive);

        background.draw(c);
        icon.draw(c);
    }

}
