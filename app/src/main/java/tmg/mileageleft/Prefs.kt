package tmg.mileageleft

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by jordan on 06/08/2017.
 */
class Prefs {
    companion object Values {
        var PREFS: String = "MILEAGELEFT"
        var START_VALUE: String = "START_VALUE"
        var START_DATE: String = "START_DATE"
        var END_VALUE: String = "END_VALUE"
        var END_DATE: String = "END_DATE"

        private fun getPrefs(c: Context): SharedPreferences {
            return c.getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE)
        }

        fun saveStartValues(c: Context, value: Int, date: String) {
            this.getPrefs(c).edit().putInt(Prefs.START_VALUE, value).apply()
            this.getPrefs(c).edit().putString(Prefs.START_DATE, date).apply()
        }
        fun saveEndValues(c: Context, value: Int, date: String) {
            this.getPrefs(c).edit().putInt(Prefs.END_VALUE, value).apply()
            this.getPrefs(c).edit().putString(Prefs.END_DATE, date).apply()
        }
        fun getStartVal(c: Context): Int {
            return this.getPrefs(c).getInt(Prefs.START_VALUE, 0)
        }
        fun getStartDate(c: Context): String {
            return this.getPrefs(c).getString(Prefs.START_DATE, "01/01/1970")
        }
        fun getEndVal(c: Context): Int {
            return this.getPrefs(c).getInt(Prefs.END_VALUE, 0)
        }
        fun getEndDate(c: Context): String {
            return this.getPrefs(c).getString(Prefs.END_DATE, "01/01/1970")
        }
    }
}