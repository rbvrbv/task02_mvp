package su.rbv.task02_mvp.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class JSONCategory implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

}
