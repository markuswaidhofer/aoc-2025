package day11

import utils.readLinesFromResource
import java.util.regex.Pattern

fun main() {
    val lines = readLinesFromResource("/inputs/day11/11puzzle.txt")

    val devicesByName = lines.flatMap { it.split(Pattern.compile(":? ")) }.toSet().map { Device(it) }.associateBy { it.name }

    lines.forEach { line ->
        val parts = line.split(':')
        val deviceToAdjust = devicesByName[parts[0]]

        val toConnect = parts[1].trim().split(' ').map { devicesByName[it]!! }

        deviceToAdjust!!.connect(toConnect)
    }

    val waysToOut = devicesByName["you"]!!.countWaysToOut()
    println("Answer is $waysToOut")
}