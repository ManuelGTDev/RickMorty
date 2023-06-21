package com.mgtapps.rickmorty.domain.use_case

import android.util.Log
import com.mgtapps.rickmorty.common.Resource
import com.mgtapps.rickmorty.data.remote.dto.GetCharacterResponse
import com.mgtapps.rickmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    /*suspend fun getCharacterById(id: Int): Resource<Character> {
        return repository.getCharacterById(id)
    }*/

    operator fun invoke(id : Int): Flow<Resource<GetCharacterResponse>> = flow {
        try {
            emit(Resource.Loading<GetCharacterResponse>())
            val response = repository.getCharacterById(id)
            Log.i("characterResponse", response.toString())
            emit(Resource.Success<GetCharacterResponse>(response))

        }catch (e : HttpException){
            emit(Resource.Error<GetCharacterResponse>(e.localizedMessage ?: "Error"))
        }catch (e : IOException){
            emit(Resource.Error<GetCharacterResponse>("Error 2"))
        }
    }


}