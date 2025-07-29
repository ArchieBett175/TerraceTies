package com.example.terraceties

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment


class settingsPage : Fragment() {

    public var selectedItem = "Barnet"
    private var spinnerPointer = 3
    private var userName = ""
    private var colChk = false
    private var not1Chk = false
    private var not2Chk = false
    private var not3Chk = false


    var canSave = true

//to-do - create functions to recieve the values of the settings page
    // import the settings page, to the other pages
    // get the team image from the API
    // load team image and change name of team and user on the profile page
    // Check for a change in name if none then do not save a new text



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings_page, container, false)
        val myActiv:MainActivity = activity as MainActivity
        val inter:MyInterface = activity as MyInterface

        if(myActiv.checkSharedPrefrences() == false){
            assignVals(myActiv)
        }


        val backBut = view.findViewById<AppCompatButton>(R.id.settBackButton)
        backBut.setOnClickListener {
            if (canSave){
                inter.setFragFromFrag("profile_page")
            }else{
                Toast.makeText(myActiv, "Pleas save before exiting", Toast.LENGTH_LONG).show()
            }
        }

        val saveBut = view.findViewById<AppCompatButton>(R.id.settingsSave)
        saveBut.setOnClickListener {
            saveSharedPrefrences(myActiv)
            canSave = true
        }
        view.findViewById<AppCompatButton>(R.id.reset).setOnClickListener {
            assignVals(myActiv)
            canSave = true
            Toast.makeText(myActiv, "Your Settings have been reset to their previous state", Toast.LENGTH_LONG).show()
        }

        val spinnerID = view.findViewById<Spinner>(R.id.teamSpinner)
        val teams = arrayOf("AFC Fylde", "Aldershot Town", "Altrincham", "Barnet", "Boreham Wood", "Bromley", "Chesterfield", "Dagenham & Redbridge", "Dorking Wanderers", "Eastleigh", "Ebbsfleet United", "FC Halifax Town", "Gateshead", "Hartlepool", "Kidderminster Harriers", "Maidenhead", "Oldham", "Oxford City", "Rochdale", "Solihull Moors", "Southend", "Wealdstone", "Woking", "York")
        val arrayAdp = ArrayAdapter(myActiv, android.R.layout.simple_spinner_dropdown_item, teams)
        spinnerID.adapter = arrayAdp
        spinnerID.setSelection(spinnerPointer)
        spinnerID.textAlignment = View.TEXT_ALIGNMENT_CENTER

        spinnerID.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = teams[position]
                spinnerPointer = position
                canSave = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(myActiv, "Nothing Selected", Toast.LENGTH_LONG).show()
            }
        }

        val nmBox = view.findViewById<TextView>(R.id.nameBox)
        nmBox.text = String.format("NAME: %s",userName)

        val nameID = view.findViewById<EditText>(R.id.userNameEditText)
        nameID.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                canSave = false
            }
            override fun afterTextChanged(s: Editable?) {
                canSave = false

            }
        })

        val coloursID = view.findViewById<Switch>(R.id.teamColSwitch)
        coloursID.isChecked = colChk
        coloursID.setOnCheckedChangeListener { _, isChecked ->
            hideKeyboard(view, myActiv)
            canSave = false
        }
        val aNoti = view.findViewById<Switch>(R.id.notiSwitch1)
        aNoti.isChecked = not1Chk
        aNoti.setOnCheckedChangeListener { _, isChecked ->
            canSave = false
        }
        val mNoti = view.findViewById<Switch>(R.id.notiSwitch2)
        mNoti.isChecked = not2Chk
        mNoti.setOnCheckedChangeListener { _, isChecked ->
            canSave = false
        }
        val gNoti = view.findViewById<Switch>(R.id.notiSwitch3)
        gNoti.isChecked = not3Chk
        gNoti.setOnCheckedChangeListener { _, isChecked ->
            canSave = false
        }


        return view
    }


    fun saveSharedPrefrences(activ:MainActivity) {
        val sharedPreferences = activ.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        val team = spinnerPointer
        val name = activ.findViewById<EditText>(R.id.userNameEditText).text
        val tColours = activ.findViewById<Switch>(R.id.teamColSwitch).isChecked
        val aNoti = activ.findViewById<Switch>(R.id.notiSwitch1).isChecked
        val mNoti = activ.findViewById<Switch>(R.id.notiSwitch2).isChecked
        val gNoti = activ.findViewById<Switch>(R.id.notiSwitch3).isChecked



        val arrayList = arrayListOf(team.toString(), name.toString(), tColours.toString(), aNoti.toString(), mNoti.toString(), gNoti.toString(), "true")
        val key = "USER_PROFILE"

        for(i in 0..<arrayList.size){
            val str = String.format("%s_%s",key,i.toString())
            editor.putString(str, arrayList[i])
            editor.apply()
        }

        Toast.makeText(activ, "Prefrences Saved", Toast.LENGTH_LONG).show()
        activ.setCurrTeam(selectedItem)
        activ.updateTeamFixtures(selectedItem)
        activ.teamURLtoBitMap(selectedItem,activ.getLogosFromArray())


    }

    fun loadSharedPrefrences(activ: MainActivity):ArrayList<String> {
        val sharedPreferences = activ.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val myArray = ArrayList<String>(7)
        val key = "USER_PROFILE"

        for (i in 0..6) {
            val str = String.format("%s_%s", key, i.toString())
            val nxt = sharedPreferences.getString(str, "false")
            if (nxt != null) {
                myArray.add(nxt)
            } else {
                myArray.add("")
            }
        }

            return myArray
    }

    fun assignVals(activ: MainActivity){
        val prefArray = loadSharedPrefrences(activ)

        Log.d("TAG3", prefArray.toString())

        spinnerPointer = prefArray[0].toInt()
        userName = prefArray[1]
        colChk = prefArray[2].toBoolean()
        not1Chk = prefArray[3].toBoolean()
        not2Chk = prefArray[4].toBoolean()
        not3Chk = prefArray[5].toBoolean()

    }

    fun hideKeyboard(it: View, activ: MainActivity) {
        try {
            val imm = activ.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // code inspired by video from https://www.youtube.com/watch?v=_omdGBzLuWY
    }




}