package universityoftechnology.polytechnic.com.service_provider.Fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import universityoftechnology.polytechnic.com.service_provider.R
import com.google.firebase.internal.FirebaseAppHelper.getToken
import android.util.Log
import android.view.Gravity
import android.widget.ImageButton
import android.widget.LinearLayout
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.fragment_trangchu.*
import org.json.JSONArray
import org.json.JSONObject
import universityoftechnology.polytechnic.com.service_provider.Activity.HomeActivity


class TrangChu_Fragment : Fragment() {
    var btnMenu: ImageButton? = null
    var sharedpreference: SharedPreferences? = null
    var Server: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(
            universityoftechnology.polytechnic.com.service_provider.R.layout.fragment_trangchu,
            container,
            false
        )
        sharedpreference = PreferenceManager.getDefaultSharedPreferences(activity);
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        btnMenu = view.findViewById(universityoftechnology.polytechnic.com.service_provider.R.id.btn_menu)
        Server = resources.getString(R.string.service)

        addAcion()

    }

    fun addAcion() {
        btnMenu!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var acti: HomeActivity = activity as HomeActivity
                acti!!.mDrawable!!.openDrawer(Gravity.LEFT)
            }
        })
        thuc_don.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var acti: HomeActivity = activity as HomeActivity
                acti.transactionFragmentThucDon();
            }
        })

        giaohang_dathang.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var acti: HomeActivity = activity as HomeActivity
                acti.transactionFragmentYeuCau()
            }
        })
    }

    fun getRequestShip(currentPage: Int, status: String) {
        var token = sharedpreference!!.getString("token", null)
        var url: String = Server + "/provider/ships/getShips"
        var requestQueue: RequestQueue = Volley.newRequestQueue(activity)
        var stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { s ->

                var jobj = JSONObject(s)
                var data: JSONArray? = jobj.getJSONArray("data")
                // Log.d("Request_Ship", data!![0].toString())

            },
            Response.ErrorListener { e ->
                Log.d("Request_Ship", e.toString())
            }) {
            override fun getBody(): ByteArray {
                var body: String = "{\"currentPage\": \"" + currentPage + "\",\n" +
                        "     \"itemPerPage\":\"20\",\n" +
                        "     \"searchText\":\"Active\"}"
                return body.toByteArray()
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