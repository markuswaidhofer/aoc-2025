package day09

import kotlin.math.abs

data class Tile(val x: Long, val y: Long) {
    fun calculateArea(other: Tile): Long = (abs(x - other.x) + 1) * (abs(y - other.y) + 1)
}