package universityoftechnology.polytechnic.com.service_provider.model

class User (user_name : String, pass : String, name_Nhahang : String, Email : String, phone : String, Address : String){
    var userName : String? = null
    var password : String? = null
    var nameNhaHang : String? = null
    var email : String? = null
    var phoneNumber : String? = null
    var address : String? = null

    init {
        userName = user_name
        password = pass
        nameNhaHang = name_Nhahang
        email = Email
        phoneNumber = phone
        address = Address
    }
}