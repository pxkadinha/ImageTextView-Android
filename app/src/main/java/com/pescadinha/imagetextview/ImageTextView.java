package com.pescadinha.imagetextview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by dengun on 06/09/16.
 */
public class ImageTextView extends TextView {

    private static final String sTAG = "ImageTextView";

    private String mTextString;
    private boolean mFindNextDot;
    private int mPutImageAt;
    private Drawable mDrawable;

    private Resources res = getContext().getResources();

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView);
        mFindNextDot = a.getBoolean(R.styleable.ImageTextView_findNextDot, false);
        mTextString = a.getString(R.styleable.ImageTextView_android_text);
        mDrawable = a.getDrawable(R.styleable.ImageTextView_android_src);
        mPutImageAt = a.getInt(R.styleable.ImageTextView_putImageAt, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getParent() instanceof LinearLayout){
            handleLinear();
        } else if (getParent() instanceof RelativeLayout){
            Log.d(sTAG, "RELATIVE PARENT");
        }
    }

    private void handleLinear() {
        ImageView image = new ImageView(getContext());
        image.setImageDrawable(mDrawable);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        params.setMargins(0, 30, 0, 30);
        image.setLayoutParams(params);
        image.setScaleType(ImageView.ScaleType.CENTER);

        if(mPutImageAt == 0 || mPutImageAt > mTextString.length()){
            ((LinearLayout) getParent()).addView(image);
            return;
        }

        String subString = mTextString.substring(
                mPutImageAt,
                mTextString.length()-1
        );

        Log.d(sTAG, "AFTER " + mPutImageAt);

        if(mFindNextDot){ mPutImageAt += subString.indexOf('.'); }

        Log.d(sTAG, "BEFORE " + mPutImageAt+"");

        SpannableString spannableString = new SpannableString(mTextString);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC)
        , mPutImageAt, mTextString.length(), 0);
        setText(mTextString);






    }

}
