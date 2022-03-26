package chaoziken.tfcloader.crafttweaker;

import com.google.common.collect.ImmutableList;
import crafttweaker.CraftTweakerAPI;

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

    public static boolean isValidMetalTexture(String texture) {
        if (allowedTextures.contains(texture)) {
            return true;
        } else {
            CraftTweakerAPI.logError("Texture " + texture + " is not a valid texture! Defaulting to wrought_iron.");
            return false;
        }
    }

}
