package com.anibalofice.loteria.compose.quina

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.anibalofice.loteria.compose.megasena.MegaSenaScreen
import com.anibalofice.loteria.ui.theme.LoteriaTheme


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun QuinaScreen(){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            Text(
                text = "Heo Anibal !"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuinaPreview() {
    LoteriaTheme  {
        QuinaScreen()
    }
}