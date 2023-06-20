package com.mgtapps.rickmorty.domain.use_case

import com.mgtapps.rickmorty.common.Resource
import com.mgtapps.rickmorty.domain.model.Character
import com.mgtapps.rickmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend fun getCharacterById(id: Int): Resource<Character> {
        return repository.getCharacterById(id)
    }


}