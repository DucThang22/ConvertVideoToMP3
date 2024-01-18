package com.voiceeffects.supereffect.convertvideotomp3.utils

import android.content.Context

object SharePrefUtils {
    fun hidePermission(context: Context) {
        val pre = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = pre.edit()
        editor.putBoolean("show_permission", false)
        editor.apply()
    }
    fun showPermission(context: Context): Boolean {
        val pre = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return pre.getBoolean("show_permission", true)
    }
}