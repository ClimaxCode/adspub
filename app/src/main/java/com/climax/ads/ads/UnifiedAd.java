package com.climax.ads.ads;

import com.google.android.gms.ads.nativead.NativeAd;

public class UnifiedAd {
    private boolean isLoaded = false;
    private NativeAd nativeAd;
    private boolean isBinded = false;
    private String viewtype="normal";
    private String test;
    public boolean isBinded() {
        return isBinded;
    }

    public void setBinded(boolean binded) {
        isBinded = binded;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public NativeAd getNativeAd() {
        return nativeAd;
    }

    public void setNativeAd(NativeAd nativeAd) {
        this.nativeAd = nativeAd;
    }

    public String getViewtype() {
        return viewtype;
    }

    public void setViewtype(String viewtype) {
        this.viewtype = viewtype;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
