package universityoftechnology.polytechnic.com.service_provider.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import universityoftechnology.polytechnic.com.service_provider.R
import com.google.firebase.internal.FirebaseAppHelper.getToken
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId


class TrangChu_Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(universityoftechnology.polytechnic.com.service_provider.R.layout.fragment_trangchu, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->

                if (!task.isSuccessful) {

                    return@OnCompleteListener
                }

                val token = task.result?.token
                Log.d("Device_Token", token)

            })

    }

}