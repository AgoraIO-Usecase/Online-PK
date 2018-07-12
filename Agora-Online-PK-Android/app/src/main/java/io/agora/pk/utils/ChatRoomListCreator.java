package io.agora.pk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.agora.pk.R;
import io.agora.pk.bean.CRLBean;

public class ChatRoomListCreator extends AsyncTask<Void , Integer , List<CRLBean>>{
    private final List<CRLBean> mDataSet = new ArrayList<>();
    private WeakReference<Context> mCtx;
    private CreatorListener mCL;

    public ChatRoomListCreator(WeakReference<Context> ctx, CreatorListener cl) {
        mCtx = ctx;
        mCL = cl;
    }

    @Override
    protected List<CRLBean> doInBackground(Void... voids) {
        if (mDataSet.isEmpty())
            return genDataSets();
        return mDataSet;
    }

    @Override
    protected void onPostExecute(List<CRLBean> beans) {
        super.onPostExecute(beans);
        if (mCL != null) {
            mCL.creatFinished(beans);
        }
    }

    public interface CreatorListener{
        void creatFinished(List<CRLBean> beans);
    }

    private final int[] broadcasterBgResIds = {
            R.drawable.chat_room_broadcaster_01,
            R.drawable.chat_room_broadcaster_02,
            R.drawable.chat_room_broadcaster_03,
            R.drawable.chat_room_broadcaster_04,
            R.drawable.chat_room_broadcaster_05,
            R.drawable.chat_room_broadcaster_06,
            R.drawable.chat_room_broadcaster_07,
            R.drawable.chat_room_broadcaster_08
    };

    private final int[] userIconResIds = {
            R.drawable.user_icon_01,
            R.drawable.user_icon_02,
            R.drawable.user_icon_03,
            R.drawable.user_icon_04,
            R.drawable.user_icon_05,
            R.drawable.user_icon_06,
            R.drawable.user_icon_07,
            R.drawable.user_icon_08,
    };

    public List<CRLBean> genDataSets() {
        mDataSet.clear();
        List<Integer> icons = shuffleA(userIconResIds);
        List<Integer> bgs = shuffleA(broadcasterBgResIds);

        for (int i = 0; i < icons.size(); i++) {
            CRLBean crl = new CRLBean();
            crl.broadcasterIconId = readBitMap(mCtx.get(), bgs.get(i));
            crl.userIconId = icons.get(i);
            crl.channelAccount = StringUtils.random(8);

            mDataSet.add(crl);
        }

        return mDataSet;
    }

    private List<Integer> shuffleA(int[] ds) {
        List<Integer> solution = new ArrayList<>();
        for (int i = 0; i < ds.length; i++) {
            solution.add(ds[i]);
        }
        Collections.shuffle(solution);

        return solution;
    }

    private Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        opt.inSampleSize = 1;
        opt.inJustDecodeBounds = false;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

}
