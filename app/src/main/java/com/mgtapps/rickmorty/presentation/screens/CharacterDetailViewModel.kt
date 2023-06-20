package com.mgtapps.rickmorty.presentation.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mgtapps.rickmorty.common.Resource
import com.mgtapps.rickmorty.domain.model.Character
import com.mgtapps.rickmorty.domain.repository.CharacterRepository
import com.mgtapps.rickmorty.domain.use_case.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {



}