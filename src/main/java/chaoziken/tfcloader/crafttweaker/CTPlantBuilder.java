package chaoziken.tfcloader.crafttweaker;

import chaoziken.tfcloader.TFCLoader;
import chaoziken.tfcloader.crafttweaker.util.defaults.DefaultPlants;
import chaoziken.tfcloader.crafttweaker.util.defaults.IDefaultType;
import crafttweaker.annotations.ZenRegister;
import net.dries007.tfc.api.types.Plant;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenConstructor;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Locale;

@ZenClass("mods.tfcloader.PlantBuilder")
@ZenRegister
@SuppressWarnings("unused")
public class CTPlantBuilder {

    private final ResourceLocation plantName;
    private Plant inner;
    private int[] stages;
    private float minGrowthTemp;
    private float maxGrowthTemp;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;
    private int minSun;
    private int maxSun;
    private int maxHeight;
    private int minWaterDepth;
    private int maxWaterDepth;
    private double movementMod;

    private final Plant.PlantType plantType;
    private boolean isClayMarking;
    private boolean isSwampPlant;
    private String oreDictEntry = null;
    //TODO Document
    @ZenConstructor
    public CTPlantBuilder(String plantName, String plantType, float minTemp, float maxTemp, float minRain, float maxRain) {
        CTRegistry.validateName(plantName, "PlantBuilder");

        IDefaultType defaultPlants = new DefaultPlants();
        defaultPlants.checkIfDefault(plantName);

        this.plantName = new ResourceLocation(TFCLoader.MODID, plantName);

        try {
            this.plantType = Plant.PlantType.valueOf(plantType.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("There is no PlantType with name " + plantType);
        }

        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minRain = minRain;
        this.maxRain = maxRain;

        // Default values
        this.stages = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.isClayMarking = false;
        this.isSwampPlant = false;
        this.minGrowthTemp = 11f;
        this.maxGrowthTemp = 21f;
        this.minSun = 9;
        this.maxSun = 15;
        this.maxHeight = 1;
        this.minWaterDepth = 0;
        this.maxWaterDepth = 0;
        this.movementMod = 1.0d;
    }

    @ZenMethod
    public CTPlantBuilder setStages(int[] stages) {
        this.stages = stages;
        return this;
    }

    @ZenMethod
    public CTPlantBuilder setGrowthTemps(float min, float max) {
        this.minGrowthTemp = min;
        this.maxGrowthTemp = max;
        return this;
    }

    @ZenMethod
    public CTPlantBuilder setSun(int min, int max) {
        this.minSun = min;
        this.maxSun = max;
        return this;
    }

    @ZenMethod
    public CTPlantBuilder setWaterDepths(int min, int max) {
        this.minWaterDepth = min;
        this.maxWaterDepth = max;
        return this;
    }

    @ZenMethod
    public CTPlantBuilder setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    @ZenMethod
    public CTPlantBuilder setMovementMod(double movementMod) {
        this.movementMod = movementMod;
        return this;
    }

    @ZenMethod
    public CTPlantBuilder setClayMarking() {
        this.isClayMarking = true;
        return this;
    }

    @ZenMethod
    public CTPlantBuilder setSwampPlant() {
        this.isSwampPlant = true;
        return this;
    }

    @ZenMethod
    public CTPlantBuilder setOreDictEntry(String oreDictEntry) {
        CTRegistry.validateName(oreDictEntry, "PlantType_oreDictEntry");
        this.oreDictEntry = oreDictEntry;
        return this;
    }

    /**
     * Build the plant. Use this LAST
     */
    @ZenMethod
    public CTPlantBuilder build() {
        this.inner = new Plant(plantName, plantType, stages, isClayMarking, isSwampPlant, minGrowthTemp, maxGrowthTemp, minTemp, maxTemp, minRain, maxRain, minSun, maxSun, maxHeight, minWaterDepth, maxWaterDepth, movementMod, oreDictEntry);
        return this;
    }

    public Plant getInner() {
        return inner;
    }

}
