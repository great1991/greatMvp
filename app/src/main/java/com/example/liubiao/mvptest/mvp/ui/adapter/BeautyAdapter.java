package com.example.liubiao.mvptest.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifencoder.AnimatedGifEncoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.liubiao.mvptest.App;
import com.example.liubiao.mvptest.mvp.entity.BeautyBean;
import com.example.liubiao.mvptest.mvp.ui.adapter.base.BaseRecycleAdapter;
import com.example.liubiao.mvptest.utils.DimenUtils;
import com.example.liubiao.mvptext.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liubiao on 2016/12/21.
 */

public class BeautyAdapter extends BaseRecycleAdapter<BeautyBean.ResultsEntity> {


    private OnItemClickListern itemClickListern;

    @Inject
    public BeautyAdapter() {
        super(null);
    }

    @Override
    public int getItemViewType(int position) {
        if (isBottomVew(position))
            return TYPE_FOOTER;
        return TYPE_PHOTO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_FOOTER) {
            view = inflate(R.layout.item_footer, parent);
            return new FooterViewHolder(view);
        } else {
            view = inflate(R.layout.item_beauty, parent);
            BeautyViewHolder viewHolder = new BeautyViewHolder(view);
            setEvent(viewHolder);
            return viewHolder;
        }
    }
    public void setEvent(final BeautyViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListern.onItemClick(v,holder.getLayoutPosition());
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof BeautyViewHolder) {
            BeautyBean.ResultsEntity entity = getList().get(position);
            /*不计算大小，根据大小缓存，大小改变重新下载
            Glide.with(App.getAppContext()).load(entity.getUrl()).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.color.image_place_holder)
                    .into(((BeautyViewHolder) holder).photoIv);*/
            //自动计算大小，只缓存一次
            Picasso.with(App.getAppContext()).load(getList().get(position).getUrl())
                    .placeholder(R.color.image_place_holder)
                    .error(R.drawable.ic_load_fail)
                    .into(((BeautyViewHolder) holder).photoIv);

        }
    }




    class BeautyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo_iv)
        ImageView photoIv;

        public BeautyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
           /* int screenWidth = DimenUtils.getScreenWidth(App.getAppContext());
            ViewGroup.LayoutParams params = photoIv.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = screenWidth/2;
            params.height =  (int) (200 + Math.random() * 400) ;
            photoIv .setLayoutParams(params);*/
        }
    }
    public interface OnItemClickListern
    {
        void onItemClick(View v, int position);
    }
    public void setOnItemClickListern(OnItemClickListern itemClickListern)
    {
        this.itemClickListern=itemClickListern;
    }
}
