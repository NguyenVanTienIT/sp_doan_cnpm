package universityoftechnology.polytechnic.com.service_provider.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import universityoftechnology.polytechnic.com.service_provider.R
import universityoftechnology.polytechnic.com.service_provider.model.Product

class ProviderAdapter(con : Context, list : ArrayList<Product>) : RecyclerView.Adapter<ProviderAdapter.ProviderViewHoder>() {
    var context : Context? = null
    var listProduct : ArrayList<Product>? = null
    init {
        context = con
        listProduct = list
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProviderViewHoder {
        var view : View = LayoutInflater.from(context).inflate(R.layout.item_price_product, p0, false)
        return ProviderViewHoder(view)
    }

    override fun getItemCount(): Int {
        return listProduct!!.size
    }

    override fun onBindViewHolder(p0: ProviderViewHoder, p1: Int) {
        var product : Product = listProduct!!.get(p1)
        p0.bind(product)

    }


    inner class ProviderViewHoder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var nameProduct : TextView? = null
        var priceProduct : TextView? = null

        init {
            nameProduct = itemView.findViewById(R.id.name_product)
            priceProduct = itemView.findViewById(R.id.price_product)
        }

        fun bind(product : Product){
            nameProduct!!.setText(product.name+" x"+product.numberDishs)
            priceProduct!!.setText(product.price.toString())
        }

    }
}