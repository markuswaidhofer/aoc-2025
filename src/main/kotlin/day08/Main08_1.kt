package day08

import utils.readLinesFromResource

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
