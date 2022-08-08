package com.max.giantbomb.util

sealed class LoadState<out T>{
    object InFlight : LoadState<Nothing>()
    object Failure : LoadState<Nothing>()
    data class Success<out T>(val data: T) : LoadState<T>()
}