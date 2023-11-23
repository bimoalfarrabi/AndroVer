package com.viasco.submission.compose.androver.di

import com.viasco.submission.compose.androver.data.AndroVerRepository

object Injection {
    fun provideRepository(): AndroVerRepository {
        return AndroVerRepository.getInstance()
    }
}