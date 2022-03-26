package chaoziken.tfcloader.crafttweaker.util.defaults;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Lists of ores that TFC registers. Hardcoded because their registries are not available at the time they are needed
 */
public class DefaultOres implements IDefaultType {

    public final List<String> ores = ImmutableList.of(
            "native_copper",
            "native_gold",
            "native_platinum",
            "hematite",
            "native_silver",
            "cassiterite",
            "galena",
            "bismuthinite",
            "garnierite",
            "malachite",
            "magnetite",
            "limonite",
            "sphalerite",
            "tetrahedrite",
            "bituminous_coal",
            "lignite",
            "kaolinite",
            "gypsum",
            "satinspar",
            "selenite",
            "graphite",
            "kimberlite",
            "petrified_wood",
            "sulfur",
            "jet",
            "microcline",
            "pitchblende",
            "cinnabar",
            "cryolite",
            "saltpeter",
            "serpentine",
            "sylvite",
            "borax",
            "olivine",
            "lapis_lazuli"
    );

    @Override
    public void checkIfDefault(String name) {
        if (ores.contains(name)) {
            throw new IllegalArgumentException(name + " is an already registered ore!");
        }
    }
}
