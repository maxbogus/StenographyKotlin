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
            "hide" -> handleHide()
            "show" -> handleShow()
            "exit" -> handleExit()
            else -> handleElse()
        }
    } while (input != "exit")
}

fun handleElse() {
    println("Action is not supported.")
}

fun handleShow() {
    println("Input image file:")
    val inputFileName = readLine()!!
    var message = ""
    try {
        println("Input Image: $inputFileName")
        val inputFile = File(inputFileName)
        val image: BufferedImage = ImageIO.read(inputFile)
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val color = Color(image.getRGB(x, y))
                val modifiedBlueColor = color.blue or 1
                val newColor = Color(color.red, color.green, modifiedBlueColor)
                image.setRGB(x, y, newColor.rgb)
            }
        }

        println("Message:")
        println(message)
    } catch (_: IOException) {
        println("Can't read input file!")
    } catch (_: Exception) {
        println("Can't read output file!")
    }
}

private fun handleExit() {
    println("Bye!")
}

fun handleHide() {
    println("Input image file:")
    val inputFileName = readLine()!!
    println("Output image file:")
    val outputFileName = readLine()!!
    println("Message to hide:")
    val messageToConceal = readLine()!!
    try {
        println("Input Image: $inputFileName")
        val inputFile = File(inputFileName)
        val image: BufferedImage = ImageIO.read(inputFile)
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val color = Color(image.getRGB(x, y))
                val modifiedBlueColor = color.blue or 1
                val newColor = Color(color.red, color.green, modifiedBlueColor)
                image.setRGB(x, y, newColor.rgb)
            }
        }
        val outputFile = File(outputFileName)
        ImageIO.write(image, "png", outputFile)
        println("Message saved in $$outputFileName image.")
    } catch (_: IOException) {
        println("Can't read input file!")
    } catch (_: Exception) {
        println("Can't read output file!")
    }
}
