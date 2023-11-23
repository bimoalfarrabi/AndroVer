package com.viasco.submission.compose.androver.model

data class AndroVer(
    val id: Int,
    val name: String,
    val description: String,
    val release: String,
    val photo: Int,
    val isFavorite: Boolean = false
)
