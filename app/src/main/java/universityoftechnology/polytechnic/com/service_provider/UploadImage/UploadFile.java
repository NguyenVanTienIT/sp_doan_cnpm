package universityoftechnology.polytechnic.com.service_provider.UploadImage;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UploadFile {
    public void UploadImageToServer(File file, final Context context, String url){
        Ion.getDefault(context).configure().setLogging("MyLogs", Log.DEBUG);
        Future uploading = Ion.with(context)
                .load(url)
                //.setHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("", "multipart/form-data")
                //.setHeader("", "multipart/form-data")
                .setMultipartFile("file", file)
                //.setMultipartParameter("type", "menu")
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        if (e == null) {
                            Log.d("Ketqua", result.getResult().toString());
                        }else
                        Log.d("Ketqua", e.toString());
                    }
                });
    }

    public static String getPathFromURI(Uri contentUri, Context context) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void upload(){

    }
    public static byte[] readContentIntoByteArray(File file)
    {
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) file.length()];
        try
        {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return bFile;
    }

    public static byte[] convertFileToByteArray(File file){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //System.out.println(file.exists() + "!!");
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
            }
        } catch (IOException ex) {
            //Logger.getLogger(genJpeg.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] bytes = bos.toByteArray();
        return bytes;
    }
}
