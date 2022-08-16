package com.example.politicalpreparedness.adapters

import android.content.Intent
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.politicalpreparedness.R
import com.example.politicalpreparedness.databinding.RepresentativeCardBinding
import com.example.politicalpreparedness.models.representative.RepresentativeProfile

class RepresentativeDataAdapter(private val rlist:List<RepresentativeProfile>) :RecyclerView.Adapter<RepresentativeDataAdapter.RepViewHolder>(){

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
        val current = rlist.get(position)



        holder.binding.apply {

            tvCourtType.text = current.office
            tvRepresentativeName.text = current.name
            tvPartyLine.text = current.party


            ivRepresentativeTwitter.setOnClickListener {
                val twitterLink = if(current.twitter!=null){
                    "http://www.twitter.com/".plus(current.twitter)
                }else{ "http://www.twitter.com" }

                val intent = Intent(Intent.ACTION_VIEW,Uri.parse(twitterLink))
                startActivity(holder.binding.root.context,intent,null)
            }

            ivRepresentativeFacebook.setOnClickListener {
                val facebookLink = if(current.facebook!=null){
                    "http://www.facebook.com/".plus(current.facebook)
                }else{ "http://www.facebook.com" }

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookLink))
                startActivity(holder.binding.root.context,intent,null)
            }
            ivRepresentativeWebsite.setOnClickListener {
                val websiite = if(current.website!=null){ current.website
                }else{ "https://www.google.com/search?q=".plus(current.name) }

                val intent = Intent(Intent.ACTION_VIEW,Uri.parse(websiite))
                startActivity(holder.binding.root.context,intent,null)
            }

        }
    }

    override fun getItemCount(): Int {
        return rlist.size
    }
}