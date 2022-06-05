package ShipmentTypes

import Shipment
import UpdateStrategies.ShipmentUpdate

class BulkShipment(initialUpdate: ShipmentUpdate): Shipment(initialUpdate) {
    override fun shipmentType(): String {
        return "bulk"
    }
}