package org.operatorfoundation.starbridgeandroid

import android.content.Context
import kotlinx.serialization.Required
import java.util.logging.Logger
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.operatorfoundation.keychainandroid.PublicKey
import org.operatorfoundation.replicantandroid.PolishConfig
import org.operatorfoundation.replicantandroid.ReplicantConfig
import org.operatorfoundation.replicantandroid.Starburst
import org.operatorfoundation.transmission.Connection

@Serializable
class StarbridgeConfig(val serverAddress: String, val serverPublicKey: PublicKey)
{
    @Required
    val transport: String = "shadow"
    val serverIP: String
        get() = serverAddress.substringBefore(':')
    val port: Int
        get() = serverAddress.substringAfter(':').toInt()

    @Transient
    val toneburst = Starburst("SMTPClient")

    constructor(serverIP: String, port: Int, serverPublicKey: PublicKey) : this (
        serverPublicKey = serverPublicKey,
        serverAddress = "$serverIP:$port"
    )

    fun connect(context: Context, logger: Logger?): Connection
    {
        val polish = PolishConfig(serverAddress, serverPublicKey)
        val replicantConfig = ReplicantConfig(serverAddress, polish, toneburst, transport)
        return replicantConfig.connect(context, logger)
    }

    fun connect(connection: Connection, context: Context, logger: Logger?): Connection
    {
        val polish = PolishConfig(serverAddress, serverPublicKey)
        val replicantConfig = ReplicantConfig(serverAddress, polish, toneburst, transport)
        return replicantConfig.connect(connection, context, logger)
    }

}
