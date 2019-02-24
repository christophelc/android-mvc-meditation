package cabinetcep.com.meditation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Handler
import timber.log.Timber
import kotlin.reflect.KFunction

class MeditationTimer(lifecycle: Lifecycle, val duration: Int, val callback: () -> Unit): LifecycleObserver {

    var secondsCount: Int = 0
    private var secondsSteps: Int = 5
    private var active: Boolean = false
    private var handler = Handler()
    private lateinit var runnable: Runnable

    init {
        lifecycle.addObserver(this)
    }

    fun reset() {
        secondsCount = 0
    }

    fun activate() {
        if (!active) {
            active = true
            startTimer()
        }
    }

    fun desactivate(){
        if (active){
            stopTimer()
            active = false
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startTimer() {
        Timber.d("resume")
        runnable = Runnable {
            secondsCount += secondsSteps
            if (secondsCount > duration){
                callback()
            }
            if (active) {
                Timber.d(secondsCount.toString())
                    handler.postDelayed(runnable, secondsSteps * 1000.toLong())
            }
        }
        if (active) {
            // démarrage du timer (première fois ou sur reprise)
            handler.postDelayed(runnable, 1000)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopTimer() {
        Timber.d("pause")
        if (active) {
            handler.removeCallbacks(runnable)
        }
    }
}