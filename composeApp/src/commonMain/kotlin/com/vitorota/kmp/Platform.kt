package com.vitorota.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform