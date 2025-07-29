package com.example.terraceties

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment


class profilePage : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_page, container, false)
        val myActiv:MainActivity = activity as MainActivity
        val inter:MyInterface = activity as MyInterface


        val img = view.findViewById<ImageView>(R.id.teamPicture)
        val handler = Handler(Looper.getMainLooper())
        val bmp = myActiv.getTeamBmp()

        handler.post{
            img.setImageBitmap(bmp)
        }

        val stadVisited = view.findViewById<TextView>(R.id.groundVisBox)
        stadVisited.text = getStadFromShared(myActiv)

        val dateJnd = view.findViewById<TextView>(R.id.dateJoinedBox)
        dateJnd.text = getDateJoinFromShared(myActiv)


        val currentTeam = view.findViewById<TextView>(R.id.currentTeam)
        currentTeam.text = String.format("CURRENT TEAM\n %s",myActiv.getCurrTeam()).uppercase()

        val uName = view.findViewById<TextView>(R.id.userNameBox)
        uName.text = myActiv.getUserName().uppercase()


        val settingsBut = view.findViewById<AppCompatButton>(R.id.settingBut)
        settingsBut.setOnClickListener {
            inter.setFragFromFrag("sett_page")
        }
        val backBut = view.findViewById<AppCompatButton>(R.id.profileBackButton)
        backBut.setOnClickListener {
            inter.setFragFromFrag("homePage")
        }



        return view
    }

    fun getStadFromShared(activ:MainActivity): String{
        val sharedPreferences = activ.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val stad = sharedPreferences.getString("STAD_VISITED",null)
        if(stad!=null){
            return stad
        }else{
            return ("0")
        }

    }

    fun getDateJoinFromShared(activ: MainActivity):String{
        val sharedPreferences = activ.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val date = sharedPreferences.getString("JOIN_DATE",null)
        if(date!=null){
            return date
        }else{
            return ("DD/MM/YYYY")
        }
    }



}