package com.example.googlelearn;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class BarChartView extends View {

	private Paint mLinePaint;
	private Paint mBarPaint;
	private Paint mTextPaint;
	private Paint mBgPaint;
	private float[] mPts;
	private boolean mFirstTime = true;
	float viewWidth, barHeight;
	int barNum = 240; // A股 240,港股270,美股390
	int timeLineHeight = 20;
	float frameStrokeWidth = 1.0f;

	private final String[] TIME_VALUES_CH = { "09:30", "11:30/13:30", "15:00" };
	private final String[] TIME_VALUES_HK = { "09:30", "12:00/13:00", "15:00" };
	private final String[] TIME_VALUES_US = { "09:30", "12:30", "16:00" };

	public final int TYPE_CH = 0;
	public final int TYPE_HK = 1;
	public final int TYPE_US = 2;

	private int type = TYPE_HK;

	public BarChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mLinePaint.setColor(Color.BLACK);
		mLinePaint.setStrokeWidth(frameStrokeWidth);
		mLinePaint.setAntiAlias(true);

		mBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBarPaint.setColor(Color.RED);
		mBarPaint.setStrokeWidth(4.0f);
		mBarPaint.setAntiAlias(true);

		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setColor(Color.BLACK);
		mTextPaint.setStrokeWidth(4.0f);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(20.0f);

		mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		if(type == TYPE_CH){
			mPts = new float[28];
		}else if(type == TYPE_HK){
			mPts = new float[36];
		}else if(type ==TYPE_US){
			mPts = new float[40];
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mFirstTime) {
			viewWidth = getWidth();
			barHeight = getHeight() - timeLineHeight;
		}

		drawBackground(canvas);
//		 drawBarChart(canvas);
		drawTimeLine(canvas);
	}

	/**
	 * 画背景
	 * 
	 * @param canvas
	 */
	private void drawBackground(Canvas canvas) {
		float sectionCount;
		int sectionLineCount;
		float sectionPts[] ;
		if (type == TYPE_CH) {
			sectionCount = 4.0f;
			float sectionWidth = viewWidth / sectionCount;
			int len = ((int) (sectionCount - 1)) * 4;
		   sectionPts = new float[len];
			for (int i = 0; i < sectionCount; i++) {
				int left = (int) (sectionWidth * i);
				int top = 0;
				int right = (int) (sectionWidth * (i + 1));
				int bottom = (int) barHeight;
				Rect r = new Rect(left, top, right, bottom);
				if (i % 2 == 0) {
					mBgPaint.setColor(Color.CYAN);
				} else {
					mBgPaint.setColor(Color.LTGRAY);
				}
				canvas.drawRect(r, mBgPaint);
				if(i<sectionCount-1){
					sectionPts[i * 4] = sectionWidth * (i + 1);
					sectionPts[i * 4 + 1] = 0;
					sectionPts[i * 4 + 2] = sectionWidth * (i + 1);
					sectionPts[i * 4 + 3] = barHeight;
				}
			}
		} else if (type == TYPE_HK || type== TYPE_US) {
			switch (type) {
			case TYPE_HK:
				sectionCount = 6.0f;
				sectionLineCount = 11;
				break;
			case TYPE_US:
				sectionCount = 7.0f;
				sectionLineCount = 13;
				break;
			default:
				return;
			}
			
			float halfWidth = viewWidth / sectionLineCount;
			float sectionWidth = 2*halfWidth;
			int len = ((int) (sectionCount - 1)) * 4;
			sectionPts = new float[len];
			for (int i = 0; i < sectionCount; i++) {
				Rect r;
				if(i==0){
					int left = 0;
					int top = 0;
					int right = (int) halfWidth;
					int bottom = (int) barHeight;
					r = new Rect(left, top, right, bottom);
					mBgPaint.setColor(Color.CYAN);
				}else{
					int left = (int) (sectionWidth * (i-1)+halfWidth);
					int top = 0;
					int right = (int) (sectionWidth* i +halfWidth);
					int bottom = (int) barHeight;
					r = new Rect(left, top, right, bottom);
					if (i % 2 == 0) {
						mBgPaint.setColor(Color.CYAN);
					} else {
						mBgPaint.setColor(Color.LTGRAY);
					}
				}
				canvas.drawRect(r, mBgPaint);
				
				if(i<sectionCount-1){
					if(i==0){
						sectionPts[0] = halfWidth ;
						sectionPts[1] = 0;
						sectionPts[2] = halfWidth ;
						sectionPts[3] = barHeight;
					}else{
						sectionPts[i * 4] =  sectionWidth * i+halfWidth;
						sectionPts[i * 4 + 1] = 0;
						sectionPts[i * 4 + 2] =  sectionWidth * i+halfWidth;
						sectionPts[i * 4 + 3] = barHeight;
					}
				}
			}
		} else {
			return;
		}
		
		float halfStrokeWidth = frameStrokeWidth / 2.0f;
		float points[] = { 0 + halfStrokeWidth, 0 + halfStrokeWidth, viewWidth,
				0 + halfStrokeWidth, 0 + halfStrokeWidth, 0 + halfStrokeWidth,
				0 + halfStrokeWidth, barHeight, viewWidth - halfStrokeWidth, 0,
				viewWidth - halfStrokeWidth, barHeight, 0 + halfStrokeWidth,
				barHeight - halfStrokeWidth, viewWidth,
				barHeight - halfStrokeWidth };

		System.arraycopy(points, 0, mPts, 0, points.length);
		System.arraycopy(sectionPts, 0, mPts, points.length, sectionPts.length);
		canvas.drawLines(mPts, mLinePaint); // 画框
	}

	/**
	 * 画柱状图
	 * 
	 * @param canvas
	 */
	private void drawBarChart(Canvas canvas) {
		float barWidth = (viewWidth * 1.0f / barNum);
		mBarPaint.setStrokeWidth(barWidth);
		for (int i = 0; i < barNum; i++) {
			int top = (int) (barHeight - barHeight * new Random().nextInt(10)
					/ 10.0f);
			float startX = barWidth * i + 0.5f * barWidth;
			float startY = top;
			float stopX = startX;
			float stopY = barHeight;
			canvas.drawLine(startX, startY, stopX, stopY, mBarPaint);
		}
	}

	/**
	 * 画时间
	 * 
	 * @param canvas
	 */
	private void drawTimeLine(Canvas canvas) {
		String[] timeValues = null;
		float timeMiddleX;
		float timeMiddleY;
		if (type == TYPE_CH) {
			timeValues = TIME_VALUES_CH;
			timeMiddleX = (viewWidth - getStringWidth(mTextPaint,timeValues[1])) / 2.0f;
		} else if (type == TYPE_HK) {
			timeValues = TIME_VALUES_HK;
			timeMiddleX = viewWidth*5/11.0f - getStringWidth(mTextPaint,timeValues[1]) / 4.0f;
		} else if (type == TYPE_US) {
			timeValues = TIME_VALUES_US;
			timeMiddleX = viewWidth*6/13.0f - getStringWidth(mTextPaint,timeValues[1]) / 2.0f;
		} else {
			return;
		}

		float timeStartX = 0;
		float timeStartY = barHeight + timeLineHeight;
		float timeStopX = viewWidth - getStringWidth(mTextPaint, timeValues[2]);
		float timeStopY = barHeight + timeLineHeight;
		timeMiddleY = barHeight + timeLineHeight;
		
		canvas.drawText(timeValues[0], timeStartX, timeStartY, mTextPaint);
		canvas.drawText(timeValues[1], timeMiddleX, timeMiddleY, mTextPaint);
		canvas.drawText(timeValues[2], timeStopX, timeStopY, mTextPaint);
	}

	/**
	 * 获取字符串长度
	 * 
	 * @param paint
	 * @param str
	 * @return
	 */
	protected int getStringWidth(Paint paint, String str) {
		int iRet = 0;
		if (str != null && str.length() > 0) {
			int len = str.length();
			float[] widths = new float[len];
			paint.getTextWidths(str, widths);
			for (int j = 0; j < len; j++) {
				iRet += (int) Math.ceil(widths[j]);
			}
		}
		return iRet;
	}

}
