package ddwu.mobile.finalproject.ma02_20201003;

import android.text.Html;
import android.text.Spanned;

public class NaverBlogDto {

    private String title;
    private String description;
    private String bloggerlink;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBloggerlink() {
        return bloggerlink;
    }

    public void setBloggerlink(String bloggerlink) {
        this.bloggerlink = bloggerlink;
    }

    @Override
    public String toString() {
        return
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", bloggerlink='" + bloggerlink + '\'' +
                '}';
    }
}
