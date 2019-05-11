package universityoftechnology.polytechnic.com.service_provider.Activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import universityoftechnology.polytechnic.com.service_provider.Fragment.ThucDon_Fragment
import universityoftechnology.polytechnic.com.service_provider.Interface.APIService
import universityoftechnology.polytechnic.com.service_provider.R
import universityoftechnology.polytechnic.com.service_provider.Upload.ApiClient
import universityoftechnology.polytechnic.com.service_provider.Upload.RequestAndRespone.ResponseFile
import universityoftechnology.polytechnic.com.service_provider.UploadImage.UploadFile
import java.io.File

class AddMenuActivity : AppCompatActivity() {
    var thumnail: ImageView? = null
    var txtName: EditText? = null
    var txtDescreption: EditText? = null
    var txtPrice: EditText? = null

    var errorName: LinearLayout? = null
    var erroDescreption: LinearLayout? = null
    var errorPrice: LinearLayout? = null

    var btnSave: Button? = null
    var btnBack: ImageButton? = null

    var idMenu: String? = null
    var nameMenu: String? = null
    var descriptionMenu: String? = null
    var thumbnailMenu: String? = null
    var priceMenu: Int? = null

    var sharedpreference: SharedPreferences? = null
    var Server: String? = null
    var isFood: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
             val w = window // in Activity's onCreate() for instance
             w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
         }*/

        this@AddMenuActivity.getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_PAN)

        sharedpreference = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        Server = resources.getString(R.string.service)

        isFood = intent.getBooleanExtra("isFood", false)
        idMenu = ""
        nameMenu = ""
        descriptionMenu = ""
        thumbnailMenu = "gonjoy_sushitasty_sushishrimp.jpg"
        priceMenu = 0

        initView()
        initAction()
    }

    fun initView() {
        thumnail = findViewById(R.id.thumnail)
        txtName = findViewById(R.id.name)
        txtDescreption = findViewById(R.id.descreption)
        txtPrice = findViewById(R.id.price)

        errorName = findViewById(R.id.error_name)
        erroDescreption = findViewById(R.id.error_descreption)
        errorPrice = findViewById(R.id.error_price)

        btnSave = findViewById(R.id.save)
        btnBack = findViewById(R.id.back)

        txtName!!.setText(nameMenu)
        txtDescreption!!.setText(descriptionMenu)
        txtPrice!!.setText(priceMenu.toString())


        // load ảnh ở đây
    }

    fun initAction() {
        btnBack!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        thumnail!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                chooseImage()
            }
        })

        btnSave!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (txtName!!.text.toString() == "" || txtName!!.text.toString() == " ") {
                    errorName!!.visibility = View.VISIBLE
                } else if (txtDescreption!!.text.toString() == "" || txtDescreption!!.text.toString() == " ") {
                    erroDescreption!!.visibility = View.VISIBLE
                } else if (txtPrice!!.text.toString() == "" || txtPrice!!.text.toString() == " ") {
                    errorPrice!!.visibility = View.VISIBLE
                } else {
                    nameMenu = txtName!!.text.toString()
                    descriptionMenu = txtDescreption!!.text.toString()
                    priceMenu = Integer.parseInt(txtPrice!!.text.toString())
                    showDiaglog()
                }

            }
        })
    }


    fun chooseImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this@AddMenuActivity)
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 1)
            } else if (options[item] == "Choose from Gallery") {
                try {
                    val intent =
                        Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    intent.setType("image/image")
                    startActivityForResult(intent, 2)
                } catch (e: java.lang.Exception) {
                    Toast.makeText(applicationContext, "Please choose a picture", Toast.LENGTH_SHORT).show()
                }
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                var bundel: Bundle = data!!.extras
                var btm: Bitmap = bundel.get("data") as Bitmap
                //uploadImageToServer()
                //upload()
                //settingImage()
            } else if (requestCode == 2) {

                var selection: Uri = data!!.data
                var bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selection)
                if (bitmap != null) {
                    thumnail!!.setImageURI(selection)
                    var pathFile = UploadFile.getPathFromURI(selection, applicationContext)
                    Log.d("Ketqua", pathFile)
                    var file = File(pathFile)
                    //thumbnailMenu = file.name
                    uploadImage(file.name)
                } else {
                    Toast.makeText(applicationContext, "Please choose a picture", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun showDiaglog() {
        var alert = AlertDialog.Builder(this@AddMenuActivity)
            .setTitle("Add Food and Drink")
            .setMessage(
                "Do you want to save this product"
            )
            .setIcon(
                resources.getDrawable(
                    android.R.drawable.ic_dialog_alert
                )
            )
            .setPositiveButton(
                "Ok"
            ) { dialog, which ->
                //Do Something Here
                saveMenu()
            }
            .setNegativeButton(
                "Cancel",
                object : DialogInterface.OnClickListener {

                    override fun onClick(
                        dialog: DialogInterface,
                        which: Int
                    ) {
                    }
                }).show()
    }

    fun uploadImage(name: String) {
        var retrofit: Retrofit = ApiClient.getRetrofitClient(this@AddMenuActivity);
        var apiService: APIService = retrofit.create(APIService::class.java)
        apiService.uploadFileToServer("application/x-www-form-urlencoded", name, "menu")
            .enqueue(object : Callback<ResponseFile> {
                override fun onResponse(call: Call<ResponseFile>, response: retrofit2.Response<ResponseFile>) {
                    //Log.d("ket_qua", response.body()!!.success.toString() + "===" + response.body()!!.data)
                    if (response != null) {
                        // updateImageInLocal(response.body()!!.data)
                        thumbnailMenu = response.body()!!.data
                    }
                }

                override fun onFailure(
                    call: Call<ResponseFile>,
                    t: Throwable
                ) {

                }
            });
    }

    fun saveMenu() {
        var token = sharedpreference!!.getString("token", null)
        var url: String = Server + "/provider/menu/add"
        var requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
        var stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { s ->
                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()
                ThucDon_Fragment.isChange = true
                finish()

            },
            Response.ErrorListener { e ->
                Log.d("Accept_Book", e.toString())
            }) {
            override fun getBody(): ByteArray {
                var json: String = " {\n" +
                        "       \n" +
                        "        \"name\": \"" + nameMenu + "\",\n" +
                        "        \"description\": \"" + descriptionMenu + "\",\n" +
                        "        \"thumbnail\": \"" + thumbnailMenu + "\",\n" +
                        "        \"price\":" + priceMenu + ",\n" +
                        "        \"isFood\":" + isFood + "\n" +
                        "    }"
                return json.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                var headers: HashMap<String, String> = HashMap<String, String>()

                headers.put("Content-Type", "application/json")
                headers.put("Authorization", token)
                return headers
            }
        }
        requestQueue.add(stringRequest)
    }
}
