package utils

import ie.setu.models.Dog
import ie.setu.models.Client


fun formatListString(notesToFormat: List<Client>): String =
    notesToFormat
        .joinToString(separator = "\n") { client -> "$client" }

fun formatSetString(itemsToFormat: Set<Dog>): String =
    itemsToFormat
        .joinToString(separator = "\n") { dog -> "\t$dog" }


