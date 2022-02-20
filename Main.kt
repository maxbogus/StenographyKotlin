package cryptography

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

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
        val inputFile = File(inputFileName)
        val image: BufferedImage = ImageIO.read(inputFile)
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val color = Color(image.getRGB(x, y))
                val green = color.green or 1
                val blue = color.blue or 1
                val red = color.red or 1
                val newColor = Color(red, green, blue)
                image.setRGB(x, y, newColor.rgb)
            }
        }
        val outputFile = File(outputFileName)
        ImageIO.write(image, "png", outputFile)
        println("Output Image: $outputFileName")
        println("Image $outputFileName is saved.")
    } catch (_: IOException) {
        println("Can't read input file!")
    } catch (_: Exception) {
        println("Can't read output file!")
    }
}
