package chaoziken.tfcloader.crafttweaker;

import chaoziken.tfcloader.TFCLoader;
import chaoziken.tfcloader.crafttweaker.util.MetalTextureTypes;
import com.google.common.collect.ImmutableList;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.ISoundEventDefinition;
import crafttweaker.annotations.ZenRegister;
import net.dries007.tfc.objects.ArmorMaterialTFC;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Optional;
import org.apache.commons.lang3.Validate;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenConstructor;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.tfcloader.ArmorBuilder")
@ZenRegister
@SuppressWarnings("unused")
public class CTArmorBuilder {

    //Default values are from Wrought Iron
    private final String metalName;
    private int durability = 33;
    private int[] reductions = {1, 4, 5, 2}; //Armor values for boots, greaves/leggings, chestplate, and helmet
    private int enchantability = 12;
    private float toughness = 0.0f;
    private SoundEvent soundOnEquip = SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    private float piercingRes = 20f;
    private float slashingRes = 20f;
    private float crushingRes = 13.2F;
    private String helmetTexture = "wrought_iron";
    private String chestplateTexture = "wrought_iron";
    private String greavesTexture = "wrought_iron";
    private String bootsTexture = "wrought_iron";
    private String shieldTexture = "wrought_iron";
    private ArmorMaterialTFC inner;

    /**
     * Builder for armor sets. Also used to set the shield texture
     * @param metalName the name of the metal this armor is made out of
     */
    @ZenConstructor
    public CTArmorBuilder(String metalName) {
        CTRegistry.validateName(metalName, "ArmorBuilder");
        this.metalName = metalName;
    }

    @ZenMethod
    public CTArmorBuilder durability(int durability) {
        this.durability = durability;
        return this;
    }

    /**
     * The defense points of each piece. One point = 1/2 point on the armor bar
     * Registers from "bottom up" despite what {@link net.minecraft.item.ItemArmor.ArmorMaterial ArmorMaterial} seems to say
     */
    @ZenMethod
    public CTArmorBuilder reductions(int helmet, int chestplate, int greaves, int boots) {
        this.reductions = new int[]{boots, greaves, chestplate, helmet}; //Seems like they are registered from "bottom up"
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

    @ZenMethod
    @Optional.Method(modid = TFCLoader.MODID_CoT)
    public CTArmorBuilder soundOnEquip(ISoundEventDefinition soundOnEquip) {
        Validate.notNull(soundOnEquip, "SoundEventDefinition in soundOnEquip for ArmorBuilder " + metalName + " is null!");
        this.soundOnEquip = soundOnEquip.getInternal();
        return this;
    }

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
    public CTArmorBuilder setHelmetTexture(String helmetTexture) {
        MetalTextureTypes.checkMetalTextureValidity(helmetTexture);
        this.helmetTexture = helmetTexture;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder setChestplateTexture(String chestplateTexture) {
        MetalTextureTypes.checkMetalTextureValidity(chestplateTexture);
        this.chestplateTexture = chestplateTexture;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder setGreavesTexture(String greavesTexture) {
        MetalTextureTypes.checkMetalTextureValidity(greavesTexture);
        this.greavesTexture = greavesTexture;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder setBootsTexture(String bootsTexture) {
        MetalTextureTypes.checkMetalTextureValidity(bootsTexture);
        this.bootsTexture = bootsTexture;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder setShieldTexture(String shieldTexture) {
        MetalTextureTypes.checkMetalTextureValidity(shieldTexture);
        this.shieldTexture = shieldTexture;
        return this;
    }

    @ZenMethod
    public CTArmorBuilder setAllTextures(String texture) {
        MetalTextureTypes.checkMetalTextureValidity(texture);
        this.helmetTexture = texture;
        this.chestplateTexture = texture;
        this.greavesTexture = texture;
        this.bootsTexture = texture;
        this.shieldTexture = texture;
        return this;
    }

    /**
     * Builds the armor. Use this LAST
     */
    @ZenMethod
    public CTArmorBuilder build() {
        this.inner = new ArmorMaterialTFC(EnumHelper.addArmorMaterial(metalName, TFCLoader.MODID + ":" + metalName, durability, reductions, enchantability, soundOnEquip, toughness), piercingRes, slashingRes, crushingRes);
        return this;
    }

    public String getMetalName() {
        return this.metalName;
    }

    public ArmorMaterialTFC getInner() {
        return this.inner;
    }

    public List<String> getTextures() {
        return ImmutableList.of(helmetTexture, chestplateTexture, greavesTexture, bootsTexture, shieldTexture);
    }
}
