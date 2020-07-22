package su.rbv.task02_mvp.ui.main.help;

public class HelpListItem {
    private int imageId;
    private String name;

    HelpListItem(int imageId, String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

}
