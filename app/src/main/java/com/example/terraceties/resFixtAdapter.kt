package com.example.terraceties

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class resFixtAdapter(private val resFixtList :
                     ArrayList<resFixt>): RecyclerView.Adapter<resFixtAdapter.resFixtHolder>(){
    inner class resFixtHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val round : TextView = itemView.findViewById(R.id.round)
        val date : TextView = itemView.findViewById(R.id.date)
        val home : TextView = itemView.findViewById(R.id.homeTeam)
        val away : TextView = itemView.findViewById(R.id.awayTeam)
        val venue : TextView = itemView.findViewById(R.id.venue)
        val goalH : TextView = itemView.findViewById(R.id.homeTeamScore)
        val goalA : TextView = itemView.findViewById(R.id.awayTeamScore)
        val divider : TextView = itemView.findViewById(R.id.seperator)
        val vs :TextView = itemView.findViewById(R.id.vs)
        val LL: LinearLayout = itemView.findViewById(R.id.scoreLL)
        val MainLL : LinearLayout = itemView.findViewById(R.id.MainLL)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): resFixtHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.res_fixt_item, parent, false)
        return resFixtHolder(v)
    }

    override fun getItemCount() = resFixtList.size


    override fun onBindViewHolder(holder: resFixtHolder, position: Int) {
        holder.round.text = (resFixtList[position].round)
        holder.date.text = (resFixtList[position].date)
        holder.home.text = (resFixtList[position].home)
        holder.away.text = (resFixtList[position].away)
        holder.venue.text = (resFixtList[position].venue)
        holder.goalH.text = (resFixtList[position].goalH)
        holder.goalA.text = (resFixtList[position].goalsA)

        if(resFixtList[position].goalH == "N/A"){
            holder.divider.text = ""
            holder.LL.isActivated = false
            holder.goalA.text = ""
            holder.goalH.text = ""
        }
        if(resFixtList[position].round == ""){
            holder.vs.text = ""
            holder.home.text =""
        }
    }
}