package chaoziken.tfcloader.crafttweaker.util.defaults;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Lists of plants that TFC registers. Hardcoded because their registries are not available at the time they are needed
 */
public class DefaultPlants implements IDefaultType {

    public static final List<String> plants = ImmutableList.of(
            "allium",
            "athyrium_fern",
            "barrel_cactus",
            "black_orchid",
            "blood_lily",
            "blue_orchid",
            "butterfly_milkweed",
            "calendula",
            "canna",
            "dandelion",
            "duckweed",
            "field_horsetail",
            "fountain_grass",
            "foxglove",
            "goldenrod",
            "grape_hyacinth",
            "guzmania",
            "houstonia",
            "labrador_tea",
            "lady_fern",
            "licorice_fern",
            "lotus",
            "meads_milkweed",
            "morning_glory",
            "moss",
            "nasturtium",
            "orchard_grass",
            "ostrich_fern",
            "oxeye_daisy",
            "pampas_grass",
            "perovskia",
            "pistia",
            "poppy",
            "porcini",
            "primrose",
            "pulsatilla",
            "reindeer_lichen",
            "rose",
            "rough_horsetail",
            "ryegrass",
            "sacred_datura",
            "sagebrush",
            "sapphire_tower",
            "sargassum",
            "scutch_grass",
            "snapdragon_pink",
            "snapdragon_red",
            "snapdragon_white",
            "snapdragon_yellow",
            "spanish_moss",
            "strelitzia",
            "switchgrass",
            "sword_fern",
            "tall_fescue_grass",
            "timothy_grass",
            "toquilla_palm",
            "tree_fern",
            "trillium",
            "tropical_milkweed",
            "tulip_orange",
            "tulip_pink",
            "tulip_red",
            "tulip_white",
            "vriesea",
            "water_canna",
            "water_lily",
            "yucca"
    );

    @Override
    public void checkIfDefault(String name) {
        if (plants.contains(name)) {
            throw new IllegalArgumentException(name + " is an already registered plant!");
        }
    }
}
