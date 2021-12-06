package day06

import readInput

fun main() {
    val initial = readInput("Day06")[0]
    val test = "3,4,3,1,2"

    part1(initial)
    part2(initial)
}

fun part1(initial: String) {
    val fish = initial.split(",").map { it.toInt() }.toMutableList()
    var fishToAdd: Int

    repeat(80) {
        fishToAdd = 0
        for (i in fish.indices) {
            fish[i]--
            if (fish[i] < 0) {
                fish[i] = 6
                fishToAdd++
            }
        }
        repeat(fishToAdd) { fish.add(8) }
    }

    println(fish.size)
}

// Had to refactor cause of memory issues
fun part2(initial: String) {
    val map = mutableMapOf(
        8 to 0L,
        7 to 0L,
        6 to 0L,
        5 to 0L,
        4 to 0L,
        3 to 0L,
        2 to 0L,
        1 to 0L,
        0 to 0L
    )

    initial.split(",").forEach { map[it.toInt()] = map[it.toInt()]!! + 1 }

    repeat(256) {
        val zero = map[0]
        for (i in 1..8) map[i - 1] = map[i]!!
        map[8] = zero!!
        map[6] = map[6]!! + zero
    }

    var sum = 0L
    map.forEach { (_, v) -> sum += v}

    println(sum)
}
