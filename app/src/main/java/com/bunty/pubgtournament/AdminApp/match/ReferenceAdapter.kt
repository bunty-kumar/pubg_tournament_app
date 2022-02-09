package com.bunty.pubgtournament.AdminApp.match

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bunty.pubgtournament.R

class ReferenceAdapter(var referenceList:ArrayList<ReferenceModel>):
    RecyclerView.Adapter<ReferenceAdapter.ReferenceViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferenceViewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.reference_itemview,parent,false)
        return ReferenceViewholder(view)
    }

    override fun onBindViewHolder(holder: ReferenceViewholder, position: Int) {
       var itemPosition = referenceList[position]
        holder.refTxt.text = itemPosition.referId
    }

    override fun getItemCount(): Int {
        return referenceList.size
    }

    class ReferenceViewholder(view: View):RecyclerView.ViewHolder(view){
        var refTxt:TextView = view.findViewById(R.id.refTxt)
    }

}