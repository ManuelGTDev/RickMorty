package com.mgtapps.rickmorty.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mgtapps.rickmorty.R
import com.mgtapps.rickmorty.domain.model.Character

@Composable
fun CharacterListScreen(
    navController: NavController,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    Surface(
        color = colorResource(id = R.color.blue),
        modifier = Modifier.fillMaxSize()
    ) {
        Column() {
            SpacerComponent(20)
            Image(
                painter = painterResource(id = R.drawable.rickmortytitle),
                contentDescription = "Characters",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(CenterHorizontally)
            )
            SearchBar(modifier = Modifier
                .padding(16.dp), "Search...", {
                    viewModel.onSearch(it)
            })

            CharacterList(navController, viewModel)
        }

    }
}

@Composable
fun SpacerComponent(size: Int) {
    Spacer(modifier = Modifier.height(size.dp))
}

@Composable
fun SearchBar(modifier: Modifier, hint: String = "", onSearch: (String) -> Unit = {}) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(43.dp)
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused
                }
        )
        if (!isHintDisplayed) {
            Text(text = "Search...", modifier = Modifier.padding(10.dp))
        }
    }
}

@Composable
fun CharacterList(
    navController: NavController,
    viewModel: CharacterListViewModel
) {
    val characterList by viewModel.characterList.collectAsStateWithLifecycle()
    val isLoading = viewModel.isLoading
    val listState = rememberLazyGridState()
    val query by viewModel.query.collectAsStateWithLifecycle()

    LazyVerticalGrid(columns = GridCells.Fixed(2), state = listState) {
        itemsIndexed(characterList.filter { it.name.contains(query) }) { index, character ->
            if (listState.isScrolledToTheEnd() && !isLoading.value) {
                viewModel.nextPage()
            }
            CharacterRow(
                character = character,
                navController = navController,
                viewModel = viewModel
            )
        }
        if (isLoading.value) {
            items(2) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.White)
                )
            }
        }
    }
}

fun LazyGridState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1


@Composable
fun CharacterEntry(
    entry: Character,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CharacterListViewModel
) {
    Box(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(Color.White)
            .clickable {
                navController.navigate(
                    "characater_detail_screen/${entry.id}"
                )
            }
    ) {
        Box {
            AsyncImage(
                model = entry.image,
                contentDescription = entry.name,
                modifier = Modifier
                    .fillMaxSize()
            )
            Text(
                text = entry.name,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(BottomCenter)
            )
        }
    }
}

@Composable
fun CharacterRow(
    character: Character,
    navController: NavController,
    viewModel: CharacterListViewModel
) {
    Column() {
        Box {
            CharacterEntry(
                entry = character,
                modifier = Modifier,
                navController = navController,
                viewModel = viewModel
            )
        }
        SpacerComponent(16)
    }
}

@Composable
private fun ButtonsRow(
    showPrevious: Boolean,
    showNext: Boolean,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                modifier = Modifier
                    .height(48.dp),
                enabled = showPrevious,
                onClick = onPreviousPressed
            ) {
                Text(text = "<-")
            }
            TextButton(
                modifier = Modifier
                    .height(48.dp),
                enabled = showNext,
                onClick = onNextPressed
            ) {
                Text(text = "->")
            }
        }
    }
}




