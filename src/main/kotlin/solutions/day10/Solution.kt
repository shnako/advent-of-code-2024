package solutions.day10

import solutions.GenericSolution
import util.are2DCoordinatesValid
import util.neighbours2DNoDiagonals
import util.read2dCharArray
import java.io.File

/*
Lucky day today - I implemented part 2 by mistake when trying to implement part 1,
so it took me no time to get the result for part 2 after part 1 as all I had to do was Ctrl + Z :)

Part 1:
We use a depth-first search starting from all coordinates with height 0 and ending at all reachable ones with height 9.
We accumulate the coordinates of all reachable 9s in a set.
The result is the sum of the sizes of the sets from each starting point.

Part 2:
Same as part 1, but it uses a list instead of a set to also count the multiple possible trails to a trail end.
 */
class Solution : GenericSolution {
    override fun runPart1(inputFile: File): String {
        val grid = read2dCharArray(inputFile)
        var result = 0
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == '0') {
                    val trailEnds = findTrailEndsFromHead(Pair(i, j), grid, true)
                    result += trailEnds.size
                }
            }
        }
        return result.toString()
    }

    override fun runPart2(inputFile: File): String {
        val grid = read2dCharArray(inputFile)
        var result = 0
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == '0') {
                    val trailEnds = findTrailEndsFromHead(Pair(i, j), grid, false)
                    result += trailEnds.size
                }
            }
        }
        return result.toString()
    }

    private fun findTrailEndsFromHead(
        currentPosition: Pair<Int, Int>,
        grid: Array<CharArray>,
        uniqueTrailsOnly: Boolean
    ): Collection<Pair<Int, Int>> {
        val foundTrailEnds = if (uniqueTrailsOnly) mutableSetOf() else mutableListOf<Pair<Int, Int>>()
        if (grid[currentPosition.first][currentPosition.second] == '9') {
            foundTrailEnds.add(currentPosition)
        } else {
            val nextHeight = (grid[currentPosition.first][currentPosition.second].digitToInt() + 1).digitToChar()
            for (neighbourDelta in neighbours2DNoDiagonals) {
                val neighbourX = currentPosition.first + neighbourDelta[0]
                val neighbourY = currentPosition.second + neighbourDelta[1]
                if (are2DCoordinatesValid(neighbourX, neighbourY, grid) && grid[neighbourX][neighbourY] == nextHeight) {
                    foundTrailEnds.addAll(findTrailEndsFromHead(Pair(neighbourX, neighbourY), grid, uniqueTrailsOnly))
                }
            }
        }
        return foundTrailEnds
    }
}
