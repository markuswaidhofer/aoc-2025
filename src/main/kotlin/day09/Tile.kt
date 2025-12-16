package day09

import java.awt.geom.Path2D
import java.awt.geom.Rectangle2D
import kotlin.math.abs

data class Tile(val x: Long, val y: Long) {
    fun calculateArea(other: Tile): Long = width(other) * height(other)

    private fun width(other: Tile): Long = (abs(x - other.x) + 1)

    private fun height(other: Tile): Long = (abs(y - other.y) + 1)

    fun isWithin(polygon: Path2D, other: Tile): Boolean {
        // adapt because contains does not allow the point to be exactly on the border
        val addToThisX = (if(this.x == other.x) 0 else if(this.x < other.x) 0.1 else -0.1).toDouble()
        val addToThisY = (if(this.y == other.y) 0 else if(this.y < other.y) 0.1 else -0.1).toDouble()
        val addToOtherX = -addToThisX
        val addToOtherY = -addToThisY

        val rectangle = rectangleFromPoints(
            x.toDouble() + addToThisX to y.toDouble() + addToThisY,
            x.toDouble() + addToThisX to other.y.toDouble() + addToOtherY,
            other.x.toDouble() + addToOtherX to y.toDouble() + addToThisY,
            other.x.toDouble() + addToOtherX to other.y.toDouble() + addToOtherY,
        )

        val contains = polygon.contains(rectangle)
        return contains
    }

    private fun rectangleFromPoints(vararg points: Pair<Double, Double>): Rectangle2D.Double {
        val xs = points.map { it.first }
        val ys = points.map { it.second }
        val minX = xs.min()
        val minY = ys.min()
        val maxX = xs.max()
        val maxY = ys.max()
        return Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY)
    }
}


