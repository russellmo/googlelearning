package com.example.googlelearn;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.googlelearn.widgt.CheckableFrameLayout;

public class MainActivity extends ActionBarActivity {
	CheckableFrameLayout mAddScheduleButton;
	boolean mStarred = false;
	int a;
	boolean isMo ;
	TextView tv;
	TextView tv3;
	boolean isOk = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_a);
		
        mAddScheduleButton = (CheckableFrameLayout)findViewById(R.id.add_schedule_button);
        mAddScheduleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mStarred = !mStarred;
				   mAddScheduleButton.setChecked(mStarred, true);
			      ImageView iconView = (ImageView) mAddScheduleButton.findViewById(R.id.add_schedule_icon);
			      setOrAnimatePlusCheckIcon(iconView,mStarred, true);
			}
		});
        mAddScheduleButton.setVisibility(View.VISIBLE);
        
        tv = (TextView)findViewById(R.id.tv);
        setYuandian(tv);
        
        findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,HideActivity.class);
				startActivity(intent);
			}
		});
//		setupFloatingWindow();
//
//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}
	
    public void setOrAnimatePlusCheckIcon(final ImageView imageView, boolean isCheck,
            boolean allowAnimate) {
        final int imageResId = isCheck
                ? R.drawable.add_schedule_button_icon_checked
                : R.drawable.add_schedule_button_icon_unchecked;

        if (imageView.getTag() != null) {
            if (imageView.getTag() instanceof Animator) {
                Animator anim = (Animator) imageView.getTag();
                anim.end();
                imageView.setAlpha(1f);
            }
        }

        if (allowAnimate && isCheck) {
            int duration =  getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            Animator outAnimator = ObjectAnimator.ofFloat(imageView, View.ALPHA, 0f);
            outAnimator.setDuration(duration / 2);
            outAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    imageView.setImageResource(imageResId);
                }
            });

            AnimatorSet inAnimator = new AnimatorSet();
            outAnimator.setDuration(duration);
            inAnimator.playTogether(
                    ObjectAnimator.ofFloat(imageView, View.ALPHA, 1f),
                    ObjectAnimator.ofFloat(imageView, View.SCALE_X, 0f, 1f),
                    ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 0f, 1f)
            );

            AnimatorSet set = new AnimatorSet();
            set.playSequentially(outAnimator, inAnimator);
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    imageView.setTag(null);
                }
            });
            imageView.setTag(set);
            set.start();
        } else {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(imageResId);
                }
            });
        }
    }
    
    int mTagColorDotSize;
	
    private void setYuandian(TextView chipView){ 
    	/**TextViewÅÔ±ßÉèÖÃÔ­µã*/
         chipView.setText("Android");
         mTagColorDotSize = getResources().getDimensionPixelSize(R.dimen.tag_color_dot_size);
             ShapeDrawable colorDrawable = new ShapeDrawable(new OvalShape());
             colorDrawable.setIntrinsicWidth(mTagColorDotSize);
             colorDrawable.setIntrinsicHeight(mTagColorDotSize);
             colorDrawable.getPaint().setStyle(Paint.Style.FILL);
             chipView.setCompoundDrawablesWithIntrinsicBounds(colorDrawable,
                     null, null, null);
             colorDrawable.getPaint().setColor(Color.RED);
    }
    
    private void setupFloatingWindow() {
        // configure this Activity as a floating window, dimming the background
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = 200;
        params.height = 400;
        params.alpha = 1;
        params.dimAmount = 0.7f;
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        getWindow().setAttributes(params);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
