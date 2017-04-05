package project.hugbunadarverkefni2;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by svein on 5.4.2017.
 */

class DayFilterView extends View {

    private boolean mShowText;
    private int mTextPos;

    public DayFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DayFilterView,
                0, 0);

        try {
            mShowText = a.getBoolean(R.styleable.DayFilterView_showText, false);
            mTextPos = a.getInteger(R.styleable.DayFilterView_labelPosition, 0);
        } finally {
            a.recycle();
        }
    }
}