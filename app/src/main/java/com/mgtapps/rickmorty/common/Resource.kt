package com.mgtapps.rickmorty.common

//Clase para posteriormente decirle a nuestro ViewModel los posible serrores que sucedan
sealed class Resource<T>(val data: T? = null, val message: String? = null){
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message:String,data: T? = null): Resource<T>(data, message)
    class Loading<T>(data: T? = null): Resource<T>(data)
}
