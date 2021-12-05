package day05

import readInput

fun main() {
    val input = readInput("Day05")
    val example = listOf(
        "0,9 -> 5,9",
        "8,0 -> 0,8",
        "9,4 -> 3,4",
        "2,2 -> 2,1",
        "7,0 -> 7,4",
        "6,4 -> 2,0",
        "0,9 -> 2,9",
        "3,4 -> 1,4",
        "0,0 -> 8,8",
        "5,5 -> 8,2"
    )

    part1(input)
    part2(input)
}

fun part1(input: List<String>) {
    val map = mutableMapOf<String, Int>()

    input.forEach {
        val (x1, y1, x2, y2) = getXY(it)
        if (x1 == x2)
            for (i in (if (y1 > y2) y2..y1 else y1..y2))
                if (map.containsKey("$x1,$i")) map["$x1,$i"] = map["$x1,$i"]!! + 1
                else map["$x1,$i"] = 1
        else if (y1 == y2)
            for (i in (if (x1 > x2) x2..x1 else x1..x2))
                if (map.containsKey("$i,$y1")) map["$i,$y1"] = map["$i,$y1"]!! + 1
                else map["$i,$y1"] = 1
    }

    println(map.filter { (_, v) -> v >= 2 }.count())
}

fun part2(input: List<String>) {
    val map = mutableMapOf<String, Int>()

    input.forEach {
        val (x1, y1, x2, y2) = getXY(it)
        if (x1 == x2) {
            for (i in (if (y1 > y2) y2..y1 else y1..y2))
                if (map.containsKey("$x1,$i")) map["$x1,$i"] = map["$x1,$i"]!! + 1
                else map["$x1,$i"] = 1
        } else if (y1 == y2) {
            for (i in (if (x1 > x2) x2..x1 else x1..x2))
                if (map.containsKey("$i,$y1")) map["$i,$y1"] = map["$i,$y1"]!! + 1
                else map["$i,$y1"] = 1
        } else {
            val xRange = if (x1 > x2) (x2..x1) else (x2 downTo x1)
            val yRange = if (y1 > y2) (y2..y1) else (y2 downTo y1)
            for ((x, y) in xRange.zip(yRange))
                if (map.containsKey("$x,$y")) map["$x,$y"] = map["$x,$y"]!! + 1
                else map["$x,$y"] = 1
        }
    }

    println(map.filter { (_, v) -> v >= 2 }.count())
}

fun getXY(line: String): List<Int> {
    val (one, two) = line.split(" -> ")
    val (x1, y1) = one.split(",").map { it.toInt() }
    val (x2, y2) = two.split(",").map { it.toInt() }

    return listOf(x1, y1, x2, y2)
}