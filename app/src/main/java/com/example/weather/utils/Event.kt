package com.example.weather.utils

import android.database.sqlite.SQLiteException
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class Event<T>(
    value: T
) {

    private var _value: T? = value
    fun get(): T? = _value.also { _value = null }

}

// --- helper methods / aliases

/**
 * Convert mutable live-data into non-mutable live-data.
 */
fun <T> MutableLiveData<T>.share(): LiveData<T> = this

// type aliases for live-data instances which contain events
typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>
typealias LiveEvent<T> = LiveData<Event<T>>
typealias EventListener<T> = (T) -> Unit

fun <T> MutableLiveEvent<T>.publishEvent(value: T) {
    this.value = Event(value)
}

fun <T> LiveEvent<T>.observeEvent(lifecycleOwner: LifecycleOwner, listener: EventListener<T>) {
    this.observe(lifecycleOwner) {
        it?.get()?.let { value ->
            listener(value)
        }
    }
}

// --- unit events:

typealias MutableUnitLiveEvent = MutableLiveEvent<Unit>
typealias UnitLiveEvent = LiveEvent<Unit>
typealias UnitEventListener = () -> Unit

//MutableUnitLiveEvent

fun MutableUnitLiveEvent.publishEvent() = publishEvent(Unit)
fun UnitLiveEvent.observeEvent(lifecycleOwner: LifecycleOwner, listener: UnitEventListener) {
    observeEvent(lifecycleOwner) { _ ->
        listener()
    }
}

fun Fragment.findTopNavController(): NavController {
    val topLevelHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerMain) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}

fun <T> LiveData<T>.requireValue(): T {
    return this.value ?: throw IllegalStateException("Value is empty")
}

suspend fun <T> wrapSQLiteException(dispatcher: CoroutineDispatcher, block: suspend CoroutineScope.() -> T): T {
    try {
        return withContext(dispatcher, block)
    } catch (e: SQLiteException) {
        val appException = StorageException()
        appException.initCause(e)
        throw appException
    }
}