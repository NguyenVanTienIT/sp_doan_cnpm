package universityoftechnology.polytechnic.com.service_provider.Activity.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import universityoftechnology.polytechnic.com.service_provider.Activity.InformationShipActivity
import universityoftechnology.polytechnic.com.service_provider.Fragment.YeuCau_Fragment
import universityoftechnology.polytechnic.com.service_provider.Interface.LoadMore
import universityoftechnology.polytechnic.com.service_provider.R
import universityoftechnology.polytechnic.com.service_provider.model.Ship

class ShipAdapter(con: Context, list: ArrayList<Ship>, fra: YeuCau_Fragment) :
    RecyclerView.Adapter<ShipAdapter.ShipViewHoder>() {
    var context: Context? = null
    var listShip: ArrayList<Ship>? = null
    var fragment: YeuCau_Fragment? = null

    init {
        context = con
        listShip = list
        fragment = fra
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ShipViewHoder {
        var view1: View = LayoutInflater.from(context).inflate(R.layout.item_yeu_cau, p0, false)
        var view2: View = LayoutInflater.from(context).inflate(R.layout.item_load_more, p0, false)
        var view3: View = LayoutInflater.from(context).inflate(R.layout.item_no_load_more, p0, false)
        if (p1 == 1)
            return ShipViewHoder(view1)
        else {
            return ShipViewHoder(view2)
        }
    }

    override fun getItemCount(): Int {
        return listShip!!.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (listShip!!.size == position) return 2
        else return 1
    }

    override fun onBindViewHolder(p0: ShipViewHoder, p1: Int) {
        if (p1 < listShip!!.size) {
            var ship: Ship = listShip!!.get(p1)
            p0.bind(ship)
            p0.itemMain!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    var intent: Intent = Intent(context, InformationShipActivity::class.java)
                    intent.putExtra("status", ship.status)
                    intent.putExtra("address", ship.address)
                    intent.putExtra("createAt", ship.createAt)
                    intent.putExtra("telephone", ship.telephone)
                    intent.putExtra("name", ship.customer!!.name)
                    intent.putExtra("listproduct", ship.listDish.toString())
                    intent.putExtra("jsonShip", ship.jsonShip)
                    context!!.startActivity(intent)
                }
            })
        }
        p0.btnLoadView!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                fragment!!.getRequestShip(fragment!!.curentShip, fragment!!.statusTab)
                fragment!!.curentShip++
            }
        })
    }


    inner class ShipViewHoder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTen_Khach_Hang: TextView? = null
        var txtDia_Chi: TextView? = null
        var txtThoi_Gian: TextView? = null
        var itemMain: RelativeLayout? = null

        var labelComplete: LinearLayout? = null
        var labelNoneComplete: LinearLayout? = null
        var btnLoadView: Button? = null

        init {
            txtTen_Khach_Hang = itemView.findViewById(R.id.ten_khach_hang)
            txtDia_Chi = itemView.findViewById(R.id.dia_chi_khach_hang)
            txtThoi_Gian = itemView.findViewById(R.id.ngay_dat_hang)

            labelComplete = itemView.findViewById(R.id.complete)
            labelNoneComplete = itemView.findViewById(R.id.none_complete)
            btnLoadView = itemView.findViewById(R.id.load_view)
            itemMain = itemView.findViewById(R.id.request_item)
        }

        fun bind(ship: Ship) {
            txtTen_Khach_Hang!!.setText(ship.customer!!.name)
            txtDia_Chi!!.setText(ship.address)
            txtThoi_Gian!!.setText(ship.createAt)
            if (ship.status == 2) {
                labelComplete!!.visibility = View.VISIBLE
                labelNoneComplete!!.visibility = View.GONE
            } else {
                labelComplete!!.visibility = View.GONE
                labelNoneComplete!!.visibility = View.VISIBLE
            }
        }

    }
}