package com.example.inbar.memgame;

import android.util.Log;

/**
 * Created by Inbar on 8/15/2016.
 */
public class errorHandler {

    private static boolean log_all = true;
    public errorHandler(String errMsg) {
        if (log_all)
            Log.e("RememberMe", errMsg);
    }
}
