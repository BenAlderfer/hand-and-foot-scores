package com.alderferstudios.handandfootscores

import android.content.Context
import android.content.res.Configuration

/**
 * Helper functions for info about device
 *
 * @author Ben Alderfer
 */
class DeviceUtils {

    companion object {
        /**
         * Checks if the current device is a tablet
         */
        fun isTablet(context: Context): Boolean {
            val displayMetrics = context.resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels / displayMetrics.xdpi
            return screenWidth >= 600
        }

        /**
         * Checks if the device is in landscape
         *
         * @return true if in landscape
         */
        fun isLandscape(context: Context): Boolean {
            return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        }
    }
}