package universityoftechnology.polytechnic.com.service_provider.Activity.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import universityoftechnology.polytechnic.com.service_provider.R
import universityoftechnology.polytechnic.com.service_provider.model.Book
import universityoftechnology.polytechnic.com.service_provider.model.Ship

class BookAdapter (con : Context, list : ArrayList<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHoder>() {
    var context : Context? = null
    var listBook : ArrayList<Book>? = null

    init {
        context = con
        listBook = list
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BookViewHoder {
        var view : View = LayoutInflater.from(context).inflate(R.layout.item_dat_ban, p0,false)
        return BookViewHoder(view)
    }

    override fun getItemCount(): Int {
        return listBook!!.size
    }

    override fun onBindViewHolder(p0: BookViewHoder, p1: Int) {

    }


    inner class BookViewHoder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var txtTen_Khach_Hang : TextView? = null
        var txtThoi_Gian : TextView? = null

        var labelComplete : LinearLayout? = null
        var labelNoneComplete : LinearLayout? = null
        var btnLoadView : Button? = null

        init {
            txtTen_Khach_Hang = itemView.findViewById(R.id.ten_khach_hang)
            txtThoi_Gian = itemView.findViewById(R.id.ngay_dat_hang)

            labelComplete = itemView.findViewById(R.id.complete)
            labelNoneComplete = itemView.findViewById(R.id.none_complete)
            btnLoadView = itemView.findViewById(R.id.load_view)
        }

        fun bind(book : Book){
            txtTen_Khach_Hang!!.setText(book.customer!!.get("name"))
            txtThoi_Gian!!.setText(book.dateTime)
            if (book.status == 2){
                labelComplete!!.visibility = View.VISIBLE
                labelNoneComplete!!.visibility = View.GONE
            }
            else{
                labelComplete!!.visibility = View.GONE
                labelNoneComplete!!.visibility = View.VISIBLE
            }
        }
    }
}