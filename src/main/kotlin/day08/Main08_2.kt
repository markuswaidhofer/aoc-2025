package day08

import utils.readLinesFromResource

fun main() {
    val boxes = readLinesFromResource("/inputs/day08/8puzzle.txt")
        .mapIndexed {i, it -> val parts = it.split(","); Box('A' + i, parts[0].toDouble(), parts[1].toDouble(), parts[2].toDouble()) }

    boxes.forEach { it.calculateDistances(boxes) }

    var shortestConnection: Connection? = null
    while(numberOfCircuits(boxes) > 1) {
        shortestConnection = boxes.minOf { it.findMinDistance() }
        shortestConnection.a.connect(shortestConnection.b)
    }

    println("Last connected box was ${shortestConnection?.a} and ${shortestConnection?.b}")
    val result = shortestConnection!!.a.x.toLong() * shortestConnection.b.x.toLong()

    println("Answer is $result")
}

fun numberOfCircuits(boxes: List<Box>): Int {
    return boxes.map { it.circuit }.distinct().size
}
