package com.example.notetaking.presentation.feature.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notetaking.framework.ListViewModel
import com.example.notetaking.presentation.ui.component.ItemNote

@Composable
fun ListScreen(
    onAddNoteClick: () -> Unit,
    onNoteClick: (Long) -> Unit,
    viewModel: ListViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    ListScreen(
        uiState = uiState,
        onAddNoteClick = onAddNoteClick,
        onNoteClick = onNoteClick,
        modifier = modifier
    )
}

@Composable
fun ListScreen(
    uiState: ListUiState,
    onAddNoteClick: () -> Unit,
    onNoteClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            IconButton(
                modifier = Modifier.size(58.dp),
                onClick = onAddNoteClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "add note")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is ListUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is ListUiState.Empty -> {
                    Text("You don't have note, take a note")
                }

                is ListUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(12.dp)
                    ) {
                        items(uiState.data) { note ->
                            ItemNote(
                                title = note.title,
                                content = note.content,
                                updated = note.updateTime,
                                wordCount = note.wordCount,
                                onClick = { onNoteClick(note.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}