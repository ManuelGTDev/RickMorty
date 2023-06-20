package com.mgtapps.rickmorty.domain.use_case

import com.mgtapps.rickmorty.common.Resource
import com.mgtapps.rickmorty.data.remote.dto.GetCharactersResponse
import com.mgtapps.rickmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(page : Int): Flow<Resource<GetCharactersResponse>> = flow {
        try {
            emit(Resource.Loading<GetCharactersResponse>())
            val response = repository.getCharacters(page)
            emit(Resource.Success<GetCharactersResponse>(response))

        }catch (e : HttpException){
            emit(Resource.Error<GetCharactersResponse>(e.localizedMessage ?: "Error"))
        }catch (e : IOException){
            emit(Resource.Error<GetCharactersResponse>("Error 2"))
        }
    }
}