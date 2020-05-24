package ch.hes_so.master.beerfest.models

import android.app.Activity
import android.content.Context
import android.os.Build
import ch.hes_so.master.beerfest.R
import io.reactivex.subjects.PublishSubject
import java.util.*

class LanguageModel(val context: Context, val configModel: ConfigModel) {
    var language: String = ""
        private set(value) {
            field = value
        }

    val locale: Locale
        get() = Locale(language, "CH")

    init {
        // get default language
        val systemLanguageCode: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val locales = context.resources.configuration.locales
            val match = locales.getFirstMatch(ALLOWED_LANGUAGE)
            systemLanguageCode =
                if (match != null && ALLOWED_LANGUAGE.contains(match.language.toLowerCase())) {
                    match.language.toLowerCase()
                } else {
                    DEFAULT_LANGUAGE
                }
        } else {
            systemLanguageCode = Companion.validateLanguageCode(
                Locale.getDefault().language.toLowerCase(),
                DEFAULT_LANGUAGE
            )
        }

        // retrieve user language from settings if specified
        language = configModel.preferredLanguage ?: systemLanguageCode
    }

    val languageChangeObservable: PublishSubject<String> by lazy { PublishSubject.create<String>() }

    fun setLanguage(activity: Activity, languageCode: String) {
        // exit when no need to change language
        if (language == languageCode) {
            return
        }

        language = validateLanguageCode(languageCode, DEFAULT_LANGUAGE)
        configModel.preferredLanguage = language

        activity.recreate()

        languageChangeObservable.onNext(languageCode)
    }

    companion object {
        const val FR = "fr"
        const val DE = "de"
        const val DEFAULT_LANGUAGE = FR
        val ALLOWED_LANGUAGE = arrayOf(FR, DE)
        val PRINTABLE_LANG = mapOf(FR to R.string.language_french, DE to R.string.language_german)

        fun validateLanguageCode(languageCode: String, defaultLocale: String) =
            languageCode.takeIf {
                ALLOWED_LANGUAGE.contains(
                    it
                )
            } ?: defaultLocale
    }

}