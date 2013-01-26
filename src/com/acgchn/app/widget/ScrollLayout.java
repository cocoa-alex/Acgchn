package com.acgchn.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

//scroll layout
public class ScrollLayout extends ViewGroup {
	private static final String TAG = "ScollLayout";
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker; // touch listener
	private int mCurScreen;
	private int mDefaultScreen = 0;// init default
	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;
	private static final int SNAP_VELOCITY = 600;
	private int mTouchState = TOUCH_STATE_REST;
	private int mTouchSlop;
	private float mLastMotionX;
	private float mLastMotionY;
	private OnViewChangeListener mOnViewChangeListener;

	// set scroll
	private boolean isScroll = true;

	public void setIsScroll(boolean b) {
		this.isScroll = b;
		Log.v(TAG, "setIsScroll");
	}

	public ScrollLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScroller = new Scroller(context);
		mCurScreen = mDefaultScreen;
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();// shot
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				final int childWidth = childView.getMeasuredWidth();
				childView.layout(childLeft, 0, childWidth + childLeft,
						childView.getMeasuredWidth());
				childLeft += childWidth;
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("run at EXACTLY");
		}
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("only run at EXACTLY");
		}

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}

		scrollTo(mCurScreen * width, 0);
	}

	public void snapToDestination() {
		final int screenWidth = getWidth();
		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		snapToScreen(destScreen);
	}

	public void snapToScreen(int whichScreen){
		if (!isScroll) {
			this.setToScreen(whichScreen);
			return;
		}
		scrollToScreen(whichScreen);
	}

	public void scrollToScreen(int whichScreen) {
		whichScreen=Math.max(0, Math.min(whichScreen, getChildCount()-1));
		if (getScrollX()!=(whichScreen*getWidth())) {
			final int delta=whichScreen*getWidth()-getScrollX();
			mScroller.startScroll(getScrollX(), 0, delta, 0,Math.abs(delta)*1);
			mCurScreen=whichScreen;
			invalidate();
			if (mOnViewChangeListener!=null) {
				mOnViewChangeListener.OnViewChange(mCurScreen);
			}
		}
	}
	public void setToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		mCurScreen = whichScreen;
		scrollTo(whichScreen*getWidth(), 0);
		if (mOnViewChangeListener!=null) {
			mOnViewChangeListener.OnViewChange(mCurScreen);
		}
	}
	public int getCurScreen(){
		return mCurScreen;
	}
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isScroll) {
			return false;
		}

		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}

		mVelocityTracker.addMovement(event);
		final int action = event.getAction();
		final float x = event.getX();
		final float y = event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			mLastMotionX=x;
			mLastMotionY=y;
			break;
		case MotionEvent.ACTION_MOVE:
			int deltax = (int) (mLastMotionX - x);
			int deltay = (int) (mLastMotionY - y);
			if (Math.abs(deltax) < 200 && Math.abs(deltay) > 10)
				break;
			mLastMotionX = x;
			mLastMotionY = y;
			scrollBy(deltax, 0);
			break;
		case MotionEvent.ACTION_UP:
			final VelocityTracker velocityTracker=mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000);
			int veloctiyX=(int) velocityTracker.getXVelocity();
			if (veloctiyX>SNAP_VELOCITY&&mCurScreen>0) {
				snapToScreen(mCurScreen-1);
			}else if (veloctiyX<-SNAP_VELOCITY&&mCurScreen<getChildCount()-1) {
				snapToScreen(mCurScreen+1);
			}else {
				snapToDestination();
			}
			if (mVelocityTracker!=null) {
				mVelocityTracker.recycle();
				mVelocityTracker=null;
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	// 判断滑动
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE)
				&& (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}
		final float x = ev.getX();
		final float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_MOVE:
			Log.v("H", "Move");
			final int xDiff = (int) Math.abs(mLastMotionX - x);
			if (xDiff > mTouchSlop) {
				mTouchState = TOUCH_STATE_SCROLLING;
			}
			break;
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
			mLastMotionY = y;
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
					: TOUCH_STATE_SCROLLING;
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mTouchState = TOUCH_STATE_REST;
			break;
		default:
			break;
		}
		return mTouchState != TOUCH_STATE_REST;
	}

	// set change listener
	public void SetOnViewChangeListener(OnViewChangeListener listener) {
		mOnViewChangeListener = listener;
	}

	public interface OnViewChangeListener {
		public void OnViewChange(int view);
	}
}
