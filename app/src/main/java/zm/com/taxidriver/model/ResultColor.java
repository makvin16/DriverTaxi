package zm.com.taxidriver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultColor {
    @SerializedName("colors")
    @Expose
    private ArrayList<Color> colors;

    public ArrayList<Color> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Color> colors) {
        this.colors = colors;
    }

    public class Color {
        @SerializedName("id")
        @Expose
        private int id;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("hex")
        @Expose
        private String hex;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHex() {
            return hex;
        }

        public void setHex(String hex) {
            this.hex = hex;
        }
    }
}
