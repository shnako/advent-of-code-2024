package solutions.day04

import solutions.GenericSolution
import util.are2DCoordinatesValid
import util.neighbours2D
import util.read2dCharArray
import java.io.File

/*
Part 1:
We start by locating each X in the grid.
From each X we look in each direction and count how many times we can find the full word.
The result is the count of each XMAS found starting from each X location.

Part 2:
We start by locating each A in the grid, that's not on the border (to avoid going out of bounds).
We then simply look at the diagonal neighbours and see if they match the required pattern.
The result is the count of X-MAS patterns found in the grid.
 */
class Solution : GenericSolution {
    private val word = "XMAS"
    override fun runPart1(inputFile: File): String {
        val grid = read2dCharArray(inputFile)
        var result = 0
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == word[0]) {
                    result += findWordsFromPoint(Pair(i, j), grid)
                }
            }
        }
        return result.toString()
    }

    private fun findWordsFromPoint(point: Pair<Int, Int>, grid: Array<CharArray>): Int {
        var foundWords = 0
        for (direction in neighbours2D) {
            for (li in 1 until word.length) {
                val neighbourX = point.first + direction[0] * li
                val neighbourY = point.second + direction[1] * li
                if (are2DCoordinatesValid(neighbourX, neighbourY, grid)
                    && grid[neighbourX][neighbourY] == word[li]
                ) {
                    if (li == word.length - 1) {
                        foundWords++
                    }
                } else {
                    break
                }
            }
        }
        return foundWords
    }

    override fun runPart2(inputFile: File): String {
        val grid = read2dCharArray(inputFile)
        var result = 0
        for (i in 1 until grid.size - 1) {
            for (j in 1 until grid[i].size - 1) {
                if (grid[i][j] == 'A'
                    && ((grid[i - 1][j - 1] == 'M' && grid[i + 1][j + 1] == 'S')
                            || (grid[i - 1][j - 1] == 'S' && grid[i + 1][j + 1] == 'M'))
                    && ((grid[i - 1][j + 1] == 'M' && grid[i + 1][j - 1] == 'S')
                            || (grid[i - 1][j + 1] == 'S' && grid[i + 1][j - 1] == 'M'))
                ) {
                    result++
                }
            }
        }
        return result.toString()
    }
}
