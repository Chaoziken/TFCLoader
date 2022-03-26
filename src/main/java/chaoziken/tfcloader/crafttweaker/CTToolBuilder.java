package chaoziken.tfcloader.crafttweaker;

import chaoziken.tfcloader.TFCLoader;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenConstructor;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tfcloader.ToolBuilder")
@ZenRegister
@SuppressWarnings("unused")
public class CTToolBuilder {

    //Defaults are from Wrought Iron
    private final String resourceName;
    private int harvestLevel = 2;
    private int maxUses = 2200;
    private float efficiency = 12;
    private float damage = 4.75f;
    private int enchantability = 12;
    private Item.ToolMaterial inner;
    private String toolTextures = "wrought_iron";

    @ZenConstructor
    public CTToolBuilder(String resourceName) {
        this.resourceName = resourceName;
    }

    @ZenMethod
    public CTToolBuilder harvestLevel(int harvestLevel) {
        this.harvestLevel = harvestLevel;
        return this;
    }

    @ZenMethod
    public CTToolBuilder maxUses(int maxUses) {
        this.maxUses = maxUses;
        return this;
    }

    @ZenMethod
    public CTToolBuilder efficiency(float efficiency) {
        this.efficiency = efficiency;
        return this;
    }

    @ZenMethod
    public CTToolBuilder damage(float damage) {
        this.damage = damage;
        return this;
    }

    @ZenMethod
    public CTToolBuilder enchantability(int enchantability) {
        this.enchantability = enchantability;
        return this;
    }

    @ZenMethod
    public CTToolBuilder setToolTextures(String tool_textures) {
        if (MetalTextureTypes.isValidMetalTexture(tool_textures)) {
            this.toolTextures = tool_textures;
        }
        return this;
    }

    @ZenMethod
    public CTToolBuilder build() {
        this.inner = EnumHelper.addToolMaterial(TFCLoader.MODID + "_" + resourceName, harvestLevel, maxUses, efficiency, damage, enchantability);
        return this;
    }

    public Item.ToolMaterial getInner() {
        return inner;
    }

    public String getToolTextures() {
        return toolTextures;
    }
}
