package ShipmentTypes

import Shipment
import UpdateStrategies.ShipmentUpdate

class InvalidShipment(initialUpdate: ShipmentUpdate): Shipment(initialUpdate) {

    override fun shipmentType(): String {
        return "express"
    }}