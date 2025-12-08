package day07

import utils.readLinesFromResource

fun main() {
    var rows: List<List<Char>> = readLinesFromResource("/inputs/day07/7puzzle.txt").map { it.replace('S', '|').toList() }
    val rowLength = rows[0].size


    var changed = true
    while (changed) {
        changed = false
        rows = rows.mapIndexed { y, row ->
            row.mapIndexed { x, field ->
                if (y != 0 && field == '.') { // potential candidate for a beam
                    if (
                        (rows[y - 1][x] == '|') // beam directly above
                        || (x + 1 < rowLength && rows[y][x + 1] == '^' && rows[y - 1][x + 1] == '|') // beam split on left side
                        || (x > 0 && rows[y][x - 1] == '^' && rows[y - 1][x - 1] == '|') // beam split on right side
                    ) {
                        changed = true
                        '|'
                    } else '.'
                } else field
            }
        }
    }

    printRows(rows)

    var count = 0
    rows.forEachIndexed { y, row ->
        row.forEachIndexed { x, field ->
            if(y > 0 && field == '^' && rows[y - 1][x] == '|') {
                count++
            }
        }
    }

    println("Answer is $count")


}

private fun printRows(rows: List<List<Char>>) {
    println("Rows:")
    rows.forEach { println(it.joinToString("")) }
}