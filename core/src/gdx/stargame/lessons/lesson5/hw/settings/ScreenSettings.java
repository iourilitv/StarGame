package gdx.stargame.lessons.lesson5.hw.settings;

public enum ScreenSettings {
    CURRENT_PICTURE(Source.PICTURE_1024X512),
//    CURRENT_BACKGROUND(Source.GALAXY02_1800X2880),
    CURRENT_BACKGROUND(Source.GALAXY03_2610X3960),
    FRAGMENT_DIMENSIONS(128, 128),
    SCREEN_PROPORTION(1f);

    //объявим переменную для хранения пропорции экрана для подгонки спрайтов под разные экраны
    private float screenProportion;

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

    ScreenSettings(float screenProportion) {
        this.screenProportion = screenProportion;
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

    public float getScreenProportion() {
        return screenProportion;
    }

    public void setScreenProportion(float screenProportion) {
        this.screenProportion = screenProportion;
    }
}
