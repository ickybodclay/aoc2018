import java.io.File

fun main() {
    val input = File(ClassLoader.getSystemResource("input.txt").file)

    var frequency = 0
    val changeList = ArrayList<Int>()
    input.readLines().map { line ->
        val change = line.trim().toInt()
        changeList.add(change)
        frequency += change
    }
    println("[part1] frequency result = $frequency")

    frequency = 0
    var duplicateFreq = 0
    var duplicateFound = false
    val frequencySet = HashSet<Int>()
    frequencySet.add(frequency)
    while (!duplicateFound) {
        changeList.forEach { change ->
            frequency += change
            if (!duplicateFound) {
                if (frequencySet.contains(frequency)) {
                    duplicateFreq = frequency
                    duplicateFound = true
                } else {
                    frequencySet.add(frequency)
                }
            }
        }
    }
    println("[part2] first duplicate = $duplicateFreq")
}
