package universityoftechnology.polytechnic.com.service_provider.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import universityoftechnology.polytechnic.com.service_provider.Activity.InformationMenuActivity
import universityoftechnology.polytechnic.com.service_provider.R
import universityoftechnology.polytechnic.com.service_provider.model.Menu

class MenuAdapter(con: Context, list: ArrayList<Menu>) : RecyclerView.Adapter<MenuAdapter.MenuViewHoder>() {
    var listMenu: ArrayList<Menu>? = null
    var context: Context? = null

    init {
        context = con
        listMenu = list
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MenuViewHoder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.item_doan_douong, p0, false)
        return MenuViewHoder(view)
    }

    override fun getItemCount(): Int {
        return listMenu!!.size
    }

    override fun onBindViewHolder(p0: MenuViewHoder, p1: Int) {
        var menuFood: Menu = listMenu!!.get(p1)
        p0.bind(menuFood)

        p0.itemMain!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var intent: Intent = Intent(context, InformationMenuActivity::class.java)
                intent.putExtra("Name", menuFood.name)
                intent.putExtra("Descreption", menuFood.description)
                intent.putExtra("Price", menuFood.price)
                intent.putExtra("Thumbnail", menuFood.thumbnail)
                intent.putExtra("id", menuFood._id)
                context!!.startActivity(intent)
            }
        })
    }


    inner class MenuViewHoder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatar: ImageView? = null
        var name: TextView? = null
        var price: TextView? = null
        var itemMain: LinearLayout? = null

        init {
            avatar = itemView.findViewById(R.id.iamge_do_an_do_uong)
            name = itemView.findViewById(R.id.ten_do_an_do_uong)
            price = itemView.findViewById(R.id.gia_san_pham)
            itemMain = itemView.findViewById(R.id.item_main)
        }


        fun bind(menu: Menu) {
            name!!.setText(menu.name.toString())
            price!!.setText(menu.price.toString() + " đ")
            Picasso.get().load(context!!.resources.getString(R.string.server_image) + "/menu/" + menu.thumbnail)
                .into(avatar)
        }

    }
}