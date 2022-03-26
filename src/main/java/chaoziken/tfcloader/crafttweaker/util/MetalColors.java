package chaoziken.tfcloader.crafttweaker.util;

/**
 * An estimate of an "average" color (in HSB/HSV) of each material. Used to tint the fluid
 */
@SuppressWarnings("unused")
public enum MetalColors {

    bismuth_bronze(131, 43, 57),
    black_bronze(321, 36, 50),
    black_steel(180, 5, 21),
    blue_steel(213, 59, 67),
    bronze(33, 43, 57),
    copper(13, 72, 72),
    red_steel(0, 72, 70),
    steel(225, 5, 55),
    wrought_iron(0, 0, 63);

    public final int hue;
    public final int saturation;
    public final int brightness;

    MetalColors(int hue, int saturation, int brightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }



}
