package day08

import utils.readLinesFromResource
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val boxes = readLinesFromResource("/inputs/day08/8puzzle.txt")
        .mapIndexed {i, it -> val parts = it.split(","); Box('A' + i, parts[0].toDouble(), parts[1].toDouble(), parts[2].toDouble()) }

    boxes.forEach { it.calculateDistances(boxes) }

    repeat(1000) {
        val shortestConnection = boxes.minOf { it.findMinDistance() }
        shortestConnection.a.connect(shortestConnection.b)
    }

    val circuits = boxes.map { it.circuit }.distinct().sortedBy { -it.size }
    // println("${circuits.size} Circuits: ")
    // circuits.forEach { c -> println("${c.size}: " + c.map { it.name } ) }

    val result = circuits.take(3).map { it.size.toLong() }.reduce { a, b -> a * b }
    println("Answer is $result")
}

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

data class Connection(val distance: Double, val a: Box, val b: Box): Comparable<Connection> {
    override fun compareTo(other: Connection): Int {
        return distance.compareTo(other.distance)
    }
}
