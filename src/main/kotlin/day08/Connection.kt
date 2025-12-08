package day08

data class Connection(val distance: Double, val a: Box, val b: Box): Comparable<Connection> {
    override fun compareTo(other: Connection): Int {
        return distance.compareTo(other.distance)
    }
}