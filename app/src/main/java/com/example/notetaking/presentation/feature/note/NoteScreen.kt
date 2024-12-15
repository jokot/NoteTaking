package com.example.notetaking.presentation.feature.note

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.data.Note
import com.example.notetaking.framework.NoteViewModel
import com.example.notetaking.presentation.ui.component.ConfirmDeleteDialog


@Composable
fun AddNoteScreen(
    onBackClick: () -> Unit,
    viewModel: NoteViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val saved by viewModel.saved.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.onAddNewNote()
    }

    LaunchedEffect(saved) {
        if (saved) {
            keyboardController?.hide()
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
        }
    }

    NoteScreen(
        uiState = uiState,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onBackClick = onBackClick,
        onSaveClick = {
            viewModel.onTriggerSave()
            onBackClick()
        },
        modifier = modifier
    )
}

@Composable
fun EditNoteScreen(
    id: Long,
    onBackClick: () -> Unit,
    viewModel: NoteViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val saved by viewModel.saved.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(
        id
    ) {
        viewModel.getNote(id)
    }

    LaunchedEffect(saved) {
        if (saved) {
            keyboardController?.hide()
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
        }
    }

    NoteScreen(
        uiState = uiState,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onBackClick = onBackClick,
        onSaveClick = {
            viewModel.onTriggerSave()
            onBackClick()
        },
        onRemoveClick = {
            viewModel.removeNote()
            onBackClick()
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    uiState: NoteUiState,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onRemoveClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val note = if (uiState is NoteUiState.Edit) uiState.note else Note()

    var showDialog by remember { mutableStateOf(false) }

    AnimatedVisibility(showDialog) {
        ConfirmDeleteDialog(
            onConfirm = {
                showDialog = false
                onRemoveClick?.let { it() }
            },
            onDismiss = {
                showDialog = false
            }
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                actions = {
                    onRemoveClick?.let {
                        IconButton(
                            onClick = {
                                showDialog = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "remove"
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            IconButton(
                modifier = Modifier.size(58.dp),
                onClick = {
                    if (note.title.isNotEmpty() && note.content.isNotEmpty()) {
                        onSaveClick()
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = "update note")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = note.title,
                    placeholder = {
                        Text("Title")
                    },
                    onValueChange = onTitleChange,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = note.content,
                    placeholder = {
                        Text("Content")
                    },
                    onValueChange = onContentChange,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
            }

            if (uiState is NoteUiState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
}