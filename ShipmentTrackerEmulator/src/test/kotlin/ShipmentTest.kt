import ShipmentTypes.StandardShipment
import UpdateStrategies.ShipmentUpdate
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ShipmentTest {

    @Test
    fun creationOfShipment() {
        val initialUpdate = ShipmentUpdate("s100", "created", "processing", 10000)
        val shipment = StandardShipment(initialUpdate)
        assertEquals(shipment.shipmentId, initialUpdate.shipmentId)
        assertEquals(shipment.newStatus, initialUpdate.newStatus)
        assertNull(shipment.expectedDeliveryDateTimestamp)
        assertNull(shipment.currentLocation)
        assertEquals(0, shipment.shipmentNotes.size)
        assertEquals(1, shipment.shipmentUpdateHistory.size)
    }

    @Test
    fun creationOfShipmentNotCreated() {
        val initialUpdate = ShipmentUpdate("s100", "shipped", "processing", 10000)
        val shipment = StandardShipment(initialUpdate)
        assertEquals(shipment.shipmentId, initialUpdate.shipmentId)
        assertEquals(shipment.newStatus, initialUpdate.newStatus)
        assertNull(shipment.expectedDeliveryDateTimestamp)
        assertNull(shipment.currentLocation)
        assertEquals(0, shipment.shipmentNotes.size)
        assertEquals(1, shipment.shipmentUpdateHistory.size)
    }
    @Test
    fun addObserver() {
        val initialUpdate = ShipmentUpdate("s100", "created", "processing", 10000)
        val shipment = StandardShipment(initialUpdate)
        val viewHelper = ShipmentViewHelper(shipment)
        assertEquals("created", viewHelper.shipmentStatus)
    }

    @Test
    fun removeObserver() {
        val initialUpdate = ShipmentUpdate("s100", "created", "processing", 10000)
        val shipment = StandardShipment(initialUpdate)
        val viewHelper = ShipmentViewHelper(shipment)
        shipment.removeObserver(viewHelper)
        val newUpdate = ShipmentUpdate("s100", "location", "created", 10001,location="LA California")
        shipment.update(newUpdate)
        assertNotEquals(shipment.newStatus, viewHelper.shipmentStatus)
    }

    @Test
    fun updateShipped() {
        val initialUpdate = ShipmentUpdate("s100", "created", "processing", 10000)
        val shipment = StandardShipment(initialUpdate)
        val viewHelper = ShipmentViewHelper(shipment)
        val newUpdate = ShipmentUpdate("s100", "shipped", "created", 10001, 15000)
        shipment.update(newUpdate)
        assertEquals("shipped", viewHelper.shipmentStatus)
        assertEquals(2, viewHelper.shipmentUpdateHistory.size)
    }

    @Test
    fun updateCanceled() {
        val initialUpdate = ShipmentUpdate("s100", "created", "processing", 10000)
        val shipment = StandardShipment(initialUpdate)
        val viewHelper = ShipmentViewHelper(shipment)
        val newUpdate = ShipmentUpdate("s100", "canceled", "created", 10001)
        shipment.update(newUpdate)
        assertEquals("canceled", viewHelper.shipmentStatus)
        assertEquals(2, viewHelper.shipmentUpdateHistory.size)
    }

    @Test
    fun updateDelayed() {
        val initialUpdate = ShipmentUpdate("s100", "created", "processing", 10000)
        val shipment = StandardShipment(initialUpdate)
        val viewHelper = ShipmentViewHelper(shipment)
        val shippedUpdate = ShipmentUpdate("s100", "shipped", "processing", 10000, 15000)
        shipment.update(shippedUpdate)
        assertEquals(15000, shipment.expectedDeliveryDateTimestamp)
        assertEquals(15000, viewHelper.expectedDeliveryDate)
        val newUpdate = ShipmentUpdate("s100", "delayed", "shipped", 10001, 50000)
        shipment.update(newUpdate)
        assertEquals("delayed", viewHelper.shipmentStatus)
        assertEquals(3, viewHelper.shipmentUpdateHistory.size)
        assertEquals(50000, shipment.expectedDeliveryDateTimestamp)
        assertEquals(50000, viewHelper.expectedDeliveryDate)
    }
    @Test
    fun updateDelivered() {
        val initialUpdate = ShipmentUpdate("s100", "created", "processing", 10000)
        val shipment = StandardShipment(initialUpdate)
        val viewHelper = ShipmentViewHelper(shipment)
        val newUpdate = ShipmentUpdate("s100", "delivered", "created", 10001)
        shipment.update(newUpdate)
        assertEquals("delivered", viewHelper.shipmentStatus)
        assertEquals(2, viewHelper.shipmentUpdateHistory.size)
    }
}