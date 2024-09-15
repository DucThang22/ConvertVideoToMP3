package com.voiceeffects.supereffect.convertvideotomp3.utils

import android.content.Context
import com.voiceeffects.supereffect.convertvideotomp3.common.Constants.KEY_THEME_APP

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
    fun addTheme(context: Context, theme: String) {
        val pre = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = pre.edit()
        editor.putString(KEY_THEME_APP, theme)
        editor.apply()
    }
}