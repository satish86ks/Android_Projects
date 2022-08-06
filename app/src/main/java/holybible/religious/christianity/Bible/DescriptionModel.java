package holybible.religious.christianity.Bible;

public class DescriptionModel {

    int id, bookmark;
    String chaptertitle, chapterdesc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }

    public String getChaptertitle() {
        return chaptertitle;
    }

    public void setChaptertitle(String chaptertitle) {
        this.chaptertitle = chaptertitle;
    }

    public String getChapterdesc() {
        return chapterdesc;
    }

    public void setChapterdesc(String chapterdesc) {
        this.chapterdesc = chapterdesc;
    }
}
