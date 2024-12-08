import controllers.ClientAPI
import ie.setu.models.Client
import ie.setu.models.Dog
import utils.readNextInt
import utils.readNextLine
import kotlin.system.exitProcess

private val clientAPI = ClientAPI()

/**
 * Entry point of the application. Runs the main menu.
 */

fun main() = runMenu()

/**
 * Displays the main menu and handles user input to navigate through the application's features.
 */

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addClient()
            2 -> deleteClient()
            3 -> updateClient()
            4 -> listClients()
            5 -> markClientAsInactive()
            6 -> addDogToClient()
            7 -> updateDogDetailsInClient()
            8 -> deleteADog()
            9 -> searchClients()
            10 -> searchDogs()
            11 -> searchBreeds()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

/**
 * Displays the main menu options to the user and reads their selection.
 * @return The selected menu option as an integer.
 */

fun mainMenu() = readNextInt(
    """ 
         > 
         > 
         >           ∘˙○˚.•.°.°• ∘˙○˚.•.°• ∘˙○˚.•∘˙○˚.•.°•∘˙○˚.•.°•∘˙○˚.•.°
         >    °∘˙○˚.•.°∘˙○˚.•.°•   BUBBLES AND BLOSSOMS APP   .°• ∘˙○˚.•∘˙○˚.•.° 
         >          ∘˙○˚.•.°.°• ∘˙○˚.•.°• ∘˙○˚.•∘˙○˚.•.°•∘˙○˚.•.°•∘˙○˚.•.° 
         >                  
         >        
         >          옷 CLIENT MENU                          𐂯 DOG MENU 
         >         
         >       𝟏. Add a Client                         𝟔. Add Dog To Client
         >       𝟐. Delete a Client                      𝟕. Update Dog Details
         >       𝟑. Update a Clients Details             𝟖. Remove Dog from Files
         >       𝟒. List Clients                         
         >       𝟓. Is this an inactive Client?            
         > 
         >  ⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿
         >         
         >                                  ⌕ SEARCH  
         >                                                          
         >                         𝟗. Search for Client by Name                    
         >                        𝟏𝟎. Search for Dog by Name                      
         >                        𝟏𝟏. Search for Dog by Breed                 
         >                          
         >  ⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆  
         > 
         >                                   𝟎. Exit                                         
         >                                 
         >  ⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆ 
         > 
         > ○˚✿° : """.trimMargin(">")
)

//------------------------------------
//CLIENT MENU
//------------------------------------

/**
 * Prompts the user to enter details for a new client and adds the client to the system.
 */

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

/**
 * Displays a list of clients, allowing the user to choose viewing all or only inactive clients.
 */

fun listClients() {
    if (clientAPI.numberOfClients() > 0) {
        val option = readNextInt(
            """
                  > ˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿
                  > 
                  >          𝟏. View ALL clients        
                  >          𝟐. View INACTIVE clients   
                  >           
                  > ˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿˚✿˖°⋆˚✿˖°⋆˚✿˖°⋆˚✿
                  > 
         > ○˚✿° : """.trimMargin(">")
        )

        when (option) {
            1 -> listAllClients()
            2 -> listInactiveClients()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No clients stored")
    }
}

/**
 * Prints a list of all clients.
 */

fun listAllClients() = println(clientAPI.listAllClients())

/**
 * Prints a list of all inactive clients.
 */

fun listInactiveClients() = println(clientAPI.listInactiveClients())

/**
 * Allows the user to update an existing client's details.
 */

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

/**
 * Allows the user to delete an existing client by their ID.
 */

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

/**
 * Marks a client as inactive if they exist and are active.
 */

fun markClientAsInactive() {
    listInactiveClients()
    if (clientAPI.numberOfInactiveClients() > 0) {
        // only ask the user to choose the client to archive if active clients exist
        val id = readNextInt("Enter the id of the client you want to mark as inactive: ")
        // pass the index of the client to ClientAPI for archiving and check for success.
        if (clientAPI.markAsInactive(id)) {
            println("This client is now marked as inactive!")
        } else {
            println("This client is NOT marked as inactive")
        }
    }
}

//-------------------------------------------
//DOG MENU (only available for active clients)
//-------------------------------------------
/**
 * Adds a new dog to a selected client.
 */

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

/**
 * Updates the details of a dog for a selected client.
 * Prompts the user to choose a client and then a dog, and allows them to enter new details for the dog.
 * If the update is successful, a success message is displayed; otherwise, a failure message is shown.
 */

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

/**
 * Deletes a dog from the selected client's records.
 * Prompts the user to choose a client and then a dog. If the deletion is successful, a confirmation message is displayed.
 * Otherwise, a failure message is shown.
 */

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
/**
 * Searches for clients by their name.
 * Prompts the user to enter a client name, and displays the search results.
 * If no clients are found, a message is displayed indicating no clients were found.
 */

fun searchClients() {
    val searchTitle = readNextLine("Enter the name of the client you want to search for: ")
    val searchResults = clientAPI.searchClientsByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No clients found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches for dogs by their name.
 * Prompts the user to enter a dog name, and displays the search results.
 * If no dogs are found, a message is displayed indicating no dogs were found.
 */

fun searchDogs() {
    val searchDogs = readNextLine("Enter the name of the dog you want to search for: ")
    val searchResults = clientAPI.searchDogsByName(searchDogs)
    if (searchResults.isEmpty()) {
        println("No dogs found")
    } else {
        println(searchResults)
    }
}

/**
 * Searches for dogs by their breed.
 * Prompts the user to enter a breed name, and displays the search results.
 * If no dogs are found with the specified breed, a message is displayed indicating no dogs were found.
 */

fun searchBreeds() {
    val searchBreeds = readNextLine("Enter the breed of dog you want to search by: ")
    val searchResults = clientAPI.searchDogsByBreed(searchBreeds)
    if (searchResults.isEmpty()) {
        println("No dogs found")
    } else {
        println(searchResults)
    }
}

//------------------------------------
// Exit App
//------------------------------------

/**
 * Exits the application.
 * Displays a farewell message and terminates the program.
 */

fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}

//------------------------------------
//HELPER FUNCTIONS
//------------------------------------
/**
 * Prompts the user to choose a client from the list of active clients.
 * Displays the list of clients and allows the user to select one by entering the client ID.
 * Returns the selected client if valid, or null if no valid client is selected.
 *
 * @return The chosen [Client], or null if no valid client is selected.
 */

private fun askUserToChooseClient(): Client? {
    listClients()
    if (clientAPI.numberOfClients() > 0) {
        val client = clientAPI.findClient(readNextInt("\nEnter the id of the client: "))
        if (client != null) {
            if (client.isInactiveClient) {
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

/**
 * Prompts the user to choose a dog for the selected client.
 * Displays the list of dogs for the client and allows the user to select one by entering the dog ID.
 * Returns the selected dog if valid, or null if no valid dog is selected.
 *
 * @param client The client whose dogs are to be displayed and chosen from.
 * @return The chosen [Dog], or null if no valid dog is selected.
 */

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


