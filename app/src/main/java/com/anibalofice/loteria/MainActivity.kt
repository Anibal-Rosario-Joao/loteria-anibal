package com.anibalofice.loteria

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anibalofice.loteria.ui.theme.Green
import com.anibalofice.loteria.ui.theme.LoteriaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoteriaTheme  {
                HomeScreen()
            }
        }
    }
}


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen(){
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
            LotteryItem("Mega Sena")
        }

    }
}

@Composable
fun LotteryItem(name: String){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column (
            modifier = Modifier
                .background(Green)
        ){
            Image(
                painter = painterResource(R.drawable.trevo),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
            )
            Text(
                name,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoteriaTheme  {
        HomeScreen()
    }
}