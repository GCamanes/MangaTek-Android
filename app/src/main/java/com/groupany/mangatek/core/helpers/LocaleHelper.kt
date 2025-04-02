package com.groupany.mangatek.core.helpers

import android.content.Context
import android.content.res.Configuration
import java.util.Locale
import androidx.core.content.edit
import com.groupany.mangatek.R
import com.groupany.mangatek.core.states.GenericState.Success

object LocaleHelper {
    private const val PREFS_NAME = "app_prefs"
    private const val KEY_LANGUAGE = "app_language"
    private val SUPPORTED_LANGUAGES = listOf("en", "fr")

    val supportedLanguages: List<String>
        get() = SUPPORTED_LANGUAGES

    fun getLocaleFlag(locale: String): Int {
        return when(locale) {
            SUPPORTED_LANGUAGES[1] -> R.drawable.flag_fr
            else -> R.drawable.flag_en
        }
    }

    fun setLocale(context: Context, language: String): Context {
        saveLanguagePreference(context, language)

        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }

    fun getSavedLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANGUAGE, SUPPORTED_LANGUAGES.first())
            ?: SUPPORTED_LANGUAGES.first()
    }

    private fun saveLanguagePreference(context: Context, language: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit() { putString(KEY_LANGUAGE, language) }
    }

    fun getCurrentLocale(context: Context): String {
        return context.resources.configuration.locales[0].language
    }

    fun getDefaultSystemLanguage(): String {
        val systemLanguage = Locale.getDefault().language
        return if (systemLanguage in SUPPORTED_LANGUAGES) {
            systemLanguage
        } else {
            SUPPORTED_LANGUAGES.first()
        }
    }
}