package universityoftechnology.polytechnic.com.service_provider.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import universityoftechnology.polytechnic.com.service_provider.R
import universityoftechnology.polytechnic.com.service_provider.adapter.ProviderAdapter
import universityoftechnology.polytechnic.com.service_provider.model.Product
import universityoftechnology.polytechnic.com.service_provider.model.Ship

class InformationShipActivity : AppCompatActivity() {


    var txtTime : TextView? = null
    var txtName : TextView? = null
    var txtPhoneNumber : TextView? = null
    var txtAddress : TextView? = null
    var listProviderView : RecyclerView? = null
    var listProduct : ArrayList<Product>? = null
    var productAdapter : ProviderAdapter? = null

    var listButton : LinearLayout? = null
    var btnAccept : Button? = null
    var btnDeline : Button? = null

    var jsonShip : String? = null
    var sharedpreference : SharedPreferences? = null
    var Server : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_ship)

        listProduct = ArrayList()
        sharedpreference = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        Server = resources.getString(R.string.service)
        productAdapter = ProviderAdapter(applicationContext, listProduct!!)
        var intent = intent

        jsonShip = intent.getStringExtra("jsonShip")


        initView(intent)
        initAction()


    }

    fun initView(intent : Intent){
        txtTime = findViewById(R.id.time)
        txtName = findViewById(R.id.name)
        txtPhoneNumber = findViewById(R.id.number_phone)
        txtAddress = findViewById(R.id.address)
        listProviderView = findViewById(R.id.list_product)

        listButton = findViewById(R.id.list_btn)
        btnAccept = findViewById(R.id.accept)
        btnDeline = findViewById(R.id.decline)

        if (intent.getIntExtra("status", 0) == 2 ) listButton!!.visibility = View.GONE

        txtTime!!.setText(intent.getStringExtra("createAt"))
        txtAddress!!.setText(intent.getStringExtra("address"))
        txtPhoneNumber!!.setText(intent.getStringExtra("telephone"))
        txtName!!.setText(intent.getStringExtra("name"))


        listProviderView!!.layoutManager = LinearLayoutManager(applicationContext) as RecyclerView.LayoutManager?
        val itemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager(applicationContext).orientation)
        listProviderView!!.setHasFixedSize(true)
        listProviderView!!.setLayoutManager(LinearLayoutManager(applicationContext))
        listProviderView!!.addItemDecoration(itemDecoration)
        listProviderView!!.adapter = productAdapter

        getListProvice(JSONArray(intent.getStringExtra("listproduct")))
    }

    fun initAction(){
        btnAccept!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                acceptShip()
            }
        })

        btnDeline!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                declineShip()
            }
        })
    }

    fun acceptShip(){
        var index = jsonShip!!.lastIndexOf("\"status\":")
        var json : String = jsonShip!!.substring(0, index + 9)+"2"+jsonShip!!.substring(index + 10)
        Log.d("ACCEPT_SHIP", jsonShip!!.substring(0, index + 9)+"2"+jsonShip!!.substring(index + 10))

        var token = sharedpreference!!.getString("token", null)
        var url: String = Server + "/provider/ships/updateShip"
        var requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
        var stringRequest : StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { s ->

                var jobj = JSONObject(s)
                // Log.d("Request_Ship", data!![0].toString())
                Toast.makeText(applicationContext, "Accept Complete", Toast.LENGTH_SHORT).show()
                finish()

            },
            Response.ErrorListener { e ->
                Log.d("Accept_Ship", e.toString())
            }){
            override fun getBody(): ByteArray {
                return json.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                var headers : HashMap<String, String> = HashMap<String, String>()

                headers.put("Content-Type", "application/json")
                headers.put("Authorization", token)
                return headers
            }
        }
        requestQueue.add(stringRequest)
    }

    fun declineShip(){

    }

    fun getListProvice(json : JSONArray){
        for (i in 0..json.length()-1){
            var product : Product = Product()
            var jsonProduct = JSONObject(json[i].toString())
            product.name = jsonProduct.getString("name")
            product.id = jsonProduct.getString("id")
            product._id = jsonProduct.getString("_id")
            product.price = jsonProduct.getInt("price")
            product.numberDishs = jsonProduct.getInt("numberDishs")

            listProduct!!.add(product)
        }
        productAdapter!!.notifyDataSetChanged()

    }
}
