package com.nitishkasturia.sonar.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

public class TypefaceManager {

    public static final int OPENSANS_REGULAR = 1;
    public static final int OPENSANS_REGULAR_ITALIC = 2;
    public static final int OPENSANS_LIGHT = 3;
    public static final int OPENSANS_LIGHT_ITALIC = 4;
    public static final int OPENSANS_SEMIBOLD = 5;
    public static final int OPENSANS_SEMIBOLD_ITALIC = 6;

    private final static SparseArray<Typeface> sTypefaces = new SparseArray<>(4);

    public static Typeface getTypeface(Context context, int typefaceId) {
        Typeface typeface = sTypefaces.get(typefaceId);

        if (typeface == null) {
            String path = "OpenSans-Regular.ttf";

            switch (typefaceId) {
                case OPENSANS_REGULAR:
                    path = "OpenSans-Regular.ttf";
                    break;
                case OPENSANS_REGULAR_ITALIC:
                    path = "OpenSans-RegularItalic.ttf";
                    break;
                case OPENSANS_LIGHT:
                    path = "OpenSans-Light.ttf";
                    break;
                case OPENSANS_LIGHT_ITALIC:
                    path = "OpenSans-LightItalic.ttf";
                    break;
                case OPENSANS_SEMIBOLD:
                    path = "OpenSans-Semibold.ttf";
                    break;
                case OPENSANS_SEMIBOLD_ITALIC:
                    path = "OpenSans-SemiboldItalic.ttf";
                    break;
            }

            typeface = Typeface.createFromAsset(context.getAssets(), path);
            sTypefaces.put(typefaceId, typeface);
        }

        return typeface;
    }
}