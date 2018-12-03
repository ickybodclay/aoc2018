import java.io.File

fun main() {
    val input = File(ClassLoader.getSystemResource("input.txt").file)

    // Write solution here!
    var checksumA = 0
    var checksumB = 0

    val idList = ArrayList<String>()

    input.readLines().map{line ->
        idList.add(line)
        val letterCountMap = HashMap<Char, Int>()
        val letters = line.toCharArray()
        letters.forEach {
            if (letterCountMap.containsKey(it)) {
                letterCountMap[it] = letterCountMap[it]!!.plus(1)
            }
            else {
                letterCountMap[it] = 1
            }
        }

        var twiceCount = 0
        var thriceCount = 0
        letterCountMap.map {
            when (it.value) {
                2 ->  twiceCount++
                3 -> thriceCount++
                else -> {
                    // do nothing
                }
            }
        }

        if (twiceCount > 0) checksumA++
        if (thriceCount > 0) checksumB++
    }

    println("[part1] checksum = ${checksumA * checksumB}")

    var minDiff = Integer.MAX_VALUE
    var minIndexA = 0
    var minIndexB = 0

    idList.forEachIndexed { i, strA ->
        idList.forEachIndexed { j, strB ->
            if (i != j) {
                val d = diff(strA, strB)
                if (d < minDiff) {
                    minDiff = d
                    minIndexA = i
                    minIndexB = j
                }
            }
        }
    }

    println("[part2] diff = $minDiff - ${idList[minIndexA]}, ${idList[minIndexB]}")
    printCommonLetters(idList[minIndexA], idList[minIndexB])
}

fun diff(strA: String, strB: String) : Int {
    var diff = 0
    for (i in 0 until strA.length) {
        if (strA[i] != strB[i]) {
            diff++
        }
    }
    return diff
}

fun printCommonLetters(strA: String, strB: String) {
    for (i in 0 until strA.length) {
        if (strA[i] == strB[i]) {
            print(strA[i])
        }
    }
    println()
}


