package com.jesusmoreira.sparrow.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesUtil {
    private const val PREFERENCE = "PreferenceUtil.PREFERENCE"

    private const val PREFERENCE_OAUTH_TOKEN = "$PREFERENCE.PREFERENCE_OAUTH_TOKEN"
    private const val PREFERENCE_OAUTH_TOKEN_SECRET = "$PREFERENCE.PREFERENCE_OAUTH_TOKEN_SECRET"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
    }

    private fun getEditor(context: Context): SharedPreferences.Editor {
        return getPreferences(context).edit()
    }

    private fun getString(context: Context, key: String): String? {
        return getPreferences(context).getString(key, null)
    }

    private fun putString(context: Context, key: String, value: String): Boolean {
        return getEditor(context).let { editor ->
            editor.putString(key, value)
            editor.commit()
        }
    }

    private fun getInt(context: Context, key: String): Int? {
        return getPreferences(context).getInt(key, -1).let {
            when {
                it < 0 -> null
                else -> it
            }
        }
    }

    private fun putInt(context: Context, key: String, value: Int): Boolean {
        return getEditor(context).let { editor ->
            editor.putInt(key, value)
            editor.commit()
        }
    }

    private fun getLong(context: Context, key: String): Long? {
        return getPreferences(context).getLong(key, -1L).let {
            when {
                it < 0L -> null
                else -> it
            }
        }
    }

    private fun putLong(context: Context, key: String, value: Long): Boolean {
        return getEditor(context).let { editor ->
            editor.putLong(key, value)
            editor.commit()
        }
    }

    fun getOauthToken(context: Context) =
        getPreferences(context).getString(PREFERENCE_OAUTH_TOKEN, "")
    fun setOauthToken(context: Context, value: String) =
        getEditor(context).putString(PREFERENCE_OAUTH_TOKEN, value).apply()

    fun getOauthTokenSecret(context: Context) =
        getPreferences(context).getString(PREFERENCE_OAUTH_TOKEN_SECRET, "")
    fun setOauthTokenSecret(context: Context, value: String) =
        getEditor(context).putString(PREFERENCE_OAUTH_TOKEN_SECRET, value).apply()
}