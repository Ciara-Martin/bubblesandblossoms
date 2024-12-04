package controllers

import ie.setu.models.Client
import utils.formatListString
import java.util.ArrayList

class ClientAPI() {

    private var clients = ArrayList<Client>()

    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR CLIENT ArrayList
    // ----------------------------------------------
    fun add(client: Client): Boolean {
        client.clientId = getId()
        return clients.add(client)
    }

    fun delete(id: Int) = clients.removeIf { client -> client.clientId == id }

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


    // ----------------------------------------------
    //  LISTING METHODS FOR CLIENT ArrayList
    // ----------------------------------------------
    fun listAllClients() =
        if (clients.isEmpty()) "No clients stored"
        else formatListString(clients)

    /*fun listActiveClients() =
        if (numberOfActiveClients() == 0) "No active clients stored"
        else formatListString(clients.filter { client -> !client.isClientArchived })*/


    // ----------------------------------------------
    //  COUNTING METHODS FOR CLIENT ArrayList
    // ----------------------------------------------
    fun numberOfClients() = clients.size
    //fun numberOfActiveClients(): Int = clients.count { client: Client -> !client.isClientArchived }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    fun findClient(clientId : Int) =  clients.find{ client -> client.clientId == clientId }

    fun searchClientsByTitle(searchString: String) =
        formatListString(clients.filter { client -> client.ClientName.contains(searchString, ignoreCase = true) })

}