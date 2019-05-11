package universityoftechnology.polytechnic.com.service_provider.UploadImage

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.content.CursorLoader
import android.util.Log
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import android.widget.Toast




class UploadImage(con : Context) {
    var serverResponseCode = 0
    var progress : ProgressDialog? = null
    var context : Context? = null

    init {
        context = con
        progress = ProgressDialog(con)
        progress!!.setMessage("Uploading...")
        progress!!.setCancelable(false)
    }
    fun upload(sourceFileUri : String){
        val fileName = sourceFileUri

        var conn : HttpURLConnection? = null
        var dos : DataOutputStream? = null
        var lineEnd = "\r\n"
        var twoHyphens = "--"
        var boundary = "*****"
        var bytesRead: Int
        var bytesAvailable: Int
        var bufferSize: Int
        var buffer: ByteArray
        var maxBufferSize = 1 * 1024 * 1024
        var sourceFile = File(sourceFileUri)
        Log.d("UPLOAD_IMAGE", "upload image" +sourceFileUri)
        if (!sourceFile.isFile){
            Toast.makeText(context, "Please choose a file", Toast.LENGTH_SHORT).show()
        }else{
            progress!!.show()
            try{
                val fileInputStream = FileInputStream(sourceFile)
                val url = URL("https://do-an-cnpm.herokuapp.com/provider/image/upload?type=bg")
                conn = url.openConnection() as HttpURLConnection
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                dos = DataOutputStream(conn.getOutputStream())
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = ByteArray(bufferSize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize)
                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }


                serverResponseCode = conn.responseCode
                val serverResponseMessage = conn.responseMessage
                if (serverResponseCode === 200) {


                }

                fileInputStream.close();
                dos.flush();
                dos.close();


            }catch (e : Exception){
                Log.d("UPLOAD_IMAGE", "Lỗi upload image" +e.toString())
            }
            progress!!.dismiss()
        }


    }

    fun getPathFromURI(contentUri: Uri, context: Context): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(context, contentUri, proj, null, null, null)
        val cursor = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }
}