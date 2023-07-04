package com.example.wordslearner.ui.test

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordslearner.R
import com.example.wordslearner.ui.AppViewModelProvider
import com.example.wordslearner.ui.navigation.NavigationDestination

object TestDestination : NavigationDestination {
    override val route = "test"
    override val titleRes = R.string.test_screen_tittle
}


@Composable
fun TestScreen(navigateToHome: () -> Unit, modifier: Modifier = Modifier, testViewModel: TestViewModel = viewModel(factory = AppViewModelProvider.Factory)){
    val homeUiState by testViewModel.testUiState.collectAsState()//do not delete!!!!!
    val testUiState by testViewModel.uiState.collectAsState()
    var pom = when(val x =testUiState.currentWord){
        "" -> 0
        else -> 1
    }

    Box(modifier = Modifier.background(if (isSystemInDarkTheme()) Color.Gray else Color.White).fillMaxSize()) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            TestStatus(wordCount = if (testViewModel.pom==0){testUiState.currentWordCount-1}else{testUiState.currentWordCount}, maxWords = testViewModel.listSize)
            TestLayout(
                currentWord = if(testUiState.currentWord == ""){
                    "Telefon"}else{testUiState.currentWord},
                userGuess = testViewModel.userAnswer,
                onUserGuessChange = { testViewModel.updateUserAnswer(it) },
                onKeyboardDone = { testViewModel.checkUserAnswer() })
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp), horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = { testViewModel.checkUserAnswer() }, modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(id = R.string.zatwierdź))
                }
            }
            if (testUiState.isGameOver && testUiState.currentWordCount != 0) {
                FinalScoreDialog(
                    score = testUiState.score,
                    onPlayAgain = { testViewModel.resetGame() },
                    navigateToHome
                )
            }
        }
    }
}


@Composable
fun TestStatus(wordCount: Int, maxWords: Int, modifier: Modifier = Modifier){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)
        .size(48.dp), horizontalArrangement = Arrangement.Center) {
        Text(text = stringResource(id = R.string.word_count_info, wordCount, maxWords), fontSize = 18.sp, modifier = Modifier.align(
            Alignment.CenterVertically))
    }
}

@Composable
fun TestLayout(currentWord: String, modifier: Modifier = Modifier, userGuess: String, onUserGuessChange: (String) -> Unit, onKeyboardDone: () ->Unit){
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Text(text = currentWord, fontSize = 45.sp, modifier=modifier.align(Alignment.CenterHorizontally))
        Text(text = stringResource(id = R.string.przetłumacz), fontSize = 17.sp, modifier = Modifier.align(
            Alignment.CenterHorizontally))
        OutlinedTextField(value = userGuess, singleLine = true, modifier = Modifier.fillMaxWidth(), onValueChange = onUserGuessChange, label = {
            Text(
            stringResource(id = R.string.wpisz)
            )
        },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done), keyboardActions = KeyboardActions(onDone = {onKeyboardDone()})
        )
    }
}

@Composable
private fun FinalScoreDialog(score: Int, onPlayAgain: () -> Unit, navigateToHome: () -> Unit,modifier: Modifier = Modifier){

    AlertDialog(
        onDismissRequest = {

        },
        title = { Text(stringResource(id = R.string.gratulacje)) },
        text = { Text(stringResource(id = R.string.score_info, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    navigateToHome()
                }
            ) {
                Text(text = stringResource(id = R.string.wyjdź))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(id = R.string.ponownie))
            }
        }
    )
}