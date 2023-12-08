import classes.Node
import java.io.File

fun main() {
    val inputLines = File("day8/resources/input.txt").readLines()

    val instruction = inputLines[0]
    val nodes = inputLines.drop(2).map {
        Node.parse(it)
    }
    nodes.forEach { it.configureNodes(nodes) }

    val startingNodes = nodes.filter { it.name.endsWith("A") }
    val numberOfSteps = startingNodes.map {
        numberOfSteps(instruction, it).toLong()
    }.reduce { acc, n -> lcm(acc, n) }

    println(numberOfSteps)
}

fun numberOfSteps(instruction: String, startingNode: Node): Int {
    var currentNode = startingNode
    var numberOfSteps = 0
    var instructionIdx = 0
    while (!currentNode.name.endsWith("Z")) {
        when (instruction[instructionIdx]) {
            'L' -> currentNode = currentNode.leftNode
            'R' -> currentNode = currentNode.rightNode
        }
        instructionIdx = (instructionIdx + 1) % instruction.length
        numberOfSteps += 1
    }
    return numberOfSteps
}

fun gcd(a: Long, b: Long): Long = if (b == 0.toLong()) a else gcd(b, a % b)

fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b
