package com.acgchn.app;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

public class AppManager {

	private static Stack<Activity> activityStack;//activity  stack list
	private static AppManager instance;
	
	private AppManager(){}
	
	/*
	 * single instance
	 */
	public static AppManager getAppManager(){
		if (instance==null) {
			instance=new AppManager();
		}
		return instance;
	}
	
	/*
	 * add a activity to the Stack on the top
	 */
	public void addActivity(Activity activity){
		if (activityStack==null) {
			activityStack=new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	/*
	 * return the current activity the current is the last in the stack
	 */
	public Activity currentActivity() {
		Activity activity=activityStack.lastElement();
		return activity;
	}
	
	/*
	 * finish the current activity
	 */
	public void finishActivity(){
		Activity activity=activityStack.lastElement();
		finishActivity(activity);
	}
	
	/*
	 * finish the specified activity
	 */
	public void finishActivity(Activity activity){
		if (activity!=null) {
			activityStack.remove(activity);
			activity=null;
		}
	}
	
	/*
	 * finish the specified class activity
	 */
	public void finishActivity(Class<?> clas){
		for (Activity activity: activityStack) {
			if (activity.getClass().equals(clas)) {
				finishActivity(activity);
			}
		}
	}
	
	/*
	 * finish all the activity
	 */
	public void finishAllActivity(){
		for (int i = 0,size=activityStack.size(); i < size; i++) {
			if (activityStack.get(i)!=null) {
				activityStack.get(i).finish();
			}
		}
	}
	
	/*
	 * exit the app
	 */
	public void ExitApp(Context context){
		try {
			finishAllActivity();
			ActivityManager activityManager=(ActivityManager) context.getSystemService(Context.ACCOUNT_SERVICE);
			activityManager.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {}
	}
	
}
