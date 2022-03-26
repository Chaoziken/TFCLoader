package chaoziken.tfcloader.crafttweaker.util.defaults;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Lists of rock categories that TFC registers. Hardcoded because their registries are not available at the time they are needed
 */
public class DefaultRockTypes implements IDefaultType {

    public final List<String> rockCategories = ImmutableList.of(
            "sedimentary",
            "metamorphic",
            "igneous_intrusive",
            "igneous_extrusive"
    );

    @Override
    public void checkIfDefault(String name) {
        if (rockCategories.contains(name)) {
            throw new IllegalArgumentException(name + " is an already registered rock category!");
        }
    }
}
