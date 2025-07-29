package com.example.terraceties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class results_page : Fragment() {

    private var allResults = Bundle()
    private var teamResults = Bundle()

    private lateinit var resList:ArrayList<resFixt>
    private lateinit var resAdapter: resFixtAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        allResults = arguments?.getBundle("ALL_RESULTS")!!
        teamResults = arguments?.getBundle("TEAM_RESULTS")!!


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_results_page, container, false)

        val activ:MainActivity = activity as MainActivity
        val inter:MyInterface = activity as MyInterface

        resList = ArrayList()

        val recyclerView = view.findViewById<RecyclerView>(R.id.resRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(activ, 1, GridLayoutManager.VERTICAL, false)

        resAdapter = resFixtAdapter(resList)
        recyclerView.adapter = resAdapter

        val allResultsBut = view.findViewById<AppCompatButton>(R.id.allResults)
        allResultsBut.setOnClickListener {
            clearRecycler()
            accessArray(allResults, activ)
        }

        val teamResultBut = view.findViewById<AppCompatButton>(R.id.teamResults)
        teamResultBut.setOnClickListener {
            clearRecycler()
            accessArray(teamResults,activ)
        }

        val bckBut = view.findViewById<AppCompatButton>(R.id.resBackButton)
        bckBut.setOnClickListener {
            inter.setFragFromFrag("homePage")
        }

        resAdapter.notifyDataSetChanged()


        return view
    }

    private fun clearRecycler(){
        resList.clear()
        resAdapter.notifyDataSetChanged()
    }




    private fun accessArray(bundle:Bundle, activ: MainActivity){
        val counter = bundle.getInt("BUNDLE_SIZE")

        for(i in 0..counter){
            val temp = bundle.getStringArray(i.toString())
            if(temp != null) {
                var date = (temp[6])
                val date1 = (date[8] + "" + date[9] + "." + date[5] + "" + date[6] + "." + date[0]
                        + "" + date[1] + "" + date[2] + "" + date[3])
                val dataItem = resFixt(
                    temp[1], //round
                    date1, //date
                    temp[2], //home
                    temp[3], //away
                    temp[7], //venue
                    temp[4], //goalsH
                    temp[5] //goalsA
                )
                resList.plusAssign(dataItem)
            }
        }

        val dataItem = resFixt(
            "N/A",
            "",
            "",
            "",
            "",
            "N/A",
            "N/A"
        )
        resList.plusAssign(dataItem)
        resAdapter.notifyDataSetChanged()
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
//                view?.findViewById<LinearLayout>(R.id.ResultsLL)?.addView(infoText)
//
//                //something in the loop cutting off the last element being printed to the screen
//            }
//        }
//        val spacer = TextView(context)
//        spacer.text = "\n \n"
//
//        view?.findViewById<LinearLayout>(R.id.ResultsLL)?.addView(spacer)
//    }
//
//    private fun clearScreen(){
//        val scrlView = view?.findViewById<ScrollView>(R.id.ResultsScrollView)
//        val prevLayout = view?.findViewById<LinearLayout>(R.id.ResultsLL)
//        scrlView?.removeView(prevLayout)
//
//
//        val layout = LinearLayout(context)
//        layout.id = R.id.ResultsLL
//        layout.orientation = LinearLayout.VERTICAL
//        val params = RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.MATCH_PARENT,
//            RelativeLayout.LayoutParams.MATCH_PARENT
//        )
//        layout.layoutParams = params
//
//        scrlView?.addView(layout)
//
//    }

}