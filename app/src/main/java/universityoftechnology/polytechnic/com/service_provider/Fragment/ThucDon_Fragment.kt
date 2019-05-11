package universityoftechnology.polytechnic.com.service_provider.Fragment

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.JsonObject
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_thucdon.view.*
import org.json.JSONArray
import org.json.JSONObject
import universityoftechnology.polytechnic.com.service_provider.Activity.AddMenuActivity
import universityoftechnology.polytechnic.com.service_provider.Activity.HomeActivity
import universityoftechnology.polytechnic.com.service_provider.R
import universityoftechnology.polytechnic.com.service_provider.adapter.MenuAdapter
import universityoftechnology.polytechnic.com.service_provider.model.Menu

class ThucDon_Fragment : Fragment() {
    var btnMenu: ImageButton? = null
    var btnDoan: RelativeLayout? = null
    var btnDouong: RelativeLayout? = null
    var btnAdd: FloatingActionButton? = null

    var txtDoan: TextView? = null
    var txtDoUong: TextView? = null
    var lineDoan: View? = null
    var lineDouong: View? = null

    var recyclerView: RecyclerView? = null
    var menuAdapter: MenuAdapter? = null
    var listMenu: ArrayList<Menu>? = null
    var progress: ProgressDialog? = null
    var isFood: Boolean = true

    var sharedpreference: SharedPreferences? = null
    var Server: String? = null

    companion object {
        var isChange: Boolean = false
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(
            universityoftechnology.polytechnic.com.service_provider.R.layout.fragment_thucdon,
            container,
            false
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedpreference = PreferenceManager.getDefaultSharedPreferences(activity)
        Server = resources.getString(R.string.service)
        listMenu = ArrayList()
        menuAdapter = MenuAdapter(context!!, listMenu!!)
        progress = ProgressDialog(context)
        progress!!.setCancelable(false)
        initView(view)
        addAction()

        getMenu(isFood)
    }

    override fun onResume() {
        super.onResume()
        if (isChange) {
            getMenu(isFood)
            isChange = false
        }
    }

    fun initView(view: View) {
        btnMenu = view.findViewById(R.id.menu)
        btnDoan = view.findViewById(R.id.doan)
        btnDouong = view.findViewById(R.id.douong)

        txtDoan = view.findViewById(R.id.text_doan)
        txtDoUong = view.findViewById(R.id.text_douong)
        lineDoan = view.findViewById(R.id.line_doan)
        lineDouong = view.findViewById(R.id.line_douong)
        recyclerView = view.findViewById(R.id.list_doan_douong)
        btnAdd = view.findViewById(R.id.btn_add)

        recyclerView!!.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        val itemDecoration = DividerItemDecoration(activity, LinearLayoutManager(activity).orientation)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.setLayoutManager(LinearLayoutManager(activity))
        recyclerView!!.addItemDecoration(itemDecoration)
        recyclerView!!.adapter = menuAdapter
    }

    fun addAction() {
        btnAdd!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //Toast.makeText(activity, "Xin chào", Toast.LENGTH_SHORT).show()
                var intent: Intent = Intent(activity, AddMenuActivity::class.java)
                intent.putExtra("isFood", isFood)
                startActivity(intent)
            }
        })

        btnMenu!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var acti: HomeActivity = activity as HomeActivity
                acti!!.mDrawable!!.openDrawer(Gravity.LEFT)
            }
        })

        btnDoan!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                txtDoan!!.setTextColor(resources.getColor(R.color.btn_selected))
                lineDoan!!.setBackgroundColor(resources.getColor(R.color.btn_selected))

                txtDoUong!!.setTextColor(resources.getColor(R.color.btn_none_selected))
                lineDouong!!.setBackgroundColor(resources.getColor(R.color.btn_none_selected))
                isFood = true
                getMenu(isFood)
            }
        })

        btnDouong!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                txtDoUong!!.setTextColor(resources.getColor(R.color.btn_selected))
                lineDouong!!.setBackgroundColor(resources.getColor(R.color.btn_selected))

                txtDoan!!.setTextColor(resources.getColor(R.color.btn_none_selected))
                lineDoan!!.setBackgroundColor(resources.getColor(R.color.btn_none_selected))
                isFood = false
                getMenu(isFood)
            }
        })
    }

    fun getMenu(status: Boolean) {
        progress!!.setMessage("Waiting...")
        progress!!.show()
        var token = sharedpreference!!.getString("token", null)
        var url: String = Server + "/provider/menu"
        var requestQueue: RequestQueue = Volley.newRequestQueue(activity)
        var stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener { s ->
                //var jobj = JSONObject(s)
                //var data: JSONArray? = jobj.getJSONArray("data")
                var data: JSONArray? = JSONArray(s)
                // Log.d("Request_Ship", data!![0].toString())
                if (data != null)
                    initDataMenu(data, status)

            },
            Response.ErrorListener { e ->
                Log.d("Request_Ship", e.toString())
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                var headers: HashMap<String, String> = HashMap<String, String>()
                headers.put("Authorization", token)
                return headers
            }
        }
        requestQueue.add(stringRequest)
    }

    fun initDataMenu(data: JSONArray, isFood: Boolean) {
        deleteAllList()
        for (i in 0..data.length() - 1) {
            var menuFood: Menu = Menu()
            var jsonMenu: JSONObject = JSONObject(data[i].toString())
            menuFood._id = jsonMenu.getString("_id")
            menuFood.name = jsonMenu.getString("name")
            menuFood.price = jsonMenu.getInt("price")
            menuFood.createdAt = jsonMenu.getString("createdAt")
            menuFood.description = jsonMenu.getString("description")
            menuFood.isActive = jsonMenu.getBoolean("isActive")
            menuFood.isDeleted = jsonMenu.getBoolean("isDeleted")
            menuFood.provider = jsonMenu.getString("provider")
            menuFood.thumbnail = jsonMenu.getString("thumbnail")
            menuFood.isFood = jsonMenu.getBoolean("isFood")
            menuFood.updatedAt = jsonMenu.getString("updatedAt")

            menuFood.jsonMenu = data[i].toString()

            if (isFood) {
                if (menuFood.isFood!!) {
                    listMenu!!.add(menuFood)
                }
            } else {
                if (!menuFood.isFood!!) {
                    listMenu!!.add(menuFood)
                }
            }
        }
        if (progress != null) progress!!.dismiss()
        menuAdapter!!.notifyDataSetChanged()
    }

    fun deleteAllList() {
        while (listMenu != null && listMenu!!.size > 0) {
            listMenu!!.removeAt(0)
        }
    }
}