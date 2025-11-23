package com.vitorota.kmp

import kotlinx.datetime.TimeZone

data class Country(
    val name: String,
    val zone: TimeZone,
    val imageUrl:String
)

fun countries() = listOf(
    Country("Brazil", TimeZone.of("America/Sao_Paulo"), imageUrl = "https://flagcdn.com/w320/br.png"),
    Country("Japan", TimeZone.of("Asia/Tokyo"), imageUrl = "https://flagcdn.com/w320/jp.png"),
    Country(name = "France", TimeZone.of("Europe/Paris"), imageUrl = "https://flagcdn.com/w320/fr.png"),
    Country("Mexico", TimeZone.of("America/Mexico_City"), imageUrl = "https://flagcdn.com/w320/mx.png"),
    Country("Indonesia", TimeZone.of("Asia/Jakarta"), imageUrl = "https://flagcdn.com/w320/id.png"),
    Country("Egypt", TimeZone.of("Africa/Cairo"), imageUrl = "https://flagcdn.com/w320/eg.png")
)