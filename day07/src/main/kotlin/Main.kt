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
    val timeToCompleteMap = mutableMapOf<String, Int>()
    val completedSteps = ArrayList<String>()

    for (key in sortedKeys) {
        timeToCompleteMap[key] = timeToCompleteStep(key)
    }
    println(timeToCompleteMap)

    var time = 0
    while (completedSteps.size != stepMap.size) {
        val workersCount = 5
        var workersActive = 0
        val justCompleted = ArrayList<String>()

        var workerA = "."
        var workerB = "."
        var workerC = "."
        var workerD = "."
        var workerE = "."

        for (key in sortedKeys) {
            if (workersActive < workersCount &&
                    (stepMap[key]!!.isEmpty() || completedSteps.containsAll(stepMap[key]!!))) {

                when (workersActive) {
                    0 -> workerA = key
                    1 -> workerB = key
                    2 -> workerC = key
                    3 -> workerD = key
                    4 -> workerE = key
                }

                timeToCompleteMap[key] = timeToCompleteMap[key]!!.minus(1)
                if (timeToCompleteMap[key]!! <= 0) {
                    justCompleted.add(key)
                }
                workersActive++
            }
        }

        println("${time.toString().padStart(4, '0')}\t$workerA\t$workerB\t$workerC\t$workerD\t$workerE\t$completedSteps")

        sortedKeys.removeAll(justCompleted)
        completedSteps.addAll(justCompleted)
        time++
    }

    println(timeToCompleteMap)
    println("total time = $time")

//    for(step in completedSteps)
//        print(step)
//    println()
}

private val charOffset = "A"[0].toInt() // 65
fun timeToCompleteStep(step: String) : Int {
    return (step[0].toInt() - charOffset + 1) + 60
}
