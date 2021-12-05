package utils

import java.nio.file.Paths
import kotlin.io.path.readLines

class PuzzleInput<T>(year: Int, day: Int, transform: (String) -> T) {
    private val dayStr = "Day${day.toString().padStart(2, '0')}"
    private val path = "src/year$year/${dayStr.lowercase()}/$dayStr"

    val real: List<T> = Paths.get("$path.txt").readLines().map(transform)
    val test: List<T> = Paths.get("${path}_test.txt").readLines().map(transform)
}