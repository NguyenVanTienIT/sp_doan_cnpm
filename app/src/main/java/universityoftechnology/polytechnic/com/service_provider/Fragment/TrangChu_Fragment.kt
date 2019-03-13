package universityoftechnology.polytechnic.com.service_provider.Fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import universityoftechnology.polytechnic.com.service_provider.R
import com.google.firebase.internal.FirebaseAppHelper.getToken
import android.util.Log
import android.view.Gravity
import android.widget.ImageButton
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import org.json.JSONObject
import universityoftechnology.polytechnic.com.service_provider.Activity.HomeActivity






class TrangChu_Fragment : Fragment() {
    var btnMenu : ImageButton? = null
    var sharedpreference : SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(universityoftechnology.polytechnic.com.service_provider.R.layout.fragment_trangchu, container, false)
        sharedpreference = PreferenceManager.getDefaultSharedPreferences(activity);
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        btnMenu = view.findViewById(universityoftechnology.polytechnic.com.service_provider.R.id.btn_menu)
        addAcion()

    }



    fun addAcion(){
        btnMenu!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var acti : HomeActivity = activity as HomeActivity
                acti!!.mDrawable!!.openDrawer(Gravity.LEFT)
            }
        })
    }

}