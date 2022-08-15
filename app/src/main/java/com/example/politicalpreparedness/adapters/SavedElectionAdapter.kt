package com.example.politicalpreparedness.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.databinding.RvUpcomingElectionListBinding
import com.example.politicalpreparedness.models.Election

class SavedElectionAdapter (private val mlist:List<Election>, private val listener:OnItemClickListener): RecyclerView.Adapter<SavedElectionAdapter.SEViewHolder>() {

    inner class SEViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{

        val binding = RvUpcomingElectionListBinding.bind(view)


        init {
            binding.root.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val position:Int = adapterPosition
            if (position!= RecyclerView.NO_POSITION){
                listener.onItemClick2(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SEViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_upcoming_election_list,parent,false)
        return SEViewHolder(view)


    }

    override fun onBindViewHolder(holder: SEViewHolder, position: Int) {

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
        fun onItemClick2(position: Int)
    }


}