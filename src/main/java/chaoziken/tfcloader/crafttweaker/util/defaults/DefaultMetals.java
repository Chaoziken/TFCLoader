package chaoziken.tfcloader.crafttweaker.util.defaults;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Lists of metals that TFC registers. Hardcoded because their registries are not available at the time they are needed
 */
public class DefaultMetals implements IDefaultType {

    public final List<String> metals = ImmutableList.of(
            "bismuth",
            "bismuth_bronze",
            "black_bronze",
            "brass",
            "bronze",
            "copper",
            "gold",
            "lead",
            "nickel",
            "rose_gold",
            "silver",
            "tin",
            "zinc",
            "sterling_silver",
            "wrought_iron",
            "pig_iron",
            "steel",
            "platinum",
            "black_steel",
            "blue_steel",
            "red_steel",
            "weak_steel",
            "weak_blue_steel",
            "weak_red_steel",
            "high_carbon_steel",
            "high_carbon_blue_steel",
            "high_carbon_red_steel",
            "high_carbon_black_steel",
            "unknown"
    );

    @Override
    public void checkIfDefault(String name) {
        if (metals.contains(name)) {
            throw new IllegalArgumentException(name + " is an already registered metal!");
        }
    }
}
