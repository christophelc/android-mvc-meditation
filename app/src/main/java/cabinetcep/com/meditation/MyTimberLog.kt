package cabinetcep.com.meditation

import android.util.Log
import timber.log.Timber

class LineNoDebugTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String? {
        return "LOCALDEBUG-" + super.createStackElementTag(element) + ":" + element.lineNumber
    }
}

class CrashlyticsTree: Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.INFO)
            return
        // TODO
        //super.log(priority, tag, message, t)
    }
}
