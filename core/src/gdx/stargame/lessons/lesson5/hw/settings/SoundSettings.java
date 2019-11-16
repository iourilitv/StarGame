package gdx.stargame.lessons.lesson5.hw.settings;

public enum SoundSettings {
    //общий уровень громкости для всех эффектов
    SOUND_EFFECTS_LEVEL_MAIN(0.05f),
    //общий уровень громкости для всей музыки
    SOUND_MUSIC_LEVEL_MAIN(1f),
    ;

    private float power;

    SoundSettings(float power) {
        this.power = power;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }
}
