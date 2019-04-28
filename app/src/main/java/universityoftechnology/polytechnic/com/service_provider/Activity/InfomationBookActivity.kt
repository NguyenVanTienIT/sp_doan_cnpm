package universityoftechnology.polytechnic.com.service_provider.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
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
import universityoftechnology.polytechnic.com.service_provider.Fragment.YeuCau_Fragment
import universityoftechnology.polytechnic.com.service_provider.R

class InfomationBookActivity : AppCompatActivity() {

    var txtName : TextView? = null
    var txtNumber : TextView? = null
    var txtTime : TextView? = null
    var txtNote : TextView? = null

    var listButton : LinearLayout? = null
    var btnAccept : Button? = null
    var btnDeline : Button? = null
    var btnBack : ImageButton? = null

    var jsonBook : String? = null
    var sharedpreference : SharedPreferences? = null
    var Server : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infomation_book)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        var intent : Intent = getIntent()
        sharedpreference = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        Server = resources.getString(R.string.service)
        jsonBook= intent.getStringExtra("jsonBook")
        initView(intent)
        initAction()
    }

    fun initView(intent : Intent){
        txtName = findViewById(R.id.ten_khach_hang)
        txtNumber = findViewById(R.id.so_luong)
        txtTime = findViewById(R.id.thoi_gian)
        txtNote = findViewById(R.id.ghi_chu)

        listButton = findViewById(R.id.list_btn)
        btnAccept = findViewById(R.id.accept)
        btnDeline = findViewById(R.id.decline)
        btnBack = findViewById(R.id.btn_back)

        if (intent.getIntExtra("status", 0) == 2){
            listButton!!.visibility = View.GONE
        }else{
            listButton!!.visibility = View.VISIBLE
        }
        txtName!!.setText(intent.getStringExtra("name"))
        txtNumber!!.setText(intent.getStringExtra("number"))
        txtTime!!.setText(intent.getStringExtra("time"))
        txtNote!!.setText("Không có")
    }

    fun initAction(){
        btnBack!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }
        })

        btnAccept!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                acceptBook()
            }
        })

        btnDeline!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                declineBook()
            }
        })
    }

    fun acceptBook(){
        var index = jsonBook!!.lastIndexOf("\"status\":")
        var json : String = jsonBook!!.substring(0, index + 9)+"2"+jsonBook!!.substring(index + 10)
        Log.d("ACCEPT_BOOK", jsonBook!!.substring(0, index + 9)+"2"+jsonBook!!.substring(index + 10))

        var token = sharedpreference!!.getString("token", null)
        var url: String = Server + "/provider/books/updateBook"
        var requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
        var stringRequest : StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { s ->

                var jobj = JSONObject(s)
                // Log.d("Request_Ship", data!![0].toString())
                Toast.makeText(applicationContext, "Accepted", Toast.LENGTH_SHORT).show()
                YeuCau_Fragment.acceptAndDecline = true
                finish()

            },
            Response.ErrorListener { e ->
                Log.d("Accept_Book", e.toString())
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

    fun declineBook(){
        var index = jsonBook!!.lastIndexOf("\"status\":")
        var json : String = jsonBook!!.substring(0, index + 9)+"3"+jsonBook!!.substring(index + 10)
        Log.d("ACCEPT_BOOK", jsonBook!!.substring(0, index + 9)+"2"+jsonBook!!.substring(index + 10))

        var token = sharedpreference!!.getString("token", null)
        var url: String = Server + "/provider/books/updateBook"
        var requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
        var stringRequest : StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { s ->

                var jobj = JSONObject(s)
                // Log.d("Request_Ship", data!![0].toString())
                Toast.makeText(applicationContext, "Declined", Toast.LENGTH_SHORT).show()
                YeuCau_Fragment.acceptAndDecline = true
                finish()

            },
            Response.ErrorListener { e ->
                Log.d("Accept_Book", e.toString())
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
}
