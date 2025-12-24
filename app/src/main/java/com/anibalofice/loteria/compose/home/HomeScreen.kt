package com.anibalofice.loteria.compose.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anibalofice.loteria.R
import com.anibalofice.loteria.components.LotItenType
import com.anibalofice.loteria.model.MainItem
import com.anibalofice.loteria.ui.theme.Green
import com.anibalofice.loteria.ui.theme.LoteriaTheme
import com.anibalofice.loteria.ui.theme.blueColor

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen(onClick:(MainItem)-> Unit){
    val mainItems = mutableListOf<MainItem>(
        MainItem(1,"Mega Sena",Green,R.drawable.trevo),
        MainItem(2,"Quina", blueColor,R.drawable.trevo_blue)

    )
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background
    ){innerPadding ->
        Row  (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            for (it in mainItems){
                LotteryItem(it){
                    onClick(it)
                }
            }
        }

    }
}

@Composable
fun LotteryItem(item: MainItem, clicavel:()-> Unit){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        onClick = {
            clicavel()
        }
    ) {
        LotItenType(
            name = item.name,
            icon = item.icon,
            bgColor = item.color,
            color = Color.White
        )
    }

}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LoteriaTheme  {
        HomeScreen{}
    }
}