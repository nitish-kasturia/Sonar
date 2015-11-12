package com.nitishkasturia.sonar.ui.views;

import android.content.Context;
import android.util.AttributeSet;

import com.nitishkasturia.sonar.R;

public class FlatButton extends android.widget.Button {

    public FlatButton(Context context) {
        super(context);
        initialize(context);
    }

    public FlatButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    public FlatButton(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        setBackgroundResource(R.drawable.transparent);
        setTextColor(getResources().getColor(R.color.colorPrimary));
        setTypeface(TypefaceManager.getTypeface(context, TypefaceManager.OPENSANS_SEMIBOLD));
    }
}