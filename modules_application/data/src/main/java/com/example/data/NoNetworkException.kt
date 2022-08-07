package com.example.data

import java.io.IOException

class NoNetworkException : IOException() {
    override val message: String = "No Internet Connection"
}