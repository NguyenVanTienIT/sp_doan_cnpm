package universityoftechnology.polytechnic.com.service_provider.UploadImage

import android.os.Parcel
import android.os.Parcelable
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream


class VolleyMultipartRequest(method: Int, url: String?, listener: Response.ErrorListener?, listener2 : Response.Listener<NetworkResponse>) : Request<NetworkResponse>(method, url, listener) {

    var mListener : Response.Listener<NetworkResponse>? = null
    var mErrorListener : Response.ErrorListener? = null

    init {
        mListener = listener2
        mErrorListener = listener
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<NetworkResponse>? {
        try {
            return Response.success<NetworkResponse>(
                response,
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: Exception) {
            return Response.error<NetworkResponse>(ParseError(e))
        }

    }

    override fun getHeaders(): MutableMap<String, String> {
        return super.

            getHeaders()
    }

   /* override fun getBody(): ByteArray {
        var bos = ByteArrayOutputStream()
        var dos = DataOutputStream(bos)

    }*/

    override fun deliverResponse(response: NetworkResponse?) {
        mListener!!.onResponse(response)
    }

    override fun deliverError(error: VolleyError?) {
        mErrorListener!!.onErrorResponse(error)
    }



}