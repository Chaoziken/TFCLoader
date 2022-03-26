package chaoziken.tfcloader.crafttweaker.util;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class MetalTextureTypes {

    private static final List<String> allowedTextures = ImmutableList.of(
            "bismuth_bronze",
            "black_bronze",
            "black_steel",
            "blue_steel",
            "bronze",
            "copper",
            "red_steel",
            "steel",
            "wrought_iron");

    public static void checkMetalTextureValidity(String texture) {
        if (!allowedTextures.contains(texture)) {
            throw new IllegalArgumentException("Texture " + texture + " is not a valid texture!");
        }
    }

}
