package com.bunty.pubgtournament.AdminApp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bunty.pubgtournament.AdminApp.match.DataClass
import com.bunty.pubgtournament.R
import com.google.firebase.database.FirebaseDatabase

class DeleteMatchAdapter(var deleteMatchList:ArrayList<DataClass>,
                         var mContext:Context)
    :RecyclerView.Adapter<DeleteMatchAdapter.DeleteMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeleteMatchViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.delete_match_itemview,parent,false)
        return DeleteMatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeleteMatchViewHolder, position: Int) {
        var deletePosition = deleteMatchList[position]

        holder.matchDate.text = deletePosition.matchDate
        holder.matchTime.text = deletePosition.matchTime
        holder.uploadDate.text = deletePosition.uploadDate
        holder.uploadTime.text = deletePosition.uploadTime
        holder.referId.text = deletePosition.referId
        holder.slots.text = deletePosition.slots
        holder.matchCharge.text = deletePosition.matchCharge
        holder.reward.text = deletePosition.reward
        holder.matchDuration.text = deletePosition.matchDuration
        holder.matchCategory.text = deletePosition.matchCategory
        holder.roomId.text = deletePosition.roomId
        holder.roomPass.text = deletePosition.roomPass

        Glide.with(mContext).load(deletePosition.imageUrl)
            .into(holder.deleteImage)

        holder.deleteBtn.setOnClickListener {
            val alert = AlertDialog.Builder(mContext)
            alert.setMessage("Are you sure?")
            alert.setCancelable(false)
                .setPositiveButton("Delete") { dialog, which ->
                  var   reference = FirebaseDatabase.getInstance().getReference().child("Matches")
                    reference.child(deletePosition.matchDuration).child(deletePosition.referId).removeValue().addOnSuccessListener {
                        Toast.makeText(mContext, "deleted", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(mContext, "something went wrong", Toast.LENGTH_SHORT).show()
                    }
                    notifyItemRemoved(position)

                }.setNegativeButton("Cancel", null)

            val alert1 = alert.create()
            alert1.show()
        }
    }

    override fun getItemCount(): Int {
        return deleteMatchList.size
    }

    class DeleteMatchViewHolder(view:View):RecyclerView.ViewHolder(view){
        var uploadDate:TextView = view.findViewById(R.id.uploadDate)
        var uploadTime: TextView = view.findViewById(R.id.uploadTime)
        var referId: TextView = view.findViewById(R.id.referenceId)
        var slots: TextView = view.findViewById(R.id.matchSlots)
        var matchCharge: TextView = view.findViewById(R.id.matchCharge)
        var reward: TextView = view.findViewById(R.id.matchReward)
        var matchDuration:TextView = view.findViewById(R.id.matchType)
        var matchCategory:TextView = view.findViewById(R.id.matchCategory)
        var roomId:TextView = view.findViewById(R.id.roomId)
        var roomPass:TextView = view.findViewById(R.id.roomPass)
        var matchDate:TextView = view.findViewById(R.id.matchDate)
        var matchTime: TextView = view.findViewById(R.id.matchTime)
        var deleteBtn :Button = view.findViewById(R.id.deleteBtn)
        var deleteImage:ImageView = view.findViewById(R.id.deleImage)
    }

}