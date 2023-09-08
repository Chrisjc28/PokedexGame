package com.unomaster.pokedexgame.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MultipleChoiceItem(
    value: String,
    onClick: (name: String) -> Unit
) {
    val textColor = if (isSystemInDarkTheme()) Color.Black else Color.White

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                onClick(value)
            }
            .padding(12.dp)
            .border(2.dp, Color.LightGray, RoundedCornerShape(4.dp))
            .padding(36.dp, 24.dp)
            .defaultMinSize(minWidth = 100.dp)
    ) {
        Text(
            value,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }

}