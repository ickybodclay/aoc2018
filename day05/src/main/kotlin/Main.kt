import java.io.File

fun main() {
    val input = File(ClassLoader.getSystemResource("input.txt").file)

    // Write solution here!
    input.readLines().map { line ->
        val polymerStr = getReactingPolymer(line)
        println("polymer string = $polymerStr")
        println("[part1] polymer count = ${polymerStr.length}")

        var minUnit = ' '
        var minLength = Int.MAX_VALUE
        val alphabet = "abcdefghijklmnopqrstuvwxyz"
        alphabet.forEach { c ->
            val testPolymer = line.replace("$c", "", true)
            //println ("Removing $c : $testPolymer")
            val resultPolymer = getReactingPolymer(testPolymer)
            if (resultPolymer.length < minLength) {
                minUnit = c
                minLength = resultPolymer.length
            }
        }
        println("[part2] shortest polymer = $minLength by removing $minUnit")
    }
}

fun getReactingPolymer(polymer: String) : String {
    var polymerStr = ""
    var prevChar = ' '
    polymer.forEach { c ->
        if ((c.isUpperCase() && c.toLowerCase() == prevChar) || (prevChar.isUpperCase() && prevChar.toLowerCase() == c)) {
            //println("Reaction from $prevChar + $c, removing")
            polymerStr = polymerStr.substring(0, polymerStr.length - 1)
            prevChar = if (polymerStr.isNotEmpty()) polymerStr.last() else ' '
        }
        else {
            polymerStr += c
            prevChar = c
        }
    }
    return polymerStr
}
