package tmg.mileageleft

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jordan on 06/08/2017.
 */
class SettingsDialog : View.OnClickListener {

    var isStart: Boolean = false
    var context: Context? = null

    var sdf: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var cal: Calendar = Calendar.getInstance()
    var btn: Button? = null

    fun start(context: Context, done: Done): AlertDialog {
        this.isStart = true
        return config(context, done)
    }

    fun end(context: Context, done: Done): AlertDialog {
        this.isStart = false
        return config(context, done)
    }

    private fun config(context: Context, done: Done): AlertDialog {
        this.context = context;
        var view: View = LayoutInflater.from(context).inflate(R.layout.dialog_numberdate, null)
        var tvValue: TextView = view.findViewById(R.id.dialog_numberdate_tvValue) as TextView;
        var tvDate: TextView = view.findViewById(R.id.dialog_numberdate_tvDate) as TextView;
        var etValue: EditText = view.findViewById(R.id.dialog_numberdate_etValue) as EditText;
        var btnDate: Button = view.findViewById(R.id.dialog_numberdate_btnDate) as Button;

        tvValue.text = context.getString(if (isStart) (R.string.start_value) else (R.string.end_value))
        tvDate.text = context.getString(if (isStart) (R.string.start_date) else (R.string.end_date))

        if (isStart) {
            etValue.setText(Integer.toString(Prefs.getStartVal(context)))
            btnDate.text = Prefs.getStartDate(context)
            cal.timeInMillis = sdf.parse(Prefs.getStartDate(context)).time
        }
        else {
            etValue.setText(Integer.toString(Prefs.getEndVal(context)))
            btnDate.text = Prefs.getEndDate(context)
            cal.timeInMillis = sdf.parse(Prefs.getEndDate(context)).time
        }

        btnDate.setOnClickListener(this)
        this.btn = btnDate
        updateLabel(this.btn)


        return AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.settings))
                .setView(view)
                .setPositiveButton(R.string.save, DialogInterface.OnClickListener { dialog, which ->
                    if (btnDate.text != "01/01/1970") {
                        try {
                            val value = Integer.parseInt(etValue.text.toString())
                            if (isStart) {
                                Prefs.saveStartValues(context, value, btnDate.text.toString())
                            }
                            else {
                                Prefs.saveEndValues(context, value, btnDate.text.toString())
                            }
                            done.done()
                        } catch (e: NumberFormatException) {
                            e.printStackTrace()
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create()
    }

    fun updateLabel(btn: Button?) {
        btn?.text = sdf.format(cal.time)
    }

    @SuppressLint("WrongConstant")
    override fun onClick(v: View?) {
        DatePickerDialog(this.context, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(btn)
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    interface Done {
        fun done()
    }
}