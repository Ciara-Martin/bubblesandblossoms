import controllers.ClientAPI
import ie.setu.models.Client
import utils.readNextInt
import utils.readNextLine
import kotlin.system.exitProcess

private val clientAPI = ClientAPI()

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addClient()
            2 -> listClients()
            3 -> updateClient()
            4 -> deleteClient()
            //5 -> archiveClient()
            //6 -> addItemToClient()
            //7 -> updateItemContentsInClient()
            //8 -> deleteAnItem()
            //9 -> markItemStatus()
            10 -> searchClients()
            //15 -> searchItems()
            //16 -> listToDoItems()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |            BUBBLES AND BLOSSOMS APP               |
         > -----------------------------------------------------  
         > | CLIENT MENU                                         |
         > |   1) Add a Client                                   |
         > |   2) List Clients                                   |
         > |   3) Update a Client                                |
         > |   4) Delete a Client                                |
         > -----------------------------------------------------  
         > | DOG MENU                                          | 
         > |   6) ------------------                           |
         > |   7) ------------------------------               |
         > |   8) -----------------------                      |
         > |   9) --------------------------                   | 
         > -----------------------------------------------------  
         > | SEARCH MENU FOR CLIENTS                             | 
         > |   10) ------------------------------------        |
         > |   11) .....                                       |
         > |   12) .....                                       |
         > |   13) .....                                       |
         > |   14) .....                                       |
         > -----------------------------------------------------  
         > | REPORT MENU FOR DOGS                              |                                
         > |   15) -------------------------------             |
         > |   16) ---------------                             |
         > |   17) .....                                       |
         > |   18) .....                                       |
         > |   19) .....                                       |
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

//------------------------------------
//CLIENT MENU
//------------------------------------
fun addClient() {
    val ClientName = readNextLine("Enter a title for the client: ")
    val email = readNextLine("Enter an email ")
    val phone = readNextInt("Enter a phone number ")
    val PaymentMethod = readNextLine("Enter a category for the client: ")
    val isAdded = clientAPI.add(Client(ClientName = ClientName, email = email, phone = phone, PaymentMethod = PaymentMethod))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listClients() {
    if (clientAPI.numberOfClients() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL clients        |
                  > |   2) View ACTIVE clients     |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllClients()
            //2 -> listActiveClients()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No clients stored")
    }
}

fun listAllClients() = println(clientAPI.listAllClients())
//fun listActiveClients() = println(clientAPI.listActiveClients())
//fun listArchivedClients() = println(clientAPI.listArchivedClients())

fun updateClient() {
    listClients()
    if (clientAPI.numberOfClients() > 0) {
        // only ask the user to choose the client if clients exist
        val id = readNextInt("Enter the id of the client to update: ")
        if (clientAPI.findClient(id) != null) {
            val ClientName = readNextLine("Enter a title for the client: ")
            val email = readNextLine("Enter an email ")
            val phone = readNextInt("Enter a phone number ")
            val PaymentMethod = readNextLine("Enter a payment method for the client: ")

            // pass the index of the client and the new client details to ClientAPI for updating and check for success.
            if (clientAPI.update(id, Client(0, ClientName, email, phone, PaymentMethod))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no clients for this index number")
        }
    }
}

fun deleteClient() {
    listClients()
    if (clientAPI.numberOfClients() > 0) {
        // only ask the user to choose the client to delete if clients exist
        val id = readNextInt("Enter the id of the client to delete: ")
        // pass the index of the client to ClientAPI for deleting and check for success.
        val clientToDelete = clientAPI.delete(id)
        if (clientToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

/*fun archiveClient() {
    listActiveClients()
    if (clientAPI.numberOfActiveClients() > 0) {
        // only ask the user to choose the client to archive if active clients exist
        val id = readNextInt("Enter the id of the client to archive: ")
        // pass the index of the client to ClientAPI for archiving and check for success.
        if (clientAPI.archiveClient(id)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}*/

//-------------------------------------------
//ITEM MENU (only available for active clients)
//-------------------------------------------

//TODO

//------------------------------------
//CLIENT REPORTS MENU
//------------------------------------
fun searchClients() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = clientAPI.searchClientsByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No clients found")
    } else {
        println(searchResults)
    }
}

//------------------------------------
//ITEM REPORTS MENU
//------------------------------------

//TODO

//------------------------------------
// Exit App
//------------------------------------
fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}

//------------------------------------
//HELPER FUNCTIONS
//------------------------------------

/*private fun askUserToChooseActiveClient(): Client? {
    listActiveClients()
    if (clientAPI.numberOfActiveClients() > 0) {
        val client = clientAPI.findClient(readNextInt("\nEnter the id of the client: "))
        if (client != null) {
            if (client.isClientArchived) {
                println("Client is NOT Active, it is Archived")
            } else {
                return client //chosen client is active
            }
        } else {
            println("Client id is not valid")
        }
    }
    return null //selected client is not active
}
*/