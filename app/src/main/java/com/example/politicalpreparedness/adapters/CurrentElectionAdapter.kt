package com.example.politicalpreparedness.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.databinding.RvUpcomingElectionListBinding
import com.example.politicalpreparedness.models.Election

class CurrentElectionAdapter (private val mlist:List<Election>, private val listener:OnItemClickListener): RecyclerView.Adapter<CurrentElectionAdapter.CEViewHolder>() {

    inner class CEViewHolder(view: View): RecyclerView.ViewHolder(view),View.OnClickListener{
        val binding = RvUpcomingElectionListBinding.bind(view)

        init {
            binding.root.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val position:Int = adapterPosition
            if (position!=RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CEViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_upcoming_election_list,parent,false)
        return CEViewHolder(view)


    }

    override fun onBindViewHolder(holder: CEViewHolder, position: Int) {

        val current = mlist[position]
        holder.binding.apply {
            tvTitleCurrentElection.text = current.name.toString()
            tvDateCurrentElection.text = current.electionDay.toString()
        }

    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }


}