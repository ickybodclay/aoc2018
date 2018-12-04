import java.io.File

data class Time(val year:Int, val month: Int, val day: Int, val hour: Int, val min: Int)
data class Record(val time: Time, var guardId: Int, val action: Action)
enum class Action { START, SLEEP, WAKE }
data class Guard(val guardId: Int, var minutesSleeping: Int, val sleepList: ArrayList<Sleep>)
data class Sleep(val time: Time, val startMin: Int, val endMin: Int)

fun main() {
    val input = File(ClassLoader.getSystemResource("input.txt").file)

    // Write solution here!Sample Text
    //[1518-11-01 00:00] Guard #10 begins shift
    //[1518-11-01 00:05] falls asleep
    //[1518-11-01 00:25] wakes up
    val recordRegex = Regex("""\[(\d+)-(\d+)-(\d+) (\d+):(\d+)] (Guard #(\d+) begins shift|falls asleep|wakes up)""")

    val recordList = ArrayList<Record>()
    input.readLines().map {
        val recordResult = recordRegex.matchEntire(it)!!
        val time = Time(
                recordResult.groups[1]!!.value.toInt(),
                recordResult.groups[2]!!.value.toInt(),
                recordResult.groups[3]!!.value.toInt(),
                recordResult.groups[4]!!.value.toInt(),
                recordResult.groups[5]!!.value.toInt())

        var guardId = -1
        val actionStr = recordResult.groups[6]!!.value
        var action = when (actionStr) {
            "falls asleep" -> Action.SLEEP
            "wakes up" -> Action.WAKE
            else -> {
                guardId = recordResult.groups[7]!!.value.toInt()
                Action.START
            }
        }


        val record = Record(time, guardId, action)
        recordList.add(record)
    }

    val guardMap = HashMap<Int, Guard>()
    var currentGuardId = 0
    var startSleepMin = 0
    recordList
            .sortedWith(compareBy({ it.time.year }, { it.time.month }, { it.time.day }, { it.time.hour }, { it.time.min }))
            .forEach {
                if (it.guardId == -1) {
                    it.guardId = currentGuardId
                }
                else {
                    currentGuardId = it.guardId
                    if (!guardMap.containsKey(currentGuardId)) {
                        guardMap[currentGuardId] = Guard(currentGuardId, 0, ArrayList())
                    }
                }

                when (it.action) {
                    Action.SLEEP -> startSleepMin = it.time.min
                    Action.WAKE -> {
                        val sleepTime = it.time.min - startSleepMin
                        guardMap[currentGuardId]!!.minutesSleeping += sleepTime
                        guardMap[currentGuardId]!!.sleepList.add(Sleep(it.time, startSleepMin, it.time.min))
                    }
                    else -> {}
                }
            }

    val lazyGuardId = guardMap.toSortedMap(compareBy { guardMap[it]!!.minutesSleeping }).lastKey()
    println("Lazy boy = ${guardMap[lazyGuardId]}")
    println("[part1]")
    printMinuteSleptMost(guardMap[lazyGuardId]!!)

    println("\n\n[part2]")
    guardMap.map {
        printMinuteSleptMost(it.value)
    }
}

fun printMinuteSleptMost(guard: Guard) {
    //println("\t\t000000000011111111112222222222333333333344444444445555555555")
    //println("\t\t012345678901234567890123456789012345678901234567890123456789")
    val minuteList = ArrayList<Int>()
    for (i in 0 until 60) minuteList.add(0)
    guard.sleepList.forEach {
        //val month = "${it.time.month}".padStart(2, '0')
        //val day = "${it.time.day}".padStart(2, '0')
        //print ("$month-$day\t")
        //for(i in 0 until it.startMin) print ('.')
        for(i in it.startMin until it.endMin) {
            minuteList[i]++
            //print ('#')
        }
        //for(i in it.endMin until 60) print ('.')
        //println()
    }
    var maxSleptMin = -1
    var maxSleptCount = -1
    minuteList.forEachIndexed { index, i ->
        if (i > maxSleptCount) {
            maxSleptMin = index
            maxSleptCount = i
        }
    }
    println("guardId = ${guard.guardId} max slept min = $maxSleptMin, max slept count = $maxSleptCount")
    //println("[part1] solution = ${maxSleptMin * guard.guardId}")
}
