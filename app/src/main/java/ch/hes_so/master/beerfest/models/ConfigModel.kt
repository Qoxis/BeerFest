package ch.hes_so.master.beerfest.models

import android.content.Context
import ch.hes_so.master.beerfest.editAndApply

class ConfigModel(context: Context){
    private val preferences by lazy {
        context.applicationContext.getSharedPreferences(
            PREFERENCE_FILE,
            Context.MODE_PRIVATE
        )
    }

    fun setLeftHandMode(activated: Boolean){
        preferences.editAndApply { putBoolean(LEFT_HAND_ACTIVE, activated) }
    }

    fun isLeftHanded(): Boolean = preferences.getBoolean(LEFT_HAND_ACTIVE, false)

    fun setLastBreweryId(value: Int) {
        preferences.editAndApply { putInt(LAST_BREWERY, value) }
    }

    fun getLastBreweryId(): Int =  preferences.getInt(LAST_BREWERY, -1)


    var preferredLanguage: String?
        get() {
            return preferences.getString(LANG_KEY, null)
        }
        set(value) = preferences.editAndApply { putString(LANG_KEY, value) }

    companion object {
        private const val PREFERENCE_FILE = "beerfest-preferences"
        private const val LANG_KEY = "preferredLanguage"
        private const val LEFT_HAND_ACTIVE = "leftHandActivated"
        private const val LAST_BREWERY = "lastbreweryid"
    }
}