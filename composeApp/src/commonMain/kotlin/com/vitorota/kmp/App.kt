package com.vitorota.kmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
@Preview
fun App(countries: List<Country> = countries()) {
    MaterialTheme {

        var timeAtLocation by remember { mutableStateOf("No location selected") }

        Column(
           modifier = Modifier.safeContentPadding()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .weight(1f)
            ) {

                Text(
                    timeAtLocation,
                    style = TextStyle(fontSize = 20.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )

                CountryDropDownMenu(
                    countries = countries,
                    onSelectCountry = { timeAtLocation = currentTimeAt(it.name, it.zone) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Text(
                text = getPlatform().name,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDropDownMenu(
    countries: List<Country>,
    onSelectCountry: (Country) -> Unit,
    modifier: Modifier = Modifier
) {
    var showCountries by remember { mutableStateOf(false) }
    var selectedCountry: Country? by remember { mutableStateOf(null) }

    ExposedDropdownMenuBox(
        expanded = showCountries,
        onExpandedChange = { showCountries = !showCountries },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedCountry?.name ?: "",
            onValueChange = {},
            leadingIcon = selectedCountry?.let { { CountryIcon(it) } },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(showCountries) },
            label = { Text("Select an option") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(
                    ExposedDropdownMenuAnchorType.PrimaryEditable,
                    enabled = true
                )
        )

        ExposedDropdownMenu(
            expanded = showCountries,
            onDismissRequest = { showCountries = false },
        ) {
            countries.forEach {
                CountryDropdownOption(
                    it,
                    onClick = {
                        selectedCountry = it
                        onSelectCountry(it)
                        showCountries = false
                    }
                )
            }
        }
    }

}

@Composable
fun CountryDropdownOption(
    country: Country,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        modifier = modifier,
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CountryIcon(country)
                Text(
                    country.name,
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        },
        onClick = onClick
    )
}

@Composable
fun CountryIcon(country: Country) {
    with(country) {
        AsyncImage(
            model = imageUrl,
            placeholder = ColorPainter(Color.Gray),
            error = ColorPainter(Color.Red),
            contentDescription = name,
            modifier = Modifier.size(32.dp, 32.dp)
        )
    }
}

@OptIn(ExperimentalTime::class)
fun currentTimeAt(location: String, zone: TimeZone): String {
    fun LocalTime.formatted() = "$hour:$minute:$second"

    val time = Clock.System.now()
    val localTime = time.toLocalDateTime(zone).time

    return "The time in $location is ${localTime.formatted()}"
}