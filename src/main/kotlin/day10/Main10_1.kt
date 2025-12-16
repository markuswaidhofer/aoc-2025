package day10

import utils.readLinesFromResource

fun main() {
    val lines = readLinesFromResource("/inputs/day10/10puzzle.txt")
    val machines: List<Machine> = lines.map { toMachine(it) }
    val minSwitches = machines.map { it.calculateMinSwitches() }
    val sum = minSwitches.sum()
    println("Answer is $sum")
}
