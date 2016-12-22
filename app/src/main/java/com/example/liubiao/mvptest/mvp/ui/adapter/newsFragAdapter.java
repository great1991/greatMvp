package com.example.liubiao.mvptest.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.liubiao.mvptest.App;
import com.example.liubiao.mvptest.mvp.entity.NewsBean;
import com.example.liubiao.mvptest.mvp.ui.adapter.base.BaseRecycleAdapter;
import com.example.liubiao.mvptest.utils.DimenUtils;
import com.example.liubiao.mvptext.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liubiao on 2016/12/8.
 */

public class newsFragAdapter extends BaseRecycleAdapter<NewsBean> {

    @Inject
    public newsFragAdapter() {
        super(null);
    }

    @Override
    public int getItemViewType(int position) {

        if (isShowFoot && isBottomVew(position)) {
            return TYPE_FOOTER;
        }
        NewsBean newsBean = getList().get(position);
        String digest = newsBean.getDigest();
        if (TextUtils.isEmpty(digest)) {
            return TYPE_PHOTO;
        }
        return TYPE_OTHER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_FOOTER) {
            view = inflate(R.layout.item_footer, parent);
            return new FooterViewHolder(view);
        } else if (viewType == TYPE_PHOTO) {
            view = inflate(R.layout.item_news_photo, parent);
            NewsPhotoViewHolder viewHolder = new NewsPhotoViewHolder(view);
            setItemClick(viewHolder, true);
            return viewHolder;
        } else if (viewType == TYPE_OTHER) {
            view = inflate(R.layout.item_news_other, parent);
            NewsOtherViewHolder viewHolder = new NewsOtherViewHolder(view);
            setItemClick(viewHolder, false);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItemViewType(position)==TYPE_OTHER) {
            setItemValues((NewsOtherViewHolder) holder, position);
        } else if (getItemViewType(position)==TYPE_PHOTO) {
            setPhotoItemValues((NewsPhotoViewHolder) holder, position);
        }
    }

    private void setItemValues(NewsOtherViewHolder holder, int position) {
        NewsBean newsBean = getList().get(position);
        String title = newsBean.getTitle();
        String ptime = newsBean.getPtime();
        String digest = newsBean.getDigest();
        String imgSrc = newsBean.getImgsrc();
        holder.tvTitle.setText(title);
        holder.tvDigest.setText(ptime);
        holder.tvDigest.setText(digest);
         /*
                与picasso不同，glide缓存图片时会根据imageview大小，缓存与imageview大小的图片
                那么同一个url因为imageview大小不同重新进行下载图片，不会使用缓存的图片。
                解决办法：
                通过diskCacheStrategy()让Glide既缓存全尺寸又缓存其他尺寸。
                在任意大小ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓
                 */
        Glide.with(App.getAppContext()).load(imgSrc)
                .asBitmap() // gif格式有时会导致整体图片不显示，貌似有冲突
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .error(R.drawable.ic_load_fail)
                .into(holder.iv);
    }

    private void setPhotoItemValues(NewsPhotoViewHolder holder, int position) {
        NewsBean newsBean = getList().get(position);
        String title = newsBean.getTitle();
        String ptime = newsBean.getPtime();

        holder.tv.setText(title);
        holder.tvTime.setText(ptime);
        int PhotoThreeHeight = (int) DimenUtils.dp2dx(90);
        int PhotoTwoHeight = (int) DimenUtils.dp2dx(120);
        int PhotoOneHeight = (int) DimenUtils.dp2dx(150);

        String imgSrcLeft = null;
        String imgSrcMiddle = null;
        String imgSrcRight = null;

        ViewGroup.LayoutParams layoutParams = holder.ll_image.getLayoutParams();

        if (newsBean.getAds() != null) {
            List<NewsBean.AdsBean> adsBeanList = newsBean.getAds();
            int size = adsBeanList.size();
            if (size >= 3) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();
                imgSrcMiddle = adsBeanList.get(1).getImgsrc();
                imgSrcRight = adsBeanList.get(2).getImgsrc();
                layoutParams.height = PhotoThreeHeight;
                holder.tv.setText(adsBeanList.get(0).getTitle());//图集：%1$s
            } else if (size >= 2) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();
                imgSrcMiddle = adsBeanList.get(1).getImgsrc();
                layoutParams.height = PhotoTwoHeight;
            } else if (size >= 1) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();

                layoutParams.height = PhotoOneHeight;
            }
        } else if (newsBean.getImgextra() != null) {
            int size = newsBean.getImgextra().size();
            if (size >= 3) {
                imgSrcLeft = newsBean.getImgextra().get(0).getImgsrc();
                imgSrcMiddle = newsBean.getImgextra().get(1).getImgsrc();
                imgSrcRight = newsBean.getImgextra().get(2).getImgsrc();

                layoutParams.height = PhotoThreeHeight;
            } else if (size >= 2) {
                imgSrcLeft = newsBean.getImgextra().get(0).getImgsrc();
                imgSrcMiddle = newsBean.getImgextra().get(1).getImgsrc();

                layoutParams.height = PhotoTwoHeight;
            } else if (size >= 1) {
                imgSrcLeft = newsBean.getImgextra().get(0).getImgsrc();

                layoutParams.height = PhotoOneHeight;
            }
        } else {
            imgSrcLeft = newsBean.getImgsrc();

            layoutParams.height = PhotoOneHeight;
        }

        setPhotoImageView(holder, imgSrcLeft, imgSrcMiddle, imgSrcRight);
        layoutParams.width = DimenUtils.getScreenWidth(App.getAppContext());
        holder.ll_image.setLayoutParams(layoutParams);

    }
    private void setPhotoImageView(NewsPhotoViewHolder holder, String imgSrcLeft, String imgSrcMiddle, String imgSrcRight) {
        if (imgSrcLeft != null) {
            showAndSetPhoto(holder.ivLeft, imgSrcLeft);
        } else {
            hidePhoto(holder.ivLeft);
        }

        if (imgSrcMiddle != null) {
            showAndSetPhoto(holder.ivMiddle, imgSrcMiddle);
        } else {
            hidePhoto(holder.ivMiddle);
        }

        if (imgSrcRight != null) {
            showAndSetPhoto(holder.ivRight, imgSrcRight);
        } else {
            hidePhoto(holder.ivRight);
        }
    }

    private void showAndSetPhoto(ImageView imageView, final String imgSrc) {
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(App.getAppContext(),imgSrc,Toast.LENGTH_SHORT).show();
            }
        });
        Glide.with(App.getAppContext()).load(imgSrc).asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.image_place_holder)
                .error(R.drawable.ic_load_fail)
                .into(imageView);
    }

    private void hidePhoto(ImageView imageView) {
        imageView.setVisibility(View.GONE);
    }


     class NewsPhotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.ll_image)
        LinearLayout ll_image;
        @BindView(R.id.iv_left)
        ImageView ivLeft;
        @BindView(R.id.iv_middle)
        ImageView ivMiddle;
        @BindView(R.id.iv_right)
        ImageView ivRight;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public NewsPhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

     class NewsOtherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_digest)
        TextView tvDigest;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public NewsOtherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
