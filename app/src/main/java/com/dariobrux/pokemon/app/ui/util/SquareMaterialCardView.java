package com.dariobrux.pokemon.app.ui.util;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.card.MaterialCardView;

/**
 *
 * Created by Dario Bruzzese on 29/10/2020.
 *
 * This widget is a square MaterialCardView.
 */
public class SquareMaterialCardView extends MaterialCardView {

    public SquareMaterialCardView(Context context) {
        super(context);
    }

    public SquareMaterialCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareMaterialCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}