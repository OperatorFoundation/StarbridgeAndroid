package org.operatorfoundation.starbridgeandroid

import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.junit.Test

import org.junit.Assert.*
import org.operatorfoundation.keychainandroid.PublicKey
import org.operatorfoundation.replicantandroid.Starburst
import org.operatorfoundation.replicantandroid.ToneBurst
import org.operatorfoundation.transmission.ConnectionType
import org.operatorfoundation.transmission.TransmissionConnection
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
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun connectToStarbridgeServer()
    {

        val logger: Logger? = null
        val serverPublicKey = PublicKey.new("AgRNLkCxDNgg3mPQ+QYDfUK+gnTsIh9MvzgpGCVbEBsPZAsP+jK5GrXP9pPYRVnyGd6T0ggJ1MWa5Z8sYnh1+5Pm")
        val starbridgeConfig = StarbridgeConfig("128.199.15.158:6456", serverPublicKey)
        val starbridgeConnection = starbridgeConfig.connect(appContext, logger)
        val success = starbridgeConnection.write("pass")
        assert(success)
    }

    @Test
    fun testConnectWithExistingConnection()
    {
        val logger: Logger? = null
        val serverPublicKey = PublicKey.new("AgRNLkCxDNgg3mPQ+QYDfUK+gnTsIh9MvzgpGCVbEBsPZAsP+jK5GrXP9pPYRVnyGd6T0ggJ1MWa5Z8sYnh1+5Pm")
        val starbridgeConfig = StarbridgeConfig("128.199.15.158:6456", serverPublicKey)
        val connection = TransmissionConnection(starbridgeConfig.serverIP, starbridgeConfig.port, ConnectionType.TCP, logger)
        val starbridgeConnection = starbridgeConfig.connect(connection, appContext, logger)
        val success = starbridgeConnection.write("pass")
        assert(success)
    }

    @Test
    fun testStarbridge() {
        val configFile = File("/path/StarbridgeClientConfig.json")
        val configText = configFile.readText(Charsets.UTF_8)

        val customJson = Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            allowStructuredMapKeys = true
            encodeDefaults = true
            serializersModule = SerializersModule {
                polymorphic(ToneBurst::class) {
                    subclass(Starburst::class, Starburst.serializer())
                }
            }
        }

        val starbridgeConfig = customJson.decodeFromString<StarbridgeConfig>(configText)
        val starbridgeConnection = starbridgeConfig.connect(appContext, null)
        val success = starbridgeConnection.write("pass")
        assert(success)
        val serverBytes = starbridgeConnection.read(7)
        println("server bytes: ${serverBytes}")
    }
}