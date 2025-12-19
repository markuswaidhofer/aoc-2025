package day12

import utils.readLinesFromResource

fun main() {
    val lines = readLinesFromResource("/inputs/day12/12puzzle.txt")

    val presents = mutableListOf<Present>()
    val trees = mutableListOf<Tree>()

    var currentPresentLines = mutableListOf<String>()

    lines.forEach { line ->
        if(line.contains('x')) {
            val parts = line.split(':')
            val widthLength = parts[0].split('x')
            val numbers = parts[1].trim().split(' ')
            trees.add(Tree(widthLength[0].toInt(), widthLength[1].toInt(), listOf(
                numbers[0].toInt(), numbers[1].toInt(), numbers[2].toInt(),
                numbers[3].toInt(), numbers[4].toInt(), numbers[5].toInt(),
            )))
        } else {
            if(!line.contains(':')) {
                if(line.isEmpty()) {
                    presents.add(Present(currentPresentLines))
                    currentPresentLines = mutableListOf()
                } else {
                    currentPresentLines.add(line)
                }
            }
        }
    }

    val answer = trees.sumOf { it.isEnoughSpace(presents) }
    println("Answer is $answer")
}