package utils

class Utils

fun readLinesFromResource(input: String): List<String> = readLineFromResource(input).split("\n")

fun readLineFromResource(input: String): String = Utils::class.java.getResource(input)!!.readText()