package com.max.giantbomb.util

sealed class DomainException {
    data class ServerError(val message: String?) : DomainException()
    data class CacheError(val message: String?) : DomainException()
}
