import controllers.ClientAPI
import ie.setu.models.Client
import ie.setu.models.Dog
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
            5 -> markClientAsNew()
            6 -> addDogToClient()
            7 -> updateDogDetailsInClient()
            8 -> deleteADog()
            //9 -> groomedRecently()
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
         > | CLIENT MENU                                       |
         > |   1) Add a Client                                 |
         > |   2) List Clients                                 |
         > |   3) Update a Client                              |
         > |   4) Delete a Client                              |
         > |   5) Is this a new Client?                        |
         > -----------------------------------------------------  
         > | DOG MENU                                          | 
         > |   6) Add Dog To Client                            |
         > |   7) Update Dog Details                           |
         > |   8) Remove Dog from Files                        |
         > -----------------------------------------------------  
         > | SEARCH MENU FOR CLIENTS                           | 
         > |   10) Search for Client                           |
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
    val ClientName = readNextLine("Enter a name for the client: ")
    val email = readNextLine("Enter an email for the client: ")
    val phone = readNextInt("Enter a phone number for the client: ")
    val PaymentMethod = readNextLine("Enter a payment method for the client: ")
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
                  > |   2) View NEW clients        |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllClients()
            2 -> listNewClients()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No clients stored")
    }
}

fun listAllClients() = println(clientAPI.listAllClients())
fun listNewClients() = println(clientAPI.listNewClients())
//fun listArchivedClients() = println(clientAPI.listArchivedClients())

fun updateClient() {
    listClients()
    if (clientAPI.numberOfClients() > 0) {
        // only ask the user to choose the client if clients exist
        val id = readNextInt("Enter the id of the client to update: ")
        if (clientAPI.findClient(id) != null) {
            val ClientName = readNextLine("Enter a name for the client: ")
            val email = readNextLine("Enter an email for the client: ")
            val phone = readNextInt("Enter a phone number for the client: ")
            val PaymentMethod = readNextLine("Enter a payment method for the client: ")

            // pass the index of the client and the new client details to ClientAPI for updating and check for success.
            if (clientAPI.update(id, Client(0, ClientName, email, phone, PaymentMethod, false))){
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

fun markClientAsNew() {
    listNewClients()
    if (clientAPI.numberOfNewClients() > 0) {
        // only ask the user to choose the client to archive if active clients exist
        val id = readNextInt("Enter the id of the client you want to mark as new: ")
        // pass the index of the client to ClientAPI for archiving and check for success.
        if (clientAPI.markAsNew(id)) {
            println("This client is now marked as new!")
        } else {
            println("This client is NOT marked as new")
        }
    }
}

//-------------------------------------------
//DOG MENU (only available for active clients)
//-------------------------------------------

private fun addDogToClient() {
    val client: Client? = askUserToChooseClient()
    if (client != null) {
        if (client.addDog(Dog(
                nameofdog = readNextLine("\t Dog Name: "),
                breed = readNextLine("\t Dog Breed: "),
                colour = readNextLine("\t Dog Colour: "),
                size = readNextLine("\t Dog Size: "),
                coattype = readNextLine("\t Dog Coat Type: "),
                price = readNextInt("\t Dog Groom Price: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}

fun updateDogDetailsInClient() {
    val client: Client? = askUserToChooseClient()
    if (client != null) {
        val dog: Dog? = askUserToChooseDog(client)
        if (dog != null) {
            //val newDetails = readNextLine("Enter new details: ")
            if (client.update(dog.dogId, Dog(
                    nameofdog = readNextLine("\t Dog Name: "),
                    breed = readNextLine("\t Dog Breed: "),
                    colour = readNextLine("\t Dog Colour: "),
                    size = readNextLine("\t Dog Size: "),
                    coattype = readNextLine("\t Dog Coat Type: "),
                    price = readNextInt("\t Dog Groom Price: ")))) {
                println("Dog Details updated")
            } else {
                println("Dog Details NOT updated")
            }
        } else {
            println("Invalid Item Id")
        }
    }
}

fun deleteADog() {
    val client: Client? = askUserToChooseClient()
    if (client != null) {
        val dog: Dog? = askUserToChooseDog(client)
        if (dog!= null) {
            val isDeleted = client.delete(dog.dogId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}

//------------------------------------
//SEARCH MENU
//------------------------------------
fun searchClients() {
    val searchTitle = readNextLine("Enter the name of the client you want to search for: ")
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

private fun askUserToChooseClient(): Client? {
    listClients()
    if (clientAPI.numberOfClients() > 0) {
        val client = clientAPI.findClient(readNextInt("\nEnter the id of the client: "))
        if (client != null) {
            if (client.isNewClient) {
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

private fun askUserToChooseDog(client: Client): Dog? {
    if (client.numberOfDogs() > 0) {
        print(client.listDogs())
        return client.findOne(readNextInt("\nEnter the id of the dog: "))
    }
    else{
        println ("No dog for chosen client")
        return null
    }
}


