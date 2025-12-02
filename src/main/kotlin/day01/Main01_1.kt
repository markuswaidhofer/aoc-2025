package day01

import utils.readLinesFromResource

fun main() {
    val input = readLinesFromResource("/inputs/day01/1puzzle.txt")

    var password = 0
    var current = 50

    input.forEach {
        val direction = it[0]
        val number = it.substring(1).toInt()
        current = (current + (if (direction == 'R') number else -number)) % 100
        if(current == 0) {
            password += 1
        }
    }

    println("The password is $password")
}