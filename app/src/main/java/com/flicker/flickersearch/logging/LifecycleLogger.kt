package com.flicker.flickersearch.logging

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.flicker.domain.logging.Layer
import com.flicker.domain.logging.Tag
import com.flicker.domain.logging.type


/**
 * Created by Pratik Behera on 2019-09-03.
 */

@RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
class LifecycleLogger : Application.ActivityLifecycleCallbacks {
    private fun Activity?.logEvent(lifecycleEvent: String) = this?.log(lifecycleEvent) ?: Unit

    private fun Activity?.logEvent(lifecycleEvent: String, bundle: Bundle?) =
            this?.log(lifecycleEvent, "undergoes $lifecycleEvent" + (bundle?.let { " with Bundle:\n${it.dump()}\n" } ?: "")) ?: Unit

    private fun Activity.log(lifecycleEvent: String, descriptiveEvent: String = "⟳ $lifecycleEvent") =
            logDebug(Tag(Layer.VIEW, this@LifecycleLogger, lifecycleEvent)) {
        "Activity '${this.type()}' (${this.hashCode()}) $descriptiveEvent"
    }

    override fun onActivityPaused(activity: Activity?) = activity.logEvent("paused")

    override fun onActivityResumed(activity: Activity?) = activity.logEvent("resumed")

    override fun onActivityStarted(activity: Activity?) = activity.logEvent("started")

    override fun onActivityDestroyed(activity: Activity?) = activity.logEvent("destroyed")

    override fun onActivityStopped(activity: Activity?) = activity.logEvent("stopped")

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) = activity.logEvent("saveInstanceState", outState)

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) = activity.logEvent("created", savedInstanceState)
}
