package cryptography

fun main() {
    do {
        println("Task (hide, show, exit):")
        val input = readLine()!!
        when (input) {
            "hide" -> {
                handleHide()
            }
            "show" -> {

            }
            "exit" -> {
                handleExit()
            }
            else -> {

            }
        }
    } while (input != "exit")
}

private fun handleExit() {
    println("Bye!")
}

fun handleHide() {
    println("Input image file:")
    val inputFileName = readLine()!!
    println("Output image file:")
    val outputFileName = readLine()!!
    try {
        println("Input Image: $inputFileName")
    } catch (_: Exception) {
        println("Can't read input file!")
    }

    try {
        println("Output Image: $outputFileName")
        println("Image $outputFileName is saved.")
    } catch (_: Exception) {
        println("Can't read output file!")
    }
}
