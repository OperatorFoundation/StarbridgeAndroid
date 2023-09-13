package org.operatorfoundation.starbridgeandroid

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.junit.Test

import org.junit.Assert.*
import org.operatorfoundation.replicantandroid.ReplicantConfig
import org.operatorfoundation.replicantandroid.Starburst
import org.operatorfoundation.replicantandroid.ToneBurst
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testStarbridge() {
        val configFile = File("/PathToConfig/StarbridgeClientConfig.json")
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
        val starbridgeConnection = starbridgeConfig.connect(null)
        val success = starbridgeConnection.write("pass")
        assert(success)
        val serverBytes = starbridgeConnection.read(7)
        println("server bytes: ${serverBytes}")
    }
}