package com.anibalofice.loteria.compose.quina

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anibalofice.loteria.App
import com.anibalofice.loteria.R
import com.anibalofice.loteria.components.AutoTextDropDown
import com.anibalofice.loteria.components.LotItenType
import com.anibalofice.loteria.components.LotNumberTextField
import com.anibalofice.loteria.compose.megasena.MegaSenaScreen
import com.anibalofice.loteria.data.Bet
import com.anibalofice.loteria.ui.theme.LoteriaTheme
import kotlinx.coroutines.launch
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun QuinaScreen(context: Context= LocalContext.current, clicado:(String) -> Unit, backClicado:() -> Unit){
    val db = (context.applicationContext as App).db
    val saveBets = mutableListOf<String>()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = "Aposta"
                    )
                },
                navigationIcon = {
                    IconButton (
                        onClick = {
                            backClicado()
                        }){
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton (
                        onClick = {
                            clicado("quina")
                        }){
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "List"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
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
        val rules = stringArrayResource(R.array.array_bets_rules)
        var selectedItem by remember { mutableStateOf(rules.first()) }

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
                    name = "Quina",
                    R.drawable.trevo_blue
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
                label = R.string.rule,
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

            Column(
                modifier = Modifier
                    .width(296.dp)
            ) {
                AutoTextDropDown(
                    label = stringResource(R.string.bet_rule),
                    initialValuee = selectedItem,
                    list = rules.toList()){
                    selectedItem = it
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
                        saveBets.clear()

                        //para pegar o valor das regras das apostas geradas
                        val rule = rules.indexOf(selectedItem)
                        for (i in 1..bets) {
                            val res = numberGenerator(numbers,rule)
                            saveBets.add(res)
                            result += "[$i] => $res \n\n"
                        }
                        showAlertDialog = !showAlertDialog
                    }
                    keyboardController?.hide() // Hide Keyboard
                }
            ) {
                Text(text = stringResource(R.string.bets_generated))
            }

            //Estilo do resultado
            Text(
                text = result,
                modifier = Modifier
                    .padding(20.dp)
            )
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
                        onClick = { showAlertDialog = false
                        }
                    ) {
                        Text(
                            text = stringResource(android.R.string.ok)
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            Thread{
                                for (res in saveBets) {
                                    val bet = Bet(type = "quina", number = res)
                                    db.betDao().insert(bet)
                                }
                            }.start()

                            showAlertDialog = false }
                    ) {
                        Text(
                            text = stringResource(R.string.save)
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

private fun numberGenerator(qtd: Int, rule: Int): String {
    val numbers = mutableSetOf<Int>()

    while (numbers.size < qtd) {
        val n = Random.nextInt(1, 61) // 1 até 60

        // Regra
        when (rule) {
            1 -> if (n % 2 == 0) continue // só ímpares
            2 -> if (n % 2 != 0) continue // só pares
        }

        numbers.add(n)
    }

    return numbers.joinToString(" - ")
}


@Preview(showBackground = true)
@Composable
fun QuinaPreview() {
    LoteriaTheme  {
        QuinaScreen(
            clicado = {},
            backClicado = {}
        )
    }
}