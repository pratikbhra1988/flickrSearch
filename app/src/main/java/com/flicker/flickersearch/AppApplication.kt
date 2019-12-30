package com.flicker.flickersearch

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.flicker.domain.logging.DomainLogger
import com.flicker.domain.logging.Layer
import com.flicker.domain.logging.LegacyLogger
import com.flicker.domain.logging.Tag
import com.flicker.flickersearch.koin.appModules
import com.flicker.flickersearch.logging.AndroidLegacyLogger
import com.flicker.flickersearch.logging.AndroidLogger
import com.flicker.flickersearch.logging.LifecycleLogger
import com.flicker.flickersearch.logging.logDebug
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Pratik Behera on 2019-12-30.
 */

open class AppApplication : MultiDexApplication() {

    private val tag: Tag by lazy { Tag(Layer.UNSPECIFIED, this) }

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        enableLogging()
        startKoin {
            androidContext(this@AppApplication)
            modules(appModules)
        }

    }

    @SuppressLint("ObsoleteSdkInt")
    private fun enableLogging() {
        DomainLogger.injected = AndroidLogger()
        LegacyLogger.injected = AndroidLegacyLogger()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(LifecycleLogger())
        }
        logDebug(tag.method("enableLogging")) {
            "Application started"
        }
    }


    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

}