import java.io.File

data class Claim(
        val claimNum: Int,
        val offsetX: Int,
        val offsetY: Int,
        val width: Int,
        val height: Int)

fun main() {
    val input = File(ClassLoader.getSystemResource("input.txt").file)

    // Write solution here!
    val claimList = ArrayList<Claim>()
    val claimRegex = Regex("#(\\d+) @ (\\d+),*(\\d+): (\\d+)x(\\d+)")

    input.readLines().map {
        val claimMatch = claimRegex.matchEntire(it)!!
        val claim = Claim(
                claimMatch.groups[1]!!.value.toInt(),
                claimMatch.groups[2]!!.value.toInt(),
                claimMatch.groups[3]!!.value.toInt(),
                claimMatch.groups[4]!!.value.toInt(),
                claimMatch.groups[5]!!.value.toInt())
        claimList.add(claim)
    }

    val maxWidth = 1000
    val maxHeight = 1000
    val fabricGrid = Array(maxWidth) { IntArray(maxHeight) }
    claimList.forEach {
        mapClaim(fabricGrid, it)
    }
    //printGrid(fabricGrid)
    printPart1Solution(fabricGrid)
    printPart2Solution(fabricGrid, claimList)
}

fun mapClaim(grid: Array<IntArray>, claim: Claim) {
    for (x in claim.offsetX until claim.width + claim.offsetX) {
        for (y in claim.offsetY until claim.height + claim.offsetY) {
            grid[x][y]++
        }
    }
}

fun printPart1Solution(grid: Array<IntArray>) {
    var sqInches = 0
    for (x in 0 until 1000) {
        for (y in 0 until 1000) {
            if (grid[x][y] > 1) {
                sqInches++
            }
        }
    }
    println("[part1] square inches of fabric are within two or more claims = $sqInches")
}

fun printPart2Solution(grid: Array<IntArray>, claimList: List<Claim>) {
    claimList.forEach {
        if (!checkClaimForOverlap(grid, it)) {
            println("[part2] claim that doesn't overlap = $it")
        }
    }
}

fun checkClaimForOverlap(grid: Array<IntArray>, claim: Claim) : Boolean {
    var overlap = false
    for (x in claim.offsetX until claim.width + claim.offsetX) {
        for (y in claim.offsetY until claim.height + claim.offsetY) {
            if (grid[x][y] > 1) {
                overlap = true
            }
        }
    }
    return overlap
}

fun printGrid(grid: Array<IntArray>) {
    println("Current Grid State:")
    for (x in 0 until 1000) {
        for (y in 0 until 1000) {
            print(when(grid[x][y]) {
                0 -> '.'
                1 -> 'x'
                else -> '#'
            })
        }
        println()
    }
}
