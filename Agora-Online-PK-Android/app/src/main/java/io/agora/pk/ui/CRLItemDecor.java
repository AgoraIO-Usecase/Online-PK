package io.agora.pk.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CRLItemDecor extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int childCount = parent.getAdapter().getItemCount();
        if (parent.getChildAdapterPosition(view) < childCount) {
            outRect.top = 16;
            outRect.bottom = 16;
            outRect.left = 9;
            outRect.right = 9;
        }
    }
}
