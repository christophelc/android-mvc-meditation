package cabinetcep.com.meditation

import android.content.Context
import android.content.SharedPreferences
import timber.log.Timber
import android.support.v4.app.Fragment

data class LocalUserPreferences(val ringName: String?, val duration: String?)

open class PreferencesStorage: Fragment() {

    private fun getSharedPreferences() : SharedPreferences? =
        this.context?.getSharedPreferences(
            getString(R.string.preferences_choose_text),
            Context.MODE_PRIVATE
        )

    fun getUserPreferences(): LocalUserPreferences? {
        val sharedPref: SharedPreferences? = getSharedPreferences() ?: return null
        val sRingName = sharedPref?.getString(getString(R.string.preference_ring), "")
        val sDuration = sharedPref?.getString(getString(R.string.preference_duration), "")
        Timber.d("duration: $sDuration")
        Timber.d("ring name: $sRingName")
        return LocalUserPreferences(sRingName, sDuration)
    }

    fun storeRing(ringName: String){
        val sharedPref: SharedPreferences? = getSharedPreferences() ?: return
        with (sharedPref!!.edit()){
            putString(getString(R.string.preference_ring), ringName)
            Timber.d("save $ringName to preferences")
            apply()
        }

    }

    fun storeDuration(duration: String) {
        val sharedPref: SharedPreferences? = getSharedPreferences() ?: return
        with (sharedPref!!.edit()){
            putString(getString(R.string.preference_duration), duration)
            Timber.d("save $duration to preferences")
            apply()
        }

    }
}