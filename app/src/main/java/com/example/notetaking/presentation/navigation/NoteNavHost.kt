package com.example.notetaking.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NoteNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BaseRoute,
        modifier = modifier
    ) {
        listScreen(
            onNoteClick = navController::navigateToEditNote,
            onAddNoteClick = navController::navigateToAddNote
        )

        addNoteScreen(onBackClick = navController::popBackStack)

        editNoteScreen(onBackClick = navController::popBackStack)
    }
}