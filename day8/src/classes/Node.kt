package classes

data class Node(
    val name: String,
    val left: String,
    val right: String
) {
    lateinit var leftNode: Node
    lateinit var rightNode: Node

    companion object {
        fun parse(inputLine: String): Node {
            return Node(
                name = inputLine.substring(0, 3),
                left = inputLine.substring(7, 10),
                right = inputLine.substring(12, 15)
            )
        }
    }

    fun configureNodes(nodes: List<Node>) {
        leftNode = nodes.first { it.name == left }
        rightNode = nodes.first { it.name == right }
    }
}