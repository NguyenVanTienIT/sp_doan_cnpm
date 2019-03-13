package universityoftechnology.polytechnic.com.service_provider.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import dmax.dialog.SpotsDialog
import org.json.JSONObject
import universityoftechnology.polytechnic.com.service_provider.R
import android.content.SharedPreferences
import android.preference.PreferenceManager


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    var optionalUser : Spinner? = null
    var editTextMaNhaHang : EditText? = null
    var textRegister : TextView? = null
    var editUserName : EditText? = null
    var editPassword : EditText? = null
    var btnLogin : Button? = null
    var dialog : android.app.AlertDialog? = null
    var Server : String = ""
    var sharedpreference : SharedPreferences? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Server = resources.getString(R.string.service)
        optionalUser = findViewById(R.id.option_user)
        editTextMaNhaHang = findViewById(R.id.ma_nha_hang)

        optionalUser!!.setSelection(0, true)

        ArrayAdapter.createFromResource(
            this,
            R.array.optionl_user,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            optionalUser!!.adapter = adapter

        }
        optionalUser!!.onItemSelectedListener = this

        initView()
        addActionListener()



    }

    fun initView(){
        textRegister = findViewById(R.id.register)
        editUserName = findViewById(R.id.username)
        editPassword = findViewById(R.id.password)
        btnLogin = findViewById(R.id.login)

        sharedpreference = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        if (sharedpreference!!.getString("token", null) != null) {
                var intent: Intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
        }
    }

    fun addActionListener(){
        textRegister!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intent : Intent = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intent)
            }
        })
        btnLogin!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                LoginUser(editUserName!!.text.toString(), editPassword!!.text.toString())
            }
        })

    }


    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position == 0){
            editTextMaNhaHang!!.visibility = View.VISIBLE
        }
        else{
            editTextMaNhaHang!!.visibility = View.GONE
        }
    }

    fun LoginUser(userName : String, password : String){
        dialog = SpotsDialog.Builder().setContext(this@MainActivity)
            .setMessage("Login...")
            .build()

        dialog!!.show()
        var url: String = Server + "/auth/login-provider"
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)
        var stringRequest : StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { s ->
                try {
                    var jobj = JSONObject(s)
                    var user: String? = jobj.getString("username")
                    if (user != null){ // Login thành công
                        if(dialog != null) dialog!!.dismiss()
                        Toast.makeText(applicationContext, "Đăng nhập tài khoản thành công", Toast.LENGTH_SHORT).show()
                        var intent : Intent = Intent(applicationContext, HomeActivity::class.java)
                        sharedpreference!!.edit().putString("token",jobj.getString("accessToken")).apply();
                        startActivity(intent)
                    }else{
                        if(dialog != null) dialog!!.dismiss()
                        Toast.makeText(applicationContext, "Đăng nhập tài khoản thất bại", Toast.LENGTH_SHORT).show()
                    }
                }catch (e : Exception){
                    if(dialog != null) dialog!!.dismiss()
                    Toast.makeText(applicationContext, "Đăng nhập tài khoản thất bại", Toast.LENGTH_SHORT).show()
                    Log.d("Error", e.toString())
                }
            },
            Response.ErrorListener { e ->
                if(dialog != null) dialog!!.dismiss()
                Toast.makeText(applicationContext, "Đăng nhập tài khoản thất bại", Toast.LENGTH_SHORT).show()
            }){
            override fun getParams(): Map<String, String> {
                var params : HashMap<String, String> = HashMap<String, String>()
                params.put("username", userName)
                params.put("password", password)
                return params
            }
        }
        requestQueue.add(stringRequest)
    }
}
