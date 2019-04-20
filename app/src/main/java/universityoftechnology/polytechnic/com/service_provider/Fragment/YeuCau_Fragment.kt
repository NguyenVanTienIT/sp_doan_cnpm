package universityoftechnology.polytechnic.com.service_provider.Fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import universityoftechnology.polytechnic.com.service_provider.Activity.HomeActivity
import universityoftechnology.polytechnic.com.service_provider.R

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

    var iconShip : ImageView? = null
    var iconDatBan : ImageView? = null
    var txtShip : TextView? = null
    var txtDatban : TextView? = null

    var recyclerView : RecyclerView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_yeucau, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        recyclerView = view.findViewById(R.id.list_yeu_cau)


        initAction()
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

            }
        })

        linearGiaoHang!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                iconDatBan!!.setImageDrawable(resources.getDrawable(R.drawable.ic_service))
                iconShip!!.setImageDrawable(resources.getDrawable(R.drawable.ic_ship_selected))
                txtShip!!.setTextColor(Color.parseColor("#006837"))
                txtDatban!!.setTextColor(Color.parseColor("#747474"))
            }
        })
    }


}