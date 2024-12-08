package controllers

import ie.setu.models.Client
import utils.formatListString
import java.util.ArrayList

/**
 * A controller class for managing a collection of clients. Provides CRUD operations, listing,
 * counting, and searching methods for client data.
 */

class ClientAPI() {

    /**
     * A list of clients managed by this API.
     */

    private var clients = ArrayList<Client>()

    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------

    /**
     * Tracks the last assigned client ID.
     */

    private var lastId = 0

    /**
     * Generates a unique ID for a new client.
     * @return a unique integer ID.
     */

    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR CLIENT ArrayList
    // ----------------------------------------------

    /**
     * Adds a new client to the list.
     * @param client The client object to add.
     * @return True if the client was added successfully, false otherwise.
     */

    fun add(client: Client): Boolean {
        client.clientId = getId()
        return clients.add(client)
    }

    /**
     * Deletes a client by their ID.
     * @param id The ID of the client to delete.
     * @return True if the client was found and removed, false otherwise.
     */

    fun delete(id: Int) = clients.removeIf { client -> client.clientId == id }

    /**
     * Updates the details of an existing client.
     * @param id The ID of the client to update.
     * @param client The new client data.
     * @return True if the update was successful, false otherwise.
     */

    fun update(id: Int, client: Client?): Boolean {
        // find the client object by the index number
        val foundClient = findClient(id)

        // if the client exists, use the client details passed as parameters to update the found client in the ArrayList.
        if ((foundClient != null) && (client != null)) {
            foundClient.clientId = client.clientId
            foundClient.ClientName = client.ClientName
            foundClient.email = client.email
            foundClient.phone = client.phone
            foundClient.PaymentMethod = client.PaymentMethod
            return true
        }

        // if the client was not found, return false, indicating that the update was not successful
        return false
    }

    /**
     * Marks a client as inactive.
     * @param id The ID of the client to mark as inactive.
     * @return True if the client was successfully marked as inactive, false otherwise.
     */

    fun markAsInactive(id: Int): Boolean {
        val foundClient = findClient(id)
        if (( foundClient != null) && (!foundClient.isInactiveClient)
        ){
            foundClient.isInactiveClient = true
            return true
        }
        return false
    }


    // ----------------------------------------------
    //  LISTING METHODS FOR CLIENT ArrayList
    // ----------------------------------------------

    /**
     * Lists all clients in the system.
     * @return A formatted string of all clients or a message if none exist.
     */

    fun listAllClients() =
        if (clients.isEmpty()) "No clients stored"
        else formatListString(clients)

    /**
     * Lists all inactive clients.
     * @return A formatted string of inactive clients or a message if none exist.
     */

    fun listInactiveClients() =
        if (clients.filter { it.isInactiveClient }.isEmpty()) "No inactive clients stored"
        else formatListString(clients.filter { it.isInactiveClient })


    // ----------------------------------------------
    //  COUNTING METHODS FOR CLIENT ArrayList
    // ----------------------------------------------

    /**
     * Gets the total number of clients.
     * @return The number of clients.
     */

    fun numberOfClients() = clients.size

    /**
     * Counts the number of inactive clients.
     * @return The number of inactive clients.
     */

    fun numberOfInactiveClients(): Int = clients.count { client: Client -> !client.isInactiveClient }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------

    /**
     * Finds a client by their ID.
     * @param clientId The ID of the client to find.
     * @return The client object if found, null otherwise.
     */

    fun findClient(clientId : Int) =  clients.find{ client -> client.clientId == clientId }

    /**
     * Searches clients by their name.
     * @param searchString The name or partial name to search for.
     * @return A formatted string of matching clients.
     */

    fun searchClientsByTitle(searchString: String) =
        formatListString(clients.filter { client -> client.ClientName.contains(searchString, ignoreCase = true) })

    /**
     * Searches dogs owned by clients by the dog's name.
     * @param searchString The name or partial name of the dog to search for.
     * @return A formatted string of matching dogs and their owners or a message if none are found.
     */

    fun searchDogsByName(searchString: String): String {
        return if (numberOfClients() == 0) "No clients stored"
        else {
            var listOfClients = ""
            for (client in clients) {
                for (dog in client.dogs) {
                     if (dog.nameofdog.contains(searchString, ignoreCase = true)) {
                        listOfClients += "${client.clientId}: ${client.ClientName} \n\t${dog}\n"
                    }
                }
            }
            if (listOfClients == "") "No dogs found for: $searchString"
            else listOfClients
        }
    }

    /**
     * Searches dogs owned by clients by the dog's breed.
     * @param searchString The breed or partial breed name of the dog to search for.
     * @return A formatted string of matching dogs and their owners or a message if none are found.
     */

    fun searchDogsByBreed(searchString: String): String {
        return if (numberOfClients() == 0) "No clients stored"
        else {
            var listOfClients = ""
            for (client in clients) {
                for (dog in client.dogs) {
                    if (dog.breed.contains(searchString, ignoreCase = true)) {
                        listOfClients += "${client.clientId}: ${client.ClientName} \n\t${dog}\n"
                    }
                }
            }
            if (listOfClients == "") "No dogs found for: $searchString"
            else listOfClients
        }
    }

}