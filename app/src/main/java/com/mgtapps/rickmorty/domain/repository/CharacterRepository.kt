package com.mgtapps.rickmorty.domain.repository

import com.mgtapps.rickmorty.common.Resource
import com.mgtapps.rickmorty.data.remote.dto.GetCharacterResponse
import com.mgtapps.rickmorty.data.remote.dto.GetCharactersResponse
import com.mgtapps.rickmorty.data.remote.dto.character.CharacterDto
import com.mgtapps.rickmorty.domain.model.Character

interface CharacterRepository {
    suspend fun getCharacters(page:Int): GetCharactersResponse
    suspend fun getCharacterById(characterId: Int): GetCharacterResponse
}