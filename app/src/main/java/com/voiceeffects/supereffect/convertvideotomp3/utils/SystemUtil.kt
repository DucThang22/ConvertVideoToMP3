package com.voiceeffects.supereffect.convertvideotomp3.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat
import com.voiceeffects.supereffect.convertvideotomp3.common.Constants
import java.io.File
import java.util.Locale

object SystemUtil {
    private var myLocale: Locale? = null

    // Load lại ngôn ngữ đã lưu và thay đổi chúng
    fun setLocale(context: Context) {
        val language = getPreLanguage(context)
        if (language == "") {
            val config = Configuration()
            val locale = Locale.getDefault()
            Locale.setDefault(locale)
            config.locale = locale
            context.resources
                .updateConfiguration(config, context.resources.displayMetrics)
        } else {
            changeLang(language, context)
        }
    }

    // method phục vụ cho việc thay đổi ngôn ngữ.
    fun changeLang(lang: String?, context: Context) {
        if (lang.equals("", ignoreCase = true)) return
        myLocale = lang?.let { Locale(it) }
        saveLocale(context, lang)
        myLocale?.let { Locale.setDefault(it) }
        val config = Configuration()
        config.locale = myLocale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun saveLocale(context: Context, lang: String?) {
        setPreLanguage(context, lang)
    }

    fun getPreLanguage(mContext: Context?): String {
        val preferences = mContext?.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        return preferences?.getString("KEY_LANGUAGE", "en")?: "en"
    }

    fun setPreLanguage(context: Context, language: String?) {
        if (language == null || language == "") {
            return
        } else {
            val preferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
            preferences.edit().putString("KEY_LANGUAGE", language).apply()
        }
    }

    fun getLanguageApp(context: Context): List<String> {
        val sharedPreferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        // boolean openLanguage = sharedPreferences.getBoolean("openLanguage", false);
        val languages: MutableList<String> = ArrayList()
        languages.add("en")
        languages.add("fr")
        languages.add("pt")
        languages.add("es")
        languages.add("hi")
        return languages
    }

    fun hasStoragePermission(context: Context) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        else
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED


    fun getAllNameInFolder(newName: String, type: String): String {

        var finalName = newName
        var index = 1

        val yourDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).path + "/${Constants.FOLDER_NAME}"
        )
        val newFile = File("${yourDir}/${newName}.${type}")
        if (newFile.exists()) {
            while (true) {
                val newFile = File("${yourDir}/${newName}(${index}).${type}")
                if (newFile.exists()) {
                    index++
                } else {
                    return "${newName}(${index})"
                }
            }
        } else {
            return finalName
        }
    }
}