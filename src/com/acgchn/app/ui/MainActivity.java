package com.acgchn.app.ui;

import com.acgchn.app.R;

import greendroid.widget.MyQuickAction;
import greendroid.widget.QuickActionGrid;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

	private static final int QUICKACTION_ADDRESS=0;
	private static final int QUICKACTION_EDIT=1;
	private static final int QUICKACTION_EMAIL=2;
	private static final int QUICKACTION_STAR=3;
	private static final int QUCIKACTION_HEART=4;
	private static final int QUCIKACTION_USER=5;
	
	private QuickActionWidget mGrid;
	private ImageView mImageView;
	
	private RadioButton rbNews;
	private RadioButton rbQuestion;
	private RadioButton rbTweet;
	private RadioButton rbActive;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.initQuickActionGrid();
		this.initFootBar();
	}
	@Override
	protected void onResume(){
		super.onResume();
		rbNews.setChecked(true);
		rbQuestion.setChecked(false);
		rbTweet.setChecked(false);
		rbActive.setChecked(false);
	}
	private void initFootBar(){
		rbNews=(RadioButton) findViewById(R.id.main_footer_news);
		rbNews.setChecked(true);
		rbQuestion=(RadioButton) findViewById(R.id.main_footer_news);
		rbTweet=(RadioButton) findViewById(R.id.main_footer_news);
		rbActive=(RadioButton) findViewById(R.id.main_footer_news);
	}
	//stop the old menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return false;
	}
	
	//override the keydown 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag=true;
		if (keyCode==KeyEvent.KEYCODE_MENU) {
			mImageView=(ImageView)findViewById(R.id.main_footbar_more);
			mGrid.show(mImageView,true);
		}else if (keyCode==KeyEvent.KEYCODE_BACK) {
			ExitApp(this);
		}
		return flag;
	}
	
	private void ExitApp(final Context context){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.star);
		builder.setTitle(R.string.warning);
		builder.setMessage(R.string.exit);
		//set ok 
		builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		//set cancle
		builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
	
	//init the quickactiongrid
	private void initQuickActionGrid(){
		mGrid=new QuickActionGrid(this);
		mGrid.addQuickAction(new MyQuickAction(this,R.drawable.ic_menu_exit, R.string.edit));
		mGrid.addQuickAction(new MyQuickAction(this,R.drawable.ic_menu_logout, R.string.email));
		mGrid.addQuickAction(new MyQuickAction(this,R.drawable.ic_menu_myinfo, R.string.heart));
		mGrid.addQuickAction(new MyQuickAction(this,R.drawable.ic_menu_search, R.string.star));
		mGrid.addQuickAction(new MyQuickAction(this,R.drawable.ic_menu_setting, R.string.user));
		mGrid.addQuickAction(new MyQuickAction(this,R.drawable.ic_menu_software, R.string.address));
		
		mGrid.setOnQuickActionClickListener(mActionClickListener);
	}
	
	//set the items listener
	private OnQuickActionClickListener mActionClickListener=new OnQuickActionClickListener() {
		
		@Override
		public void onQuickActionClicked(QuickActionWidget widget, int position) {
			// TODO Auto-generated method stub
			switch (position) {
			case QUCIKACTION_HEART:
				Toast.makeText(MainActivity.this, "Click Heart", Toast.LENGTH_SHORT).show();
				break;
			case QUCIKACTION_USER:
				Toast.makeText(MainActivity.this, "Click User", Toast.LENGTH_SHORT).show();
				break;
			case QUICKACTION_ADDRESS:
				Toast.makeText(MainActivity.this, "Click Address", Toast.LENGTH_SHORT).show();
				break;
			case QUICKACTION_EDIT:
				Toast.makeText(MainActivity.this, "Click Edit", Toast.LENGTH_SHORT).show();
				break;
			case QUICKACTION_EMAIL:
				Toast.makeText(MainActivity.this, "Click Mail", Toast.LENGTH_SHORT).show();
				break;
			case QUICKACTION_STAR:
				Toast.makeText(MainActivity.this, "Click Star", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
}
