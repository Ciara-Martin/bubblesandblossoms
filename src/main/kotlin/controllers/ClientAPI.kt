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
    fun listAllClients() =
        if (clients.isEmpty()) "No clients stored"
        else formatListString(clients)

    fun listInactiveClients() =
        if (clients.filter { it.isInactiveClient }.isEmpty()) "No inactive clients stored"
        else formatListString(clients.filter { it.isInactiveClient })


    // ----------------------------------------------
    //  COUNTING METHODS FOR CLIENT ArrayList
    // ----------------------------------------------
    fun numberOfClients() = clients.size
    fun numberOfInactiveClients(): Int = clients.count { client: Client -> !client.isInactiveClient }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    fun findClient(clientId : Int) =  clients.find{ client -> client.clientId == clientId }

    fun searchClientsByTitle(searchString: String) =
        formatListString(clients.filter { client -> client.ClientName.contains(searchString, ignoreCase = true) })

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


