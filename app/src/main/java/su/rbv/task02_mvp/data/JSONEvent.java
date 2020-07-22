package su.rbv.task02_mvp.data;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class JSONEvent implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("startDate")
    public long startDate;

    @SerializedName("endDate")
    public long endDate;

    @SerializedName("organisation")
    public String organisation;

    @SerializedName("address")
    public String address;

    @SerializedName("phone")
    public String phone;

    @SerializedName("description")
    public String description;

    @SerializedName("category")
    public String category;

}
