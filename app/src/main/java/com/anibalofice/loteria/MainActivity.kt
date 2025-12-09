package com.anibalofice.loteria

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anibalofice.loteria.components.LotItenType
import com.anibalofice.loteria.components.LotNumberTextField
import com.anibalofice.loteria.ui.theme.Green
import com.anibalofice.loteria.ui.theme.LoteriaTheme
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoteriaTheme  {
              val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = AppRouter.HOME.rota
                    ) {
                    composable(AppRouter.HOME.rota){
                        HomeScreen{
                          navController.navigate(AppRouter.FORM.rota)
                        }
                    }
                    composable (AppRouter.FORM.rota){
                        FormScreen()
                    }
                }
            }
        }
    }
}


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen(onClick:()-> Unit){
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background
    ){innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            LotteryItem("Mega Sena", clicavel = onClick )
        }

    }
}

@Composable
fun LotteryItem(name: String, clicavel:()-> Unit){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        onClick = {
            clicavel()
        }
    ) {
        LotItenType(
            name = "Mega Sena",
            bgColor = Green,
            color = Color.White
        )
    }

}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun FormScreen(){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ){innerPadding ->
        var qtdNumbers by remember { mutableStateOf("") }
        var qtdBets by remember {mutableStateOf("")}
        var result by remember { mutableStateOf("") }
        val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LotItenType(
                name = "Mega Sena"
            )

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
                if(newNumber.length < 3){
                    qtdNumbers = validatedInput(newNumber)
                }
            }


            LotNumberTextField(
                value = qtdBets,
                label = R.string.bets,
                placeholder = R.string.bets_quantity,
                keyboardAction = ImeAction.Done
            ) { newBet ->
                if(newBet.length < 3){
                    qtdBets = validatedInput(newBet)
                }
            }

            OutlinedButton(
                enabled = qtdNumbers.isNotEmpty() && qtdBets.isNotEmpty(),
                onClick = {
                    if (qtdBets.toInt() < 1 || qtdBets.toInt() > 10){
                        scope.launch {
                            snackBarHostState.showSnackbar("Máximo de caracter permitido: 10")
                        }
                    }else if (qtdNumbers.toInt() < 1 || qtdNumbers.toInt() > 15){
                        scope.launch {
                            snackBarHostState.showSnackbar("Números devem ser de 6 á 15")
                        }
                    }else{
                       result = ""
                        for (i in 0..qtdBets.toInt()){
                            result += "[$i] => ${numberGenerator(qtdNumbers)} \n\n"
                        }
                    }
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

    }
}

private fun numberGenerator(qtd:String):String{
    val numbers: MutableSet<Int> = mutableSetOf()

    while (true){
        val n = Random.nextInt(60)
        numbers.add(n+1)
        if(numbers.size == qtd.toInt()){
            break
        }
    }
    return numbers.joinToString(" - ")
}

private fun validatedInput(input:String):String{
    val filteredChars = input.filter { character ->
        character in "0123456789"
    }
    return filteredChars
}

enum class AppRouter(val rota: String){
    HOME("home"),
    FORM("form")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoteriaTheme  {
        HomeScreen{}
    }
}

@Preview(showBackground = true)
@Composable
fun FormPreview() {
    LoteriaTheme  {
       FormScreen()
    }
}