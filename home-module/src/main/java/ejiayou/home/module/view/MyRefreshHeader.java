package ejiayou.home.module.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

import ejiayou.home.module.R;

public class MyRefreshHeader extends LinearLayout implements RefreshHeader {

    private ImageView mImage;
    private ImageView gif;
    private Context context;
    private String mHomeRefreshGif;

    public MyRefreshHeader(Context context) {
        super(context);
        initView(context);
    }

    public MyRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.widget_custom_refresh_header, this);
        mImage = view.findViewById(R.id.iv_refresh_header);
        gif = view.findViewById(R.id.gif_refresh_header);
        if(!TextUtils.isEmpty(mHomeRefreshGif)) {
            Glide.with(context).asGif().load(mHomeRefreshGif).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(gif);
        } else {
            Glide.with(context).asGif().load(R.drawable.refresh_loading).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(gif);
        }
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        // 下拉的百分比小于100%时，不断调用 setScale 方法改变图片大小
        if (percent < 1) {
            mImage.setScaleX(percent);
            mImage.setScaleY(percent);
        }
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
//        gif.setVisibility(View.VISIBLE);
//        mImage.setVisibility(View.VISIBLE);
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case PullDownToRefresh: //下拉刷新开始。正在下拉还没松手时调用
                //每次重新下拉时，将图片资源重置为小人的大脑袋
//                mImage.setImageResource(R.drawable.refresh_icon);
                if(!((Activity)context).isFinishing()) {
                    if(!TextUtils.isEmpty(mHomeRefreshGif)) {
                        Glide.with(context).asGif().load(mHomeRefreshGif).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(gif);
                    } else {
                        Glide.with(context).asGif().load(R.drawable.refresh_loading).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(gif);
                    }
                }
                break;
            case Refreshing: //正在刷新。只调用一次
                //状态切换为正在刷新状态时，设置图片资源为小人卖萌的动画并开始执行
                mImage.setVisibility(View.GONE);
                gif.setVisibility(View.VISIBLE);
                break;
            case ReleaseToRefresh:
                break;
        }
    }

    public void setGifData(String homeRefreshGif) {
        mHomeRefreshGif = homeRefreshGif;
    }
}
