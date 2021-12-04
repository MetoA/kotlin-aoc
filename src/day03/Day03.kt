package day03

import readInput

fun main() {
    val input = readInput("Day03")

    val exampleList = listOf(
        "00100",
        "11110",
        "10110",
        "10111",
        "10101",
        "01111",
        "00111",
        "11100",
        "10000",
        "11001",
        "00010",
        "01010"
    )

    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    val entries = mutableListOf<MutableList<Char>>()

    for (i in 0 until input[0].length) {
        entries.add(mutableListOf())
        for (entry in input) {
            entries[i].add(entry[i])
        }
    }

    var gamma = ""
    var epsilon = ""

    for (entry in entries) {
        val frequencies = entry.groupingBy { it }.eachCount()
        val ones = frequencies['1']
        val zeroes = frequencies['0']
        if (ones!! > zeroes!!) {
            gamma += "1"
            epsilon += "0"
        } else {
            gamma += "0"
            epsilon += "1"
        }
    }

    return gamma.toInt(2) * epsilon.toInt(2)
}

/** ===================== PART 2 ========================= */

tailrec fun oxygen(input: List<String>, digit: Int): String {
    if (input.size == 1 || input[0].length == digit) {
        return input[0]
    }

    val lists = input.partition { it[digit] == '1' }
    return oxygen(if (lists.first.size >= lists.second.size) lists.first else lists.second, digit + 1)
}

tailrec fun co2(input: List<String>, digit: Int): String {
    if (input.size == 1 || input[0].length-1 == digit) {
        return input[0]
    }

    val lists = input.partition { it[digit] == '0' }
    return co2(if (lists.first.size <= lists.second.size) lists.first else lists.second, digit + 1)
}

fun part2(input: List<String>) = oxygen(input, 0).toInt(2) * co2(input, 0).toInt(2)