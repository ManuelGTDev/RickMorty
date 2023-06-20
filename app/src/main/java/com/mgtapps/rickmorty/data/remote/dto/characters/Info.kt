package com.mgtapps.rickmorty.data.remote.dto.characters

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)