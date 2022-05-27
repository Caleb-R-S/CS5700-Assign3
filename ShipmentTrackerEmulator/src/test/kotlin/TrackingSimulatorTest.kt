import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.lang.instrument.Instrumentation

internal class TrackingSimulatorTest {

    @Test
    fun runSimulation() {
        val trackingSimulator = TrackingSimulator(CoroutineScope(Dispatchers.Default))
        trackingSimulator.waitTime = 1
        trackingSimulator.runSimulation()

        while(trackingSimulator.shipments.size < 10) {println(trackingSimulator.shipments.size)}
        assertEquals(10, trackingSimulator.shipments.size)
        for (i in 0..9) {
            assertEquals("s1000${i}", trackingSimulator.shipments[i].shipmentId)
        }
    }

    @Test
    fun findShipmentRealShipment() {
        val trackingSimulator = TrackingSimulator(CoroutineScope(Dispatchers.Default))
        trackingSimulator.waitTime = 1
        trackingSimulator.runSimulation()

        while(trackingSimulator.shipments.size < 10) {println(trackingSimulator.shipments.size)}
        val testShipment = trackingSimulator.findShipment("s10004")
        assertEquals(testShipment?.shipmentId, trackingSimulator.shipments[4].shipmentId)
    }

    @Test
    fun findShipmentFakeShipment() {
        val trackingSimulator = TrackingSimulator(CoroutineScope(Dispatchers.Default))
        trackingSimulator.waitTime = 1
        trackingSimulator.runSimulation()

        while(trackingSimulator.shipments.size < 10) {println(trackingSimulator.shipments.size)}
        val testShipment = trackingSimulator.findShipment("s14")
        assertEquals(null, testShipment?.shipmentId)
    }
}