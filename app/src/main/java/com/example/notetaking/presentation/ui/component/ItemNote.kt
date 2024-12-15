package com.example.notetaking.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notetaking.framework.util.toDate

@Composable
fun ItemNote(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    updated: Long,
    wordCount: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Text(content, style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Last updated: ${updated.toDate()}",
                    style = MaterialTheme.typography.labelSmall
                )
                Text("$wordCount words", style = MaterialTheme.typography.labelSmall)
            }
        }

    }
}