package com.nitishkasturia.sonar.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.nitishkasturia.sonar.R;

/**
 * Created by Nitish on 15-08-21.
 */
public class Checkbox extends CheckBox {

    Context mContext;

    public Checkbox(Context context) {
        super(context);
        initialize(context, null);
    }

    public Checkbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public Checkbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attributeSet) {
        this.mContext = context;

        int typefaceId = TypefaceManager.OPENSANS_LIGHT;
        int typefaceStyle = Typeface.NORMAL;

        if (!isInEditMode()) {
            if (attributeSet != null) {
                TypedArray array = mContext.obtainStyledAttributes(attributeSet, R.styleable.Checkbox);

                for (int i = 0; i < array.length(); i++) {
                    int attr = array.getIndex(i);

                    switch (attr) {
                        case R.styleable.Checkbox_typeface:
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
}
