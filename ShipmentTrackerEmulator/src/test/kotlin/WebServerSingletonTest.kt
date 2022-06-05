import ShipmentTypes.StandardShipment
import UpdateStrategies.ShipmentUpdate
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

internal class WebServerSingletonTest {

    @Test
    fun runServer() {
        WebServerSingleton.runServer()
        val client = HttpClient()

        runBlocking {
            launch {
                var responseCreated: HttpResponse = client.get("http://127.0.0.1:8080/update?shipmentId=s10&newStatus=created&timestamp=160225465&additionalInformation=&type=standard")
                var responseShipped: HttpResponse = client.get("http://127.0.0.1:8080/update?shipmentId=s10&newStatus=shipped&timestamp=160225465&additionalInformation=1000000&type=standard")
                var responseDelayed: HttpResponse = client.get("http://127.0.0.1:8080/update?shipmentId=s10&newStatus=delayed&timestamp=160225465&additionalInformation=1000000&type=standard")
                var responseCanceled: HttpResponse = client.get("http://127.0.0.1:8080/update?shipmentId=s10&newStatus=canceled&timestamp=160225465&additionalInformation=1000000&type=standard")
                var responseNoteAdded: HttpResponse = client.get("http://127.0.0.1:8080/update?shipmentId=s10&newStatus=noteadded&timestamp=160225465&additionalInformation=1000000&type=standard")
                var responseLost: HttpResponse = client.get("http://127.0.0.1:8080/update?shipmentId=s10&newStatus=lost&timestamp=160225465&additionalInformation=1000000&type=standard")
                var responseLocation: HttpResponse = client.get("http://127.0.0.1:8080/update?shipmentId=s10&newStatus=location&timestamp=160225465&additionalInformation=1000000&type=standard")
                var responseDelivered: HttpResponse = client.get("http://127.0.0.1:8080/update?shipmentId=s10&newStatus=delivered&timestamp=160225465&additionalInformation=1000000&type=standard")

                assertEquals("200 OK", responseCreated.status.toString())
                assertEquals("200 OK", responseCanceled.status.toString())
                assertEquals("200 OK", responseDelayed.status.toString())
                assertEquals("200 OK", responseDelivered.status.toString())
                assertEquals("200 OK", responseLost.status.toString())
                assertEquals("200 OK", responseLocation.status.toString())
                assertEquals("200 OK", responseShipped.status.toString())
                assertEquals("200 OK", responseNoteAdded.status.toString())
            }
        }
    }

    @Test
    fun shipmentFactoryStandard() {
        val initialUpdate = ShipmentUpdate("s100", "created", "processing", 10000)
        val newShipment = WebServerSingleton.shipmentFactory(initialUpdate, "standard")
        assertEquals("standard", newShipment.shipmentType())
        assertTrue(newShipment is StandardShipment)
    }

    @Test
    fun shipmentFactoryOvernight() {
        val initialUpdate = ShipmentUpdate("s100", "created", "processing", 10000)
        val newShipment = WebServerSingleton.shipmentFactory(initialUpdate, "overnight")
        assertEquals("overnight", newShipment.shipmentType())
    }

    @Test
    fun shipmentFactoryExpress() {
        val initialUpdate = ShipmentUpdate("s100", "created", "processing", 10000)
        val newShipment = WebServerSingleton.shipmentFactory(initialUpdate, "express")
        assertEquals("express", newShipment.shipmentType())
    }

    @Test
    fun shipmentFactoryBulk() {
        val initialUpdate = ShipmentUpdate("s100", "created", "processing", 10000)
        val newShipment = WebServerSingleton.shipmentFactory(initialUpdate, "bulk")
        assertEquals("bulk", newShipment.shipmentType())
    }

    @Test
    fun findShipmentNull() {
        val initialUpdate = ShipmentUpdate("s100", "created", "processing", 10000)
        val newShipment = WebServerSingleton.shipmentFactory(initialUpdate, "bulk")
        val result = WebServerSingleton.findShipment("s100")
        assertNull(result)
    }

    @Test
    fun findShipmentNotNull() {
        val initialUpdate = ShipmentUpdate("s100", "created", "processing", 10000)
        val newShipment = WebServerSingleton.shipmentFactory(initialUpdate, "bulk")
        WebServerSingleton.addShipment(newShipment)
        val result = WebServerSingleton.findShipment("s100")
        assertNotNull(result)
    }
}