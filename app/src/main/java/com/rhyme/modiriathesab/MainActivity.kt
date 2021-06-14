package com.rhyme.modiriathesab
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var sharedPreferences: SharedPreferences? = null
    var sharedEditor: SharedPreferences.Editor? = null
    var helper: myDbAdapter? = null
    var fm = supportFragmentManager
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helper = myDbAdapter(context)

        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)


        val tab = tabs.getTabAt(2)
        tab!!.select()

        var addCostFrag = AddCostFragment(totalTv)
        var addIncFrag = AddIncFragment(totalTv)
        var traFrag = TransactionsFragment()

        var ft = fm.beginTransaction()
        ft.add(R.id.items_f, addIncFrag)
        ft.commit()

        tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.text == "هزینه") {
                    totalTv.setText(helper!!.getTotal(sharedPref.getString("mhUsername", "unknown")).toString())
                    val ft1 = fm.beginTransaction()
                    ft1.replace(R.id.items_f, addCostFrag)
                    ft1.commit()
                    overridePendingTransition(0, 0)
                } else if (tab.text == "درآمد") {
                    totalTv.setText(helper!!.getTotal(sharedPref.getString("mhUsername", "unknown")).toString())
                    val ft1 = fm.beginTransaction()
                    ft1.replace(R.id.items_f, addIncFrag)
                    ft1.commit()
                    overridePendingTransition(0, 0)
                } else if (tab.text == "گزارش") {
                    totalTv.setText(helper!!.getTotal(sharedPref.getString("mhUsername", "unknown")).toString())
                    val ft1 = fm.beginTransaction()
                    ft1.replace(R.id.items_f, traFrag)
                    ft1.commit()
                    overridePendingTransition(0, 0)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        totalTv.setText(helper!!.getTotal(sharedPref.getString("mhUsername", "unknown")).toString())
    }

    fun isItFirestTime(): Boolean {
        return if (sharedPreferences!!.getBoolean("firstTime", true)) {
            sharedEditor!!.putBoolean("firstTime", false)
            sharedEditor!!.commit()
            sharedEditor!!.apply()
            true
        } else {
            false
        }
    }

    override fun onResume() {
        super.onResume()
    }

}
