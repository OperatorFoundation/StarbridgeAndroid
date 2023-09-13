package org.operatorfoundation.starbridgeandroid

import java.util.logging.Logger
import kotlinx.serialization.Serializable
import org.operatorfoundation.keychainandroid.PublicKey
import org.operatorfoundation.replicantandroid.PolishConfig
import org.operatorfoundation.replicantandroid.ReplicantConfig
import org.operatorfoundation.replicantandroid.Starburst
import org.operatorfoundation.transmission.Connection

@Serializable
class StarbridgeConfig(val serverAddress: String, val serverPublicKey: PublicKey, val transport: String) {
    fun connect(logger: Logger?): Connection {
        val polish = PolishConfig(serverAddress, serverPublicKey)
        val toneburst = Starburst("SMTPClient")
        val replicantConfig = ReplicantConfig(serverAddress, polish, toneburst, transport)
        return replicantConfig.connect(logger)
    }
}
