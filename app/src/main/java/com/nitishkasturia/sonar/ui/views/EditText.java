package com.nitishkasturia.sonar.ui.views;

import android.content.Context;
import android.util.AttributeSet;

public class EditText extends android.widget.EditText {

    private Context mContext = null;

    public EditText(Context context) {
        super(context);
        initialize(context);
    }

    public EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public EditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        mContext = context;

        setTypeface(TypefaceManager.getTypeface(mContext, TypefaceManager.OPENSANS_REGULAR));
    }
}