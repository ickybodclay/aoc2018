import java.io.File
import java.util.*
import kotlin.math.abs
import kotlin.math.tan

data class Point(val x: Int, val y: Int)

fun main() {
    val input = File(ClassLoader.getSystemResource("input.txt").file)

    // Write solution here!
    val points = ArrayList<Point>()
    input.readLines().map { line ->
        val tmpStrArray = line.split(",")
        val p = Point(tmpStrArray[0].trim().toInt(), tmpStrArray[1].trim().toInt())
        points.add(p)
    }

    // find boundary points

    // plot points on grid

    // figure out manhattan distance for each point of grid, which position closest to which point, if tie ignore

    // figure out point with highest count, ignoring boundary points
}

fun getManhattanDistance(a: Point, b: Point) : Int {
    return abs(a.x - b.x) + abs(a.y - b.y)
}

// use graham scan to find boundary points
fun getBoundaryPoints(allPoints: List<Point>) : List<Point> {
    /*
    let N be number of points
    let points[N] be the array of points
    swap points[0] with the point with the lowest y-coordinate
    sort points by polar angle with points[0]
    let stack = empty_stack()
    push points[0] to stack
    push points[1] to stack
    for i = 2 to N-1:
        while count stack >= 2 and ccw(next_to_top(stack), top(stack), points[i]) <= 0:
            pop stack
        push points[i] to stack
    end
     */
    val points = ArrayList(allPoints)
    val lowestYPoint = points.minBy { it -> it.y }



    val stack = Stack<Point>()


    return emptyList()
}

fun polarAngle(origin: Point, p: Point) : Float {
    return tan((p.y - origin.y).toFloat() / (p.x - origin.x).toFloat())
}

fun ccw(p1: Point, p2: Point, p3: Point) : Boolean {
    return ((p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x)) > 0
}