package com.mgtapps.rickmorty.data.repository


import com.mgtapps.rickmorty.common.Resource
import com.mgtapps.rickmorty.data.remote.RickMortyApi
import com.mgtapps.rickmorty.data.remote.dto.GetCharactersResponse
import com.mgtapps.rickmorty.domain.model.Character
import com.mgtapps.rickmorty.domain.repository.CharacterRepository
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private val api : RickMortyApi) : CharacterRepository {
    override suspend fun getCharacters(page: Int): GetCharactersResponse {
        return api.getCharacters(page)
    }

    override suspend fun getCharacterById(characterId: Int): Resource<Character> {
        return api.getCharacter(characterId)
    }


}