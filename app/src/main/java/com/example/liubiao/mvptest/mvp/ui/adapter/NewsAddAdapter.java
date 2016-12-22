package com.example.liubiao.mvptest.mvp.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liubiao.mvptest.App;
import com.example.liubiao.mvptest.event.NewsAddDBEvent;
import com.example.liubiao.mvptest.mvp.ui.adapter.base.BaseRecycleAdapter;
import com.example.liubiao.mvptest.utils.RxBus;
import com.example.liubiao.mvptest.widget.ItemDragHelperCallback;
import com.example.liubiao.mvptext.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.mvp.greendao.NewsChannelTable;

/**
 * Created by liubiao on 2016/12/12.
 */

public class NewsAddAdapter extends BaseRecycleAdapter<NewsChannelTable> implements ItemDragHelperCallback.OnItemMoveListener {
    private final int TYPE_FIX = 0;
    private final int TYPE_OTHER = 1;
    private ItemClickListener listener;
    private ItemDragHelperCallback onItemDragHelperCallback;

    @Inject
    public NewsAddAdapter() {
        super(null);
    }

    @Override
    public int getItemViewType(int position) {
        if (getList().get(position).getNewsChannelFixed()) {
            return TYPE_FIX;
        } else {
            return TYPE_OTHER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_add, parent, false);
        final TableViewHolder holder = new TableViewHolder(view);
        if (viewType == TYPE_OTHER) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, holder.getLayoutPosition());
                }
            });
        }
        if (onItemDragHelperCallback != null) {
            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int position = holder.getLayoutPosition();
                    if (getList().get(position).getNewsChannelFixed()) {
                        onItemDragHelperCallback.setLongPressEnabled(false);
                    } else {
                        onItemDragHelperCallback.setLongPressEnabled(true);
                    }
                    return false;
                }
            });
        }


        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        NewsChannelTable table = getList().get(position);
        ((TableViewHolder) holder).tv.setText(table.getNewsChannelName());
        if (table.getNewsChannelFixed()) {
            ((TableViewHolder) holder).tv.setTextColor(ContextCompat
                    .getColor(App.getAppContext(), R.color.alpha_05_black));
        }
    }

    public void setOnItemDragHelperCallback(ItemDragHelperCallback onItemDragHelperCallback) {
        this.onItemDragHelperCallback = onItemDragHelperCallback;
    }


    class TableViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;

        public TableViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemclickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(View v, int position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        replaceItem(fromPosition,toPosition);
        //修改数据库
        RxBus.getRxBusInstace().post(new NewsAddDBEvent(fromPosition,toPosition));
        return false;
    }


}
