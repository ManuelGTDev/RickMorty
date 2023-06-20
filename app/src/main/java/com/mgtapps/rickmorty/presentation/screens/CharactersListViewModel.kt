package com.mgtapps.rickmorty.presentation.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgtapps.rickmorty.common.Resource
import com.mgtapps.rickmorty.data.remote.dto.GetCharactersResponse
import com.mgtapps.rickmorty.domain.model.Character
import com.mgtapps.rickmorty.domain.use_case.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {
    private var currentPage = 1
    var isLoading = mutableStateOf(false)

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _getCharactersState = mutableStateOf(GetCharactersState())
    val getCharactersState: State<GetCharactersState> = _getCharactersState

    private val _characterList = MutableStateFlow(listOf<Character>())
    val characterList: StateFlow<List<Character>> = _characterList

    private val _firstLoad = MutableStateFlow(true)
    val firstLoad = _firstLoad.asStateFlow()
    private val _maxPages = MutableStateFlow(0)
    val maxPages = _maxPages.asStateFlow()
    private val _endReached = MutableStateFlow(true)
    val endReached = _endReached.asStateFlow()
    private val _updateContent = MutableStateFlow(true)
    val updateContent = _updateContent.asStateFlow()

    init {
        firstLoad()
    }

    private fun firstLoad() {
        getCharactersUseCase(currentPage).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _getCharactersState.value =
                        GetCharactersState(error = result.message ?: "Unexpected Error")
                }

                is Resource.Loading -> {
                    _getCharactersState.value = GetCharactersState(isLoading = true)
                }

                is Resource.Success -> {
                    _getCharactersState.value = GetCharactersState(
                        getCharactersResponse = (result.data
                            ?: GetCharactersResponse())
                    )
                    _characterList.value =
                        _getCharactersState.value.getCharactersResponse.characters
                    _getCharactersState.value.getCharactersResponse.info?.pages?.let { pages ->
                        _maxPages.value = pages
                    }
                    currentPage++
                    _firstLoad.value = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun nextPage() {
        viewModelScope.launch {
            isLoading.value = true
            getCharactersPaginated()
            delay(2000)
            isLoading.value = false
        }
    }

    private fun getCharactersPaginated() {
        getCharactersUseCase(currentPage).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _getCharactersState.value =
                        GetCharactersState(error = result.message ?: "Unexpected Error")
                }

                is Resource.Loading -> {
                    _getCharactersState.value = GetCharactersState(isLoading = true)
                }

                is Resource.Success -> {
                    _getCharactersState.value = GetCharactersState(
                        getCharactersResponse = result.data ?: GetCharactersResponse()
                    )
                    _endReached.value = currentPage == _maxPages.value
                    currentPage++
                    _characterList.value += _getCharactersState.value.getCharactersResponse.characters
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onSearch(query: String) {
        _query.value = query
    }

}