package com.example.googlelearn;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:TODO
 * 
 * @author mfx
 * @date 2014年10月23日 上午1:10:12
 */
public class HideBarActivity extends Activity {

	private ArrayList<View> mHideableHeaderViews = new ArrayList<View>();
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidebar_activity_main);

		mListView = (ListView) findViewById(R.id.my_listview);
		if (mListView != null) {
			enableActionBarAutoHide(mListView);
		}
		registerHideableHeaderView(findViewById(R.id.headerbar));

		mListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getData()));
		
		mListView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				autoShowOrHideActionBar(false);
			}
		}, 2000);

	}

	private List<String> getData() {
		List<String> data = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			data.add(String.valueOf(i));
			
		}
		return data;
	}

	protected void registerHideableHeaderView(View hideableHeaderView) {
		if (!mHideableHeaderViews.contains(hideableHeaderView)) {
			mHideableHeaderViews.add(hideableHeaderView);
		}
	}

	protected void deregisterHideableHeaderView(View hideableHeaderView) {
		if (mHideableHeaderViews.contains(hideableHeaderView)) {
			mHideableHeaderViews.remove(hideableHeaderView);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	private static final int HEADER_HIDE_ANIM_DURATION = 300;
	private boolean mActionBarShown = true;
	private int mActionBarAutoHideSensivity = 0;
	private int mActionBarAutoHideMinY = 0;
	private int mActionBarAutoHideSignal = 0;

	private void initActionBarAutoHide() {
		// mActionBarAutoHideMinY =
		// getResources().getDimensionPixelSize(R.dimen.action_bar_auto_hide_min_y);
		// mActionBarAutoHideSensivity = getResources().getDimensionPixelSize(
		// R.dimen.action_bar_auto_hide_sensivity);
	}

	protected void enableActionBarAutoHide(final ListView listView) {
		initActionBarAutoHide();
		listView.setOnScrollListener(new AbsListView.OnScrollListener() {
			final static int ITEMS_THRESHOLD = 3;
			int lastFvi = 0;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				onMainContentScrolled(firstVisibleItem <= ITEMS_THRESHOLD ? 0
						: Integer.MAX_VALUE,
						lastFvi - firstVisibleItem > 0 ? Integer.MIN_VALUE
								: lastFvi == firstVisibleItem ? 0
										: Integer.MAX_VALUE);
				lastFvi = firstVisibleItem;
			}
		});
	}

	private void onMainContentScrolled(int currentY, int deltaY) {
		if (deltaY > mActionBarAutoHideSensivity) {
			deltaY = mActionBarAutoHideSensivity;
		} else if (deltaY < -mActionBarAutoHideSensivity) {
			deltaY = -mActionBarAutoHideSensivity;
		}

		if (Math.signum(deltaY) * Math.signum(mActionBarAutoHideSignal) < 0) {
			// deltaY is a motion opposite to the accumulated signal, so reset
			// signal
			mActionBarAutoHideSignal = deltaY;
		} else {
			// add to accumulated signal
			mActionBarAutoHideSignal += deltaY;
		}

		boolean shouldShow = currentY < mActionBarAutoHideMinY
				|| (mActionBarAutoHideSignal <= -mActionBarAutoHideSensivity);
		autoShowOrHideActionBar(shouldShow);
	}

	protected void autoShowOrHideActionBar(boolean show) {
//		if (show == mActionBarShown) {
//			return;
//		}
//		mActionBarShown = show;
		onActionBarAutoShowOrHide(show);
	}

	protected void onActionBarAutoShowOrHide(boolean shown) {
		for (View view : mHideableHeaderViews) {
			if (shown) {
				view.animate().translationY(0).alpha(1)
						.setDuration(HEADER_HIDE_ANIM_DURATION)
						.setInterpolator(new DecelerateInterpolator());
			} else {
				view.animate().translationY(-view.getBottom()).alpha(0)
						.setDuration(HEADER_HIDE_ANIM_DURATION)
						.setInterpolator(new DecelerateInterpolator());
			}
		}
	}

}
