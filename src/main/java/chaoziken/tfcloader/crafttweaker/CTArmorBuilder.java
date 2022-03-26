package chaoziken.tfcloader.crafttweaker;

import chaoziken.tfcloader.TFCLoader;
import com.google.common.collect.ImmutableList;
import crafttweaker.annotations.ZenRegister;
import net.dries007.tfc.objects.ArmorMaterialTFC;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenConstructor;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.tfcloader.ArmorBuilder")
@ZenRegister
@SuppressWarnings("unused")
public class CTArmorBuilder {

    //Default values are from Wrought Iron
    private final String resourceName;
    private int durability = 33;
    private int[] reductions = {1, 4, 5, 2};
    private int enchantability = 12;
    private float toughness = 0.0f;
    private SoundEvent soundOnEquip = SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    private float piercingRes = 20;
    private float slashingRes = 20;
    private float crushingRes = 13.2F;
    private String helmet_texture = "wrought_iron";
    private String chestplate_texture = "wrought_iron";
    private String greaves_texture = "wrought_iron";
    private String boots_texture = "wrought_iron";
    private String shield_texture = "wrought_iron";
    private ArmorMaterialTFC inner;

    @ZenConstructor
    public CTArmorBuilder(String resourceName) {
        this.resourceName = resourceName;
    }

    @ZenMethod
    public CTArmorBuilder durability(int durability) {
        this.durability = durability;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder reductions(int int1, int int2, int int3, int int4) {
        this.reductions = new int[]{int1, int2, int3, int4};
        return this;
    }

    @ZenMethod
    public CTArmorBuilder enchantability(int enchantability) {
        this.enchantability = enchantability;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder toughness(float toughness) {
        this.toughness = toughness;
        return this;
    }

    //TODO equipSound needs ContentTweaker ISoundTypeDefinition

    @ZenMethod
    public CTArmorBuilder piercingRes(float piercingRes) {
        this.piercingRes = piercingRes;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder slashingRes(float slashingRes) {
        this.slashingRes = slashingRes;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder crushingRes(float crushingRes) {
        this.crushingRes = crushingRes;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder setHelmetTexture(String helmet_texture) {
        MetalTextureTypes.checkMetalTextureValidity(helmet_texture);
        this.helmet_texture = helmet_texture;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder setChestplateTexture(String chestplate_texture) {
        MetalTextureTypes.checkMetalTextureValidity(chestplate_texture);
        this.chestplate_texture = chestplate_texture;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder setGreavesTexture(String greaves_texture) {
        MetalTextureTypes.checkMetalTextureValidity(greaves_texture);
        this.greaves_texture = greaves_texture;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder setBootsTexture(String boots_texture) {
        MetalTextureTypes.checkMetalTextureValidity(boots_texture);
        this.boots_texture = boots_texture;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder setShieldTexture(String shield_texture) {
        MetalTextureTypes.checkMetalTextureValidity(shield_texture);
        this.shield_texture = shield_texture;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder setAllTextures(String texture) {
        MetalTextureTypes.checkMetalTextureValidity(texture);
        this.helmet_texture = texture;
        this.chestplate_texture = texture;
        this.greaves_texture = texture;
        this.boots_texture = texture;
        this.shield_texture = texture;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder build() {
        this.inner = new ArmorMaterialTFC(EnumHelper.addArmorMaterial(resourceName, TFCLoader.MODID + ":" + resourceName, durability, reductions, enchantability, soundOnEquip, toughness), piercingRes, slashingRes, crushingRes);
        return this;
    }

    public ArmorMaterialTFC getInner() {
        return inner;
    }

    public List<String> getTextures() {
        return ImmutableList.of(helmet_texture, chestplate_texture, greaves_texture, boots_texture, shield_texture);
    }
}
