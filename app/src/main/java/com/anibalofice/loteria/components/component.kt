package com.anibalofice.loteria.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anibalofice.loteria.R
import com.anibalofice.loteria.ui.theme.Green
import com.anibalofice.loteria.ui.theme.LoteriaTheme

@Composable
fun LotNumberTextField(
    value: String,
    @StringRes label: Int,
    @StringRes placeholder:Int,
    keyboardAction: ImeAction = ImeAction.Next,
    onNewValue:(String)-> Unit
){
    OutlinedTextField(
        value = value,
        maxLines = 1,
        label = {
            Text(
                text = stringResource(id = label)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = keyboardAction
        ),
        placeholder = {
            Text(
                text = stringResource(id = placeholder)
            )
        },
        onValueChange = { newNumber ->
           onNewValue(newNumber)
        }
    )
}

@Composable
fun LotItenType(
    name:String,
    @DrawableRes icon: Int = R.drawable.trevo,
    bgColor: Color = Color.Transparent,
    color: Color = Color.Black

){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .wrapContentSize()
            .background(bgColor)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = stringResource(R.string.trevo),
            modifier = Modifier
                .size(100.dp)
                .padding(10.dp)
        )

        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(2.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun LotItenTypePreview() {
    LoteriaTheme  {
        LotItenType(
            name = "Mega Sena",
            bgColor = Green,
            color = Color.White,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LotNumberTextFieldPreview() {
    LoteriaTheme  {
        LotNumberTextField(
            value = "Abc",
            label = R.string.trevo,
            placeholder = R.string.trevo,
            keyboardAction = ImeAction.Done
        ){}
    }
}