package com.mgtapps.rickmorty.data.remote

import com.mgtapps.rickmorty.common.Resource
import com.mgtapps.rickmorty.data.remote.dto.GetCharactersResponse
import com.mgtapps.rickmorty.data.remote.dto.character.CharacterDto
import com.mgtapps.rickmorty.domain.model.Character
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int): GetCharactersResponse //Lista con todos los personajes

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id:Int): Resource<Character>//Obtener solo un personaje

}