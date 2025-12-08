package day08

import kotlin.math.pow
import kotlin.math.sqrt

data class Box(val name: Char, val x: Double, val y: Double, val z: Double) {
    var circuit: MutableSet<Box> = mutableSetOf<Box>(this)

    val distancePerUnconnectedBox = mutableMapOf<Box, Double>()

    fun calculateDistances(boxes: List<Box>) {
        boxes.forEach { box ->
            if(box != this) {
                val distance = sqrt((box.x - this.x).pow(2) + (box.y - this.y).pow(2) + (box.z - this.z).pow(2))
                distancePerUnconnectedBox[box] = distance
            }
        }
    }

    fun findMinDistance(): Connection {
        val minEntry = distancePerUnconnectedBox.minBy { it.value }
        return Connection(minEntry.value, minEntry.key, this)
    }

    fun connect(other: Box) {
        distancePerUnconnectedBox.remove(other)
        other.distancePerUnconnectedBox.remove(this)
        circuit.addAll(other.circuit)
        circuit.forEach { box -> box.circuit = this.circuit }
        // println("Connecting ${this.name} with ${other.name}, circuit size is now ${circuit.size}")
    }

}