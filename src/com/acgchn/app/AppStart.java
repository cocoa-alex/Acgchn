package com.acgchn.app;

import com.acgchn.app.ui.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class AppStart extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final View view=View.inflate(this,R.layout.activity_start, null);
		setContentView(view);
		
		/**
		 * set the animation /time onListener
		 */
		AlphaAnimation amAlphaAnimation=new AlphaAnimation(0.3f, 1.0f);
		amAlphaAnimation.setDuration(2000);
		view.setAnimation(amAlphaAnimation);
		amAlphaAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {}
			@Override
			public void onAnimationRepeat(Animation arg0) {}
			
			@Override
			public void onAnimationEnd(Animation arg0) {	
				redirectTo();
			}
		});
	}
	
	/*
	 * redirect to the main
	 */
	private void redirectTo(){
		Intent intent=new Intent(this,MainActivity.class);
		startActivity(intent);
		finish();
	}
}
