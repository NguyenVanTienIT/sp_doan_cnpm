package universityoftechnology.polytechnic.com.service_provider.model

import org.json.JSONObject

class SavedAddress {
    var serviceUrl : String? = null
    var channelId : String? = null
    var id : String? = null
    var bot : HashMap<String, String>? = HashMap()
    var conversation : HashMap<String, String>? = HashMap()
    var user : HashMap<String, String>? = HashMap()

    fun parseObject(json : JSONObject){
        this.serviceUrl = json.getString("serviceUrl")
        this.channelId = json.getString("channelId")
        this.id = json.getString("id")

        var jsonBot : JSONObject = JSONObject(json.getString("bot"))
        this.bot!!.put("name", jsonBot.getString("name"))
        this.bot!!.put("id", jsonBot.getString("id"))

        var jsonConversation : JSONObject = JSONObject(json.getString("conversation"))
        this.conversation!!.put("id", jsonConversation.getString("id"))

        var jsonUser : JSONObject = JSONObject(json.getString("user"))
        this.user!!.put("name", jsonUser.getString("name"))
        this.user!!.put("id", jsonUser.getString("id"))
    }
}