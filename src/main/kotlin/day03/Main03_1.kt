package day03

import utils.readLinesFromResource

fun main() {
    val banks = readLinesFromResource("/inputs/day03/3puzzle.txt")
    val sum = banks.sumOf {
        val numbers = mutableListOf<Int>()
        numbers.addAll(it.map { it.digitToInt() })

        val numbersWithoutLast = mutableListOf<Int>()
        numbersWithoutLast.addAll(numbers)
        numbersWithoutLast.removeLast()
        val first = numbersWithoutLast.max()

        val indexOfMax = numbers.indexOf(first)
        repeat(indexOfMax + 1) {
            numbers.removeAt(0)
        }

        val second = numbers.max()
        val final = "$first$second"
        println(final)
        final.toInt()
    }
    println("Answer is $sum")
}