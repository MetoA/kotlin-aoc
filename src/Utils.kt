import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src\\${name.lowercase()}", "$name.txt").readLines()

/**
 * Taken from Kotlin YouTube channel, maps the input to Int.
 */
fun readInputAsInts(name: String) = File("src", "$name.txt").readLines().map { it.toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun <T> List<T>.print() = this.forEach { println(it) }
