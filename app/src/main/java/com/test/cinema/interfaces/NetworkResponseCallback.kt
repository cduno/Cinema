package  com.test.cinema.interfaces

interface NetworkResponseCallback {
    fun onNetworkSuccess()
    fun onNetworkFailure(th : Throwable)
}