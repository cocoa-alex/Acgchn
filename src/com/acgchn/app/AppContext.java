package com.acgchn.app;

import android.app.Application;

public class AppContext extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	public boolean isScroll(){
		return true;
		//TODO: set isnt scroll
	}
}
