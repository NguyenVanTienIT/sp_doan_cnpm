package universityoftechnology.polytechnic.com.service_provider.Fragment

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONStringer
import universityoftechnology.polytechnic.com.service_provider.Activity.HomeActivity
import universityoftechnology.polytechnic.com.service_provider.Activity.adapter.BookAdapter
import universityoftechnology.polytechnic.com.service_provider.Activity.adapter.ShipAdapter
import universityoftechnology.polytechnic.com.service_provider.R
import universityoftechnology.polytechnic.com.service_provider.model.Book
import universityoftechnology.polytechnic.com.service_provider.model.CustomerShip
import universityoftechnology.polytechnic.com.service_provider.model.SavedAddress
import universityoftechnology.polytechnic.com.service_provider.model.Ship

class YeuCau_Fragment : Fragment(){

    var btn_tat_ca : RelativeLayout? = null
    var btn_hoan_thanh : RelativeLayout? = null
    var btn_chua_hoan_thanh : RelativeLayout? = null
    var btnMenu : ImageButton? = null
    var txtTatCa : TextView? = null
    var txtHoanThanh : TextView? = null
    var txtChuaHoanThanh : TextView? = null
    var lineTatCa : View? = null
    var lineHoanThanh : View? = null
    var lineChuaHoanThanh : View? = null

    var linearGiaoHang : LinearLayout? = null
    var linearDatBan : LinearLayout? = null
    var progress : ProgressDialog? = null

    var iconShip : ImageView? = null
    var iconDatBan : ImageView? = null
    var txtShip : TextView? = null
    var txtDatban : TextView? = null

    var listRequestShip : ArrayList<Ship>? = null
    var listRequestBook : ArrayList<Book>? = null

    var recyclerShip: RecyclerView? = null
    var recyclerBook : RecyclerView? = null

    var shipAdapter : ShipAdapter? = null
    var bookAdapter : BookAdapter? = null

    var shipTab : Boolean = true
    var statusTab : String = "all"

    var sharedpreference : SharedPreferences? = null

    var Server : String? = null

    companion object {
        var acceptAndDecline : Boolean = false
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_yeucau, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listRequestBook = ArrayList()
        listRequestShip = ArrayList()
        shipAdapter = ShipAdapter(context!!, listRequestShip!!)
        bookAdapter = BookAdapter(context!!, listRequestBook!!)

        progress = ProgressDialog(activity!!)
        progress!!.setCancelable(false)

        sharedpreference = PreferenceManager.getDefaultSharedPreferences(activity)
        Server = resources.getString(R.string.service)
        initView(view)
        initAction()
        initDataSource()
    }

    override fun onResume() {
        super.onResume()
        if (acceptAndDecline){
            progress!!.setMessage("Waiting...")
            progress!!.show()
            getRequestShip(0, statusTab)
            getRequestBook(0, statusTab)
            acceptAndDecline = false
        }
    }

    fun initView(view : View){
        btn_tat_ca = view.findViewById(R.id.tat_ca)
        btn_hoan_thanh = view.findViewById(R.id.hoan_thanh)
        btn_chua_hoan_thanh = view.findViewById(R.id.chua_hoan_thanh)
        btnMenu = view.findViewById(R.id.menu)

        txtTatCa = view.findViewById(R.id.text_tatca)
        txtHoanThanh = view.findViewById(R.id.text_hoan_thanh)
        txtChuaHoanThanh = view.findViewById(R.id.text_chua_hoan_thanh)
        lineTatCa =view.findViewById(R.id.line_tat_ca)
        lineHoanThanh = view.findViewById(R.id.line_hoan_thanh)
        lineChuaHoanThanh = view.findViewById(R.id.line_chua_hoan_thanh)

        linearGiaoHang = view.findViewById(R.id.giao_hang)
        linearDatBan = view.findViewById(R.id.dat_ban)

        iconShip = view.findViewById(R.id.icon_giao_hang)
        iconDatBan = view.findViewById(R.id.icon_dat_ban)

        txtShip = view.findViewById(R.id.txt_giao_hang)
        txtDatban = view.findViewById(R.id.txt_dat_ban)

        recyclerShip = view.findViewById(R.id.list_yeu_cau)
        recyclerBook = view.findViewById(R.id.list_yeu_cau_book)

        recyclerShip!!.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        val itemDecoration = DividerItemDecoration(activity, LinearLayoutManager(activity).orientation)
        recyclerShip!!.setHasFixedSize(true)
        recyclerShip!!.setLayoutManager(LinearLayoutManager(activity))
        recyclerShip!!.addItemDecoration(itemDecoration)
        recyclerShip!!.adapter = shipAdapter

        recyclerBook!!.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        val itemDecoration2 = DividerItemDecoration(activity, LinearLayoutManager(activity).orientation)
        recyclerBook!!.setHasFixedSize(true)
        recyclerBook!!.setLayoutManager(LinearLayoutManager(activity))
        recyclerBook!!.addItemDecoration(itemDecoration)
        recyclerBook!!.adapter = bookAdapter
    }

    fun initAction(){
        btn_tat_ca!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                txtTatCa!!.setTextColor(resources.getColor(R.color.btn_selected))
                lineTatCa!!.setBackgroundColor(resources.getColor(R.color.btn_selected))

                txtHoanThanh!!.setTextColor(resources.getColor(R.color.btn_none_selected))
                lineHoanThanh!!.setBackgroundColor(resources.getColor(R.color.btn_none_selected))

                txtChuaHoanThanh!!.setTextColor(resources.getColor(R.color.btn_none_selected))
                lineChuaHoanThanh!!.setBackgroundColor(resources.getColor(R.color.btn_none_selected))

                statusTab = "all"
                progress!!.setMessage("Waiting...")
                progress!!.show()

                if (shipTab) {
                    getRequestShip(0, "all")
                }else{
                    getRequestBook(0, "all")
                }

            }
        })

        btn_hoan_thanh!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                txtHoanThanh!!.setTextColor(resources.getColor(R.color.btn_selected))
                lineHoanThanh!!.setBackgroundColor(resources.getColor(R.color.btn_selected))

                txtTatCa!!.setTextColor(resources.getColor(R.color.btn_none_selected))
                lineTatCa!!.setBackgroundColor(resources.getColor(R.color.btn_none_selected))

                txtChuaHoanThanh!!.setTextColor(resources.getColor(R.color.btn_none_selected))
                lineChuaHoanThanh!!.setBackgroundColor(resources.getColor(R.color.btn_none_selected))

                statusTab = "complete"
                progress!!.setMessage("Waiting...")
                progress!!.show()
                if (shipTab) {
                    getRequestShip(0, "complete")
                }else{
                    getRequestBook(0, "complete")
                }
            }
        })

        btn_chua_hoan_thanh!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                txtChuaHoanThanh!!.setTextColor(resources.getColor(R.color.btn_selected))
                lineChuaHoanThanh!!.setBackgroundColor(resources.getColor(R.color.btn_selected))

                txtTatCa!!.setTextColor(resources.getColor(R.color.btn_none_selected))
                lineTatCa!!.setBackgroundColor(resources.getColor(R.color.btn_none_selected))

                txtHoanThanh!!.setTextColor(resources.getColor(R.color.btn_none_selected))
                lineHoanThanh!!.setBackgroundColor(resources.getColor(R.color.btn_none_selected))

                statusTab = "uncomplete"
                progress!!.setMessage("Waiting...")
                progress!!.show()
                if (shipTab) {
                    getRequestShip(0, "uncomplete")
                }else{
                    getRequestBook(0, "uncomplete")
                }
            }
        })

        btnMenu!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var acti : HomeActivity = activity as HomeActivity
                acti!!.mDrawable!!.openDrawer(Gravity.LEFT)
            }
        })

        linearDatBan!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                iconDatBan!!.setImageDrawable(resources.getDrawable(R.drawable.ic_service_selected))
                iconShip!!.setImageDrawable(resources.getDrawable(R.drawable.ic_ship))
                txtDatban!!.setTextColor(Color.parseColor("#006837"))
                txtShip!!.setTextColor(Color.parseColor("#747474"))

                resetDefaultView()
                statusTab = "all"
                progress!!.setMessage("Waiting...")
                progress!!.show()
                getRequestBook(0, "all")
                recyclerBook!!.visibility = View.VISIBLE
                recyclerShip!!.visibility = View.GONE
                shipTab = false

            }
        })

        linearGiaoHang!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                iconDatBan!!.setImageDrawable(resources.getDrawable(R.drawable.ic_service))
                iconShip!!.setImageDrawable(resources.getDrawable(R.drawable.ic_ship_selected))
                txtShip!!.setTextColor(Color.parseColor("#006837"))
                txtDatban!!.setTextColor(Color.parseColor("#747474"))

                resetDefaultView()
                statusTab = "all"
                progress!!.setMessage("Waiting...")
                progress!!.show()
                getRequestShip(0, "all")
                recyclerBook!!.visibility = View.GONE
                recyclerShip!!.visibility = View.VISIBLE
                shipTab = true
            }
        })
    }

    fun resetDefaultView(){
        txtTatCa!!.setTextColor(resources.getColor(R.color.btn_selected))
        lineTatCa!!.setBackgroundColor(resources.getColor(R.color.btn_selected))

        txtHoanThanh!!.setTextColor(resources.getColor(R.color.btn_none_selected))
        lineHoanThanh!!.setBackgroundColor(resources.getColor(R.color.btn_none_selected))

        txtChuaHoanThanh!!.setTextColor(resources.getColor(R.color.btn_none_selected))
        lineChuaHoanThanh!!.setBackgroundColor(resources.getColor(R.color.btn_none_selected))
    }

    fun initDataSource(){
        progress!!.setMessage("Waiting...")
        progress!!.show()
        getRequestShip(0, "all")
    }

    fun getRequestShip(currentPage : Int, status : String){
        var token = sharedpreference!!.getString("token", null)
        var url: String = Server + "/provider/ships/getShips"
        var requestQueue: RequestQueue = Volley.newRequestQueue(activity)
        var stringRequest : StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { s ->

                    var jobj = JSONObject(s)
                    var data: JSONArray? = jobj.getJSONArray("data")
                   // Log.d("Request_Ship", data!![0].toString())
                    if (data != null)
                    initDataShip(data!!, status)

            },
            Response.ErrorListener { e ->
                Log.d("Request_Ship", e.toString())
            }){
            override fun getBody(): ByteArray {
                var body : String = "{\"currentPage\": \""+currentPage+"\",\n" +
                        "     \"itemPerPage\":\"20\",\n" +
                        "     \"searchText\":\"Active\"}"
                return body.toByteArray()
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

    fun getRequestBook(currentPage: Int, status: String){
        var token = sharedpreference!!.getString("token", null)
        var url: String = Server + "/provider/books/getBooks"
        var requestQueue: RequestQueue = Volley.newRequestQueue(activity)
        var stringRequest : StringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { s ->

                var jobj = JSONObject(s)
                var data: JSONArray? = jobj.getJSONArray("data")
                // Log.d("Request_Ship", data!![0].toString())
                if (data != null)
                    initDataBook(data!!, status)

            },
            Response.ErrorListener { e ->
                Log.d("Request_Book", e.toString())
            }){
            override fun getBody(): ByteArray {
                var body : String = "{\"currentPage\": \""+currentPage+"\",\n" +
                        "     \"itemPerPage\":\"20\",\n" +
                        "     \"searchText\":\"Active\"}"
                return body.toByteArray()
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

    fun initDataBook(data: JSONArray?, status: String){
        deleteAllBook()
        for (i in 0..data!!.length()-1!!){
            var jsonBook : JSONObject = JSONObject(data[i].toString())
            var book : Book = Book()

            book._id = jsonBook.getString("_id")
            book.conditions = jsonBook.getString("conditions")
            book.dateTime = jsonBook.getString("dateTime")
            book.numberOfCustomer = jsonBook.getString("numberOfCustomer")
            book.botName = jsonBook.getString("botName")
            book.__v = jsonBook.getInt("__v")
            book.status = jsonBook.getInt("status")
            book.updatedAt = jsonBook.getString("updatedAt")
            book.createdAt = jsonBook.getString("createdAt")
            book.isDeleted = jsonBook.getBoolean("isDeleted")
            book.isActive = jsonBook.getBoolean("isActive")
            book.isNotified = jsonBook.getBoolean("isNotified")

            var jsonProvider : JSONObject = JSONObject(jsonBook.getString("provider"))
            book.provider.put("_id", jsonProvider.getString("_id"))

            var jsonCustomer : JSONObject = JSONObject(jsonBook.getString("customer"))
            book.customer.put("_id", jsonCustomer.getString("_id"))
            book.customer.put("name", jsonCustomer.getString("name"))

            book.jsonBook = data[i].toString()

            if (status.equals("complete")){
                if (book.status == 2) listRequestBook!!.add(book)
            }else if (status.equals("uncomplete")){
                if (book.status != 2) listRequestBook!!.add(book)
            }else{
                listRequestBook!!.add(book)
            }
        }
        if (progress != null) progress!!.dismiss()
        bookAdapter!!.notifyDataSetChanged()
    }

    fun initDataShip(data : JSONArray?, status : String){
        deleteAllShip()

        for (i in 0..data!!.length()-1){
            var jsonShip : JSONObject = JSONObject(data[i].toString())

            var ship : Ship = Ship()
            ship._id = jsonShip.getString("_id")
            ship.oderNumber = jsonShip.getString("orderNumber")
            ship.createAt = jsonShip.getString("createdAt")
            ship.__v  = jsonShip.getInt("__v")
            ship.address = jsonShip.getString("address")
            ship.notes = jsonShip.getString("notes")
            ship.telephone = jsonShip.getString("telephone")
            ship.status = jsonShip.getInt("status")
            ship.isDeleted = jsonShip.getBoolean("isDeleted")
            ship.isNotified = jsonShip.getBoolean("isNotified")
            ship.priceTotal = jsonShip.getInt("priceTotal")

            var jsonProvider : JSONObject = JSONObject(jsonShip.getString("provider"))
            ship.provider = HashMap()
            ship.provider!!.put("_id", jsonProvider.getString("_id"))

            var jsonCustomer : JSONObject = JSONObject(jsonShip.getString("customer"))
            var customer : CustomerShip = CustomerShip()
            customer._id = jsonCustomer.getString("_id")
            customer.name = jsonCustomer.getString("name")
            var savedAddress : SavedAddress = SavedAddress()
            savedAddress.parseObject(JSONObject(jsonCustomer.getString("savedAddress")))
            customer.savedAddress = savedAddress
            ship.customer = customer

            ship.jsonShip = data[i].toString()

            ship.listDish = jsonShip.getJSONArray("listDish")
            if (status.equals("complete")){
                if (ship.status == 2) listRequestShip!!.add(ship)
            }else if (status.equals("uncomplete")){
                if (ship.status != 2) listRequestShip!!.add(ship)
            }else{
                listRequestShip!!.add(ship)
            }

            Log.d("Request_Ship",ship._id+"===="+ship.customer!!.name+"==="+ship.customer!!._id)
        }
        if (progress != null) progress!!.dismiss()
        shipAdapter!!.notifyDataSetChanged()
    }

    fun deleteAllShip(){
        while (listRequestShip != null && listRequestShip!!.size > 0){
            listRequestShip!!.removeAt(0)
        }
    }

    fun deleteAllBook(){
        while (listRequestBook != null && listRequestBook!!.size > 0){
            listRequestBook!!.removeAt(0)
        }
    }

}