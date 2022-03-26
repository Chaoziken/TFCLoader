package chaoziken.tfcloader.crafttweaker.util.defaults;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Lists of trees that TFC registers. Hardcoded because their registries are not available at the time they are needed
 */
public class DefaultTrees implements IDefaultType {

    public final List<String> trees = ImmutableList.of(
            "acacia",
            "ash",
            "aspen",
            "birch",
            "blackwood",
            "chestnut",
            "douglas_fir",
            "hickory",
            "maple",
            "oak",
            "palm",
            "pine",
            "rosewood",
            "sequoia",
            "spruce",
            "sycamore",
            "white_cedar",
            "willow",
            "kapok"
    );

    @Override
    public void checkIfDefault(String name) {
        if (trees.contains(name)) {
            throw new IllegalArgumentException(name + " is an already registered tree!");
        }
    }
}
