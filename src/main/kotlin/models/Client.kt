package ie.setu.models

import utils.formatSetString

/**
 * Represents a client who owns dogs and interacts with the system.
 * @property clientId The unique identifier for the client.
 * @property ClientName The name of the client.
 * @property email The email address of the client.
 * @property phone The phone number of the client.
 * @property PaymentMethod The preferred payment method of the client.
 * @property isInactiveClient Indicates whether the client is inactive.
 * @property dogs A mutable set of dogs owned by the client.
 */

data class Client(  var clientId: Int = 0,
                    var ClientName: String,
                    var email: String,
                    var phone: Int,
                    var PaymentMethod: String,
                    var isInactiveClient: Boolean = false,
                    var dogs: MutableSet<Dog> = mutableSetOf()) {

    /**
     * Tracks the last assigned ID for a dog owned by the client.
     */

    private var lastDogId = 0

    /**
     * Generates a unique ID for a new dog.
     * @return The next unique integer ID for a dog.
     */

    private fun getNextDogId() = lastDogId++

    /**
     * Adds a new dog to the client's collection.
     * @param dog The dog to be added.
     * @return True if the dog was added successfully, false otherwise.
     */

    fun addDog(dog: Dog) : Boolean {
        dog.dogId = getNextDogId()
        return dogs.add(dog)
    }

    /**
     * Gets the total number of dogs owned by the client.
     * @return The number of dogs owned by the client.
     */

    fun numberOfDogs()= dogs.size

    /**
     * Finds a dog owned by the client by its ID.
     * @param id The ID of the dog to find.
     * @return The dog object if found, or null if not found.
     */

    fun findOne(id: Int): Dog?{
        return dogs.find{ dog -> dog.dogId == id}
    }

    /**
     * Deletes a dog from the client's collection by its ID.
     * @param id The ID of the dog to delete.
     * @return True if the dog was successfully removed, false otherwise.
     */

    fun delete(id: Int): Boolean {
        return dogs.removeIf { dog -> dog.dogId == id}
    }

    /**
     * Updates the details of a dog owned by the client.
     * @param id The ID of the dog to update.
     * @param newDog The new details for the dog.
     * @return True if the update was successful, false otherwise.
     */

    fun update(id: Int, newDog : Dog): Boolean {
        val foundDog = findOne(id)

        //if the object exists, use the details passed in the newItem parameter to
        //update the found object in the Set
        if (foundDog != null){
            foundDog.dogId = newDog.dogId
            foundDog.nameofdog = newDog.nameofdog
            foundDog.breed = newDog.breed
            foundDog.colour = newDog.colour
            foundDog.size = newDog.size
            foundDog.coattype = newDog.coattype
            foundDog.price = newDog.price
            return true
        }

        //if the object was not found, return false, indicating that the update was not successful
        return false
    }

    /**
     * Lists all dogs owned by the client.
     * @return A formatted string of all dogs or a message if no dogs are added.
     */

    fun listDogs() =
        if (dogs.isEmpty())  "\tNO DOGS ADDED"
        else  formatSetString(dogs)

    /**
     * Provides a string representation of the client and their dogs.
     * @return A string summarizing the client's details and their dogs.
     */

    override fun toString(): String {
        val InactiveClient = if (isInactiveClient) 'Y' else 'N'
        return "$clientId: $ClientName, Email($email), Phone($phone), Payment Method($PaymentMethod) \n${listDogs()}"
    }

}