package ie.setu.models

/**
 * Represents a dog with various attributes including its breed, size, and price.
 * @property dogId The unique identifier for the dog.
 * @property nameofdog The name of the dog.
 * @property breed The breed of the dog.
 * @property colour The color of the dog's coat.
 * @property size The size of the dog (e.g., small, medium, large).
 * @property coattype The type of coat the dog has (e.g., short, long, curly).
 * @property price The price of the dog.
 */

data class Dog(var dogId: Int = 0,
               var nameofdog: String,
               var breed: String,
               var colour: String,
               var size: String,
               var coattype: String,
               var price: Int)