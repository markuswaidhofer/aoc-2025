package day09

import utils.readLinesFromResource
import java.awt.geom.Path2D


fun main() {
    val tiles = readLinesFromResource("/inputs/day09/9puzzle.txt").map {
        val parts = it.split(",")
        Tile(parts[0].toLong(), parts[1].toLong())
    }

    val polygon = createPolygon(tiles)

    var largest = 0L
    tiles.forEach { minTile ->
        tiles.forEach { maxTile ->
            val area = minTile.calculateArea(maxTile)
            if(area > largest && minTile.isWithin(polygon, maxTile)) {
                largest = area
            }
        }
    }

    println("Answer is $largest")
}

fun createPolygon(tiles: List<Tile>): Path2D {
    val polygon = Path2D.Double()

    val mutableTiles = tiles.toMutableList()
    var current = mutableTiles[0]
    polygon.moveTo(current.x.toDouble(), current.y.toDouble())
    mutableTiles.removeAt(0)

    while(mutableTiles.isNotEmpty()) {
        var i = 0
        while (i < mutableTiles.size) {
            val toCompare = mutableTiles[i]
            if(current.x == toCompare.x || current.y == toCompare.y) {
                current = mutableTiles[i]
                polygon.lineTo(current.x.toDouble(), current.y.toDouble())
                mutableTiles.removeAt(0)
                break
            }
            i++
        }
    }
    polygon.closePath()

    return polygon
}

