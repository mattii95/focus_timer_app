package com.courrouxdigital.focustimerapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.courrouxdigital.focustimerapp.presentation.theme.FocusTimerAppTheme


@Composable
fun AutoResizedText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.displayLarge
) {
    var timeTextStyle by remember { mutableStateOf(textStyle) }
    Text(
        text = text,
        modifier = modifier.fillMaxWidth(),
        softWrap = false,
        style = timeTextStyle,
        onTextLayout = {
            if(it.didOverflowWidth) {
                timeTextStyle = timeTextStyle.copy(
                    fontSize = timeTextStyle.fontSize * 0.95
                )
            }
        }
    )
}

@Preview(name = "AutoResizedTextPreview", showBackground = true)
@Composable
fun AutoResizedTextPreview() {
    FocusTimerAppTheme {
        AutoResizedText(text = "Test")
    }
}