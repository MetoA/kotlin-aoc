import kotlin.system.measureTimeMillis

fun main() {
    val input = readInput("Day02").map { it.split(" ") }.map { Pair(it[0], it[1].toInt()) }

    println(part1(input))
    println(part2(input))
    println(part2MorePerformant(readInput("Day02")))
    println("part2Millis: $part2Millis")
    println("part2PerformanceMillis: $part2PerformantMillis")
}

fun part1(input: List<Pair<String, Int>>): Int =
    /**                      x  y               */
    input.fold(mutableListOf(0, 0)) { acc, pair ->
        acc.also {
            when (pair.first) {
                "forward" -> acc[0] += pair.second
                "up" -> acc[1] -= pair.second
                "down" -> acc[1] += pair.second
            }
        }
    }
        .fold(1) { acc, value -> acc * value }

fun part2(input: List<Pair<String, Int>>): Int =
    /**                      x  y aim              */
    input.fold(mutableListOf(0, 0, 0)) { acc, pair ->
        acc.also {
            when (pair.first) {
                "forward" -> {
                    acc[0] += pair.second
                    acc[1] += acc[2] * pair.second
                }
                "up" -> acc[2] -= pair.second
                "down" -> acc[2] += pair.second
            }
        }
    }
        .take(2)
        .fold(1) { acc, value -> acc * value }

/** This was done for fun to test performance difference, results below.*/
fun part2MorePerformant(input: List<String>): Int {
    var x = 0
    var y = 0
    var aim = 0

    for(entry in input) {
        val count = "${entry[entry.length - 1]}".toInt()
        when (entry[0]) {
            'f' -> {
                x += count
                y += aim * count
            }
            'u' -> aim -= count
            'd' -> aim += count
        }
    }

    return x * y
}

/** Most of the time it is around 100 milliseconds or above*/
val part2Millis = measureTimeMillis {
    val input = readInput("Day02").map { it.split(" ") }.map { Pair(it[0], it[1].toInt()) }
    input.fold(mutableListOf(0, 0, 0)) { acc, pair ->
        acc.also {
            when (pair.first) {
                "forward" -> {
                    acc[0] += pair.second
                    acc[1] += acc[2] * pair.second
                }
                "up" -> acc[2] -= pair.second
                "down" -> acc[2] += pair.second
            }
        }
    }
        .take(2)
        .fold(1) { acc, value -> acc * value }
}

/** Most of the time it is 0 milliseconds or a bit over that */
val part2PerformantMillis = measureTimeMillis {
    var x = 0
    var y = 0
    var aim = 0
    val input = readInput("Day02")

    for(entry in input) {
        val count = "${entry[entry.length - 1]}".toInt()
        when (entry[0]) {
            'f' -> {
                x += count
                y += aim * count
            }
            'u' -> aim -= count
            'd' -> aim += count
        }
    }
}