package universityoftechnology.polytechnic.com.service_provider.Upload.RequestAndRespone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFile {
    @Expose
    Boolean success;
    @Expose
    String data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
