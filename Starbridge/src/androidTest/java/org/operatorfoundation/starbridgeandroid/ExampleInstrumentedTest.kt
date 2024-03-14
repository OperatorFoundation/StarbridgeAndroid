package org.operatorfoundation.starbridgeandroid

import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.junit.Test

import org.operatorfoundation.keychainandroid.PublicKey
import org.operatorfoundation.replicantandroid.Starburst
import org.operatorfoundation.replicantandroid.ToneBurst
import org.operatorfoundation.transmission.ConnectionType
import org.operatorfoundation.transmission.TransmissionConnection
import java.io.File
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
        val serverPublicKey = PublicKey.new("")

        println("Got server public key")

        val starbridgeClientConfig = StarbridgeConfig("0.0.0.0:1234", serverPublicKey)

        println("Initialized a StarbridgeClientConfig")

        val connection = TransmissionConnection(starbridgeClientConfig.serverIP, starbridgeClientConfig.port, ConnectionType.TCP, logger)
        val starbridgeConnection = starbridgeClientConfig.connect(connection, appContext, logger)

        println("Made a Starbridge connection.")

        val successString = "pass"
        starbridgeConnection.write(successString)
        println("Wrote to server.")

        val read = starbridgeConnection.read(successString.count())

        if (read == null)
        {
            println("Tried to read but got no response.")
        }
        else
        {
            val readString = String(read)
            println("Read from server.")

            assert(successString == readString)
        }

    }

    @Test
    fun testConnectWithExistingConnection()
    {
        val logger: Logger? = null
        val serverPublicKey = PublicKey.new("AgSRi3is8CElkrD/evIWliZJf8/QP5Dd5LPNZc9iB4CjUDZgEODcl0Rj6PKoA7qIoSDDJPT+eVVrQeboaGYZG6qH")
        val starbridgeConfig = StarbridgeConfig("24.199.99.92:4567", serverPublicKey)
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