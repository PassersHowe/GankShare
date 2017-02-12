package com.passershowe.gank.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.passershowe.gank.R;
import com.passershowe.gank.data.entity.Gank;
import com.passershowe.gank.widget.RatioImageView;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PassersHowe on 2016/9/1.
 */

public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Gank> mWelfareGankList;
    private Context mContext;

    public WelfareAdapter(Context context, List<Gank> gankList) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        mWelfareGankList = gankList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.welfare_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       String imageUrl = mWelfareGankList.get(position).getUrl();
        //瀑布流图片比例设置
        if (position % 2 == 0) {
            holder.welfareItemImage.setImageRatio(0.7f);
        } else {
            holder.welfareItemImage.setImageRatio(0.6f);
        }
        //显示图片
        Glide.with(mContext)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.img_default_gray)
                .centerCrop()
                .into(holder.welfareItemImage);
    }

    @Override
    public int getItemCount() {
        return mWelfareGankList == null ? 0 : mWelfareGankList.size();
    }

    //为list添加数据
    public void addAll(Collection list) {
        mWelfareGankList.addAll(list);
    }

    //清除list
    public void clean() {
        mWelfareGankList.clear();
    }

    //重新设置list
    public void setDataList(Collection dataList) {
        mWelfareGankList.clear();
        if (dataList == null) return;
        mWelfareGankList.addAll(dataList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.welfare_item_image)
        RatioImageView welfareItemImage;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
