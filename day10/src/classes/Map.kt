package classes

class Map(private val map: Array<CharArray>) {

    /**
     * The footprint of the map
     * The cells contain either a positive number which is the distance from the start or -1 for enclosing cells
     * inside the loop or -2 for other cells
     */
    private val mapFootprint: Array<IntArray> = Array(map.size) { IntArray(map.first().size) { -1 } }

    /**
     * Requested data
     */
    var farthestDistance = 0
    var numberOfEnclosedTile = 0

    init {
        fillFootprint()
    }

    /**
     * Get the coordinate of the starting point inside the input map
     */
    private fun getStartingPoint(): Pair<Int, Int> {
        map.forEachIndexed { indexRow, row ->
            val indexCol = row.indexOfFirst { it == 'S' }
            if (indexCol != -1) {
                return Pair(indexRow, indexCol)
            }
        }
        throw Exception("No starting point in the map")
    }

    /**
     * Fill the footprint map based on the input map and mark the enclosing and non-enclosing tiles
     */
    private fun fillFootprint() {
        // Create a double-sized footprint map. It will be useful to tracking enclosing tiles inside the loop
        val doubleMapFootprint: Array<IntArray> = Array(map.size * 2 - 1) { IntArray(map.first().size * 2 - 1) { -1 } }

        // Set the starting point distance inside the footprints to 0
        val startingPoint = getStartingPoint()
        doubleMapFootprint[startingPoint.first * 2][startingPoint.second * 2] = 0
        mapFootprint[startingPoint.first][startingPoint.second] = 0

        // Get the next pipes going in and out from the starting point
        val nextPipe1 = getNextPipes(null, startingPoint)[0]
        val nextPipe2 = getNextPipes(null, startingPoint)[1]
        // Follow the two pipes from the starting points and fill the footprint
        footprintLoop(doubleMapFootprint, startingPoint, nextPipe1, startingPoint, nextPipe2, 1)
        // Mark the non-enclosing tiles
        footprintArea(doubleMapFootprint)
        numberOfEnclosedTile = mapFootprint.sumOf { it.count { it == -1 } }
    }

    /**
     * Mark all the non enclosing points (i.e. in contact with any border of the map)
     */
    private fun footprintArea(doubleMapFootprint: Array<IntArray>) {
        for (i in 0 until doubleMapFootprint.first().size) {
            listOf(0, doubleMapFootprint.size - 1).forEach {
                footprintAreaLoop(doubleMapFootprint, it, i)
            }
        }
        for (i in doubleMapFootprint.indices) {
            listOf(0, doubleMapFootprint.first().size - 1).forEach {
                footprintAreaLoop(doubleMapFootprint, i, it)
            }
        }
    }

    /**
     * Mark all the non enclosing points (i.e. in contact with the coordinate passed as parameter)
     */
    private fun footprintAreaLoop(doubleMapFootprint: Array<IntArray>, i: Int, j: Int) {
        if (doubleMapFootprint[i][j] != -1)
            return
        val cellsToMark = mutableSetOf(Pair(i, j))
        while (cellsToMark.isNotEmpty()) {
            val cell = cellsToMark.first()
            doubleMapFootprint[cell.first][cell.second] = -2
            if (cell.first % 2 == 0 && cell.second % 2 == 0) {
                mapFootprint[cell.first / 2][cell.second / 2] = -2
            }
            if (cell.first > 0 && doubleMapFootprint[cell.first - 1][cell.second] == -1) cellsToMark.add(Pair(cell.first - 1, cell.second))
            if (cell.first + 1 < doubleMapFootprint.size && doubleMapFootprint[cell.first + 1][cell.second] == -1) cellsToMark.add(Pair(cell.first + 1, cell.second))
            if (cell.second > 0 && doubleMapFootprint[cell.first][cell.second - 1] == -1) cellsToMark.add(Pair(cell.first, cell.second - 1))
            if (cell.second + 1 < doubleMapFootprint.first().size && doubleMapFootprint[cell.first][cell.second + 1] == -1) cellsToMark.add(Pair(cell.first, cell.second + 1))

            cellsToMark.remove(cell)
        }
    }

    /**
     * Fill the footprint map based on the input map recursively
     */
    private tailrec fun footprintLoop(
        doubleMapFootprint: Array<IntArray>,
        originPipe1: Pair<Int, Int>,
        currentPipe1: Pair<Int, Int>,
        originPipe2: Pair<Int, Int>,
        currentPipe2: Pair<Int, Int>,
        currentDistance: Int
    ) {
        // Write the distance from the starting point in the footprints
        doubleMapFootprint[(originPipe1.first * 2 + currentPipe1.first * 2) / 2][(originPipe1.second * 2 + currentPipe1.second * 2) / 2] = currentDistance
        doubleMapFootprint[(originPipe2.first * 2 + currentPipe2.first * 2) / 2][(originPipe2.second * 2 + currentPipe2.second * 2) / 2] = currentDistance
        doubleMapFootprint[currentPipe1.first * 2][currentPipe1.second * 2] = currentDistance
        doubleMapFootprint[currentPipe2.first * 2][currentPipe2.second * 2] = currentDistance
        mapFootprint[currentPipe1.first][currentPipe1.second] = currentDistance
        mapFootprint[currentPipe2.first][currentPipe2.second] = currentDistance

        // If the two pipes has joined, end the recursive
        if (currentPipe1 == currentPipe2) {
            farthestDistance = currentDistance
            return
        }

        // Otherwise, continue following the two pipes
        val nextPipe1 = getNextPipes(originPipe1, currentPipe1).first()
        val nextPipe2 = getNextPipes(originPipe2, currentPipe2).first()
        footprintLoop(doubleMapFootprint, currentPipe1, nextPipe1, currentPipe2, nextPipe2, currentDistance + 1)
    }

    /**
     * Detect the next possible pipes from a given position in the map
     */
    private fun getNextPipes(originPipe: Pair<Int, Int>?, currentPipe: Pair<Int, Int>): List<Pair<Int, Int>> {
        val currentPipeChar = map[currentPipe.first][currentPipe.second]
        val northPipe: Pair<Pair<Int, Int>, Char?>? by lazy {
            val pipePos = Pair(currentPipe.first - 1, currentPipe.second)
            if (currentPipe.first <= 0 || originPipe == pipePos) null
            else Pair(pipePos, map[pipePos.first][pipePos.second])
        }
        val eastPipe: Pair<Pair<Int, Int>, Char?>? by lazy {
            val pipePos = Pair(currentPipe.first, currentPipe.second + 1)
            if (currentPipe.second + 1 >= map.first().size || originPipe == pipePos) null
            else Pair(pipePos, map[pipePos.first][pipePos.second])

        }
        val southPipe: Pair<Pair<Int, Int>, Char?>? by lazy {
            val pipePos = Pair(currentPipe.first + 1, currentPipe.second)
            if (currentPipe.first + 1 >= map.size || originPipe == pipePos) null
            else Pair(pipePos, map[pipePos.first][pipePos.second])
        }
        val westPipe: Pair<Pair<Int, Int>, Char?>? by lazy {
            val pipePos = Pair(currentPipe.first, currentPipe.second - 1)
            if (currentPipe.second <= 0 || originPipe == pipePos) null
            else Pair(pipePos, map[pipePos.first][pipePos.second])
        }

        return when (currentPipeChar) {
            'S' -> getNextPipes(north = northPipe, east = eastPipe, south = southPipe, west = westPipe)
            '|' -> getNextPipes(north = northPipe, south = southPipe)
            '-' -> getNextPipes(east = eastPipe, west = westPipe)
            'L' -> getNextPipes(north = northPipe, east = eastPipe)
            'J' -> getNextPipes(north = northPipe, west = westPipe)
            '7' -> getNextPipes(south = southPipe, west = westPipe)
            'F' -> getNextPipes(east = eastPipe, south = southPipe)
            else -> throw Exception("Stuck in a wall. Shouldn't be possible")
        }
    }

    /**
     * Detect the next possible pipes from the given adjacent characters
     */
    private fun getNextPipes(north: Pair<Pair<Int, Int>, Char?>? = null,
                             east: Pair<Pair<Int, Int>, Char?>? = null,
                             south: Pair<Pair<Int, Int>, Char?>? = null,
                             west: Pair<Pair<Int, Int>, Char?>? = null
    ): List<Pair<Int, Int>> {
        val possiblePipes = mutableListOf<Pair<Int, Int>>()
        north?.also {
            if (it.second == '|' || it.second == '7' || it.second == 'F')
                possiblePipes.add(it.first)
        }
        east?.also {
            if (it.second == '-' || it.second == '7' || it.second == 'J')
                possiblePipes.add(it.first)
        }
        south?.also {
            if (it.second == '|' || it.second == 'L' || it.second == 'J')
                possiblePipes.add(it.first)
        }
        west?.also {
            if (it.second == '-' || it.second == 'L' || it.second == 'F')
                possiblePipes.add(it.first)
        }
        return possiblePipes
    }
}