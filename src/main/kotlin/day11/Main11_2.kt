package day11

fun main() {
    val devicesByName = createDevices("/inputs/day11/11puzzle.txt")

    val waysToOut = devicesByName["svr"]!!.countWaysToOutRememberDacFft(false, false)
    println("Answer is $waysToOut")
}