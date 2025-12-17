package day10

import utils.readLinesFromResource

fun main() {
    val lines = readLinesFromResource("/inputs/day10/10puzzle.txt")
    val machines: List<Machine> = lines.map { toMachine(it) }
    println("Total machines: " + machines.size)
    val minSwitchesJoltage = machines.mapIndexed { index, machine -> machine.calculateMinSwitchesJoltage().also { println("Finished index $index") } }
    val sum = minSwitchesJoltage.sum()
    println("Min switches is ${minSwitchesJoltage}, Answer is $sum")
}
