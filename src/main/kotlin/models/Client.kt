package ie.setu.models

data class Client(  var clientId: Int = 0,
                    var ClientName: String,
                    var email: String,
                    var phone: Int,
                    var PaymentMethod: String)