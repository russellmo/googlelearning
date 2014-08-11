package com.example.googlelearn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TrendChartView extends View {

	private Paint mLinePaint;
	private Paint mTextPaint;
	private Paint mBgPaint ;
	
	
	public TrendChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mLinePaint.setColor(Color.BLACK);
		mLinePaint.setStrokeWidth(2.0f);
		mLinePaint.setAntiAlias(true);

		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setColor(Color.BLACK);
		mTextPaint.setStrokeWidth(4.0f);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(20.0f);

		mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBgPaint.setColor(Color.RED);
		
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
	}
}
