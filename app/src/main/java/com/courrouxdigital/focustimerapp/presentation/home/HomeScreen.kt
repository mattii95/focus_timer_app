package com.courrouxdigital.focustimerapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.courrouxdigital.focustimerapp.R
import com.courrouxdigital.focustimerapp.presentation.components.AutoResizedText
import com.courrouxdigital.focustimerapp.presentation.components.BorderedIcon
import com.courrouxdigital.focustimerapp.presentation.components.CircleDot
import com.courrouxdigital.focustimerapp.presentation.components.CustomButton
import com.courrouxdigital.focustimerapp.presentation.components.InformationItem
import com.courrouxdigital.focustimerapp.presentation.components.TimerTypeItem
import com.courrouxdigital.focustimerapp.presentation.theme.FocusTimerAppTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(FocusTimerAppTheme.dimens.paddingMedium)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                modifier = Modifier.size(FocusTimerAppTheme.dimens.iconSizeNormal),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Menu",
                painter = painterResource(id = R.drawable.ic_menu)
            )
        }

        AutoResizedText(
            text = "Focus Timer", textStyle = MaterialTheme.typography.displayMedium.copy(
                color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(FocusTimerAppTheme.dimens.spacerMedium))

        Row {
            CircleDot()
            Spacer(modifier = Modifier.width(FocusTimerAppTheme.dimens.spacerNormal))
            CircleDot()
            Spacer(modifier = Modifier.width(FocusTimerAppTheme.dimens.spacerNormal))
            CircleDot(color = MaterialTheme.colorScheme.tertiary)
            Spacer(modifier = Modifier.width(FocusTimerAppTheme.dimens.spacerNormal))
            CircleDot(color = MaterialTheme.colorScheme.tertiary)
        }

        Spacer(modifier = Modifier.height(FocusTimerAppTheme.dimens.spacerMedium))

        TimerSessionView(timer = "05:00", onIncreasedTap = {}, onDecreasedTap = {})

        Spacer(modifier = Modifier.height(FocusTimerAppTheme.dimens.spacerMedium))

        TimerTypeSessionView(onTap = {})

        Spacer(modifier = Modifier.height(FocusTimerAppTheme.dimens.spacerMedium))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomButton(
                text = "Start",
                textColor = MaterialTheme.colorScheme.surface,
                buttonColor = MaterialTheme.colorScheme.primary,
                onTap = {}
            )
            CustomButton(
                text = "Reset",
                textColor = MaterialTheme.colorScheme.primary,
                buttonColor = MaterialTheme.colorScheme.surface,
                onTap = {}
            )
        }

        Spacer(modifier = Modifier.height(FocusTimerAppTheme.dimens.spacerMedium))

        InformationSessionView(
            modifier = Modifier.weight(1f),
            round = "10",
            time = "20:00"
        )
    }
}

@Composable
private fun TimerSessionView(
    modifier: Modifier = Modifier,
    timer: String,
    onIncreasedTap: () -> Unit = {},
    onDecreasedTap: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BorderedIcon(icon = R.drawable.ic_minus, onTap = onDecreasedTap)
            Spacer(modifier.height(FocusTimerAppTheme.dimens.spacerMedium))
        }
        AutoResizedText(
            text = timer,
            modifier = Modifier
                .fillMaxWidth()
                .weight(6f)
                .align(Alignment.CenterVertically),
            textStyle = MaterialTheme.typography.displayLarge.copy(
                color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center
            )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BorderedIcon(icon = R.drawable.ic_plus, onTap = onIncreasedTap)
            Spacer(modifier.height(FocusTimerAppTheme.dimens.spacerMedium))
        }
    }
}

@Composable
private fun TimerTypeSessionView(
    modifier: Modifier = Modifier,
    onTap: () -> Unit = {},
) {
    val gridCount = 3
    val itemSpacing = Arrangement.spacedBy(FocusTimerAppTheme.dimens.paddingNormal)

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(FocusTimerAppTheme.dimens.spacerLarge),
        columns = GridCells.Fixed(gridCount),
        horizontalArrangement = itemSpacing,
        verticalArrangement = itemSpacing
    ) {
        item(key = "FT") {
            TimerTypeItem(
                text = "Focus Timer", textColor = MaterialTheme.colorScheme.primary
            )
        }
        item(key = "SB") {
            TimerTypeItem(
                text = "Short Break", textColor = MaterialTheme.colorScheme.secondary
            )
        }
        item(key = "LB") {
            TimerTypeItem(
                text = "Long Break", textColor = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun InformationSessionView(
    modifier: Modifier = Modifier,
    round: String,
    time: String,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            InformationItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = round,
                label = "rounds"
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))
            InformationItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = time,
                label = "time"
            )
        }
    }
}

@Preview(name = "HomeScreenPreview", showBackground = true)
@Composable
private fun HomeScreenPreview() {
    FocusTimerAppTheme {
        HomeScreen()
    }
}