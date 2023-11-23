package com.viasco.submission.compose.androver.data

import com.viasco.submission.compose.androver.model.AndroVer
import com.viasco.submission.compose.androver.model.AndroVerData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class AndroVerRepository {
    private val dummyAndroVer = mutableListOf<AndroVer>()

    init {
        if (dummyAndroVer.isEmpty()) {
            AndroVerData.androids.forEach {
                dummyAndroVer.add(it)
            }
        }
    }

    fun getAndroVerById(androVerId: Int): AndroVer {
        return dummyAndroVer.first {
            it.id == androVerId
        }
    }

    fun getFavoriteAndroVer(): Flow<List<AndroVer>> {
        return flowOf(dummyAndroVer.filter { it.isFavorite })
    }

    fun searchAndroVer(query: String) = flow {
        val data = dummyAndroVer.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updateAndroVer(androVerId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyAndroVer.indexOfFirst { it.id == androVerId }
        val result = if (index >= 0) {
            val androVer = dummyAndroVer[index]
            dummyAndroVer[index] = androVer.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var INSTANCE: AndroVerRepository? = null

        fun getInstance(): AndroVerRepository {
            return INSTANCE?: synchronized(this) {
                INSTANCE?: AndroVerRepository().also { INSTANCE = it }
            }
        }
    }
}