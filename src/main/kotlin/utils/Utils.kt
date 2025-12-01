package utils

class Utils

fun readInput(input: String): List<String>
    = Utils::class.java.getResource(input)!!.readText().split("\n")