package cryptography

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

const val STOP_BYTE = 0x000000000000000000000011

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
    val array: ByteArray = byteArrayOf()
    try {
        println("Input Image: $inputFileName")
        val inputFile = File(inputFileName)
        val image: BufferedImage = ImageIO.read(inputFile)
        var index = 0
        var stop = false
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                if (!stop) {
                    val color = Color(image.getRGB(x, y))
                    val lsbFromColor = color.blue and 1
                    if (lsbFromColor != STOP_BYTE) {
                        array[index] = lsbFromColor.toByte()
                    } else {
                        stop = true
                    }
                    index++
                }
            }
        }
        println("Message:")
        println(array.toString(Charsets.UTF_8))
    } catch (e: IOException) {
        println(e.cause)
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
    val array = messageToConceal.encodeToByteArray()
    try {
        val inputFile = File(inputFileName)
        val image: BufferedImage = ImageIO.read(inputFile)
        val imageLimit = image.height * image.width
        if (array.size > imageLimit) {
            println("The input image is not large enough to hold this message.")
        } else {
            var counter = 0
            for (x in 0 until image.width) {
                for (y in 0 until image.height) {
                    val color = Color(image.getRGB(x, y))
                    if (counter <= array.size - 1) {
                        val modifiedBlueColor = color.blue or array[counter].toInt()
                        val newColor = Color(color.red, color.green, modifiedBlueColor)
                        image.setRGB(x, y, newColor.rgb)
                    } else if (counter == array.size) {
                        val modifiedBlueColor = color.blue or STOP_BYTE
                        val newColor = Color(color.red, color.green, modifiedBlueColor)
                        image.setRGB(x, y, newColor.rgb)
                    } else {
                        image.setRGB(x, y, color.rgb)
                    }
                    counter++
                }
            }
            val outputFile = File(outputFileName)
            ImageIO.write(image, "png", outputFile)
            println("Message saved in $outputFileName image.")
        }
    } catch (_: IOException) {
        println("Can't read input file!")
    } catch (_: Exception) {
        println("Can't read output file!")
    }
}
