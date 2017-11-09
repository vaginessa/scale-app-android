package tmg.mileageleft

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, SettingsDialog.Done {

    val startSettingsDialog: SettingsDialog = SettingsDialog()
    val endSettingsDialog: SettingsDialog = SettingsDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_home_btnStart.setOnClickListener(this)
        activity_home_btnEnd.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        done();
    }

    override fun onClick(v: View?) {
        if (v == activity_home_btnStart) {
            startSettingsDialog.start(this, this).show()
        }
        if (v == activity_home_btnEnd) {
            endSettingsDialog.end(this, this).show()
        }
    }

    override fun done() {
        val value: Int = (Prefs.getEndVal(applicationContext) - Prefs.getStartVal(applicationContext))
        val sdf: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val start: Date = sdf.parse(Prefs.getStartDate(applicationContext))
        val end: Date = sdf.parse(Prefs.getEndDate(applicationContext))
        val now: Date = Date()
        if (end.time - start.time == 0L) {
            activity_home_tvValue.text = "9"
        }
        else {
            val days: Long = ((now.time - start.time) * value) / (end.time - start.time)
            activity_home_tvValue.text = days.toString()
        }
    }

}
