package com.tntadvance.firebasenews.view;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by thana on 3/30/2017 AD.
 */

public class BundleSavedState extends View.BaseSavedState {

    private Bundle bundle = new Bundle();

    public BundleSavedState(Parcel source) {
        super(source);
        bundle = source.readBundle();
    }

    public BundleSavedState(Parcelable superState) {
        super(superState);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeBundle(bundle);
    }

    public Bundle getBundle() {
        return bundle;
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new BundleSavedState(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new BundleSavedState[size];
        }
    };

}