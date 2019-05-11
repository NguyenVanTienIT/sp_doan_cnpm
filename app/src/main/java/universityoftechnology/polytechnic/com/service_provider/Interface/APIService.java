package universityoftechnology.polytechnic.com.service_provider.Interface;

import com.koushikdutta.async.http.body.MultipartFormDataBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;
import universityoftechnology.polytechnic.com.service_provider.Upload.RequestAndRespone.RequestFile;
import universityoftechnology.polytechnic.com.service_provider.Upload.RequestAndRespone.ResponseFile;
import universityoftechnology.polytechnic.com.service_provider.Upload.RequestAndRespone.ResquestStringFile;

public interface APIService {

    @POST("provider/image/upload")
    Call<ResponseFile> uploadImage(@Header("Content-Type") String header, @Body ResquestStringFile resquestStringFile, @Query("type") String type);


    @POST("provider/image/upload")
    Call<ResponseFile> uploadFile(@Header("Content-Type") String header, @Body MultipartBody.Part image, @Query("type") String type);

    @POST("provider/image/upload")
    Call<ResponseFile> uploadFile(@Header("Content-Type") String header, @Body MultipartFormDataBody image, @Query("type") String type);

    @POST("provider/image/upload")
    Call<ResponseFile> uploadFile(@Header("Content-Type") String header, @Body RequestFile image, @Query("type") String type);

    @POST("provider/image/upload")
    Call<ResponseFile> uploadFileToServer(@Header("Content-Type") String header, @Query("fileName") String fileName, @Query("type") String type);


}
