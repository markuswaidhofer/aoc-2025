package day11

fun main() {
    val devicesByName = createDevices("/inputs/day11/11puzzle.txt")

    val waysToOut = devicesByName["you"]!!.countWaysTo("out")
    println("Answer is $waysToOut")
}
