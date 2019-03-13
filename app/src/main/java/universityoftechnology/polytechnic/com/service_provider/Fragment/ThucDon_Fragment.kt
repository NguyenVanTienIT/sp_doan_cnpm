package universityoftechnology.polytechnic.com.service_provider.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.fragment_thucdon.view.*
import universityoftechnology.polytechnic.com.service_provider.Activity.HomeActivity
import universityoftechnology.polytechnic.com.service_provider.R

class ThucDon_Fragment : Fragment() {
    var btnMenu : ImageButton? = null
    var btnDoan : RelativeLayout? = null
    var btnDouong : RelativeLayout? = null

    var txtDoan : TextView? = null
    var txtDoUong : TextView? = null
    var lineDoan : View? = null
    var lineDouong : View? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(universityoftechnology.polytechnic.com.service_provider.R.layout.fragment_thucdon, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnMenu = view.findViewById(R.id.menu)
        btnDoan = view.findViewById(R.id.doan)
        btnDouong = view.findViewById(R.id.douong)

        txtDoan = view.findViewById(R.id.text_doan)
        txtDoUong = view.findViewById(R.id.text_douong)
        lineDoan =view.findViewById(R.id.line_doan)
        lineDouong = view.findViewById(R.id.line_douong)

        addAction()

    }

    fun addAction(){
        btnMenu!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var acti : HomeActivity = activity as HomeActivity
                acti!!.mDrawable!!.openDrawer(Gravity.LEFT)
            }
        })

        btnDoan!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                txtDoan!!.setTextColor(resources.getColor(R.color.btn_selected))
                lineDoan!!.setBackgroundColor(resources.getColor(R.color.btn_selected))

                txtDoUong!!.setTextColor(resources.getColor(R.color.btn_none_selected))
                lineDouong!!.setBackgroundColor(resources.getColor(R.color.btn_none_selected))



            }
        })

        btnDouong!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                txtDoUong!!.setTextColor(resources.getColor(R.color.btn_selected))
                lineDouong!!.setBackgroundColor(resources.getColor(R.color.btn_selected))

                txtDoan!!.setTextColor(resources.getColor(R.color.btn_none_selected))
                lineDoan!!.setBackgroundColor(resources.getColor(R.color.btn_none_selected))

            }
        })
    }
}