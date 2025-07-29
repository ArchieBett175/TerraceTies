package com.example.terraceties

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import java.text.SimpleDateFormat
import java.util.Date

class profile_setup : AppCompatActivity() {

    var canSave = false
    var spinnerPointer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        setContentView(R.layout.activity_profile_setup)

        val lay = findViewById<LinearLayout>(R.id.layoutPage)
        lay.visibility = INVISIBLE


        if(checkSharedPrefrences() == false){
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }else{
            lay.visibility = VISIBLE

        }

        val spinnerID = findViewById<Spinner>(R.id.teamSpinner)
        val teams = arrayOf("AFC Fylde", "Aldershot Town", "Altrincham", "Barnet", "Boreham Wood", "Bromley", "Chesterfield", "Dagenham & Redbridge", "Dorking Wanderers", "Eastleigh", "Ebbsfleet United", "FC Halifax Town", "Gateshead", "Hartlepool", "Kidderminster Harriers", "Maidenhead", "Oldham", "Oxford City", "Rochdale", "Solihull Moors", "Southend", "Wealdstone", "Woking", "York")
        val arrayAdp = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, teams)
        spinnerID.adapter = arrayAdp
        spinnerID.setSelection(spinnerPointer)
        spinnerID.textAlignment = View.TEXT_ALIGNMENT_CENTER

        spinnerID.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerPointer = position
                canSave = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@profile_setup, "Nothing Selected", Toast.LENGTH_LONG).show()
            }
        }

        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        val current = formatter.format(date)
        saveJoinDate(current)




        val but = findViewById<AppCompatButton>(R.id.settingsSave)
        but.setOnClickListener {
            val name = findViewById<EditText>(R.id.userNameEditText)
            if(name.text.toString() != "" || name.text.toString() != " ") {
                saveSharedPrefrences()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
    fun checkSharedPrefrences():Boolean?{
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val profileSettings = sharedPreferences.getString("USER_PROFILE_6", null)
        return profileSettings == null
    }

    fun saveSharedPrefrences() {
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        val team = spinnerPointer
        val name = findViewById<EditText>(R.id.userNameEditText).text
        val tColours = findViewById<Switch>(R.id.teamColSwitch).isChecked
        val aNoti = findViewById<Switch>(R.id.notiSwitch1).isChecked
        val mNoti = findViewById<Switch>(R.id.notiSwitch2).isChecked
        val gNoti = findViewById<Switch>(R.id.notiSwitch3).isChecked


        val arrayList = arrayListOf(
            team.toString(),
            name.toString(),
            tColours.toString(),
            aNoti.toString(),
            mNoti.toString(),
            gNoti.toString(),
            "true"
        )
        val key = "USER_PROFILE"

        for (i in 0..<arrayList.size) {
            val str = String.format("%s_%s", key, i.toString())
            editor.putString(str, arrayList[i])
            editor.apply()
        }

        Toast.makeText(this, "Prefrences Saved", Toast.LENGTH_LONG).show()
    }

    fun saveJoinDate(date:String){
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("JOIN_DATE", date)
        editor.apply()
    }
}