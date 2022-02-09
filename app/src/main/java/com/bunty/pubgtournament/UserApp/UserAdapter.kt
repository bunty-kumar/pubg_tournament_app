package com.bunty.pubgtournament.UserApp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bunty.pubgtournament.BuyTickit
import com.bunty.pubgtournament.R
import de.hdodenhof.circleimageview.CircleImageView


class UserAdapter(var userList:ArrayList<UserDataClass>,
                  var mContext: Context
):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.user_match_view,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var deletePosition = userList[position]

        holder.matchDate.text = deletePosition.matchDate
        holder.matchTime.text = deletePosition.matchTime
        holder.uploadDate.text = "upload Date: ${deletePosition.uploadDate}"
        holder.uploadTime.text = "Upload Time: ${deletePosition.uploadTime}"
        holder.referId.text = "Ref Id: ${deletePosition.referId}"
       // holder.slots.text = deletePosition.slots
        holder.matchCharge.text = deletePosition.matchCharge
        holder.reward.text = "Reward: ${deletePosition.reward}"
        holder.matchDuration.text = deletePosition.matchDuration
        holder.matchCategory.text = deletePosition.matchCategory
        holder.roomId.text = "Room Id: ${deletePosition.roomId}"
        holder.roomPass.text = "Room Pass: ${deletePosition.roomPass}"

        Glide.with(mContext).load(deletePosition.imageUrl)
            .into(holder.deleteImage)

        /*holder.deleteBtn.setOnClickListener {
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
        }*/

        holder.matchCardview.setOnClickListener {
            var intent = Intent(mContext,BuyTickit::class.java)
            intent.putExtra("dt",deletePosition.matchDate)
            intent.putExtra("ti",deletePosition.matchTime)
            intent.putExtra("updt",deletePosition.uploadDate)
            intent.putExtra("uptm",deletePosition.uploadTime)
            intent.putExtra("rid",deletePosition.roomId)
            intent.putExtra("rpas",deletePosition.roomPass)
            intent.putExtra("charge",deletePosition.matchCharge)
            intent.putExtra("reward",deletePosition.reward)
            intent.putExtra("refid",deletePosition.referId)
            intent.putExtra("category",deletePosition.matchCategory)
            intent.putExtra("duration",deletePosition.matchDuration)
            intent.putExtra("image",deletePosition.imageUrl)
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(view:View):RecyclerView.ViewHolder(view){
        var uploadDate: TextView = view.findViewById(R.id.uploadDate)
        var uploadTime: TextView = view.findViewById(R.id.uploadTime)
        var referId: TextView = view.findViewById(R.id.referenceId)
       // var slots: TextView = view.findViewById(R.id.matchSlots)
        var matchCharge: TextView = view.findViewById(R.id.matchCharge)
        var reward: TextView = view.findViewById(R.id.matchReward)
        var matchDuration: TextView = view.findViewById(R.id.matchType)
        var matchCategory: TextView = view.findViewById(R.id.matchCategory)
        var roomId: TextView = view.findViewById(R.id.roomId)
        var roomPass: TextView = view.findViewById(R.id.roomPass)
        var matchDate: TextView = view.findViewById(R.id.matchDate)
        var matchTime: TextView = view.findViewById(R.id.matchTime)
       // var deleteBtn : Button = view.findViewById(R.id.deleteBtn)
        var deleteImage: CircleImageView = view.findViewById(R.id.deleImage)
        var matchCardview:CardView = view.findViewById(R.id.matchCardview)
    }
}