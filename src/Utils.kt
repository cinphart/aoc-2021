import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun List<String>.asInts(): List<Int> = map{Integer.parseInt(it.trim())}

fun String.splitToInts(sep : Char = ',') = trim().split(sep).filter{it.length>0}.map{Integer.parseInt(it.trim())}

