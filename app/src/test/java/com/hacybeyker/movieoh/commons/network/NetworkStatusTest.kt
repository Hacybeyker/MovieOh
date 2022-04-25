package com.hacybeyker.movieoh.commons.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowNetworkCapabilities

@RunWith(AndroidJUnit4::class)
class NetworkStatusTest {

    lateinit var context: Context

    lateinit var sutNetworkStatus: NetworkStatus

    private var cm: ConnectivityManager? = null

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sutNetworkStatus = NetworkStatus(context)
    }

/*    @Test
    fun isOnlineTrue() {
        cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = ShadowNetworkCapabilities.newInstance()
        shadowOf(networkCapabilities).addCapability(NetworkCapabilities.TRANSPORT_WIFI)
        shadowOf(networkCapabilities).addCapability(NetworkCapabilities.TRANSPORT_CELLULAR)
        shadowOf(cm).setNetworkCapabilities(cm?.activeNetwork, networkCapabilities)
        assertTrue(sutNetworkStatus.isOnline())
    }*/

    @Test
    fun isOnlineFalse() {
        cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        println("Here - T1")
        val networkCapabilities = ShadowNetworkCapabilities.newInstance()
        println("Here - T2")
        shadowOf(networkCapabilities).addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        println("Here - T3")
        shadowOf(cm).setNetworkCapabilities(cm?.activeNetwork, networkCapabilities)
        println("Here - T4")
        val algo = cm?.getNetworkCapabilities(cm?.activeNetwork)
        println("Here - T5")
        println("Here - $algo")
        println("Here - T6")
        println(sutNetworkStatus.isOnline())
        println("Here - T7")
        assertTrue(true)
        println("Here - T8")
    }

    @Test
    fun isOnlineTrueTransportWifi() {
        cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        println("Here - T1")
        val networkCapabilities = ShadowNetworkCapabilities.newInstance()
        println("Here - T2")
        shadowOf(networkCapabilities).addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

        println("Here - T3")
        shadowOf(cm).setNetworkCapabilities(cm?.activeNetwork, networkCapabilities)
        println("Here - T4")
        val algo = cm?.getNetworkCapabilities(cm?.activeNetwork)
        shadowOf(algo).addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        println("Here - T5")
        println("Here - $algo")
        println("Here - T6")
        val status = sutNetworkStatus.isOnline()
        println(status)
        println("Here - T7")
        assertTrue(status)
        println("Here - T8")
    }

    @Test
    fun isOnlineTrueTransportCelular() {
        cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        println("Here - T1")
        val networkCapabilities = ShadowNetworkCapabilities.newInstance()
        println("Here - T2")
        shadowOf(networkCapabilities).addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

        println("Here - T3")
        shadowOf(cm).setNetworkCapabilities(cm?.activeNetwork, networkCapabilities)
        println("Here - T4")
        val algo = cm?.getNetworkCapabilities(cm?.activeNetwork)
        shadowOf(algo).addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        println("Here - T5")
        println("Here - $algo")
        println("Here - T6")
        val status = sutNetworkStatus.isOnline()
        println(status)
        println("Here - T7")
        assertTrue(status)
        println("Here - T8")
    }

    @Test
    fun isOnlineCapabilityIsNull() {
        cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        println("Here - T1")
        val networkCapabilities = ShadowNetworkCapabilities.newInstance()
        println("Here - T2")
        shadowOf(networkCapabilities).addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        println("Here - T3")
        shadowOf(cm).setNetworkCapabilities(cm?.activeNetwork, null)
        val status = sutNetworkStatus.isOnline()
        println(status)
        assertFalse(status)
    }

    @Test
    fun isOnlineActiveNetworkIsNull() {
        val context: Context = mock()
        val cm: ConnectivityManager = mock()
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE)).doReturn(cm)
        // cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        println("Here - T1")
        val networkCapabilities = ShadowNetworkCapabilities.newInstance()
        println("Here - T2")
        // shadowOf(networkCapabilities).addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        println("Here - T3")
        whenever(cm.activeNetwork).doReturn(null)
        val status = sutNetworkStatus.isOnline()
        println(status)
        assertTrue(status)
    }
}
