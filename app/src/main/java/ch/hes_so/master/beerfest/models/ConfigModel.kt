package ch.hes_so.master.beerfest.models

import android.content.Context
import android.preference.PreferenceManager
import android.provider.Settings.Global.putString
import ch.hes_so.master.beerfest.editAndApply

class ConfigModel(context: Context){
    private val preferences by lazy {
        context.applicationContext.getSharedPreferences(
            PREFERENCE_FILE,
            Context.MODE_PRIVATE
        )
    }
    private val legacyPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    var preferredLanguage: String?
        get() {
            return preferences.getString(LANG_KEY, null)
        }
        set(value) = preferences.editAndApply { putString(LANG_KEY, value) }

    companion object {
        private const val PREFERENCE_FILE = "beerfest-preferences"
        private const val LANG_KEY = "preferredLanguage"
    }
}