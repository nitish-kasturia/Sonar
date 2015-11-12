package com.nitishkasturia.sonar.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.nitishkasturia.sonar.R;

public class TextView extends android.widget.TextView {

    Context mContext = null;

    public TextView(Context context) {
        super(context);
        initialize(context, null);
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attributeSet) {
        mContext = context;

        int typefaceId = TypefaceManager.OPENSANS_REGULAR;
        int typefaceStyle = Typeface.NORMAL;

        if (!isInEditMode()) {
            if (attributeSet != null) {
                TypedArray array = mContext.obtainStyledAttributes(attributeSet, R.styleable.TextView);

                for (int i = 0; i < array.length(); i++) {
                    int attr = array.getIndex(i);

                    switch (attr) {
                        case R.styleable.TextView_typeface:
                            typefaceId = array.getInt(i, TypefaceManager.OPENSANS_REGULAR);
                            break;
                    }
                }

                array = mContext.obtainStyledAttributes(attributeSet, new int[]{android.R.attr.textStyle});
                typefaceStyle = array.getInteger(0, typefaceStyle);

                array.recycle();
            }
        }

        setTypeface(TypefaceManager.getTypeface(mContext, typefaceId));
        setTypeface(getTypeface(), typefaceStyle);
    }

    public void setText(String text, int typefaceId) {
        setText(text);
        setTypeface(TypefaceManager.getTypeface(mContext, typefaceId));
    }

    public void setTypeface(int typefaceId) {
        setTypeface(TypefaceManager.getTypeface(mContext, typefaceId));
    }
}