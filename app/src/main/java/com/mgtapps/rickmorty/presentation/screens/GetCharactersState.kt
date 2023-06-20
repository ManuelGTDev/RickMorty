package com.mgtapps.rickmorty.presentation.screens

import com.mgtapps.rickmorty.data.remote.dto.GetCharactersResponse

data class GetCharactersState(

    val error : String = "",
    val getCharactersResponse: GetCharactersResponse = GetCharactersResponse(),
    val isLoading : Boolean = false

)
