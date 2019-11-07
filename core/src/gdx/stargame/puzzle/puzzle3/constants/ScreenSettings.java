package gdx.stargame.puzzle.puzzle3.constants;

public enum ScreenSettings {
    CURRENT_PICTURE(Source.PICTURE_1024X512),
    FRAGMENT_DIMENSIONS(128, 128);

    private Source picture;
    private int fragmentWidth;
    private int fragmentHeight;

    ScreenSettings(Source picture) {
        this.picture = picture;
    }

    ScreenSettings(int fragmentWidth, int fragmentHeight) {
        this.fragmentWidth = fragmentWidth;
        this.fragmentHeight = fragmentHeight;
    }


    public Source getPicture() {
        return picture;
    }

    public void setPicture(Source picture) {
        this.picture = picture;
    }

    public int getFragmentWidth() {
        return fragmentWidth;
    }

    public void setFragmentWidth(int fragmentWidth) {
        this.fragmentWidth = fragmentWidth;
    }

    public int getFragmentHeight() {
        return fragmentHeight;
    }

    public void setFragmentHeight(int fragmentHeight) {
        this.fragmentHeight = fragmentHeight;
    }
}
