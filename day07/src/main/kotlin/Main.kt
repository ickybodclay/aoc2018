import java.io.File

fun main() {
    val input = File(ClassLoader.getSystemResource("input.txt").file)

    val stepRegex = Regex("Step ([A-Z]) must be finished before step ([A-Z]) can begin.")
    val stepMap = mutableMapOf<String, ArrayList<String>>()

    input.readLines().map {
        val stepMatch = stepRegex.matchEntire(it)!!

        val preReq = stepMatch.groups[1]!!.value
        val step = stepMatch.groups[2]!!.value

        if (!stepMap.containsKey(step)) {
            stepMap[step] = ArrayList()
        }

        if (!stepMap.containsKey(preReq)) {
            stepMap[preReq] = ArrayList()
        }

        stepMap[step]!!.add(preReq)
    }

    val sortedKeys = ArrayList(stepMap.keys.sorted())
    val completedSteps = ArrayList<String>()

    while (completedSteps.size != stepMap.size) {
        for (key in sortedKeys) {
            if (stepMap[key]!!.isEmpty() || completedSteps.containsAll(stepMap[key]!!)) {
                sortedKeys.remove(key)
                completedSteps.add(key)
                break
            }
        }
    }

    for(step in completedSteps)
        print(step)
    println()
}
