package com.ais.mnc.view.motorhome;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.ais.mnc.R;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 27/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class VehicleDetailAnimation implements OnTabChangeListener {
    private static final String TAG = "VDetailAnimation >>>";

    public Context mContext;
    private  static final int ANIMATION_TIME = 240;
    private TabHost mTabHost;
    private View previousView;
    private int currentTab;

    public VehicleDetailAnimation(Context context, TabHost tabhost) {
        this.mContext = context;
        this.mTabHost = tabhost;
        this.previousView = tabhost.getCurrentView();
        this.currentTab = tabhost.getCurrentTab();
    }

    @Override
    public void onTabChanged(String tabId) {
        if (tabId.equals(mContext.getResources().getString(R.string.vdetail_tab1))) {
            Log.d(TAG, "  click tab1111111 ");
        } else if (tabId.equals(mContext.getResources().getString(R.string.vdetail_tab2))){
            Log.d(TAG, "  click tab2222222 ");
        }

        //populate animation
        View currentView = mTabHost.getCurrentView();
        if (mTabHost.getCurrentTab() > currentTab) {
            previousView.setAnimation(outToLeftAnimation());
            currentView.setAnimation(inFromRightAnimation());
        } else {
            previousView.setAnimation(outToRightAnimation());
            currentView.setAnimation(inFromLeftAnimation());
        }

    }

    private Animation inFromLeftAnimation() {
        Animation thisAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f
                );
        return setProperties(thisAnimation);
    }

    private Animation inFromRightAnimation() {
        Animation thisAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f
        );
        return setProperties(thisAnimation);
    }

    private Animation outToLeftAnimation() {
        Animation thisAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f
        );
        return setProperties(thisAnimation);
    }

    private Animation outToRightAnimation() {
        Animation thisAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f
        );
        return setProperties(thisAnimation);
    }

    private Animation setProperties(Animation animation) {
        animation.setDuration((ANIMATION_TIME));
        animation.setInterpolator(new AccelerateInterpolator());
        return animation;
    }
}
