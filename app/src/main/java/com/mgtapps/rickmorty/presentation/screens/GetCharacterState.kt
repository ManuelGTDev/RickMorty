package com.mgtapps.rickmorty.presentation.screens

import com.mgtapps.rickmorty.data.remote.dto.GetCharacterResponse

data class GetCharacterState(

    val error : String = "",
    val getCharacterResponse: GetCharacterResponse  = GetCharacterResponse(),
    val isLoading : Boolean = false

)
