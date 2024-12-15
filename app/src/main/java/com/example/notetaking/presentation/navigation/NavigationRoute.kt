package com.example.notetaking.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.notetaking.presentation.feature.list.ListScreen
import com.example.notetaking.presentation.feature.note.AddNoteScreen
import com.example.notetaking.presentation.feature.note.EditNoteScreen
import kotlinx.serialization.Serializable

@Serializable
data object BaseRoute

@Serializable
data object ListRoute

@Serializable
data object AddNoteRoute

object EditNoteDestination {
    const val ARG_ID = "id"
    const val ROUTE = "edit/{$ARG_ID}"
    fun createRoute(id: Long) = "edit/$id"
}

fun NavController.navigateToRoute(navOptions: NavOptions? = null) = navigate(ListRoute, navOptions)
fun NavController.navigateToAddNote(navOptions: NavOptions? = null) =
    navigate(AddNoteRoute, navOptions)

fun NavController.navigateToEditNote(id: Long, navOptions: NavOptions? = null) =
    navigate(EditNoteDestination.createRoute(id), navOptions)

fun NavGraphBuilder.listScreen(
    onAddNoteClick: () -> Unit,
    onNoteClick: (Long) -> Unit
) {
    navigation<BaseRoute>(startDestination = ListRoute) {
        composable<ListRoute> {
            ListScreen(onAddNoteClick, onNoteClick)
        }
    }
}

fun NavGraphBuilder.addNoteScreen(
    onBackClick: () -> Unit
) {
    composable<AddNoteRoute> {
        AddNoteScreen(onBackClick)
    }
}

fun NavGraphBuilder.editNoteScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = EditNoteDestination.ROUTE,
        arguments = listOf(
            navArgument(name = EditNoteDestination.ARG_ID, { type = NavType.LongType })
        )
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getLong(EditNoteDestination.ARG_ID) ?: return@composable
        EditNoteScreen(id, onBackClick)
    }
}