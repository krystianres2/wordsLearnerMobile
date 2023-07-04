package com.example.wordslearner.ui.word

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordslearner.R
import com.example.wordslearner.WordsTopAppBar
import com.example.wordslearner.ui.AppViewModelProvider
import com.example.wordslearner.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object WordEditDestination : NavigationDestination {
    override val route = "word_edit"
    override val titleRes = R.string.edit_item_title
    const val wordIdArg = "wordId"
    val routeWithArgs = "$route/{$wordIdArg}"
}
@Composable
fun WordEditScreen(navigateBack: () -> Unit,
onNavigateUp: () -> Unit,
modifier: Modifier = Modifier,
viewModel: WordEditViewModel = viewModel(factory = AppViewModelProvider.Factory)) {

    val coroutineScope = rememberCoroutineScope() //odzyskiwanie zapisanego stanu Coroutine
    Scaffold(
        topBar = {
            WordsTopAppBar(
                title = stringResource(WordEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        WordEntryBody(
            itemUiState = viewModel.wordUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateItem()
                    navigateBack()
                }
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}