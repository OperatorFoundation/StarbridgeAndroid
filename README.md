# The Operator Foundation

[Operator](https://operatorfoundation.org) makes usable tools to help people around the world with censorship, security, and privacy.

# StarbridgeAndroid

Starbridge is a Pluggable Transport that requires only minimal configuration information from the user. Under the hood, it uses the [Replicant](https://github.com/OperatorFoundation/ReplicantAndroid) Pluggable Transport technology for network protocol obfuscation. Replicant is more complex to configure, so Starbridge is a good starting point for those wanting to use the technology to circumvent Internet cenorship, but wanting a minimal amount of setup.

## Shapeshifter

The Shapeshifter project provides network protocol shapeshifting technology
(also sometimes referred to as obfuscation). The purpose of this technology is
to change the characteristics of network traffic so that it is not identified
and subsequently blocked by network filtering devices.

There are two components to Shapeshifter: transports and the dispatcher. Each
transport provide different approach to shapeshifting. ShapeshifterAndroidKotlin is provided as a 
Kotlin library which can be integrated directly into Android applications.

If you are a tool developer working in the Kotlin programming language for Android, then you
are in the right place. If you are a tool developer working in other languages we have 
several other tools available to you:

- A Go transports library that can be used directly in your application:
[shapeshifter-transports](https://github.com/OperatorFoundation/shapeshifter-transports)

- A Swift transport library that can be used directly in your iOS and macOS applications:
[ShadowSwift](https://github.com/OperatorFoundation/ShadowSwift.git)

- A Java transports library that can be used directly in your Android application (currently supports Shadow):
[ShapeshifterAndroidJava](https://github.com/OperatorFoundation/ShapeshifterAndroidJava)

If you want a end user that is trying to circumvent filtering on your network or
you are a developer that wants to add pluggable transports to an existing tool
that is not written in the Kotlin programming language, then you probably want the
dispatcher. Please note that familiarity with executing programs on the command
line is necessary to use this tool.
<https://github.com/OperatorFoundation/shapeshifter-dispatcher>

If you are looking for a complete, easy-to-use VPN that incorporates
shapeshifting technology and has a graphical user interface, consider
[Moonbounce](https://github.com/OperatorFoundation/Moonbounce), an application for macOS which incorporates shapeshifting without
the need to write code or use the command line.

### Shapeshifter Transports

Shapeshifter Transports is a suite of pluggable transports implemented in a variety of langauges. This repository 
is the Shapeshifter implementation in Kotlin for Android applications. 

If you are looking for a tool which you can install and
use from the command line, take a look at [shapeshifter-dispatcher](https://github.com/OperatorFoundation/shapeshifter-dispatcher.git) instead.

ShapeshifterAndroidKotlin implements the Pluggable Transports 3.0 specification available here:
<https://github.com/Pluggable-Transports/Pluggable-Transports-spec/tree/main/releases/PTSpecV3.0> 

The purpose of the transport library is to provide a set of different
transports. Each transport implements a different method of shapeshifting
network traffic. The goal is for application traffic to be sent over the network
in a shapeshifted form that bypasses network filtering, allowing
the application to work on networks where it would otherwise be blocked or
heavily throttled.

## Setting up dependencies

1) add the following at the end of repositories in your PROJECT's build.gradle:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2) add the dependency in your MODULE's build.gradle:
```
dependencies {
        // Be sure to replace TAG with the most recent version
        implementation 'com.github.OperatorFoundation:StarbridgeAndroid:TAG'
}
```

3) Make sure the min SDK in your build.gradle is 21 or higher in each project/app related build.gradle

## Creating a config
It is recommended to use the command line tool provided in either [shapeshifter-dispatcher](https://github.com/OperatorFoundation/shapeshifter-dispatcher) or [ShapeshifterDispatcherSwift](https://github.com/OperatorFoundation/ShapeshifterDispatcherSwift) to generate a Starbridge config pair

## Using the library

1) Create a Starbridge config instance from a config file
```
  val configFile = File("/path/StarbridgeClientConfig.json")
  val configText = configFile.readText(Charsets.UTF_8)
  val starbridgeConfig = customJson.decodeFromString<StarbridgeConfig>(configText)
```

2) Make a connection.  Optionally, you can provide a logger
```
val starbridgeConnection = starbridgeConfig.connect(null)
```

3) Call .read() and .write() on starbridgeConnection

### Credits
* [Shadowsocks](https://shadowsocks.org/) was developed by the Shadowsocks team.
