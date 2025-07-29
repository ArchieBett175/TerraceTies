package com.example.terraceties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class club_form_page : Fragment() {

    private var allFixtures = Bundle()
    private var teamFixtures = Bundle()

    private lateinit var fixtList:ArrayList<resFixt>
    private lateinit var fixtAdapter: resFixtAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        allFixtures = arguments?.getBundle("ALL_FIXTURES")!!
        teamFixtures = arguments?.getBundle("TEAM_FIXTURES")!!


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_club_form_page, container, false)
        val activ : MainActivity = activity as MainActivity
        val inter : MyInterface = activity as MyInterface

        fixtList = ArrayList()

        val recyclerView = view.findViewById<RecyclerView>(R.id.fixtRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(activ, 1, GridLayoutManager.VERTICAL, false)

        fixtAdapter = resFixtAdapter(fixtList)
        recyclerView.adapter = fixtAdapter

        val allFixtureBut = view.findViewById<AppCompatButton>(R.id.allFixt)
        allFixtureBut.setOnClickListener {
            clearRecycler()
            accessArray(allFixtures, activ)
        }

        val teamFixturesBut = view.findViewById<AppCompatButton>(R.id.teamFixt)
        teamFixturesBut.setOnClickListener {
            clearRecycler()
            accessArray(teamFixtures, activ)
        }

        val bckBut = view.findViewById<AppCompatButton>(R.id.fixtBackButton)
        bckBut.setOnClickListener {
            inter.setFragFromFrag("homePage")
        }

        fixtAdapter.notifyDataSetChanged()

        return view
    }

    private fun clearRecycler(){
        fixtList.clear()
        fixtAdapter.notifyDataSetChanged()
    }


    private fun accessArray(bundle:Bundle, activ: MainActivity){
        val counter = bundle.getInt("BUNDLE_SIZE")

        for(i in 0..counter){
            val temp = bundle.getStringArray(i.toString())
            if(temp != null) {
                var date = (temp[4])
                val date1 = (date[8] + "" + date[9] + "." + date[5] + "" + date[6] + "." + date[0]
                        + "" + date[1] + "" + date[2] + "" + date[3])
                val time = (date[11] + "" + date[12] + "" + date[13] + "" + date[14] + "" + date[15])
                val dateTime = String.format("%s\n%s", date1, time)
                val dataItem = resFixt(
                    temp[1], //round
                    dateTime, //date
                    temp[2], //home
                    temp[3], //away
                    temp[5], //venue
                    "N/A", //goalsH
                    "N/A" //goalsA
                )
                fixtList.plusAssign(dataItem)
            }
        }

        val dataItem = resFixt(
            "",
            "",
            "",
            "",
            "",
            "N/A",
            "N/A"
        )
        fixtList.plusAssign(dataItem)
        fixtAdapter.notifyDataSetChanged()
    }

//    private fun accessArray(bundle: Bundle) {
//        val counter = bundle.getInt("BUNDLE_SIZE")
//
//        for (i in 0..counter) {
//            val temp = bundle.getStringArray(i.toString())
//            val sizeT = temp?.size
//            var data = StringBuilder()
//            if (sizeT != null) {
//                for (j in 0..sizeT.toInt() - 1) {
//                    //Log.d("FLOAT", temp[j])
//                    data.append(temp[j])
//                        .append(", ")
//                }
//
//                val dataLen = data.toString().length
//                data.deleteAt(dataLen - 2)
//
//                Log.d("FLOAT", data.toString())
//
//                val infoText = TextView(context)
//                infoText.textSize = 20f
//                infoText.text = data.toString()
//                //infoText.textAlignment = View.TEXT_ALIGNMENT_CENTER
//
//
//                view?.findViewById<LinearLayout>(R.id.FixturesLL)?.addView(infoText)
//
//                //something in the loop cutting off the last element being printed to the screen
//            }
//        }
//        val spacer = TextView(context)
//        spacer.text = "\n \n"
//
//        view?.findViewById<LinearLayout>(R.id.FixturesLL)?.addView(spacer)
//    }
}



