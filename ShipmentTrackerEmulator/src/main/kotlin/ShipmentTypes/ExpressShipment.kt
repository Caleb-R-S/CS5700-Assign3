package ShipmentTypes

import Shipment
import UpdateStrategies.ShipmentUpdate

class ExpressShipment(initialUpdate: ShipmentUpdate): Shipment(initialUpdate) {
    override fun shipmentType(): String {
        return "express"
    }
}