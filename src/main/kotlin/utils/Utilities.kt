package utils

import ie.setu.models.Dog
import ie.setu.models.Client

/**
 * Formats a list of [Client] objects into a readable string, with each client on a new line.
 * @param notesToFormat The list of clients to format.
 * @return A formatted string representation of the clients.
 */

fun formatListString(notesToFormat: List<Client>): String =
    notesToFormat
        .joinToString(separator = "\n") { client -> "$client" }

/**
 * Formats a set of [Dog] objects into a readable string, with each dog indented and on a new line.
 * @param itemsToFormat The set of dogs to format.
 * @return A formatted string representation of the dogs.
 */

fun formatSetString(itemsToFormat: Set<Dog>): String =
    itemsToFormat
        .joinToString(separator = "\n") { dog -> "\t$dog" }


