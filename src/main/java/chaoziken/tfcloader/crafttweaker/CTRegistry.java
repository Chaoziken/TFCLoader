package chaoziken.tfcloader.crafttweaker;

import chaoziken.tfcloader.TFCLoader;
import chaoziken.tfcloader.proxy.CommonProxy;
import chaoziken.tfcloader.util.MetalFileHelper;
import com.google.common.collect.ImmutableList;
import crafttweaker.annotations.ZenRegister;
import net.dries007.tfc.api.types.IArmorMaterialTFC;
import net.dries007.tfc.api.types.Metal;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.tfcloader.Registry")
@ZenRegister
@SuppressWarnings("unused")
public class CTRegistry {

    private static final List<String> defaultMetals = ImmutableList.of(
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
            "unknown");

    /**
     * Creates a metal. The first launch after adding a metal will result in model loading errors. Simply relaunch the game to clear them.
     * @param metalName     the name of the metal, in snake_case
     * @param tier          the tier, 0-6. See the comment within the function for more details
     * @param usable        is the metal usable to create basic metal items? (not tools)
     * @param specificHeat  specific heat capacity. Higher = harder to heat up / cool down. Most IRL metals are between 0.3 - 0.7
     * @param meltTemp      melting point. See below for temperature scale. Similar to IRL melting point in celsius.
     * @param color         color of the metal, in RGB. Used to auto generate a fluid texture, and now auto generates the item colors as well.
     *                      <p>Note that the brightness/value of the color (in HSB/HSV) will be ignored when coloring items!</p>
     *                      <p>Also note that if you change this after generating the textures once, you will have to delete them in order for the new color to take effect.</p>
     * @param baseItemTexture the base texture to use for items. Default "wrought_iron". See {@link MetalTextureTypes} for valid strings
     */
    @ZenMethod
    public static void registerMetal(String metalName, int tier, boolean usable, float specificHeat, float meltTemp, int color, @Optional(value = "wrought_iron") String baseItemTexture) {
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
        if (!MetalTextureTypes.isValidMetalTexture(baseItemTexture)) {
            baseItemTexture = "wrought_iron";
        }
        if (defaultMetals.contains(metalName)) {
            throw new IllegalArgumentException("Metal " + metalName + " already exists!");
        }
        Metal metal = new Metal(new ResourceLocation(TFCLoader.MODID, metalName), Metal.Tier.valueOf(tier), usable, specificHeat, meltTemp, color, null, null);
        RegistryLists.metalRegistry.add(metal);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            MetalFileHelper.generateMetalResources(CommonProxy.getTfcResourceDir(), metalName, tier, color, false, false, baseItemTexture, null, null);
        }
    }

    /**
     * Creates a metal. The first launch after adding a metal will result in model loading errors. Simply relaunch the game to clear them.
     * @param metalName     the name of the metal, in snake_case
     * @param tier          the tier, 0-6. See the comment within the previous function for more details
     * @param usable        is the metal usable to create basic metal items? (not tools)
     * @param specificHeat  specific heat capacity. Higher = harder to heat up / cool down. Most IRL metals are between 0.3 - 0.7
     * @param meltTemp      melting point. See the comment within the previous function for temperature scale. Similar to IRL melting point in celsius.
     * @param color         color of the metal, in RGB. Used to auto generate a fluid texture, and now auto generates the item colors as well.
     *                      <p>Note that the brightness/value of the color (in HSB/HSV) will be ignored when coloring items!</p>
     *                      <p>Also note that if you change this after generating the textures once, you will have to delete them in order for the new color to take effect.</p>
     * @param toolBuilder   the {@link CTToolBuilder} for this metal
     * @param armorBuilder  the {@link CTArmorBuilder} for this metal. Sadly, there is no way to only specify a tool or armor only; BOTH must be specified
     * @param baseItemTexture the base texture to use. Default "wrought_iron". See {@link MetalTextureTypes} for valid strings
     */
    @ZenMethod
    public static void registerMetal(String metalName, int tier, boolean usable, float specificHeat, float meltTemp, int color, CTToolBuilder toolBuilder, CTArmorBuilder armorBuilder, @Optional(value = "wrought_iron") String baseItemTexture) {
        if (!MetalTextureTypes.isValidMetalTexture(baseItemTexture)) {
            baseItemTexture = "wrought_iron";
        }
        if (defaultMetals.contains(metalName)) {
            throw new IllegalArgumentException("Metal " + metalName + " already exists!");
        }
        Item.ToolMaterial toolMaterial = toolBuilder.getInner();
        IArmorMaterialTFC armorMaterial = armorBuilder.getInner();
        Metal metal = new Metal(new ResourceLocation(TFCLoader.MODID, metalName), Metal.Tier.valueOf(tier), usable, specificHeat, meltTemp, color, toolMaterial, armorMaterial);
        RegistryLists.metalRegistry.add(metal);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            MetalFileHelper.generateMetalResources(CommonProxy.getTfcResourceDir(), metalName, tier, color, true, true, baseItemTexture, toolBuilder.getToolTextures(), armorBuilder.getTextures());
        }
    }

}
