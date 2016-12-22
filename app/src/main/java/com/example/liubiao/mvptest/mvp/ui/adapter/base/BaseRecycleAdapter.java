package com.example.liubiao.mvptest.mvp.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.liubiao.mvptest.mvp.ui.adapter.newsFragAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.ButterKnife;

/**
 * Created by liubiao on 2016/12/6.
 * 1.条目删除添加
 * 2.recycle本身没有条目点击事件，设置条目点击
 * 3.显示或隐藏底部
 */
public class BaseRecycleAdapter<T> extends RecyclerView.Adapter {
    private   List<T> list = new ArrayList<>();
    public int TYPE_FOOTER =0;
    public int TYPE_PHOTO=1;
    public  int TYPE_OTHER=2;
    //public int TYP
    public  boolean isShowFoot;//是否要显示底部
    private OnItemClickListern listern;

    public BaseRecycleAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                params.setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        //判断底部是否显示
        if(list==null)
        {
            return 0;
        }
        if (isShowFoot) {
            return list.size() + 1;
        }
        return list.size();
    }
    public Boolean isBottomVew(int position)
    {
        return position==getItemCount()-1;

    }
    public void addItem(int position,T item)
    {
        list.add(position,item);
        notifyItemInserted(position);
    }
    public void addItem(T item)
    {
        list.add(item);
        notifyDataSetChanged();
    }

    public void addItems(int position,List<T>li)
    {
        list.addAll(position,li);
        notifyItemRangeInserted(position,li.size());
    }
    public void addAllItem(List<T>li)
    {
        list.addAll(li);
       notifyDataSetChanged();
    }
    public void  removeItem(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
    }
    public void replaceItem(int fromPosition,int toPosition)
    {

        Collections.swap(list,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }
   public void showFootView()
   {
        isShowFoot=true;
       notifyDataSetChanged();
   }
    public void hideFootView()
    {
        isShowFoot=false;
        notifyDataSetChanged();
    }
    public View inflate(int layoutId, ViewGroup parent)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId,parent, false);
        return view;
    }

    public void setList(List<T>list)
    {
        this.list=list;
    }

    public List<T> getList()
    {
        return list;
    }
    public class FooterViewHolder extends RecyclerView.ViewHolder
    {

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
    public void setItemClick(final RecyclerView.ViewHolder holder, final boolean isPhoto) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listern.onItemClick(v,holder.getLayoutPosition(),isPhoto);
            }
        });
    }
    public void setOnItemClickListern(OnItemClickListern listern)
    {
        this.listern=listern;
    }
    public interface OnItemClickListern
    {
        void onItemClick(View v, int layoutPosition, boolean isPhoto);
    }

}
