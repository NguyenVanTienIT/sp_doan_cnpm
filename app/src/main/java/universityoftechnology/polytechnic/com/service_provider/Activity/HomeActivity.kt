package universityoftechnology.polytechnic.com.service_provider.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import universityoftechnology.polytechnic.com.service_provider.R
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import org.json.JSONObject
import universityoftechnology.polytechnic.com.service_provider.Fragment.ThucDon_Fragment
import universityoftechnology.polytechnic.com.service_provider.Fragment.TrangChu_Fragment
import universityoftechnology.polytechnic.com.service_provider.Fragment.YeuCau_Fragment
import universityoftechnology.polytechnic.com.service_provider.model.InformationUser


class HomeActivity : AppCompatActivity() {

    var optionMenu : NavigationView? = null
    var mDrawable : DrawerLayout? = null
    var sharedpreference : SharedPreferences? = null
    var information : RelativeLayout? = null
    var txtTenQuan : TextView? = null
    var txtDiaChiQuan : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(universityoftechnology.polytechnic.com.service_provider.R.layout.activity_home)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        sharedpreference = PreferenceManager.getDefaultSharedPreferences(this);

        initView()
        initEvent()

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->

                if (!task.isSuccessful) {
                    Log.d("Device_Token", "Lỗi rồi"+ task.toString())
                    return@OnCompleteListener
                }
                else {

                    var token : String? = task.result!!.token
                    Log.d("Device_Token", token)
                    if (token != null)
                        sendDeviceToken(token.toString())

                }
            })
    }

    override fun onResume() {
        super.onResume()
        getInformation()
    }

    override fun onBackPressed() {
        return
    }

    fun sendDeviceToken(deviceToken : String){
        var token = sharedpreference!!.getString("token", null)
        if (token == null) return
        var url: String = resources.getString(universityoftechnology.polytechnic.com.service_provider.R.string.service) + "/provider/deviceToken/add"
        Log.d("Device_Token", url+" "+token)
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)
        Log.d("Device_Token", requestQueue.toString())
        var stringRequest : StringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener { s ->
            var jobj = JSONObject(s)
            Log.d("Device_Token", jobj.toString())
        }, Response.ErrorListener { e ->
            Log.d("Device_Token", e.toString())
        }){
            override fun getHeaders(): MutableMap<String, String> {
                var headers : HashMap<String, String> = HashMap<String, String>()

                headers.put("Content-Type", "application/json")
                //headers.put("Authorization", "\""+token+"\"")
                headers.put("Authorization", token)
                return headers
            }

            override fun getBody(): ByteArray {
                var device_token = "{\"deviceToken\":"+"\""+deviceToken+"\"}"
                //var device_token = "deviceToken:"+token
                return device_token.toByteArray()
            }


        }
        requestQueue.add(stringRequest)
    }

   fun initView(){
       mDrawable = findViewById(R.id.drawer_menu)
       optionMenu = findViewById(R.id.navigation_menu)
       var header = optionMenu!!.getHeaderView(0)
       information = header.findViewById(R.id.information_restaurant)
       txtTenQuan = header.findViewById(R.id.ten_quan)
       txtDiaChiQuan = header.findViewById(R.id.diachi_quan)
       optionMenu!!.bringToFront()
       resetMenu()

       supportFragmentManager.beginTransaction().add(R.id.layout_main, TrangChu_Fragment()).commit()
       optionMenu!!.setCheckedItem(R.id.trangchu)
       var spanString : SpannableString = SpannableString(optionMenu!!.checkedItem!!.title.toString())
       spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
       optionMenu!!.checkedItem!!.setTitle(spanString)

   }

    fun initEvent(){
        optionMenu!!.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {  // set event click item menu
                var id = p0.itemId
                if(id == R.id.trangchu){
                    resetMenu()
                    var spanString : SpannableString = SpannableString(p0.title.toString())
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
                    p0.setTitle(spanString)
                    mDrawable!!.closeDrawer(Gravity.LEFT)
                    supportFragmentManager.beginTransaction().replace(R.id.layout_main, TrangChu_Fragment()).commit()
                }
                else if (id == R.id.thucdon){
                    resetMenu()
                    var spanString : SpannableString = SpannableString(p0.title.toString())
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
                    p0.setTitle(spanString)
                    Log.d("Choose_Navigation", "Đã chọn")
                    mDrawable!!.closeDrawer(Gravity.LEFT)
                    supportFragmentManager.beginTransaction().replace(R.id.layout_main, ThucDon_Fragment()).commit()
                }
                else if (id == R.id.lienhe){
                    resetMenu()
                    var spanString : SpannableString = SpannableString(p0.title.toString())
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
                    p0.setTitle(spanString)
                    mDrawable!!.closeDrawer(Gravity.LEFT)
                }
                else if (id == R.id.doi_mat_khau){
                    resetMenu()
                    var spanString : SpannableString = SpannableString(p0.title.toString())
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
                    p0.setTitle(spanString)
                    mDrawable!!.closeDrawer(Gravity.LEFT)
                }
                else if(id == R.id.dangxuat){
                    resetMenu()
                    var spanString : SpannableString = SpannableString(p0.title.toString())
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
                    p0.setTitle(spanString)
                    mDrawable!!.closeDrawer(Gravity.LEFT)
                }
                else if (id == R.id.yeucau){
                    resetMenu()
                    var spanString : SpannableString = SpannableString(p0.title.toString())
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.item_slected)), 0, spanString.length, 0)
                    p0.setTitle(spanString)
                    mDrawable!!.closeDrawer(Gravity.LEFT)
                    supportFragmentManager.beginTransaction().replace(R.id.layout_main, YeuCau_Fragment()).commit()
                }
                return true
            }
        })

        information!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                mDrawable!!.closeDrawer(Gravity.LEFT)
                var intent : Intent = Intent(applicationContext, UpdataInformationActivity::class.java)
                startActivity(intent)
            }
        })
    }

    fun resetMenu(){
        for (i in 0..optionMenu!!.menu.size()-1) {
            var p0 = optionMenu!!.menu.getItem(i)
            var spanString : SpannableString = SpannableString(p0.getTitle().toString());
            spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.none_selected)), 0, spanString.length, 0)
            p0.setTitle(spanString)
        }
    }

    fun getInformation(){
        var gson : Gson = Gson()
        var json = sharedpreference!!.getString("Information_User", null)
        var informationUser : InformationUser = gson.fromJson(json, InformationUser::class.java)
        Log.d("INFORMATION", informationUser.username)
        txtTenQuan!!.text = informationUser.name
        txtDiaChiQuan!!.text = informationUser.address
    }
}
