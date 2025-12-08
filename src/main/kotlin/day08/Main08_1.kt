package day08

import utils.readLinesFromResource
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val boxes = readLinesFromResource("/inputs/day08/8example.txt")
        .map { val parts = it.split(","); Box(parts[0].toDouble(), parts[1].toDouble(), parts[2].toDouble()) }

    boxes.forEach { it.calculateDistances(boxes) }

    val shortestConnection = boxes.minOf { it.findMinDistance() }

    println(shortestConnection)
}

data class Box(val x: Double, val y: Double, val z: Double) {
    val connections: MutableList<Box> = mutableListOf<Box>()

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
}

data class Connection(val distance: Double, val a: Box, val b: Box): Comparable<Connection> {
    override fun compareTo(other: Connection): Int {
        return distance.compareTo(other.distance)
    }
}
