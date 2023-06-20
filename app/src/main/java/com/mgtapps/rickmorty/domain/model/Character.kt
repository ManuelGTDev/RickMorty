package com.mgtapps.rickmorty.domain.model

import com.mgtapps.rickmorty.data.remote.dto.character.Location
import com.mgtapps.rickmorty.data.remote.dto.character.Origin

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String
)