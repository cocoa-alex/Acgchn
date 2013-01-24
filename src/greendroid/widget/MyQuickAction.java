package greendroid.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;

public class MyQuickAction extends QuickAction {
	//set colorfilter
	private final static ColorFilter COLOR_FILTER=new LightingColorFilter(Color.BLACK, Color.BLACK);
	
	public MyQuickAction(Context ctx, int drawableId, int titleId) {
		super(ctx, buildDrawable(ctx, drawableId), titleId);
	}
	public static Drawable buildDrawable(Context ctx,int drawableId) {
		Drawable a=ctx.getResources().getDrawable(drawableId);
		a.setColorFilter(COLOR_FILTER);
		return a;
	}

}
