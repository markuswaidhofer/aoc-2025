package day03

import utils.readLinesFromResource

fun main() {
    val banks = readLinesFromResource("/inputs/day03/3puzzle.txt")
    val sum = banks.sumOf {
        val numbers = mutableListOf<Int>()
        numbers.addAll(it.map { it.digitToInt() })

        var final = ""
        var i = 11
        while(i >= 0) {
            final += findAndRemoveMax(numbers, i)
            i--
        }
        final.toLong()
    }
    println("Answer is $sum")
}

private fun findAndRemoveMax(numbers: MutableList<Int>, removeLast: Int): Int {
    val numbersWithoutLast = mutableListOf<Int>()
    numbersWithoutLast.addAll(numbers)
    repeat(removeLast) {
        numbersWithoutLast.removeLast()
    }
    val max = numbersWithoutLast.max()
    val indexOfMax = numbers.indexOf(max)
    repeat(indexOfMax + 1) {
        numbers.removeAt(0)
    }
    return max
}