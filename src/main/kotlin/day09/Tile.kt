package day09

import java.awt.Polygon
import java.awt.geom.Point2D
import kotlin.collections.get
import kotlin.compareTo
import kotlin.math.abs
import kotlin.math.min
import kotlin.text.get
import kotlin.text.toDouble
import kotlin.times

data class Tile(val x: Long, val y: Long) {
    fun calculateArea(other: Tile): Long = width(other) * height(other)

    private fun width(other: Tile): Long = (abs(x - other.x) + 1)

    private fun height(other: Tile): Long = (abs(y - other.y) + 1)

    fun isWithin(polygon: Polygon, other: Tile): Boolean {
        // adapt because contains does not allow the point to be exactly on the border
        val addToThisX = (if(this.x == other.x) 0 else if(this.x < other.x) 0.1 else -0.1).toDouble()
        val addToThisY = (if(this.y == other.y) 0 else if(this.y < other.y) 0.1 else -0.1).toDouble()
        val addToOtherX = -addToThisX
        val addToOtherY = -addToThisY

        val points = listOf(
            Point2D.Double(x.toDouble() + addToThisX, y.toDouble() + addToThisY),
            Point2D.Double(x.toDouble() + addToThisX, other.y.toDouble() + addToOtherY),
            Point2D.Double(other.x.toDouble() + addToOtherX, y.toDouble() + addToThisY),
            Point2D.Double(other.x.toDouble() + addToOtherX, other.y.toDouble() + addToOtherY),
        )

        for(point in points) {
            if(!polygon.contains(point)) {
                return false
            }
        }
        return true
    }
}
