package com.mgtapps.rickmorty.presentation.screens

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgtapps.rickmorty.common.Constants.PARAM_CHARACTER_ID
import com.mgtapps.rickmorty.common.Resource
import com.mgtapps.rickmorty.data.remote.dto.GetCharacterResponse
import com.mgtapps.rickmorty.domain.use_case.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(GetCharacterState())
    val state: State<GetCharacterState> = _state

    init {
        savedStateHandle.get<Int>(PARAM_CHARACTER_ID)?.let { characterId ->
            getCharacter(characterId)
        }
    }
    private fun getCharacter(characterId: Int) {

        getCharacterUseCase(characterId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = GetCharacterState(getCharacterResponse = result.data ?: GetCharacterResponse())
                }

                is Resource.Loading -> {
                    _state.value = GetCharacterState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = GetCharacterState(error = result.message ?: "Error occurred")
                }
            }

        }.launchIn(viewModelScope)

    }

}