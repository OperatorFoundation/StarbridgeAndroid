package org.operatorfoundation.starbridgeandroid

import android.net.Uri
import android.os.Environment
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.operatorfoundation.keychainandroid.Keychain
import org.operatorfoundation.keychainandroid.PublicKey
import org.operatorfoundation.replicantandroid.Starburst
import org.operatorfoundation.replicantandroid.ToneBurst
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.logging.Logger

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest
{
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("org.operatorfoundation.starbridgeandroid", appContext.applicationContext)
    }

    @Test
    fun connectToStarbridgeServer()
    {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val logger: Logger? = null
        val serverPublicKey = PublicKey.new("")
        val starbridgeConfig = StarbridgeConfig("", serverPublicKey, "Starbridge")
        starbridgeConfig.connect(appContext, logger)
    }
}