package chaoziken.tfcloader.crafttweaker.util.defaults;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Lists of rocks that TFC registers. Hardcoded because their registries are not available at the time they are needed
 */
public class DefaultRocks implements IDefaultType {

    public final List<String> rocks = ImmutableList.of(
            "granite",
            "diorite",
            "gabbro",
            "shale",
            "claystone",
            "rocksalt",
            "limestone",
            "conglomerate",
            "dolomite",
            "chert",
            "chalk",
            "rhyolite",
            "basalt",
            "andesite",
            "dacite",
            "quartzite",
            "slate",
            "phyllite",
            "schist",
            "gneiss",
            "marble"
    );

    @Override
    public void checkIfDefault(String name) {
        if (rocks.contains(name)) {
            throw new IllegalArgumentException(name + " is an already registered rock!");
        }
    }
}
