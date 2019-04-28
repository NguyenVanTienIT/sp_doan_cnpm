package universityoftechnology.polytechnic.com.service_provider.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import universityoftechnology.polytechnic.com.service_provider.Fragment.ThucDon_Fragment
import universityoftechnology.polytechnic.com.service_provider.Fragment.YeuCau_Fragment
import universityoftechnology.polytechnic.com.service_provider.R
import android.content.DialogInterface



class InformationMenuActivity : AppCompatActivity() {
    var thumnail : ImageView? = null
    var txtName : EditText? = null
    var txtDescreption : EditText? = null
    var txtPrice : EditText? = null

    var errorName : LinearLayout? = null
    var erroDescreption : LinearLayout? = null
    var errorPrice : LinearLayout? = null

    var btnSave : Button? = null
    var btnRemove : Button? = null
    var btnBack : ImageButton? = null

    var idMenu : String? = null
    var nameMenu : String? = null
    var descriptionMenu : String? = null
    var thumbnailMenu : String? = null
    var priceMenu : Int? = null

    var sharedpreference : SharedPreferences? = null
    var Server : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_menu)

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
*/
        sharedpreference = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        Server = resources.getString(R.string.service)
        var intent : Intent = intent
        idMenu = intent.getStringExtra("id")
        nameMenu = intent.getStringExtra("Name")
        descriptionMenu = intent.getStringExtra("Descreption")
        thumbnailMenu = intent.getStringExtra("Thumbnail")
        priceMenu = intent.getIntExtra("Price", 0)

        initView()
        initAction()
    }

    fun initView(){
        thumnail = findViewById(R.id.thumnail)
        txtName = findViewById(R.id.name)
        txtDescreption = findViewById(R.id.descreption)
        txtPrice = findViewById(R.id.price)

        errorName  = findViewById(R.id.error_name)
        erroDescreption = findViewById(R.id.error_descreption)
        errorPrice = findViewById(R.id.error_price)

        btnSave = findViewById(R.id.save)
        btnRemove = findViewById(R.id.remove)
        btnBack = findViewById(R.id.back)

        txtName!!.setText(nameMenu)
        txtDescreption!!.setText(descriptionMenu)
        txtPrice!!.setText(priceMenu.toString())


        // load ảnh ở đây
    }

    fun initAction(){
        btnBack!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }
        })

        btnSave!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (txtName!!.text.toString() == "" || txtName!!.text.toString() == " "){
                    errorName!!.visibility = View.VISIBLE
                }else if (txtDescreption!!.text.toString() == "" || txtDescreption!!.text.toString() == " "){
                    erroDescreption!!.visibility = View.VISIBLE
                }else if (txtPrice!!.text.toString() == "" || txtPrice!!.text.toString() == " "){
                    errorPrice!!.visibility = View.VISIBLE
                }else{
                    nameMenu = txtName!!.text.toString()
                    descriptionMenu = txtDescreption!!.text.toString()
                    priceMenu = Integer.parseInt(txtPrice!!.text.toString())
                    saveMenu()
                }

            }
        })

        btnRemove!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                showDiaglog()
            }
        })
    }



    fun showDiaglog(){
        var alert = AlertDialog.Builder(this@InformationMenuActivity)
        .setTitle("Remove Food and Drink")
        .setMessage(
        "Do you want to remove this product")
        .setIcon(
        resources.getDrawable(
        android.R.drawable.ic_dialog_alert))
        .setPositiveButton(
        "Ok"
        ) { dialog, which ->
            //Do Something Here
            removeMenu()
        }
            .setNegativeButton(
        "Cancel",
        object:DialogInterface.OnClickListener {

        override fun onClick(dialog:DialogInterface,
        which:Int) {
         //finish()
                                }
        }).show()
    }

    fun saveMenu(){
        var token = sharedpreference!!.getString("token", null)
        var url: String = Server + "/provider/menu/update"
        var requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
        var stringRequest : StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { s ->
                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()
                ThucDon_Fragment.isChange = true
                finish()

            },
            Response.ErrorListener { e ->
                Log.d("Accept_Book", e.toString())
            }){
            override fun getBody(): ByteArray {
                var json : String = " {\n" +
                        "        \"_id\": \""+idMenu+"\",\n" +
                        "        \"name\": \""+nameMenu+"\",\n" +
                        "        \"description\": \""+descriptionMenu+"\",\n" +
                        "        \"thumbnail\": \""+thumbnailMenu+"\",\n" +
                        "        \"price\": "+priceMenu+"\n" +
                        "    }"
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
    fun removeMenu(){
        var token = sharedpreference!!.getString("token", null)
        var url: String = Server + "/provider/menu/delete"
        var requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
        var stringRequest : StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { s ->
                Toast.makeText(applicationContext, "Deleted", Toast.LENGTH_SHORT).show()
                ThucDon_Fragment.isChange = true
                finish()

            },
            Response.ErrorListener { e ->
                Log.d("Accept_Book", e.toString())
            }){
            override fun getBody(): ByteArray {
                var json : String = " {\n" +
                        "        \"menuId\": \""+idMenu+"\"\n" +
                        "    }"
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
}
