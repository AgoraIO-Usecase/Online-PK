package io.agora.pk.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.InputStream;
import java.util.List;

import io.agora.pk.R;
import io.agora.pk.bean.CRLBean;

public class CRLRecycleAdapter extends RecyclerView.Adapter<CRLRecycleHolder> {
    private Context mContxt;
    private List<CRLBean> mDataSet;

    public CRLRecycleAdapter(Context ctx, List<CRLBean> dataSets){
        this.mContxt = ctx;
        this.mDataSet = dataSets;
    }

    @Override
    public CRLRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CRLRecycleHolder(LayoutInflater.from(mContxt).inflate(R.layout.chat_room_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CRLRecycleHolder holder, int position) {
        holder.mIvUserIcon.setImageResource(mDataSet.get(position).userIconId);
        holder.mIvBroadcasterBg.setImageBitmap(mDataSet.get(position).broadcasterIconId);
        holder.mTvChannelAccont.setText(mDataSet.get(position).channelAccount);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
