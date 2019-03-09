package universityoftechnology.polytechnic.com.service_provider.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dmax.dialog.SpotsDialog
import java.util.regex.Pattern
import org.json.JSONObject
import universityoftechnology.polytechnic.com.service_provider.R
import universityoftechnology.polytechnic.com.service_provider.model.User


class RegisterActivity : AppCompatActivity() {


    var Server : String = ""
    var existUserName : Boolean = true  // username đã tồn tại

    var editUsername : EditText? = null
    var editPassword : EditText? = null
    var editConfirmPassword : EditText? = null
    var editNameNhahang : EditText? = null
    var editEmail : EditText? = null
    var editNumberPhone : EditText? = null
    var editAddress : EditText? = null

    var messageUserName : LinearLayout? = null
    var messagePassword : LinearLayout? = null
    var messageConfirmPassword : LinearLayout? = null
    var messageNameNhahang : LinearLayout? = null
    var messageEmail : LinearLayout? = null
    var messageNumberPhone : LinearLayout? = null
    var messageAddress : LinearLayout? = null

    var txtErrorUserName : TextView? = null
    var txtErrorConfirmPassword : TextView? = null
    var txtErrorEmail : TextView? = null
    var txtErrorPhonenumber : TextView? = null
    var txtErrorPassword : TextView? = null

    var btnSave : Button? = null

    var dialog : android.app.AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        Server = resources.getString(R.string.service)
        initView()
        addActionListener()
    }

    fun initView(){
        editUsername = findViewById(R.id.username)
        messageUserName = findViewById(R.id.error_username)

        editPassword = findViewById(R.id.password)
        messagePassword = findViewById(R.id.error_password)

        editConfirmPassword = findViewById(R.id.confirm_password)
        messageConfirmPassword = findViewById(R.id.error_confirm_password)

        editNameNhahang = findViewById(R.id.name_nhahang)
        messageNameNhahang = findViewById(R.id.error_ten_nha_hang)

        editEmail = findViewById(R.id.email)
        messageEmail = findViewById(R.id.error_email)

        editNumberPhone = findViewById(R.id.phonenumber)
        messageNumberPhone = findViewById(R.id.error_phonenumber)

        editAddress = findViewById(R.id.address)
        messageAddress = findViewById(R.id.error_address)

        btnSave = findViewById(R.id.save)

        txtErrorConfirmPassword = findViewById(R.id.txt_error_confirmcode)
        txtErrorEmail = findViewById(R.id.txt_error_email)
        txtErrorUserName = findViewById(R.id.txt_error_username)
        txtErrorPhonenumber = findViewById(R.id.txt_error_phonenumber)
        txtErrorPassword = findViewById(R.id.txt_error_password)
    }

    fun addActionListener(){
        editUsername!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().equals("")){
                    messageUserName!!.visibility = View.VISIBLE
                    txtErrorUserName!!.text = "Trường này không được để trống"
                }
                else if(s.toString().length < 6 ){
                    messageUserName!!.visibility = View.VISIBLE
                    txtErrorUserName!!.text = "Tên đăng nhập phải 6 đến 10 kí tự"
                }
               /* else if(checkExistUsername(s.toString())){ // username đã tồn tại
                    messageUserName!!.visibility = View.VISIBLE
                    txtErrorUserName!!.text = "Tên đăng nhập đã tồn tại"
                }*/
                else{
                    messageUserName!!.visibility = View.GONE
                }
            }
        })

        editPassword!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().equals("")){
                    messagePassword!!.visibility = View.VISIBLE
                    txtErrorPassword!!.text = "Trường này không được để trống"
                }
                else if(s.toString().length < 6 ){
                    messagePassword!!.visibility = View.VISIBLE
                    txtErrorPassword!!.text = "Mật khẩu có ít nhất 6 kí tự"
                }
                else{
                    messagePassword!!.visibility = View.GONE
                }
            }
        })

        editConfirmPassword!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().equals("")){
                    messageConfirmPassword!!.visibility = View.VISIBLE
                    txtErrorConfirmPassword!!.text = "Trường này không được để trống"
                }
                else if(s.toString().length < 6 ){
                    messageConfirmPassword!!.visibility = View.VISIBLE
                    txtErrorConfirmPassword!!.text = "Mật khẩu có ít nhất 6 kí tự"
                }
                else if(!s.toString().equals(editPassword!!.text.toString())){
                    messageConfirmPassword!!.visibility = View.VISIBLE
                    txtErrorConfirmPassword!!.text = "Mật khẩu và xác nhận không chính xác"
                }
                else{
                    messageConfirmPassword!!.visibility = View.GONE
                }
            }
        })

        editNameNhahang!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().equals("")){
                    messageNameNhahang!!.visibility = View.VISIBLE
                }
                else{
                    messageNameNhahang!!.visibility = View.GONE
                }
            }
        })

        editEmail!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().equals("")){
                    messageEmail!!.visibility = View.VISIBLE
                    txtErrorEmail!!.text = "Trường này không được để trống"
                }
                else if (!checkMail(s.toString())){
                    messageEmail!!.visibility = View.VISIBLE
                    txtErrorEmail!!.text = "Địa chỉ Email không hợp lệ"
                }
                else{
                    messageEmail!!.visibility = View.GONE
                }
            }
        })

        editNumberPhone!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().equals("")){
                    messageNumberPhone!!.visibility = View.VISIBLE
                    txtErrorPhonenumber!!.text = "Trường này không được để trống"
                }
                else if(!checkPhoneNumber(s.toString())){
                    messageNumberPhone!!.visibility = View.VISIBLE
                    txtErrorPhonenumber!!.text = "Số điện thoại không hợp lệ"
                }
                else{
                    messageNumberPhone!!.visibility = View.GONE
                }
            }
        })

        editAddress!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().equals("")){
                    messageAddress!!.visibility = View.VISIBLE
                }
                else{
                    messageAddress!!.visibility = View.GONE
                }
            }
        })

        btnSave!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                //checkExistUsername(editUsername!!.text.toString().trim())
                var user : User = User(editUsername!!.text.toString(), editPassword!!.text.toString(), editNameNhahang!!.text.toString(),
                    editEmail!!.text.toString(), editNumberPhone!!.text.toString(), editAddress!!.text.toString())

                RegisterUser(user)
            }
        })
    }

    fun checkExistUsername(userName : String) : Boolean{
        var check : Boolean = false
        var url: String = Server + "/auth/checkUsername?username=" + userName
        Log.d("url_username", url)
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)
        var stringRequest : StringRequest = object : StringRequest(Request.Method.GET, url,Response.Listener { s ->
            try {
                var jobj = JSONObject(s)
                var success: String = jobj.getString("success")
                if (success.equals("true")){ // username chưa tồn tại
                    check = false
                }else{
                    check = true
                }
            }catch (e : Exception){
                Log.d("Error", e.toString())
            }
        }, Response.ErrorListener { e ->

        }){}
        requestQueue.add(stringRequest)

        return check
    }

    fun checkMail(email : String) : Boolean{
        var EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+")
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    fun checkPhoneNumber(phoneNumber : String) : Boolean {
        var phoneUtil : PhoneNumberUtil = PhoneNumberUtil.getInstance()
        try {
            var swissNumberProto = phoneUtil.parse(phoneNumber, "VN")
            var isValid = phoneUtil.isValidNumber(swissNumberProto)
            if(isValid){
                return true
            }
            else{
               return false
            }
        } catch (e: Exception) {
            return false
        }

        return false
    }

    fun RegisterUser(user : User){
        dialog = SpotsDialog.Builder().setContext(this@RegisterActivity)
            .setMessage("Registering...")
            .build()

        dialog!!.show()
        var url: String = Server + "/auth/register "
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)
        var stringRequest : StringRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener { s ->
            try {
                var jobj = JSONObject(s)
                var success: String = jobj.getString("success")
                if (success.equals("true")){ // đăng kí tài khoản thành công
                    if(dialog != null) dialog!!.dismiss()
                    Toast.makeText(applicationContext, "Đăng kí tài khoản thành công", Toast.LENGTH_SHORT).show()
                }else{
                    if(dialog != null) dialog!!.dismiss()
                    Toast.makeText(applicationContext, "Đăng kí tài khoản thất bại", Toast.LENGTH_SHORT).show()
                }
            }catch (e : Exception){
                Log.d("Error", e.toString())
            }
            },
            Response.ErrorListener { e ->

            }){
                override fun getParams(): Map<String, String> {
                    var params : HashMap<String, String> = HashMap<String, String>()
                    params.put("name", user.nameNhaHang!!)
                    params.put("username", user.userName!!)
                    params.put("password", user.password!!)
                    params.put("email", user.email!!)
                    params.put("tel", user.phoneNumber!!)
                    params.put("address", user.address!!)
                    return params
                }
            }
        requestQueue.add(stringRequest)
    }
}
