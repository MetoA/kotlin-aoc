package day01

import readInputAsInts

fun main() {
    val input: List<Int> = readInputAsInts("Day01")

    println(part1(input))
    println(part1Ver2(input))
    println(part2(input))
}

fun part1(list: List<Int>): Int {
    var count = 0
    for ((i, value) in list.withIndex()) {
        if (i != 0) {
            if (value > list[i-1]) count++
        }
    }

    return count
}


fun part2(list: List<Int>) =
    list.windowed(3)
        .fold(mutableListOf<Int>()){ acc, window -> acc.also { it.add(window.sum()) }}
        .windowed(2).count { (a, b) -> b > a}


/** Seen from the Kotlin YouTube channel*/
fun part1Ver2(list: List<Int>) = list.windowed(2).count { (a, b) -> a < b }
