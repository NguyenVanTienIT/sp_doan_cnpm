package universityoftechnology.polytechnic.com.service_provider.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
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
    }
}