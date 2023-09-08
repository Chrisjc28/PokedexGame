package com.unomaster.pokedexgame.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
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

@Composable
fun MultipleChoiceItem(
    value: String,
    onClick: (name: String) -> Unit
) {
    val textColor = if (isSystemInDarkTheme()) Color.Black else Color.White

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(12.dp)
            .border(2.dp, Color.LightGray, RoundedCornerShape(4.dp))
            .padding(36.dp, 24.dp)
            .defaultMinSize(minWidth = 100.dp).clickable {
                onClick(value)
            }
    ) {
        Text(
            value,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }

}