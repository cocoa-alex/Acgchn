package com.acgchn.app.ui;

import com.acgchn.app.AppContext;
import com.acgchn.app.R;
import com.acgchn.app.widget.ScrollLayout;

import greendroid.widget.MyQuickAction;
import greendroid.widget.QuickActionGrid;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;
import android.R.bool;
import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

	private static final String TAG = "TEST";
	private static final int QUICKACTION_ADDRESS = 0;
	private static final int QUICKACTION_EDIT = 1;
	private static final int QUICKACTION_EMAIL = 2;
	private static final int QUICKACTION_STAR = 3;
	private static final int QUCIKACTION_HEART = 4;
	private static final int QUCIKACTION_USER = 5;

	private QuickActionWidget mGrid;
	private ImageView fbSetting;// setting

	private ImageView mHeadLogo;
	private TextView mHeadTitle;

	private RadioButton rbNews;
	private RadioButton rbQuestion;
	private RadioButton rbTweet;
	private RadioButton rbActive;

	private ScrollLayout mScrollLayout;
	private String[] mHeadTitles;
	private RadioButton[] mButtons;
	private int mViewCount;
	private int mCurSel;

	private AppContext appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.initHeadView();
		this.initFootBar();
		this.initPageScroll();
		this.initQuickActionGrid();
	}

	/**
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (mViewCount == 0)
			mViewCount = 4;
		if (mCurSel == 0 && !rbNews.isChecked()) {
			rbNews.setChecked(true);
			rbQuestion.setChecked(false);
			rbTweet.setChecked(false);
			rbActive.setChecked(false);
		}
		boolean a = true;
		mScrollLayout.setIsScroll(a);
	}

	/**
	 * initialize head view
	 */
	private void initHeadView() {
		mHeadLogo = (ImageView) findViewById(R.id.main_head_logo);
		mHeadTitle = (TextView) findViewById(R.id.main_head_title);

	}

	/**
	 * initialize Page Scroll
	 */
	private void initPageScroll() {
		mScrollLayout = (ScrollLayout) findViewById(R.id.main_scrolllayout);

		LinearLayout layout = (LinearLayout) findViewById(R.id.main_footer_linearlayout);
		mHeadTitles = getResources().getStringArray(R.array.head_titles);
		mViewCount = mScrollLayout.getChildCount();

		mButtons = new RadioButton[mViewCount];
		for (int i = 0; i < mViewCount; i++) {
			mButtons[i] = (RadioButton) layout.getChildAt(i * 2);
			mButtons[i].setTag(i);
			mButtons[i].setChecked(false);
			mButtons[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int pos = (Integer) (v.getTag());
					if (mCurSel == pos) {
						switch (pos) {
						case 0:
							break;
						case 1:
							break;
						case 2:
							break;
						case 3:
							break;
						default:
							break;
						}
					}
					mScrollLayout.snapToScreen(pos);
				}
			});
		}

		// set the first screen
		mCurSel = 0;
		mButtons[mCurSel].setChecked(true);
		mScrollLayout
				.SetOnViewChangeListener(new ScrollLayout.OnViewChangeListener() {

					@Override
					public void OnViewChange(int view) {
						switch (view) {
						case 0:
							break;
						case 1:
							break;
						case 2:
							break;
						case 3:
							break;
						default:
							break;
						}
						setCurPoint(view);
					}

				});

	}

	/**
	 * set the footer Point
	 * 
	 * @param index
	 */
	private void setCurPoint(int index) {
		if (index < 0 || index > mViewCount - 1 || mCurSel == index)
			return;

		mButtons[mCurSel].setChecked(false);
		mButtons[index].setChecked(true);
		mHeadTitle.setText(mHeadTitles[index]);
		mCurSel = index;
		if (index == 0) {
			mHeadLogo.setImageResource(R.drawable.frame_logo_news);
		} else if (index == 1) {
			mHeadLogo.setImageResource(R.drawable.frame_logo_post);
		} else if (index == 2) {
			mHeadLogo.setImageResource(R.drawable.frame_logo_tweet);
		} else if (index == 3) {
			mHeadLogo.setImageResource(R.drawable.frame_logo_active);
		}
	}

	/**
	 * initialze foot bar
	 */
	private void initFootBar() {
		rbNews = (RadioButton) findViewById(R.id.main_footer_news);
		rbNews.setChecked(true);
		rbQuestion = (RadioButton) findViewById(R.id.main_footer_news);
		rbTweet = (RadioButton) findViewById(R.id.main_footer_news);
		rbActive = (RadioButton) findViewById(R.id.main_footer_news);
		fbSetting = (ImageView) findViewById(R.id.main_footbar_more);

		fbSetting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mGrid.show(fbSetting, true);
			}
		});
	}

	/**
	 * stop the old menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return false;
	}

	/**
	 * override the keydown
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag = true;
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			mGrid.show(fbSetting, true);
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			ExitApp(this);
		}
		return flag;
	}

	/**
	 * Exit the app
	 * 
	 * @param context
	 */
	private void ExitApp(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.star);
		builder.setTitle(R.string.warning);
		builder.setMessage(R.string.exit);
		// set ok
		builder.setPositiveButton(R.string.sure,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						android.os.Process.killProcess(android.os.Process.myPid());
					}
				});
		// set cancle
		builder.setNegativeButton(R.string.cancle,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}

	/**
	 * initialize the quickactiongrid
	 */
	private void initQuickActionGrid() {
		mGrid = new QuickActionGrid(this);
		mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_exit,
				R.string.edit));
		mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_logout,
				R.string.email));
		mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_myinfo,
				R.string.heart));
		mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_search,
				R.string.star));
		mGrid.addQuickAction(new MyQuickAction(this,
				R.drawable.ic_menu_setting, R.string.user));
		mGrid.addQuickAction(new MyQuickAction(this,
				R.drawable.ic_menu_software, R.string.address));

		mGrid.setOnQuickActionClickListener(mActionClickListener);
	}

	/**
	 * set the items listener
	 */
	private OnQuickActionClickListener mActionClickListener = new OnQuickActionClickListener() {

		@Override
		public void onQuickActionClicked(QuickActionWidget widget, int position) {
			// TODO Auto-generated method stub
			switch (position) {
			case QUCIKACTION_HEART:
				Toast.makeText(MainActivity.this, "Click Heart",
						Toast.LENGTH_SHORT).show();
				break;
			case QUCIKACTION_USER:
				Toast.makeText(MainActivity.this, "Click User",
						Toast.LENGTH_SHORT).show();
				break;
			case QUICKACTION_ADDRESS:
				Toast.makeText(MainActivity.this, "Click Address",
						Toast.LENGTH_SHORT).show();
				break;
			case QUICKACTION_EDIT:
				Toast.makeText(MainActivity.this, "Click Edit",
						Toast.LENGTH_SHORT).show();
				break;
			case QUICKACTION_EMAIL:
				Toast.makeText(MainActivity.this, "Click Mail",
						Toast.LENGTH_SHORT).show();
				break;
			case QUICKACTION_STAR:
				Toast.makeText(MainActivity.this, "Click Star",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
}
