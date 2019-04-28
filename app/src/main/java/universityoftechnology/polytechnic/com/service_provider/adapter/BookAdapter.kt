package universityoftechnology.polytechnic.com.service_provider.Activity.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import universityoftechnology.polytechnic.com.service_provider.Activity.InfomationBookActivity
import universityoftechnology.polytechnic.com.service_provider.Fragment.YeuCau_Fragment
import universityoftechnology.polytechnic.com.service_provider.R
import universityoftechnology.polytechnic.com.service_provider.model.Book
import universityoftechnology.polytechnic.com.service_provider.model.Ship

class BookAdapter (con : Context, list : ArrayList<Book>, frag : YeuCau_Fragment) : RecyclerView.Adapter<BookAdapter.BookViewHoder>() {
    var context : Context? = null
    var listBook : ArrayList<Book>? = null
    var fragment : YeuCau_Fragment? = null

    init {
        context = con
        listBook = list
        fragment = frag
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BookViewHoder {


        var view1 : View = LayoutInflater.from(context).inflate(R.layout.item_dat_ban, p0,false)
        var view2 : View = LayoutInflater.from(context).inflate(R.layout.item_load_more, p0,false)
        if (p1 == 1)
        return BookViewHoder(view1)
        else return BookViewHoder(view2)
    }



    override fun getItemCount(): Int {
        return listBook!!.size+1
    }

    override fun onBindViewHolder(p0: BookViewHoder, p1: Int) {
        if (p1 <  listBook!!.size) {
            var book: Book = listBook!!.get(p1)
            p0.bind(book)
            p0.itemMain!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    var intent: Intent = Intent(context, InfomationBookActivity::class.java)
                    intent.putExtra("name", book.customer.get("name"))
                    intent.putExtra("number", book.numberOfCustomer)
                    intent.putExtra("time", book.dateTime)
                    intent.putExtra("status", book.status)
                    intent.putExtra("jsonBook", book.jsonBook)
                    context!!.startActivity(intent)
                }
            })
        }else{
            p0.btnLoadView!!.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    fragment!!.getRequestBook(fragment!!.curentBook, fragment!!.statusTab)
                    fragment!!.curentBook ++
                }
            })
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (listBook!!.size == position) return 2
        else return 1
    }


    inner class BookViewHoder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var txtTen_Khach_Hang : TextView? = null
        var txtThoi_Gian : TextView? = null

        var labelComplete : LinearLayout? = null
        var labelNoneComplete : LinearLayout? = null
        var itemMain : RelativeLayout? = null
        var btnLoadView : Button? = null

        init {
            txtTen_Khach_Hang = itemView.findViewById(R.id.ten_khach_hang)
            txtThoi_Gian = itemView.findViewById(R.id.ngay_dat_hang)

            labelComplete = itemView.findViewById(R.id.complete)
            labelNoneComplete = itemView.findViewById(R.id.none_complete)
            itemMain = itemView.findViewById(R.id.request_item)
            btnLoadView = itemView.findViewById(R.id.load_view)
        }

        fun bind(book : Book){
            if (book._id != null) {
                txtTen_Khach_Hang!!.setText(book.customer!!.get("name"))
                txtThoi_Gian!!.setText(book.dateTime)
                if (book.status == 2) {
                    labelComplete!!.visibility = View.VISIBLE
                    labelNoneComplete!!.visibility = View.GONE
                } else {
                    labelComplete!!.visibility = View.GONE
                    labelNoneComplete!!.visibility = View.VISIBLE
                }
            }else{
                itemMain!!.visibility = View.GONE
                btnLoadView!!.visibility = View.VISIBLE
            }
        }
    }
}