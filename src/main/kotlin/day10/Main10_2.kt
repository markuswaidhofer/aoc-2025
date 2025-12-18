package day10

import utils.readLinesFromResource

fun main() {
    val lines = readLinesFromResource("/inputs/day10/10puzzle.txt")
    val machines: List<Machine> = lines.map { toMachine(it) }
    val minSwitchesJoltage = machines.map { it.calculateMinSwitchesJoltage().also { println("Calculated " + it) } }
    val sum = minSwitchesJoltage.sum()
    println("Answer is $sum")
}
