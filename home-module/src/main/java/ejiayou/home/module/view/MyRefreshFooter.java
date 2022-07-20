package ejiayou.home.module.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

import ejiayou.home.module.R;


public class MyRefreshFooter extends LinearLayout implements RefreshFooter {
    private ImageView mImage;
    private LinearLayout llLoading;
    private TextView tvLoading;
    private Animation mAnim;
    private Context context;

    public MyRefreshFooter(Context context) {
        super(context);
        initView(context);
    }

    public MyRefreshFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyRefreshFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.widget_custom_refresh_footer, this);
        llLoading = view.findViewById(R.id.ll_loading);
        mImage = view.findViewById(R.id.iv_refresh_footer);
        tvLoading = view.findViewById(R.id.tv_loading);
        mAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_round_rotate);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        mAnim.setInterpolator(linearInterpolator);
    }

    public void setLoadRefresh() {
        llLoading.setVisibility(VISIBLE);
        tvLoading.setText("小易努力加载中");
    }

    public void setNotLoadMore() {
        llLoading.setVisibility(GONE);
        tvLoading.setText("没有更多了");

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
        //控制是否稍微上滑动就刷新

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if (mAnim != null && mAnim.hasStarted() && !mAnim.hasEnded()) {
            mAnim.cancel();
            mImage.clearAnimation();
        }
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
            case None:
            case PullUpToLoad:
                if (mAnim != null) {
                    mImage.startAnimation(mAnim);
                }
                break;
            case Loading:

            case LoadFinish:

                break;
            case ReleaseToLoad:
                break;
            default:
        }

    }

    @Override
    public boolean setNoMoreData(boolean b) {
        return false;
    }
}
