package day07

import utils.readLinesFromResource

private const val SPLITTER = -1L

fun main() {
    val rows: List<MutableList<Long>> = readLinesFromResource("/inputs/day07/7puzzle.txt")
        .map { line ->
            line.map {
                when (it) {
                    '^' -> SPLITTER
                    'S' -> 1L
                    else -> 0L // '.'
                }
            }.toMutableList()
        }
    val rowLength = rows[0].size

    var y = 1
    while (y < rows.size) {
        var x = 0
        while (x < rowLength) {
            var current = rows[y][x]

            if (current != SPLITTER) {
                if(rows[y - 1][x] > 0) { // beam directly above
                    current += rows[y - 1][x]
                }
                if (x + 1 < rowLength && rows[y][x + 1] == SPLITTER) { // beam split on left side
                    current += rows[y - 1][x + 1]
                }
                if (x > 0 && rows[y][x - 1] == SPLITTER) { // beam split on right side
                    current += rows[y - 1][x - 1]
                }
            }

            rows[y][x] = current
            x++
        }
        y++
    }

    val combinations = rows[rows.size - 1].sum()
    println("Answer is $combinations")


}

private fun printRows(rows: List<List<Long>>) {
    println("Rows:")
    rows.forEach { row ->
        row.forEach { print(String.format("%2d", it)) }
        println()
    }
}
