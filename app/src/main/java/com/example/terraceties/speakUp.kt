package com.example.terraceties

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment


class speakUp : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_speak_up, container, false)

        val activ: MainActivity = activity as MainActivity
        val inter: MyInterface = activity as MyInterface

        val backBut = view.findViewById<AppCompatButton>(R.id.speakUpBackButton)
        val getHlp = view.findViewById<AppCompatButton>(R.id.gethelpButton) // send to external URL
        val samsBut = view.findViewById<AppCompatButton>(R.id.samsButton)
        val ediTxt = view.findViewById<EditText>(R.id.userInput)
        val sndBut = view.findViewById<AppCompatButton>(R.id.sendButton) // give toast output

        backBut.setOnClickListener {
            inter.setFragFromFrag("homePage")
        }

        getHlp.setOnClickListener {
            val url = "https://www.mentalhealth.org.uk/explore-mental-health/get-help"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
            //activ.finish()
        }

        samsBut.setOnClickListener {
            val url = "https://www.samaritans.org/how-we-can-help/contact-samaritan/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
            //activ.finish()
        }

        sndBut.setOnClickListener {
            ediTxt.text = null
            Toast.makeText(activ, "Response Submitted...", Toast.LENGTH_LONG)

        }




        return view
    }

}