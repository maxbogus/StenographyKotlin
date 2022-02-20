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
    val list = mutableListOf<Byte>()
    try {
        println("Input Image: $inputFileName")
        val inputFile = File(inputFileName)
        val image: BufferedImage = ImageIO.read(inputFile)
        for (x in 0 until image.height) {
            var string = ""
            for (y in 0 until 8) {
                val color = Color(image.getRGB(x, y))
                val lsbFromColor = color.blue and 1
                list.add(lsbFromColor.toByte())
                string += lsbFromColor
            }
            if (string == STOP_BYTE.toString()) {
                println(string)
                break
            }
        }
        println("Message:")
        println(list.toByteArray().toString(Charsets.UTF_8))
    } catch (e: IOException) {
        println(e.cause)
        println("Can't read input file!")
    } catch (e: Exception) {
        println(e.cause)
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
    val messageToConceal = readLine()!! + "\u0000\u0000\u0003"
    val hiddenMessage = mutableListOf<String>()
    for (byte in messageToConceal.encodeToByteArray()) {
        hiddenMessage += byte.toString(2).padStart(8, '0')
    }
    try {
        val inputFile = File(inputFileName)
        val file: BufferedImage = ImageIO.read(inputFile)
        val image = BufferedImage(file.width, file.height, BufferedImage.TYPE_INT_RGB)
        val imageLimit = image.height * 8
        if (hiddenMessage.size > imageLimit) {
            println("The input image is not large enough to hold this message.")
        } else {
            for (y in 0 until hiddenMessage.size) {
                for (x in 0 until hiddenMessage[y].length) {
                    val color = Color(image.getRGB(x, y))
                    val modifiedBlueColor = color.blue or "${hiddenMessage[y][x]}".toInt()
                    val newColor = Color(color.red, color.green, modifiedBlueColor)
                    image.setRGB(x, y, newColor.rgb)
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
