package com.anibalofice.loteria.compose.detail

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anibalofice.loteria.App
import com.anibalofice.loteria.R
import com.anibalofice.loteria.data.Bet
import com.anibalofice.loteria.viewmodels.BetListDetailViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BetListDetailScreen(
    type: String,
    context: Context= LocalContext.current,
    backClicado:() -> Unit,
    betViewModel: BetListDetailViewModel = viewModel(factory = BetListDetailViewModel.Factory)
    ){
    val db = (context.applicationContext as App).db
    val bets = betViewModel.bets.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "MZ"))

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = if(type.length > 7){
                            "Mega Sena"
                        }else{
                            "Quina"
                        }
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
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn() {
                itemsIndexed(bets){ index, bet ->
                    Text(
                        stringResource(R.string.list_response,
                            index,
                            sdf.format(bet.date),
                            bet.number),
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp)
                    )
                }


            }
        ///  //  Thread{
            //    val res = db.betDao().getNumbersByType(type)
           //     bets.clear()
          //      bets.addAll(res)
         //   }.start()


        }
    }
}

@Preview(showBackground = true)
@Composable
fun BetListDetailPreview(){
    BetListDetailScreen("Mega Sena", backClicado = {})
}