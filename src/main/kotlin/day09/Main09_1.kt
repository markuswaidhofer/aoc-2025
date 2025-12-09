package day09

import utils.readLinesFromResource

fun main() {
    val tiles = readLinesFromResource("/inputs/day09/9puzzle.txt").map {
        val parts = it.split(",")
        Tile(parts[0].toLong(), parts[1].toLong())
    }

    var largest = 0L
    tiles.forEach { minTile ->
        tiles.forEach { maxTile ->
            val area = minTile.calculateArea(maxTile)
            if(area > largest) {
                largest = area
            }
        }
    }

    println("Answer is $largest")
}