package io.agora.pk.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.agora.pk.R;

public class CRLRecycleHolder extends RecyclerView.ViewHolder {
    public ImageView mIvUserIcon;
    public ImageView mIvBroadcasterBg;
    public TextView mTvChannelAccont;

    public CRLRecycleHolder(View itemView) {
        super(itemView);

        mIvUserIcon = itemView.findViewById(R.id.iv_chat_room_item_user_icon);
        mIvBroadcasterBg = itemView.findViewById(R.id.iv_chat_room_item_background);
        mTvChannelAccont = itemView.findViewById(R.id.tv_chat_room_item_channel_name);
    }
}
