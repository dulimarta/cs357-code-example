package edu.gvsu.cis.kmp_roomdb

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform