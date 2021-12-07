package day07

import readInput
import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    val input = readInput("Day07")[0].split(",").map { it.toInt() }
    val testInput = listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)

    part1(input)
    part2(input)
    part2_2(input)
}

// wait this isn't even the intended way???
fun part1(input: List<Int>) {
    val result = mutableListOf<Int>()
    val positionsChecked = mutableMapOf<Int, Boolean>()

    for (posToCheck in input) {
        if (positionsChecked.containsKey(posToCheck)) continue
        positionsChecked[posToCheck] = true

        result.add(input.fold(0) { acc, num ->
            acc + abs(num - posToCheck)
        })

    }

    println(result.minOf { it })
}

fun part2(input: List<Int>) {
    val result = mutableListOf<Int>()
    val positionsChecked = mutableMapOf<Int, Boolean>()

    // I was only taking into account all numbers from the input, not others, which I should have.
    for (posToCheck in (input.minOf { it } .. input.maxOf { it })) {
        if (positionsChecked.containsKey(posToCheck)) continue
        positionsChecked[posToCheck] = true

        result.add(input.fold(0) { acc, num ->
            acc + abs(num - posToCheck).factorialSum()
        })

    }

    println(result.minOf { it })
}

fun part2_2(input:List<Int>) {
    var result = Int.MAX_VALUE

    for (pos in (input.minOf { it } .. input.maxOf { it })) {
        var res = 0
        for (num in input) {
            res += abs(num - pos).factorialSum()
            if (res > result) break
        }

        if (res < result) result = res
    }

    println(result)
}

fun Int.factorialSum(): Int {
    return (0 .. this).fold(0) { acc, num -> acc + num}
}