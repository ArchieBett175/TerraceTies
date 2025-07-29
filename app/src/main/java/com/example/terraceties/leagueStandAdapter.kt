package com.example.terraceties

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class leagueStandAdapter(private val leagueEntList:
                         ArrayList<leagueEntry>): RecyclerView.Adapter<leagueStandAdapter.leagueStandHolder>(){


    inner class leagueStandHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val pos : TextView = itemView.findViewById(R.id.pos)
        val teamTxt : TextView = itemView.findViewById(R.id.team)
        val playd : TextView = itemView.findViewById(R.id.mp)
        val gd : TextView = itemView.findViewById(R.id.gd)
        val pts : TextView = itemView.findViewById(R.id.pts)
        val card : LinearLayout = itemView.findViewById(R.id.teamCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): leagueStandHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.team_league_card, parent, false)
        return leagueStandHolder(v)
    }
    override fun getItemCount() = leagueEntList.size




    override fun onBindViewHolder(holder: leagueStandHolder, position: Int) {
        holder.pos.text = (leagueEntList[position].pos)
        holder.teamTxt.text = (leagueEntList[position].team)
        holder.playd.text = (leagueEntList[position].mp)
        holder.pts.text = (leagueEntList[position].pts)
        val goalDiff = String.format("%s:%s",(leagueEntList[position].gf),(leagueEntList[position].ga))
        holder.gd.text = goalDiff

        val desc = (leagueEntList[position].desc)

        if(desc == "Promotion"){
            holder.card.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
        }
        if(desc == "Promotion Play-off"){
            holder.card.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.orange))
        }
        if(desc == "Relegation"){
            holder.card.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
        }
        if(desc == "null"){
            holder.card.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.style_grey))
        }

    }

}