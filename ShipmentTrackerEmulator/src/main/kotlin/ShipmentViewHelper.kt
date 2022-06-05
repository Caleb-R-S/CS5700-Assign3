import UpdateStrategies.ShipmentUpdate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ShipmentViewHelper(shipment: Shipment) : Observer{
    var shipmentId by mutableStateOf<String?>("")
    var shipmentStatus by mutableStateOf<String?>("")
    var expectedDeliveryDate by mutableStateOf<Long?>(null)
    var location by mutableStateOf<String?>("")
    var shipmentNotes = mutableStateListOf<String>()
    var shipmentUpdateHistory = mutableStateListOf<ShipmentUpdate>()
    var isOnTrack by mutableStateOf(false)
    init {
        shipment.addObserver(this)
        shipmentId = shipment.shipmentId
        shipmentStatus = shipment.newStatus
        location = shipment.currentLocation
        isOnTrack = shipment.isOnTrack
        for (shipmentNote in shipment.shipmentNotes) {
            shipmentNotes.add(shipmentNote)
        }
        for (update in shipment.shipmentUpdateHistory) {
            shipmentUpdateHistory.add(update)
        }
    }
    override fun update(shippingUpdate: ShipmentUpdate) {
        shipmentUpdateHistory.add(shippingUpdate)
        if (shippingUpdate.note != null) {
            shipmentNotes.add(shippingUpdate.note)
        }
        shipmentId = shippingUpdate.shipmentId ?: shipmentId
        expectedDeliveryDate = shippingUpdate.expectedDelivery ?: expectedDeliveryDate
        shipmentStatus = shippingUpdate.newStatus?: shipmentStatus
        location = shippingUpdate.location ?: location
    }

    override fun invalidateShipment() {
        isOnTrack = false
    }
}