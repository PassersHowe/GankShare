package com.passershowe.gank.ui.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.passershowe.gank.R;
import com.passershowe.gank.data.entity.Gank;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.passershowe.gank.constant.Constant.SOURCE_CHROME;
import static com.passershowe.gank.constant.Constant.SOURCE_WEB;
import static com.passershowe.gank.constant.Constant.TYPE_ANDROID;
import static com.passershowe.gank.constant.Constant.TYPE_APP;
import static com.passershowe.gank.constant.Constant.TYPE_EXPAND;
import static com.passershowe.gank.constant.Constant.TYPE_FOREEND;
import static com.passershowe.gank.constant.Constant.TYPE_IOS;
import static com.passershowe.gank.constant.Constant.TYPE_RECOMMEND;
import static com.passershowe.gank.constant.Constant.TYPE_REST;
import static com.passershowe.gank.constant.Constant.TYPE_WELFARE;

/**
 * Created by hao809395357 on 2016/10/13.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private List<Gank> mGankList;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public HistoryListAdapter(Context context, List<Gank> gankList) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mGankList = gankList;

    }


    /**
     * 点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    /**
     * 长按监听
     *
     * @param itemLongClickListener
     */
    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.history_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //引用view的OnClickListener来实现事件的监听
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            itemClickListener.OnItemClick(holder.itemView, holder.getLayoutPosition(),
                                    mGankList.get(position));
                        }
                    });
        }
        //引用view的OnLongClickListener来实现长按事件的监听
        if (itemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(
                    new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            itemLongClickListener.OnItemLongClick(holder.itemView, holder.getLayoutPosition());
                            return true;
                        }
                    }
            );

        }


        String date = mGankList.get(position).getCreatedAt();
        holder.historyItemDesc.setText(mGankList.get(position).getDesc());
        holder.historyItemType.setText(mGankList.get(position).getType());
        //不写else会数据错乱=￣ω￣=
        if (mGankList.get(position).getSource() != null) {
            holder.historyItemSource.setText(mGankList.get(position).getSource());
        } else {
            holder.historyItemSource.setText("");
        }
        //不写else会数据错乱=￣ω￣=
        if (mGankList.get(position).getWho() != null) {
            holder.historyItemWho.setText("发布人：" + mGankList.get(position).getWho());
        } else {
            holder.historyItemWho.setText("");
        }

        holder.historyItemDate.setText(date.substring(0, date.indexOf("T")));
        //设置类型的背景颜色
        showTextTypeBackground(position, holder);

        //设置来源的背景颜色
        showTextSourceBackground(position, holder);
    }

    @Override
    public int getItemCount() {
        return mGankList == null ? 0 : mGankList.size();
    }


    /**
     * 根据来源修改标签颜色
     *
     * @param position
     * @param holder
     */
    private void showTextSourceBackground(int position, HistoryListAdapter.ViewHolder holder) {
        GradientDrawable p = (GradientDrawable) holder.historyItemSource.getBackground();
        switch (mGankList.get(position).getSource()) {
            case SOURCE_WEB:
                p.setColor(mContext.getResources().getColor(R.color.source_web));
                break;
            case SOURCE_CHROME:
                p.setColor(mContext.getResources().getColor(R.color.source_chrome));
                break;
            default:
                p.setColor(mContext.getResources().getColor(R.color.colorPrimary));
                break;
        }
    }


    /**
     * 根据标签类型修改标签颜色 数量有点多~~~~~~~
     *
     * @param position 当前的位置
     * @param holder   ViewHolder
     */
    private void showTextTypeBackground(int position, HistoryListAdapter.ViewHolder holder) {
        //注：getColor(int)方法过时，替代方法getColor(int，theme) 限API 23 以上

        GradientDrawable p = (GradientDrawable) holder.historyItemType.getBackground();
        switch (mGankList.get(position).getType()) {
            case TYPE_ANDROID:
                p.setColor(mContext.getResources().getColor(R.color.type_android));
                break;
            case TYPE_APP:
                p.setColor(mContext.getResources().getColor(R.color.type_app));
                break;
            case TYPE_EXPAND:
                p.setColor(mContext.getResources().getColor(R.color.type_expand));
                break;
            case TYPE_FOREEND:
                p.setColor(mContext.getResources().getColor(R.color.type_foreend));
                break;
            case TYPE_WELFARE:
                p.setColor(mContext.getResources().getColor(R.color.type_welfare));
                break;
            case TYPE_REST:
                p.setColor(mContext.getResources().getColor(R.color.type_rest));
                break;
            case TYPE_RECOMMEND:
                p.setColor(mContext.getResources().getColor(R.color.type_recommend));
                break;
            case TYPE_IOS:
                p.setColor(mContext.getResources().getColor(R.color.type_iOS));
                break;
            default:
                p.setColor(mContext.getResources().getColor(R.color.colorPrimary));
                break;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.history_item_desc)
        TextView historyItemDesc;
        @BindView(R.id.history_item_who)
        TextView historyItemWho;
        @BindView(R.id.history_item_source)
        TextView historyItemSource;
        @BindView(R.id.history_item_type)
        TextView historyItemType;
        @BindView(R.id.history_item_date)
        TextView historyItemDate;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



}
