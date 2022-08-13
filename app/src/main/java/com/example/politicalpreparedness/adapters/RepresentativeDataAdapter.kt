package com.example.politicalpreparedness.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.databinding.RepresentativeCardBinding
import com.example.politicalpreparedness.models.representatives.Official

class RepresentativeDataAdapter(private val rlist:List<Official>) :RecyclerView.Adapter<RepresentativeDataAdapter.RepViewHolder>(){

    inner class RepViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = RepresentativeCardBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.representative_card,parent,false
        )
        return RepViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepViewHolder, position: Int) {
        val current = rlist[position]
        holder.binding.apply {

            tvCourtType.text = current.party
            tvRepresentativeName.text = current.name
            tvPartyLine.text = current.party
        }
    }

    override fun getItemCount(): Int {
        return rlist.size
    }
}