package utils

fun readIntNotNull() = readlnOrNull()?.toIntOrNull() ?: -1

/**
 * Reads an integer from the user input after displaying a prompt.
 * If the input is invalid, the user is asked to re-enter a value.
 * @param prompt The message displayed to the user before reading input.
 * @return The integer value entered by the user.
 */

fun readNextInt(prompt: String?): Int {
    do {
        try {
            print(prompt)
            return readln().toInt()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a number please.")
        }
    } while (true)
}

fun readDoubleNotNull() = readlnOrNull()?.toDoubleOrNull() ?: -1.0

fun readNextDouble(prompt: String?): Double {
    do {
        try {
            print(prompt)
            return readln().toDouble()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a decimal number please.")
        }
    } while (true)
}

fun readFloatNotNull() = readlnOrNull()?.toFloatOrNull() ?: -1.0f

fun readNextFloat(prompt: String?): Float {
    do {
        try {
            print(prompt)
            return readln().toFloat()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a decimal number please.")
        }
    } while (true)
}

/**
 * Reads a string from the user input after displaying a prompt.
 * @param prompt The message displayed to the user before reading input.
 * @return The string entered by the user.
 */

fun readNextLine(prompt: String?): String {
    print(prompt)
    return readln()
}


fun readNextChar(prompt: String?): Char {
    do {
        try {
            print(prompt)
            return readln().first()
        } catch (e: Exception) {
            System.err.println("\tEnter a character please.")
        }
    } while (true)
}