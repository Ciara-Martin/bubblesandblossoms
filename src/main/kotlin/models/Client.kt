package ie.setu.models

import utils.formatSetString

data class Client(  var clientId: Int = 0,
                    var ClientName: String,
                    var email: String,
                    var phone: Int,
                    var PaymentMethod: String,
                    var isInactiveClient: Boolean = false,
                    var dogs: MutableSet<Dog> = mutableSetOf()) {

    private var lastDogId = 0
    private fun getNextDogId() = lastDogId++

    fun addDog(dog: Dog) : Boolean {
        dog.dogId = getNextDogId()
        return dogs.add(dog)
    }

    fun numberOfDogs()= dogs.size

    fun findOne(id: Int): Dog?{
        return dogs.find{ dog -> dog.dogId == id}
    }

    fun delete(id: Int): Boolean {
        return dogs.removeIf { dog -> dog.dogId == id}
    }

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

    fun listDogs() =
        if (dogs.isEmpty())  "\tNO DOGS ADDED"
        else  formatSetString(dogs)

    override fun toString(): String {
        val InactiveClient = if (isInactiveClient) 'Y' else 'N'
        return "$clientId: $ClientName, Email($email), Phone($phone), Payment Method($PaymentMethod) \n${listDogs()}"
    }

}