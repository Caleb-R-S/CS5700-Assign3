package ShipmentTypes

import Shipment
import UpdateStrategies.ShipmentUpdate

class OvernightShipment(initialUpdate: ShipmentUpdate): Shipment(initialUpdate) {
    override fun shipmentType(): String {
        return "overnight"
    }
}