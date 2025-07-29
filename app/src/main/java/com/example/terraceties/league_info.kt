package com.example.terraceties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class league_info : Fragment() {

    private lateinit var leaStandList:ArrayList<leagueEntry>
    private lateinit var lsAdapter: leagueStandAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_league_info, container, false)
        val activ:MainActivity = activity as MainActivity
        val inter:MyInterface = activity as MyInterface

        leaStandList = ArrayList()

        val data = leagueEntry(
            "#",
            "TEAM",
            "MP",
            "GF",
            "GA",
            "PTS",
            "null"
        )
        leaStandList.plusAssign(data)

        val recyclerView = view.findViewById<RecyclerView>(R.id.leagueRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(activ, 1, GridLayoutManager.VERTICAL, false)

        lsAdapter = leagueStandAdapter(leaStandList)
        recyclerView.adapter = lsAdapter

        getLeagueInfo(activ.getLeagueStandings())


        val backButton = view.findViewById<AppCompatButton>(R.id.leagueInfBackButton)
        backButton.setOnClickListener {
            inter.setFragFromFrag("homePage")
        }

        lsAdapter.notifyDataSetChanged()


        return view
    }

    private fun getLeagueInfo(array: Array<Array<String>?>) {
        val arrLen = array.size
        for (i in 0..arrLen - 1) {
            val tempArr = array[i]
            if (tempArr != null) {
                val dataSet = leagueEntry(
                    tempArr[0],//pos,
                    tempArr[1], //team
                    tempArr[2], //MP
                    tempArr[6], //GF
                    tempArr[7], // GA
                    tempArr[9], //pts
                    tempArr[11] //desc
                )
                leaStandList.plusAssign(dataSet)
            }
        }
        lsAdapter.notifyDataSetChanged()
    }

}