package universityoftechnology.polytechnic.com.service_provider.model

import android.provider.ContactsContract
import org.json.JSONArray

class Ship : java.io.Serializable {
    var _id: String? = null
    var provider: HashMap<String, String>? = null
    var customer: CustomerShip? = null
    var oderNumber: String? = null
    var createAt: String? = null
    var __v: Int? = null
    var address: String? = null
    var notes: String? = null
    var telephone: String? = null
    var status: Int? = null
    var isDeleted: Boolean? = null
    var isNotified: Boolean? = null
    var priceTotal: Int? = null
    var listDish: JSONArray? = null

    var jsonShip: String? = null
}