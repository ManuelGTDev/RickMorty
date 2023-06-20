package com.mgtapps.rickmorty.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.mgtapps.rickmorty.domain.model.Character
import com.mgtapps.rickmorty.domain.model.Info

data class GetCharactersResponse(
    @SerializedName("info") var info: Info? = Info(),
    @SerializedName("results") var characters: ArrayList<Character> = arrayListOf()
)
