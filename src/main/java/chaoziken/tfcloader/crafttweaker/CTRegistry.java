package chaoziken.tfcloader.crafttweaker;

import chaoziken.tfcloader.TFCLoader;
import chaoziken.tfcloader.crafttweaker.registration.RegistryLists;
import chaoziken.tfcloader.crafttweaker.util.MetalColors;
import chaoziken.tfcloader.crafttweaker.util.MetalTextureTypes;
import chaoziken.tfcloader.crafttweaker.util.defaults.*;
import chaoziken.tfcloader.proxy.CommonProxy;
import chaoziken.tfcloader.util.MetalFileHelper;
import crafttweaker.annotations.ZenRegister;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.*;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.Validate;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.Color;
import java.util.List;

@ZenClass("mods.tfcloader.Registry")
@ZenRegister
@ParametersAreNonnullByDefault
@SuppressWarnings("unused")
public class CTRegistry {

    /**
     * Creates a rock type, like sedimentary
     * @param categoryName  the name of the type, in snake_case
     * @param toolBuilder   the tool used for stone tools made with this type
     * @param hardness      how hard this type is (higher values means slower block break times)
     * @param resistance    how resistant to explosions this type is
     * @param hasAnvil      if this type should be able to create a stone anvil
     * @param layer1        OPTIONAL: default true; whether this type spawns in the first rock layer
     * @param layer2        OPTIONAL: default true; whether this type spawns in the second rock layer
     * @param layer3        OPTIONAL: default true; whether this type spawns in the third rock layer
     * @param caveGenMod    OPTIONAL: default 0; "a modifier for cave generation. Range -0.5 <> 0.5"
     * @param caveFreqMod   OPTIONAL: default 0; "another modifier for cave generation. Sedimentary uses +5"
     */
    @ZenMethod
    public static void registerRockType(String categoryName, CTToolBuilder toolBuilder,
                                        float hardness, float resistance, boolean hasAnvil,
                                        @Optional(valueBoolean = true) boolean layer1, @Optional(valueBoolean = true) boolean layer2, @Optional(valueBoolean = true) boolean layer3,
                                        @Optional(valueDouble = 0f) float caveGenMod, @Optional(valueDouble = 0f) float caveFreqMod) {
        validateName(categoryName, "rockType");
        Validate.notNull(toolBuilder, "RockType's tool category cannot be null!");

        IDefaultType defaultRockTypes = new DefaultRockTypes();
        defaultRockTypes.checkIfDefault(categoryName);

        Item.ToolMaterial toolMaterial = toolBuilder.getInner();

        RockCategory rockCategory = new RockCategory(new ResourceLocation(TFCLoader.MODID, categoryName), toolMaterial, layer1, layer2, layer3, caveGenMod, caveFreqMod, hardness, resistance, hasAnvil);
        RegistryLists.rockCategoryRegistry.add(rockCategory);
    }

    /**
     * Creates a rock.
     * @param rockName              the name of this rock, in snake_case
     * @param categoryName          the name of the rock category this rock belongs to, in snake_case
     * @param isFluxStone           OPTIONAL: default true; whether this rock can be used as flux
     * @param isNaturallyGenerating OPTIONAL: default true; whether this rock should naturally generate or not
     * @param categoryModID         "OPTIONAL": default "tfc"; the mod id that adds the categoryName. REQUIRED (as well as above arguments) if using a custom category (use "tfcloader") or a category from another add-on (use their mod id).
     */
    @ZenMethod
    public static void registerRock(String rockName, String categoryName,
                                    @Optional(valueBoolean = true) boolean isFluxStone, @Optional(valueBoolean = true) boolean isNaturallyGenerating,
                                    @Optional(value = "tfc") String categoryModID) {
        validateName(rockName, "rock");
        validateName(categoryName, "rock_rockCategoryName");
        validateName(categoryModID, "rock_rockCategoryModID");

        IDefaultType defaultRocks = new DefaultRocks();
        defaultRocks.checkIfDefault(rockName);

        RockCategory rockCategory = java.util.Optional.ofNullable(TFCRegistries.ROCK_CATEGORIES.getValue(new ResourceLocation(categoryModID, categoryName)))
                .orElseThrow(() -> new IllegalArgumentException("Failed to register " + rockName + " because there is no RockCategory with name " + categoryName));
        Rock rock = new Rock(new ResourceLocation(TFCLoader.MODID, rockName), rockCategory, isFluxStone, isNaturallyGenerating);
        RegistryLists.rockRegistry.add(rock);
    }

    /**
     * Creates a metal. The first launch after adding a metal will result in model loading errors. Simply relaunch the game to clear them.
     * @param metalName     the name of the metal, in snake_case
     * @param tier          the tier, 0-6. See the comment within the function for more details
     * @param usable        is the metal usable to create basic metal items? (not tools)
     * @param specificHeat  specific heat capacity. Higher = harder to heat up / cool down. Most IRL metals are between 0.3 - 0.7
     * @param meltTemp      melting point. See the comment within the function for temperature scale. Similar to IRL melting point in celsius.
     * @param color         color of the metal, in RGB. Used to auto generate a fluid texture, and now auto generates the item colors as well.
     *                      <p>Note that the brightness/value of the color (in HSB/HSV) will be ignored when coloring items!</p>
     *                      <p>Also note that if you change this after generating the textures once, you will have to delete them in order for the new color to take effect.</p>
     * @param toolBuilder   OPTIONAL: default null; the {@link CTToolBuilder} for this metal
     * @param armorBuilder  "OPTIONAL": default null; the {@link CTArmorBuilder} for this metal. REQUIRED if toolBuilder is specified
     * @param baseItemTexture OPTIONAL: default "wrought_iron"; the base texture to use for items. See {@link MetalTextureTypes} for valid strings
     */
    @ZenMethod
    public static void registerMetal(String metalName, int tier, boolean usable, float specificHeat, float meltTemp, int color,
                                     @Optional @Nullable CTToolBuilder toolBuilder, @Optional @Nullable CTArmorBuilder armorBuilder,
                                     @Optional(value = "wrought_iron") String baseItemTexture) {
        /*
         * Tiers:
         * Metals / Anvils:
         * T0 - Stone - Work None, Weld T1
         * T1 - Copper - Work T1, Weld T2
         * T2 - Bronze / Bismuth Bronze / Black Bronze - Work T2, Weld T3
         * T3 - Wrought Iron - Work T3, Weld T4
         * T4 - Steel - Work T4, Weld T5
         * T5 - Black Steel - Work T5, Weld T6
         * T6 - Red Steel / Blue Steel - Work T6, Weld T6
         */

        /*
         * Heat:
         * Warming starts at 1, ends at 80
         * Hot starts at 80, ends at 210
         * Very Hot starts at 210, ends at 480
         * Faint Red starts at 480, ends at 580
         * Dark Red starts at 580, ends at 730
         * Bright Red starts at 730, ends at 930
         * Orange starts at 930, ends at 1100
         * Yellow starts at 1100, ends at 1300
         * Yellow White starts at 1300, ends at 1400
         * White starts at 1400, ends at 1500
         * Brilliant White starts at 1500, ends at 1601
         */

        validateName(metalName, "metal");
        MetalTextureTypes.checkMetalTextureValidity(baseItemTexture);

        IDefaultType defaultMetals = new DefaultMetals();
        defaultMetals.checkIfDefault(metalName);

        Item.ToolMaterial toolMaterial = null;
        IArmorMaterialTFC armorMaterial = null;
        String toolTexture = null;
        List<String> armorTexture = null;

        if (toolBuilder != null) {
            toolMaterial = toolBuilder.getInner();
            Validate.notNull(armorBuilder, "ArmorBuilder must be specified if ToolBuilder is as well! Metal: " + metalName);
            armorMaterial = armorBuilder.getInner();

            if (!toolBuilder.getResourceName().equals(metalName)) {
                throw new IllegalArgumentException("ToolBuilder material " + toolBuilder.getResourceName() + " is not the same as metal " + metalName);
            }
            if (!armorBuilder.getMetalName().equals(metalName)) {
                throw new IllegalArgumentException("ArmorBuilder material " + armorBuilder.getMetalName() + " is not the same as metal " + metalName);
            }

            toolTexture = toolBuilder.getToolTextures();
            armorTexture = armorBuilder.getTextures();
        }

        int fluidColor = tintColorToMetal(color, baseItemTexture);

        Metal metal = new Metal(new ResourceLocation(TFCLoader.MODID, metalName), Metal.Tier.valueOf(tier), usable, specificHeat, meltTemp, fluidColor, toolMaterial, armorMaterial);
        RegistryLists.metalRegistry.add(metal);

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            MetalFileHelper.generateMetalResources(CommonProxy.getTfcResourceDir(), metalName, tier, color, true, true, baseItemTexture, toolTexture, armorTexture);
        }
    }

    /**
     * Creates an ore.
     * @param oreName       the name of the ore, in snake_case
     * @param metalName     OPTIONAL: default null; the name of the metal this ore contains, in snake_case
     * @param canMelt       OPTIONAL: default false; if the metal can be melted directly from the ore. Only matters if metalName is a valid metal
     * @param chunkChance   OPTIONAL: default 0; the chance a chunk contains this ore when gold panning
     * @param panChance     OPTIONAL: default 0; the chance to drop this ore when gold panning
     * @param metalModID    "OPTIONAL": default "tfc"; the mod id that adds the metal. REQUIRED (as well as above arguments) if using a custom metal (use "tfcloader") or a metal from another add-on (use their mod id).
     */
    @ZenMethod
    public static void registerOre(String oreName, @Optional @Nullable String metalName,
                                   @Optional boolean canMelt, @Optional double chunkChance, @Optional double panChance,
                                   @Optional(value = "tfc") String metalModID) {

        validateName(oreName, "ore");
        validateName(metalModID, "ore_metalModID");

        IDefaultType defaultOres = new DefaultOres();
        defaultOres.checkIfDefault(oreName);

        Metal metal;
        if (metalName == null || metalName.isEmpty()) {
            canMelt = false; //force to false if the metal was omitted
            metal = null;
        } else {
            metal = java.util.Optional.ofNullable(TFCRegistries.METALS.getValue(new ResourceLocation(metalModID, metalName)))
                    .orElseThrow(() -> new IllegalArgumentException("Failed to register " + oreName + " because there is no Metal with name " + metalName));
        }

        Ore ore = new Ore(new ResourceLocation(TFCLoader.MODID, oreName), metal, canMelt, chunkChance, panChance);
        RegistryLists.oreRegistry.add(ore);

    }

    /**
     * Creates a tree.
     * @param treeBuilder See {@link CTTreeBuilder}
     */
    @ZenMethod
    public static void registerTree(CTTreeBuilder treeBuilder) {
        Validate.notNull(treeBuilder, "TreeBuilder in registerTree is null!");
        RegistryLists.treeRegistry.add(treeBuilder.getInner());
    }

    /**
     * Creates a plant
     * @param plantBuilder See {@link CTPlantBuilder}
     */
    @ZenMethod
    public static void registerPlant(CTPlantBuilder plantBuilder) {
        Validate.notNull(plantBuilder, "PlantBuilder in registerPlant is null!");
        RegistryLists.plantRegistry.add(plantBuilder.getInner());
    }



    public static void validateName(@Nullable String name, String categoryName) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Error in " + categoryName + ": Name cannot be null or empty!");
        }
    }

    /**
     * Used to tint the fluid color to the baseItemTexture's color
     */
    private static int tintColorToMetal(int color, String baseItemTexture) {
        float[] colorHSB = Color.RGBtoHSB((color>>16)&0xff, (color>>8)&0xff, color&0xff, null);
        MetalColors metalColor = MetalColors.valueOf(baseItemTexture);
        if (metalColor.saturation != 0) {
            colorHSB[1] = (colorHSB[1] + metalColor.saturation)/2; //Average the saturation
        }
        return Color.HSBtoRGB(colorHSB[0], colorHSB[1], colorHSB[2]);
    }

}
