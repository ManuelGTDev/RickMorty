package com.mgtapps.rickmorty.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.mgtapps.rickmorty.data.remote.dto.character.Location
import com.mgtapps.rickmorty.data.remote.dto.character.Origin
import com.mgtapps.rickmorty.domain.model.Character
import com.mgtapps.rickmorty.domain.model.Info

data class GetCharacterResponse(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String = String(),
    @SerializedName("status") var status: String = String(),
    @SerializedName("species") var species: String = String(),
    @SerializedName("type") var type: String = String(),
    @SerializedName("gender") var gender: String = String(),
    @SerializedName("origin") var origin: Origin = Origin("",""),
    @SerializedName("location") var location: Location = Location("",""),
    @SerializedName("image") var image: String = String(),
    @SerializedName("episode") var episode: List<String> = listOf(),
    @SerializedName("url") var url: String = String(),
    @SerializedName("created") var created: String = String()
    )