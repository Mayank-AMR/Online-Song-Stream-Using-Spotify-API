package com.mayank_amr.spotifysdk.utils

import kotlinx.coroutines.*

/**
 * @Project spotify SDK
 * @Created_by Mayank Kumar on 13-12-2020 05:20 PM
 */

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}