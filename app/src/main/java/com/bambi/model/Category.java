package com.bambi.model;

/**
 * Created by Miriana on 8/2/2017.
 */

public class Category {
    /*
    * "id": 1,
      "name": "Dinning Room",
      "photo": "http://bambimobilya.net/public/uploads/categories/cate_2017_08_01_13_29_18.png",
      "created_at": "2017-07-18 13:15:18",
      "updated_at": "2017-08-01 13:29:18"
    * */
    private String id;
    private String name;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
