package com.anibalofice.loteria.compose.megasena

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anibalofice.loteria.R
import com.anibalofice.loteria.components.LotItenType
import com.anibalofice.loteria.components.LotNumberTextField
import com.anibalofice.loteria.ui.theme.LoteriaTheme

import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun MegaSenaScreen(){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        val errorBet = stringResource(R.string.error_bet)
        val errorNumber = stringResource(R.string.error_number)
        var showAlertDialog by remember { mutableStateOf(false) }
        var qtdNumbers by remember { mutableStateOf("") }
        var qtdBets by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }
        val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
        val scope = rememberCoroutineScope()
        val keyboardController = LocalSoftwareKeyboardController.current
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 50.dp)
            ) {
                LotItenType(
                    name = "Mega Sena"
                )
            }

            Text(
                text = stringResource(R.string.annuncement),
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .padding(20.dp)
            )

            LotNumberTextField(
                value = qtdNumbers,
                label = R.string.mega_rule,
                placeholder = R.string.quantity,
                keyboardAction = ImeAction.Next
            ) { newNumber ->
                if (newNumber.length < 3) {
                    qtdNumbers = validatedInput(newNumber)
                }
            }


            LotNumberTextField(
                value = qtdBets,
                label = R.string.bets,
                placeholder = R.string.bets_quantity,
                keyboardAction = ImeAction.Done
            ) { newBet ->
                if (newBet.length < 3) {
                    qtdBets = validatedInput(newBet)
                }
            }

            OutlinedButton(
                enabled = qtdNumbers.isNotEmpty() && qtdBets.isNotEmpty(),
                onClick = {
                    val bets = qtdBets.toInt()
                    val numbers = qtdNumbers.toInt()

                    if (bets < 1 || bets > 10) {
                        scope.launch {
                            snackBarHostState.showSnackbar(errorBet)
                        }
                    } else if (numbers < 6 || numbers > 15) {
                        scope.launch {
                            snackBarHostState.showSnackbar(errorNumber)
                        }
                    } else {
                        result = ""
                        for (i in 0..bets) {
                            result += "[$i] => ${numberGenerator(numbers)} \n\n"
                        }
                        showAlertDialog = !showAlertDialog
                    }
                    keyboardController?.hide() // Hide Keyboard
                }
            ) {
                Text(text = stringResource(R.string.bets_generated))
            }

            Text(text = result)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.BottomCenter
        ) {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        }

        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {
                    TextButton(
                        onClick = { showAlertDialog = !showAlertDialog }
                    ) {
                        Text(
                            text = stringResource(android.R.string.ok)
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showAlertDialog = !showAlertDialog }
                    ) {
                        Text(
                            text = stringResource(android.R.string.cancel)
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.app_name)
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.good_luck)
                    )
                }
            )

        }
    }
}


private fun validatedInput(input: String): String {
    val filteredChars = input.filter { character ->
        character in "0123456789"
    }
    return filteredChars
}

private fun numberGenerator(qtd: Int): String {
    val numbers: MutableSet<Int> = mutableSetOf()

    while (true){
        val n = Random.nextInt(60)
        numbers.add(n+1)
        if(numbers.size == qtd){
            break
        }
    }
    return numbers.joinToString(" - ")

}

@Preview(showBackground = true)
@Composable
fun MegaSenaPreview() {
    LoteriaTheme  {
        MegaSenaScreen()
    }
}