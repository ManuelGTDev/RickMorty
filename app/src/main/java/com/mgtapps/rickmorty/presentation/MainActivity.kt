package com.mgtapps.rickmorty.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mgtapps.rickmorty.presentation.screens.CharacterDetailScreen
import com.mgtapps.rickmorty.presentation.screens.CharacterListScreen
import com.mgtapps.rickmorty.ui.theme.RickMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickMortyTheme {

                //NavHost -> Navegar entre pantallas
                val navController = rememberNavController();

                NavHost(
                    navController = navController,
                    startDestination = "character_list_screen"
                ) {
                    composable("character_list_screen") {
                        CharacterListScreen(navController = navController)
                    }
                    composable(
                        "characater_detail_screen/{id}",
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val id = remember {
                            it.arguments?.getInt("id")
                        }
                        if (id != null) {
                            CharacterDetailScreen()
                        }
                    }
                }

            }
        }
    }
}