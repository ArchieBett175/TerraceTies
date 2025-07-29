package com.example.terraceties

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class chatFragGen : Fragment() {

    private lateinit var msgList: ArrayList<chatMessageClass>
    private lateinit var chatAdapter: chatMessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat_frag_gen, container, false)

        val activ:chatFeature = activity as chatFeature
        val sendButton = view.findViewById<AppCompatButton>(R.id.sendBut)
        val ediTxt = view.findViewById<EditText>(R.id.editTextTextMultiLine)


        msgList = ArrayList()

        val itemModel1 = chatMessageClass(
            "USERNAME",
            "PLACEHOLDER MESSAGE..."
        )
        val itemModel2 = chatMessageClass(
            "BARNETLVR",
            "Whats every1s thoughts on the new assistant manager???"
        )

        msgList.plusAssign(itemModel1)
        msgList.plusAssign(itemModel2)


        val recyclerView = view.findViewById<RecyclerView>(R.id.chatRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(activ,1,GridLayoutManager.VERTICAL,false)

        chatAdapter = chatMessageAdapter(msgList)

        recyclerView.adapter = chatAdapter


        sendButton.setOnClickListener {
            val sharedPreferences = activ.getSharedPreferences("myPref", Context.MODE_PRIVATE)
            val uName = sharedPreferences.getString("USER_PROFILE_1", null)
            if (uName != null){
                addMessageToList(ediTxt.text.toString(),uName, 1)
                hideKeyboard(it, activ)
                ediTxt.text = null
            }else{
                Toast.makeText(activ, "NO VALID USERNAME, Message NOT sent", Toast.LENGTH_LONG).show()
                hideKeyboard(it, activ)
                ediTxt.text = null
            }


        }

        chatAdapter.notifyDataSetChanged()


        // allow the user to send messages into the recycler view in different colour
        // create a proxy response from other users for demo purposes

        return view

    }


    private fun addMessageToList(item:String, uName:String, mes:Int){
        if(item.isNotEmpty()){
            val dataItem = chatMessageClass(
                uName,
                item
            )
            msgList.plusAssign(dataItem)
            chatAdapter.notifyDataSetChanged()
            messageResponse(mes)


        }
    }

    fun hideKeyboard(it: View, activ: chatFeature) {
        try {
            val imm = activ.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // code inspired by video from https://www.youtube.com/watch?v=_omdGBzLuWY
    }


    fun messageResponse(mes:Int){
        val activ:chatFeature = activity as chatFeature
        val sharedPreferences = activ.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val uName = sharedPreferences.getString("USER_PROFILE_1", null)
        if(mes == 1) {
            val responseData = chatMessageClass(
                "John",
                "Yeah i do not totally agree with his style i think he is weak minded however i am looking foward to the approach he will bring"
            )
            msgList.plusAssign(responseData)
            chatAdapter.notifyDataSetChanged()

            val responseDate2 = chatMessageClass(
                "BARNETLVR",
                String.format("Hey %s , How are we???", uName)
            )
            msgList.plusAssign(responseDate2)
            chatAdapter.notifyDataSetChanged()
        }
    }

}