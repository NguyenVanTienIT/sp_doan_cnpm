package universityoftechnology.polytechnic.com.service_provider.UploadImage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import java.io.ByteArrayOutputStream

class AppHelper {
    companion object {
        fun getFileDataFromDrawable(context : Context, id : Int) : ByteArray{
            var drawable : Drawable = ContextCompat.getDrawable(context, id)!!
            var bitmap : Bitmap = (drawable as BitmapDrawable).bitmap
            var byteArrayOutputStream : ByteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        }

        fun getFileDataFromDrawable(context : Context, drawable: Drawable) : ByteArray{
            var bitmap : Bitmap = (drawable as BitmapDrawable).bitmap
            var byteArrayOutputStream : ByteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        }

    }
}