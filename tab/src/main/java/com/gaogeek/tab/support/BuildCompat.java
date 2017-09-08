package com.gaogeek.tab.support;

/**
 * Created by gaogeek on 2017/9/8.
 */

import android.os.Build.VERSION;

/**
 * BuildCompat contains additional platform version checking methods for
 * testing compatibility with new features.
 */
public class BuildCompat {
    private BuildCompat() {
    }
    /* Boilerplate for isAtLeast${PLATFORM}:
     * public static boolean isAtLeast*() {
     *     return !"REL".equals(VERSION.CODENAME)
     *             && ("${PLATFORM}".equals(VERSION.CODENAME)
     *                     || VERSION.CODENAME.startsWith("${PLATFORM}MR"));
     * }
     */

    /**
     * Check if the device is running on the Android N release or newer.
     *
     * @return {@code true} if N APIs are available for use
     */
    public static boolean isAtLeastN() {
        return VERSION.SDK_INT >= 24;
    }

    /**
     * Check if the device is running on the Android N MR1 release or newer.
     *
     * @return {@code true} if N MR1 APIs are available for use
     */
    public static boolean isAtLeastNMR1() {
        return VERSION.SDK_INT >= 25;
    }

    /**
     * Check if the device is running on the Android O release or newer.
     *
     * @return {@code true} if O APIs are available for use
     */
    public static boolean isAtLeastO() {
        return !"REL".equals(VERSION.CODENAME)
                && ("O".equals(VERSION.CODENAME) || VERSION.CODENAME.startsWith("OMR"));
    }
}