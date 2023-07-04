package com.example.wordslearner.ui.word

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordslearner.R
import com.example.wordslearner.WordsTopAppBar
import com.example.wordslearner.ui.AppViewModelProvider
import com.example.wordslearner.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object WordEntryDestination : NavigationDestination {
    override val route = "word_entry"
    override val titleRes = R.string.item_entry_title
}

@Composable
fun WordEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    viewModel: WordEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)//tworzy instancję przy pomocy Factory
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            WordsTopAppBar(
                title = stringResource(WordEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        WordEntryBody(
            itemUiState = viewModel.wordUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {//operacje na bazie danych powinny być wewnątrz coroutineScope
                    viewModel.saveWord()
                    navigateBack()
                }
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun WordEntryBody(
    itemUiState: WordUiState,
    onItemValueChange: (WordDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        WordInputForm(wordDetails = itemUiState.wordDetails, onValueChange = onItemValueChange)
        Button(
            onClick = onSaveClick,
            enabled = itemUiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_action))
        }
    }
}

@Composable
fun WordInputForm(
    wordDetails: WordDetails,
    modifier: Modifier = Modifier,
    onValueChange: (WordDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextField(
            value = wordDetails.wordPl,
            onValueChange = { onValueChange(wordDetails.copy(wordPl = it)) },
            label = { Text(stringResource(R.string.item_name_req)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = wordDetails.wordEng,
            onValueChange = { onValueChange(wordDetails.copy(wordEng = it)) },
            label = { Text(stringResource(R.string.item_price_req)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}

