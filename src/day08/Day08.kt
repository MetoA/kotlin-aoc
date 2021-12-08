package day08

import readInput

fun main() {
    val inputMapped = readInput("Day08").map { it.split(" | ")[1] }
    val input = readInput("Day08")

    part1(inputMapped)
    part2(input)
}

fun part1(input: List<String>) {
    var count = 0
    val requiredLengths = listOf(2, 3, 4, 7)
    input.forEach { count += it.split(" ").count { letters -> letters.length in requiredLengths } }

    println(count)
}

fun part2(input: List<String>) {

    var result = 0

    for (line in input) {
        val (left, right) = line.split(" | ")
        val display = left.split(" ")
        val output = right.split(" ")

        /* 2, 3, 5 are 5 segments, 0, 6, 9 are 6 segments
         to find 3, it means that all segments of 1 have to be in 3
         to find 9, all segments of 3 have to be in 9
         to find 2, 4 segments of 2 have to be in 9
         to find 5, 3 segments of 5 have to be in 2
         to find 0, 4 segments of 0 have to be in 5
         to find 6, 2 segments of 7 have to be in 6 */

        val one = display.first { it.length == 2 }.sort()
        val four = display.first { it.length == 4 }.sort()
        val seven = display.first { it.length == 3 }.sort()
        val eight = display.first { it.length == 7 }.sort()
        val three = display.first { it.length == 5 && intersectingSegmentCount(one, it) == 2 }.sort()
        val nine = display.first { it.length == 6 && intersectingSegmentCount(three, it) == 5 }.sort()
        val two = display.first { it.length == 5 && intersectingSegmentCount(it, nine) == 4 }.sort()
        val five = display.first { it.length == 5 && intersectingSegmentCount(it, two) == 3 }.sort()
        val zero = display.first { it.length == 6 && intersectingSegmentCount(it, five) == 4 }.sort()
        val six = display.first { it.length == 6 && intersectingSegmentCount(seven, it) == 2 }.sort()

        val nums = listOf(zero, one, two, three, four, five, six, seven, eight, nine)
        val outputStr = buildString {
            for (out in output) {
                append(nums.indexOf(out.toCharArray().sorted().joinToString("")))
            }
        }

        result += outputStr.toInt()
    }

    println(result)
}

fun intersectingSegmentCount(str1: String, str2: String): Int {
    var count = 0
    val chars2 = str2.toCharArray()
    for (ch1 in str1.toCharArray()) {
        if (ch1 in chars2) count++
    }

    return count
}

fun String.sort() = this.toCharArray().sorted().joinToString("")