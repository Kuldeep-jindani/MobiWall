package mobiwall.entwickler.pro.com.mobiwall.paginator_categoryimage;

import java.io.Serializable;

/**
 * Created by Payal on 4/9/2018.
 */

public class
Grid_model implements Serializable {

    public int id;
    private String ismyfavourite;
    private String img_url;
    private String category_id;
    private String favourite_no;
    private String type;

    public void setId(int id) {
        this.id = id;
    }

    public void setimg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setcategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setfavourite_no(String favourite_no) {
        this.favourite_no = favourite_no;
    }

    public void settype(String type) {
        this.type = type;
    }

    public void setismyfavourite(String ismyfavourite) {
        this.ismyfavourite = ismyfavourite;
    }

    public int getId() {
        return id;
    }

    public String getIsmyfavourite() {
        return ismyfavourite;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getFavourite_no() {
        return favourite_no;
    }

    public String getType() {
        return type;
    }
}
