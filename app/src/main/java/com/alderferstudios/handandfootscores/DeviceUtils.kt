package com.alderferstudios.handandfootscores

import android.content.Context

class DeviceUtils {

    companion object {
        fun isTablet(c: Context): Boolean {
            val dm = c.resources.displayMetrics
            val screenWidth = dm.widthPixels / dm.xdpi
            return screenWidth >= 600
        }
    }
}