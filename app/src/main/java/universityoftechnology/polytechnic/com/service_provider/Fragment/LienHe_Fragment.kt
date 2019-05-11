package universityoftechnology.polytechnic.com.service_provider.Fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lienhe.*
import universityoftechnology.polytechnic.com.service_provider.Activity.HomeActivity
import universityoftechnology.polytechnic.com.service_provider.R


class LienHe_Fragment : Fragment() {
    var btnGmail: FloatingActionButton? = null
    var btnCall: FloatingActionButton? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(
            universityoftechnology.polytechnic.com.service_provider.R.layout.fragment_lienhe,
            container,
            false
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCall = view.findViewById(R.id.btn_call)
        btnGmail = view.findViewById(R.id.btn_gmail)

        btnGmail!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                sendEmail()
            }
        })

        btnCall!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        activity!!,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        1
                    )
                } else {
                    callHelp()
                }
            }
        })

        menu.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var acti: HomeActivity = activity as HomeActivity
                acti!!.mDrawable!!.openDrawer(Gravity.LEFT)
            }
        })

    }

    fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO) // it's not ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Report Service Provide")
        intent.data = Uri.parse("mailto:" + "tiennguyenvan1408@gmail.com") // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        context!!.startActivity(intent)
    }

    fun callHelp() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0968931478"))
        startActivity(intent)
    }

}