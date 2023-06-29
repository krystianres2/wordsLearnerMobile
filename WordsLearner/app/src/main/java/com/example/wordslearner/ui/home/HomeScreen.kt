package com.example.wordslearner.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordslearner.R
import com.example.wordslearner.WordsTopAppBar
import com.example.wordslearner.data.Word
import com.example.wordslearner.ui.AppViewModelProvider
import com.example.wordslearner.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

/**
 * Entry route for Home screen
 */
@Composable
fun HomeScreen(
    navigateToWordEntry: () -> Unit,
    navigateToWordUpdate: (Int) -> Unit,
    navigateToTest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    Scaffold(
        topBar = {
            WordsTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false
            )
        },
    ) { innerPadding ->
        HomeBody(
            wordList = homeUiState.wordList,
            onWordClick = navigateToWordUpdate,
            navigateToWordEntry,
            navigateToTest,
            modifier = modifier.padding(innerPadding)
        )

    }
}

@Composable
private fun HomeBody(
    wordList: List<Word>,
    onWordClick: (Int) -> Unit,
    navigateToWordEntry: () -> Unit,
    navigateToTest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        WordsListHeader()
        Divider(Modifier.height(4.dp))
        if (wordList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                style = MaterialTheme.typography.subtitle2
            )
        } else {
            Box(modifier = Modifier.weight(1f)) {
                WordsList(wordList = wordList, onWordClick = { onWordClick(it.id) })
            }
        }
        BottomBar(Modifier, navigateToWordEntry, navigateToTest)

    }
}

@Composable
private fun BottomBar(modifier: Modifier = Modifier, navigateToWordEntry: () -> Unit, navigateToTest: () -> Unit){
    Divider(Modifier.height(4.dp))
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Button(
                onClick = navigateToTest,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.test_screen_tittle))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Button(
                onClick = navigateToWordEntry,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.dodaj))
            }
        }
    }
}


@Composable
private fun WordsList(
    wordList: List<Word>,
    onWordClick: (Word) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(0.dp)) {
        items(items = wordList, key = { it.id }) { item ->
            WordsWord(word = item, onItemClick = onWordClick)
            Divider()
        }
    }
}

@Composable
private fun WordsListHeader(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        headerList.forEach {
            Text(
                text = stringResource(it.headerStringId),
                modifier = Modifier
                    .weight(it.weight)
                    .padding(start = 8.dp),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun WordsWord(
    word: Word,
    onItemClick: (Word) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable { onItemClick(word) }
        .padding(vertical = 8.dp)
//        .background(color = Color.Cyan)
    ) {
        Text(
            text = word.wordPl,
            modifier = Modifier
                .weight(1.5f)
                .padding(start = 8.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = word.wordEng,
            modifier = Modifier
                .weight(1.5f)
                .padding(start = 8.dp),
            fontWeight = FontWeight.Bold
        )
    }
}

private data class WordsHeader(@StringRes val headerStringId: Int, val weight: Float)

private val headerList = listOf(
    WordsHeader(headerStringId = R.string.wordPl, weight = 1.5f),
    WordsHeader(headerStringId = R.string.wordEng, weight = 1.5f),
)

