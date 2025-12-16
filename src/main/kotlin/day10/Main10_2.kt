package day10

import utils.readLinesFromResource

fun main() {
    val lines = readLinesFromResource("/inputs/day10/10example.txt")
    val machines: List<Machine> = lines.map { toMachine(it) }
    val minSwitchesJoltage = machines.map { it.calculateMinSwitchesJoltage() }
    val sum = minSwitchesJoltage.sum()
    println("Answer is $sum")
}
