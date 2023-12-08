import classes.Node
import java.io.File

fun main() {
    val inputLines = File("day8/resources/input.txt").readLines()

    val instruction = inputLines[0]
    val nodes = inputLines.drop(2).map {
        Node.parse(it)
    }
    nodes.forEach { it.configureNodes(nodes) }

    var currentNode = nodes.first { it.name == "AAA" }
    var numberOfSteps = 0
    var instructionIdx = 0
    while (currentNode.name != "ZZZ") {
        when (instruction[instructionIdx]) {
            'L' -> currentNode = currentNode.leftNode
            'R' -> currentNode = currentNode.rightNode
        }
        instructionIdx = (instructionIdx + 1) % instruction.length
        numberOfSteps += 1
    }

    println(numberOfSteps)
}