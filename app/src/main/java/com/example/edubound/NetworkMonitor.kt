package com.example.edubound

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties

class NetworkMonitor(private val context: Context) {

    // Get the IP address prefix of the current mobile phone under Wi-Fi.
    fun getLocalIpPrefix(): String? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val linkProps: LinkProperties = cm.getLinkProperties(cm.activeNetwork) ?: return null

        for (addr in linkProps.linkAddresses) {
            val host = addr.address.hostAddress
            if (host.contains(".")) { // Simple way to determine IPv4
                return host.substringBeforeLast(".")
            }
        }
        return null
    }
}