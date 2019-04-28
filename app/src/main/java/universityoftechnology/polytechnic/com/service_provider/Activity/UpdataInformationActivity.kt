package universityoftechnology.polytechnic.com.service_provider.Activity

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
//import com.android.volley.request.SimpleMultiPartRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.async.future.FutureRunnable
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import universityoftechnology.polytechnic.com.service_provider.R
import universityoftechnology.polytechnic.com.service_provider.UploadImage.UploadFile
import universityoftechnology.polytechnic.com.service_provider.model.InformationUser
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.ObjectOutputStream
import java.net.URL
import java.util.concurrent.Future
import java.util.regex.Pattern

class UpdataInformationActivity : AppCompatActivity() {
    var imageAvatar : ImageView? = null
    var btnUpload : ImageView? = null
    var username : EditText? = null
    var tenNhaHang : EditText? = null
    var email : EditText? = null
    var numberPhone : EditText? = null
    var address : EditText? = null
    var btnSave : Button? = null
    var btnBack : ImageButton? = null

    var Server : String = ""
    val MY_PERMISSIONS_CAMERA = 10
    val PICK_IMAGE_REQUEST = 20

    var linearErrorTenNhaHang : LinearLayout? = null
    var txtErrorEmail : TextView? = null
    var linearErrorEmail : LinearLayout? = null
    var txtErrorPhone : TextView? = null
    var linearErrorPhone : LinearLayout? = null
    var linearErrorAddress : LinearLayout? = null

    var sharedpreference : SharedPreferences? = null

    var bitmap : Bitmap? = null
    var pathFile : String? = null
    var jsonObject : JsonObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updata_information)
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }*/

        sharedpreference = PreferenceManager.getDefaultSharedPreferences(this)
        Server = resources.getString(R.string.service)
        jsonObject = JsonObject()
        initView()
        initData()
        initAction()
    }

    override fun onBackPressed() {
        return
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 1){
                var bundel : Bundle = data!!.extras
                var btm : Bitmap = bundel.get("data") as Bitmap
                bitmap = btm
                imageAvatar!!.setImageBitmap(btm)
                //uploadImageToServer()
                upload()
                //settingImage()
            }
            else if (requestCode == 2){

                var selection : Uri = data!!.data
                imageAvatar!!.setImageURI(selection)
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selection)
                pathFile = UploadFile.getPathFromURI(selection, applicationContext)
                Log.d("Ketqua", pathFile)
                //uploadImageToServer()
                upload()
                //settingImage()

            }
        }
    }

    fun initView(){
        imageAvatar = findViewById(R.id.avatar)
        username = findViewById(R.id.username)
        tenNhaHang = findViewById(R.id.name_nhahang)
        email = findViewById(R.id.email)
        numberPhone = findViewById(R.id.phonenumber)
        address = findViewById(R.id.address)
        btnSave = findViewById(R.id.save)
        btnBack = findViewById(R.id.back)
        btnUpload = findViewById(R.id.upload)

        linearErrorTenNhaHang = findViewById(R.id.error_ten_nha_hang)
        linearErrorEmail = findViewById(R.id.error_email)
        txtErrorEmail = findViewById(R.id.txt_error_email)
        txtErrorPhone = findViewById(R.id.txt_error_phonenumber)
        linearErrorPhone = findViewById(R.id.error_phonenumber)
        linearErrorAddress = findViewById(R.id.error_address)
    }

    fun initData(){
        var gson : Gson = Gson()
        var json = sharedpreference!!.getString("Information_User", null)
        var informationUser : InformationUser = gson.fromJson(json, InformationUser::class.java)
        Log.d("INFORMATION", informationUser.username)
        username!!.setText(informationUser.username)
        tenNhaHang!!.setText(informationUser.name)
        email!!.setText(informationUser.email)
        numberPhone!!.setText(informationUser.tel)
        address!!.setText(informationUser.address)
    }

    fun initAction(){
        btnUpload!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (ContextCompat.checkSelfPermission(this@UpdataInformationActivity, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this@UpdataInformationActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this@UpdataInformationActivity,
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_CAMERA)
                }
                else {
                    chooseImage()
                }
            }
        })

        imageAvatar!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (ContextCompat.checkSelfPermission(this@UpdataInformationActivity, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this@UpdataInformationActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this@UpdataInformationActivity,
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_CAMERA)
                }
                else {
                    chooseImage()
                }
            }
        })


        btnSave!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if (tenNhaHang!!.text != null && address!!.text != null && email!!.text != null && numberPhone!!.text != null){
                    if (checkMail(email!!.text.toString()) && checkPhoneNumber(numberPhone!!.text.toString())){
                        updateInformationUser()
                    }
                }
            }
        })

        btnBack!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }
        })


        tenNhaHang!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().equals("")){
                    linearErrorTenNhaHang!!.visibility = View.VISIBLE
                }
                else{
                    linearErrorTenNhaHang!!.visibility = View.GONE
                }
            }
        })

        email!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().equals("")){
                    linearErrorEmail!!.visibility = View.VISIBLE
                    txtErrorEmail!!.text = "Trường này không được để trống"
                }
                else if (!checkMail(s.toString())){
                    linearErrorEmail!!.visibility = View.VISIBLE
                    txtErrorEmail!!.text = "Địa chỉ Email không hợp lệ"
                }
                else{
                    linearErrorEmail!!.visibility = View.GONE
                }
            }
        })

        numberPhone!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().equals("")){
                    linearErrorPhone!!.visibility = View.VISIBLE
                    txtErrorPhone!!.text = "Trường này không được để trống"
                }
                else if(!checkPhoneNumber(s.toString())){
                    linearErrorPhone!!.visibility = View.VISIBLE
                    txtErrorPhone!!.text = "Số điện thoại không hợp lệ"
                }
                else{
                    linearErrorPhone!!.visibility = View.GONE
                }
            }
        })

        address!!.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().equals("")){
                    linearErrorAddress!!.visibility = View.VISIBLE
                }
                else{
                    linearErrorAddress!!.visibility = View.GONE
                }
            }
        })

    }

    fun updateInformationUser(){
        // update user to server
        var url: String = Server + "/provider"
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)
        var stringRequest : StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { s ->
                try {
                    var jobj = JSONObject(s)
                    var success: String = jobj.getString("success")
                    if (success.equals("true")){ // update thành công
                        //if(dialog != null) dialog!!.dismiss()
                        updateInlocal()
                        Toast.makeText(applicationContext, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show()
                    }else{
                        //if(dialog != null) dialog!!.dismiss()
                        Toast.makeText(applicationContext, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show()
                    }
                }catch (e : Exception){
                    Log.d("Error", e.toString())
                }
            },
            Response.ErrorListener { e ->

            }){

            override fun getBody(): ByteArray {
                var json : String = "{\n" +
                        "\t\"providerInf\":{\n" +
                        "\t\t\"name\":\""+tenNhaHang!!.text+"\",\n" +
                        "\t\t\"email\":\""+email!!.text+"\",\n" +
                        "\t\t\"tel\":\""+numberPhone!!.text+"\",\n" +
                        "\t\t\"address\":\""+address!!.text+"\",\n" +
                        "\t\t\"backgroup\": \"test.jpg\"\n" +
                        "\t}\n" +
                        "}"

                return json.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                var headers : HashMap<String, String> = HashMap<String, String>()

                headers.put("Content-Type", "application/json")
                headers.put("Authorization", sharedpreference!!.getString("token", ""))
                return headers
            }
        }
        requestQueue.add(stringRequest)
    }

    fun chooseImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this@UpdataInformationActivity)
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 1)
            }
            else if (options[item] == "Choose from Gallery") {
                val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.setType("image/*")
                startActivityForResult(intent, 2)
            }
            else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    fun uploadImageToServer(){
        var progressDialog =  ProgressDialog(this@UpdataInformationActivity);
        progressDialog.setMessage("Uploading, please wait...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        //sending image to server
        var url: String = Server + "/provider/image/upload"
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)
        var stringRequest : StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { s ->
                try {
                    var jobj = JSONObject(s)
                    Log.d("Ketqua", jobj.toString())
                    var success: String = jobj.getString("success")
                    if (success.equals("true")){ // update thành công
                        //if(dialog != null) dialog!!.dismiss()
                        updateInlocal()
                        Log.d("Ketqua", jobj.getString("data"))
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Cập nhật ảnh thành công", Toast.LENGTH_SHORT).show()
                    }else{
                        //if(dialog != null) dialog!!.dismiss()
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Cập nhật ảnh thất bại", Toast.LENGTH_SHORT).show()
                    }
                }catch (e : Exception){
                    Log.d("Error", e.toString())
                }
            },
            Response.ErrorListener { e ->

            }){

            override fun getBodyContentType(): String {
                return "form-data"
            }
            override fun getBody(): ByteArray {
                var bos : ByteArrayOutputStream = ByteArrayOutputStream()
                var json : String = "{" +
                        "\"file\":"+"\""+getStringImage(bitmap!!)+"\""+
                        "}"
               // return parseImageToJson()

               /* var body : HashMap<String, File> = HashMap()
                body.put("file", File(pathFile))

                var byteOut : ByteArrayOutputStream = ByteArrayOutputStream()
                var out : ObjectOutputStream = ObjectOutputStream(byteOut);
                out.writeObject(body)
                return byteOut.toByteArray()*/
              //return UploadFile.readContentIntoByteArray(File(pathFile))
                return UploadFile.convertFileToByteArray(File(pathFile))
            }

            override fun getParams(): MutableMap<String, String> {
                var param : HashMap<String, String> = HashMap()
                param.put("type", "bg")
                return params
            }

            override fun getHeaders(): MutableMap<String, String> {
                var headers : HashMap<String, String> = HashMap<String, String>()
                //headers.put("Authorization", sharedpreference!!.getString("token", ""))
                headers.put("Content-Type", "application/x-www-form-urlencoded")
                return headers
            }
        }
        requestQueue.add(stringRequest)

    }

    fun upload(){
        var url: String = Server + "/provider/image/upload?type=menu"
        var file : File = File(pathFile)
        Log.d("Ketqua", file.length().toString())
        var upload : UploadFile = UploadFile()
        upload.UploadImageToServer(file, applicationContext, url)
    }


    fun parseImageToJson() : ByteArray{
        var baos : ByteArrayOutputStream =  ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var imageBytes = baos!!.toByteArray()
       // var imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return imageBytes
    }

     fun getStringImage(bmp : Bitmap) : String {
         var baos : ByteArrayOutputStream =  ByteArrayOutputStream()
         bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
         var imageBytes = baos.toByteArray();
         var encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
         return encodedImage;

     }

    fun updateInlocal(){
        var gson : Gson = Gson()
        var json = sharedpreference!!.getString("Information_User", null)
        var oldInformation : InformationUser = gson.fromJson(json, InformationUser::class.java)
        var informationUser : InformationUser = InformationUser()
        informationUser.username = oldInformation.username
        informationUser.address = address!!.text.toString()
        informationUser.email = email!!.text.toString()
        informationUser.tel = phonenumber!!.text.toString()
        informationUser.name = tenNhaHang!!.text.toString()
        informationUser.background = ""

        var token = sharedpreference!!.getString("token", "")

        var infor : String = "{\n" +
                "    \"username\": \""+informationUser.username+"\",\n" +
                "    \"name\": \""+informationUser.name+"\",\n" +
                "    \"email\": \""+informationUser.email+"\",\n" +
                "    \"tel\": \""+informationUser.tel+"\",\n" +
                "    \"address\": \""+informationUser.address+"\",\n" +
                "    \"background\": \"\",\n" +
                "    \"accessToken\": \""+token+"\"\n" +
                "}"

        sharedpreference!!.edit().putString("Information_User", infor ).apply()
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

}
