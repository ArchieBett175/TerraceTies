package com.example.terraceties

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment


class Loading : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_loading, container, false)
        val inter :MyInterface = activity as MyInterface

        Handler(Looper.getMainLooper()).postDelayed(3000){
            val activ:MainActivity = activity as MainActivity
            if(activ.getHasLoaded()){
                Log.d("TAG3", "LOADED")
                inter.setFragFromFrag("homePage")
            }
            else{
                inter.setFragFromFrag("homePage")
            }
        }


        return view
    }

}