package com.example.baseproject.data

import com.example.baseproject.data.model.User
import com.example.baseproject.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val mainService: MainService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getListUser(): List<User> {
        return withContext(ioDispatcher) {
            mainService.getListUser()
        }
    }


    /*
    * suspend fun fetchTwoDocs() =        // called on any Dispatcher (any thread, possibly Main)
    coroutineScope {
        val deferreds = listOf(     // fetch two docs at the same time
            async { fetchDoc(1) },  // async returns a result for the first doc
            async { fetchDoc(2) }   // async returns a result for the second doc
        )
        deferreds.awaitAll()        // use awaitAll to wait for both network requests
    }
    *
    * */
    suspend fun getListUserV2(): List<User> {
        return coroutineScope {
            val listUser = async { mainService.getListUser() }
            listUser.await()
        }
    }
}