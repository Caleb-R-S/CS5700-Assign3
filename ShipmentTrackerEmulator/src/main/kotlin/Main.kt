// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import UpdateStrategies.ShipmentUpdate
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope

@Composable
@Preview
fun App() {


    var text by remember {mutableStateOf("")}
    println(text)
    var viewHelpers = remember { mutableStateListOf<ShipmentViewHelper>() }
    var unknownShipments = mutableStateListOf<String>()
    var coroutineScope = rememberCoroutineScope()
    var trackingSimulator = remember {TrackingSimulator(coroutineScope)}
    println("I also gety called")


//    //Test stuff
//    var initialUpdate = ShipmentUpdate(updateType = "Created", shipmentId = "1234", previousStatus  = null, newStatus = "Created", location = "Tokyo")
//    var nextUpdate = ShipmentUpdate(updateType = "Shipped", previousStatus = "Created", newStatus = "Shipped", location = "Japan", note="Package overweight")
//    var finalUpdate = ShipmentUpdate(updateType = "Delivered", previousStatus = "Created", newStatus = "Delivered", location = "USA", note="Package dropped")
//    var shipment = Shipment(initialUpdate)
//    shipment.update(nextUpdate)
//    shipment.update(finalUpdate)
//    var firstView = ShipmentViewHelper(shipment)
//    viewHelpers.add(firstView)
    remember {
        trackingSimulator.runSimulation()
    }
    MaterialTheme {
        Column(modifier = Modifier.fillMaxWidth()){
            Row(modifier = Modifier.fillMaxWidth()) {
                Row {
                    TextField(value = text,
                        onValueChange = {text = it},
                        label = {Text("Enter a tracking number")},
                        modifier = Modifier.height(64.dp).weight(5f)
                    )

                    Button(onClick = {
                        val shipment = trackingSimulator.findShipment(text)
                             if (shipment != null) {
                                 viewHelpers.add(ShipmentViewHelper(shipment))
                             } else {
                                unknownShipments.add(text)
                             }
                    }, modifier = Modifier.height(64.dp).weight(1f)) {
                        Text("Track")
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth()){
                LazyColumn{
                    println("Third times a charm")
                    items(viewHelpers) { viewHelper ->
                        Card(elevation = 16.dp, modifier = Modifier.padding(8.dp).border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10)), shape = RoundedCornerShape(10)) {
                            Row {
                                trackShipmentView(viewHelper)
                                Column(modifier = Modifier.align(Alignment.Top).padding(8.dp)) {
                                    IconButton(
                                        onClick = {
                                            viewHelpers.remove(viewHelper)
                                        },
                                        modifier = Modifier.align(Alignment.End).border(1.dp, Color.Black, CircleShape)
                                    ) {
                                        Icon(Icons.Default.Close, "Remove shipment from tracking list.")
                                    }
                                }
                            }
                        }
                    }

                    items(unknownShipments) { shipment ->
                        Card(elevation = 16.dp, modifier = Modifier.padding(8.dp).border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10)), shape = RoundedCornerShape(10)) {
                            Row {
                                shipmentNotFound(shipment)
                                Column(modifier = Modifier.align(Alignment.Top).padding(8.dp)) {
                                    IconButton(
                                        onClick = {
                                            unknownShipments.remove(shipment)
                                        },
                                        modifier = Modifier.align(Alignment.End).border(1.dp, Color.Black, CircleShape)) {
                                        Icon(Icons.Default.Close, "Remove shipment from tracking list.")
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
