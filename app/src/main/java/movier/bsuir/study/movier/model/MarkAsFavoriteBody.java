package movier.bsuir.study.movier.model;

import com.google.gson.annotations.SerializedName;

public class MarkAsFavoriteBody {

    @SerializedName("media_type")
    String media_type;

    @SerializedName("media_id")
    int media_id;

    @SerializedName("favorite")
    boolean isFavorite;

    public MarkAsFavoriteBody(String media_type, int media_id, boolean isFavorite) {
        this.media_type = media_type;
        this.media_id = media_id;
        this.isFavorite = isFavorite;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
