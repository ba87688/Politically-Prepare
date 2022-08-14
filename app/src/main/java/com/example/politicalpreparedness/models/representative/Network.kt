package com.example.politicalpreparedness.models.representative

import android.util.Log
import com.example.politicalpreparedness.models.representatives.Representatives

fun parseRepresentative( rep: Representatives):MutableList<RepresentativeProfile> {
    var repProfileList = mutableListOf<RepresentativeProfile>()

    for (i in 0 until rep.officials.size){
        var title:String =""
        val currentRep = rep.officials.get(i)

        var photo:String? =null
        var twitter:String? =null
        var facebook:String? =null
        var website:String? =null


        //GET PHOTO, FACEBOOK AND TWITTER from REP, if NOT NULL
        if (currentRep.photoUrl!=null) {
            Log.i("TAG", "onCreateView: photo ${currentRep.photoUrl}")
            photo = currentRep.photoUrl
        }
        if (currentRep.channels!=null) {
            val channel = currentRep.channels
            if (channel.isNotEmpty()){
                for (j in channel){
                    if (j.type=="Facebook"){
                        facebook = j.id.toString()
                    }
                    if (j.type=="Twitter"){
                        twitter = j.id.toString()

                    }
                }
            }

        }

        //GET WEBSITE if not null
        if (currentRep.urls !=null) {
            if (currentRep.urls.isNotEmpty()){
                website = currentRep.urls.get(0).toString()
            }
        }



            //GET TITLE of representative from OFFICE
        for (j in 0 until rep.offices.size) {
//            Log.i("Top g", "parseRepresentative: $j")
            val list = rep.offices.get(j).officialIndices


//            Log.i("TIGER1", "parseRepresentative: $list")
            if (list.contains(i)){
//                Log.i("TIGER1", "yea")
//                Log.i("TIGER1", "${rep.offices.get(j).name}")


                title=rep.offices.get(j).name
            }else{
                Log.i("TIGER1", "no")

            }
        }


        val representative = RepresentativeProfile(
            office = title,
            name = rep.officials.get(i).name,
            party = rep.officials.get(i).party,
            photo = photo,
            twitter = twitter,
            facebook = facebook,
            website = website



        )
        repProfileList.add(representative)

    }



    return repProfileList


}