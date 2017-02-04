package com.bentudou.westwinglife.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.R;


public class XListViewFooter extends LinearLayout
{
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	public final static int STATE_NOTDATA = 3;
	public final static int STATE_WAIT = 4;
	private Context mContext;

	private View mContentView;
	private View mProgressBar;
	private RelativeLayout rel;
	private TextView mHintView;

	public XListViewFooter(Context context)
	{
		super(context);
		initView(context);
	}

	public XListViewFooter(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView(context);
	}

	public void setState(int state) {
		mHintView.setTextColor(Color.BLACK);
		mProgressBar.setVisibility(View.INVISIBLE);
		rel.setVisibility(View.INVISIBLE);
		switch (state)
		{
			default:
				mHintView.setVisibility(View.VISIBLE);
				mHintView.setText(R.string.xlistview_footer_hint_normal);
				break;
			case STATE_READY:
				mHintView.setVisibility(View.VISIBLE);
				mHintView.setText(R.string.xlistview_footer_hint_ready);
				break;
			case STATE_LOADING:
				mProgressBar.setVisibility(View.VISIBLE);
				rel.setVisibility(View.VISIBLE);
				mHintView.setVisibility(View.INVISIBLE);
				mHintView.setText(R.string.xlistview_header_hint_loading);
				break;
			case STATE_NOTDATA:
				mProgressBar.setVisibility(View.INVISIBLE);
				rel.setVisibility(INVISIBLE);
				mHintView.setText(R.string.xlistview_footer_hint_no_goods);
				break;
			case STATE_WAIT:
				mHintView.setVisibility(View.VISIBLE);
				mHintView.setText(R.string.xlistview_footer_hint_no_goods);
				break;
		}
	}

	public void setBottomMargin(int height)
	{
		if (height < 0)
			return;
		LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	public int getBottomMargin()
	{
		LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * normal status
	 */
	public void normal()
	{
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
		rel.setVisibility(GONE);
	}

	/**
	 * loading status
	 */
	public void loading()
	{
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
		rel.setVisibility(VISIBLE);
	}

	private void initView(Context context)
	{
		mContext = context;
		LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		rel = (RelativeLayout)moreView.findViewById(R.id.xlistview_footer_rel);
		mHintView = (TextView) moreView.findViewById(R.id.xlistview_footer_hint_textview);
	}

}
