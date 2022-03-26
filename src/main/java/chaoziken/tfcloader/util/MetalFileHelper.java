package chaoziken.tfcloader.util;

import chaoziken.tfcloader.crafttweaker.MetalTextureTypes;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static chaoziken.tfcloader.util.FileHelper.*;

public class MetalFileHelper {

    /////////////////////////////////////
    //    Metal Resource Generation    //
    /////////////////////////////////////

    public static void generateMetalResources(File resourceDir, String metalName, int tier, int color, boolean generateTools, boolean generateArmor, String baseItemTexture, @Nullable String toolTextures, @Nullable List<String> armorTextures) {
        if (toolTextures == null) {
            toolTextures = "wrought_iron"; //Not needed, but stops IntelliJ bringing up NPE problems
        }

        if (armorTextures == null) {
            armorTextures = ImmutableList.of("wrought_iron", "wrought_iron", "wrought_iron", "wrought_iron", "wrought_iron"); //Not needed, but stops IntelliJ bringing up NPE problems
        }


        File textureDir = createDir(resourceDir, "textures");
        //For some reason, it loads armor textures from the tfcloader resource folder, not tfc
        File tfcloaderTextureDir = createDir(createDir(resourceDir.getParentFile(), "tfcloader"), "textures");
        File modelsDir = createDir(resourceDir, "models");
        File blockstatesDir = createDir(resourceDir, "blockstates");
        ClassLoader classLoader = MetalFileHelper.class.getClassLoader();

        // textures/blocks
        File blocksDir = createDir(textureDir, "blocks");
        // textures/blocks/metal/metal.png
        File metalBlock = new File(createDir(blocksDir, "metal"), metalName + ".png");
        if (!metalBlock.exists()) {
            copyImageAndManipulate(classLoader.getResourceAsStream("assets/tfcloader/textures/blocks/metal/" + baseItemTexture + ".png"), metalBlock, color, 127);
        }

        // textures/items
        File itemsTextureDir = createDir(textureDir, "items");
        // textures/items/metal
        File metalItemsTextureDir = createDir(itemsTextureDir, "metal");
        if (metalItemsTextureDir == null) {
            return;
        }
        // textures/items/ceramics/fired/mold
        File firedCeramicsMoldTextureDir = createDir(createDir(createDir(itemsTextureDir, "ceramics"), "fired"), "mold");
        if (firedCeramicsMoldTextureDir == null) {
            return;
        }

        // models/item
        File itemsModelDir = createDir(modelsDir, "item");
        // models/item/metal
        File metalItemsModelDir = createDir(itemsModelDir, "metal");
        if (metalItemsModelDir == null) {
            return;
        }
        // models/item/ceramics/fired/mold
        File firedCeramicsMoldModelDir = createDir(createDir(createDir(itemsModelDir, "ceramics"), "fired"), "mold");
        if (firedCeramicsMoldModelDir == null) {
            return;
        }

        // textures/models/armor
        File armorModelTextureDir = createDir(createDir(tfcloaderTextureDir, "models"), "armor");
        if (armorModelTextureDir == null) {
            return;
        }

        // ingot
        createMetalItemTexture(classLoader, metalItemsTextureDir, "ingot", metalName, baseItemTexture, color, 127);
        createMetalItemJSON(metalItemsModelDir, "ingot", metalName, "generated");

        // double_ingot
        createMetalItemTexture(classLoader, metalItemsTextureDir, "double_ingot", metalName, baseItemTexture, color, 127);
        createMetalItemJSON(metalItemsModelDir, "double_ingot", metalName, "generated");

        // scrap
        createMetalItemTexture(classLoader, metalItemsTextureDir, "scrap", metalName, baseItemTexture, color, 127);
        createMetalItemJSON(metalItemsModelDir, "scrap", metalName, "generated");

        // dust
        createMetalItemTexture(classLoader, metalItemsTextureDir, "dust", metalName, baseItemTexture, color, 127);
        createMetalItemJSON(metalItemsModelDir, "dust", metalName, "generated");

        // nugget
        createMetalItemTexture(classLoader, metalItemsTextureDir, "nugget", metalName, baseItemTexture, color, 127);
        createMetalItemJSON(metalItemsModelDir, "nugget", metalName, "generated");

        // sheet
        createMetalItemTexture(classLoader, metalItemsTextureDir, "sheet", metalName, baseItemTexture, color, 127);
        createMetalItemJSON(metalItemsModelDir, "sheet", metalName, "generated");
        writeSheetBlockstateJSONFile(createDir(blockstatesDir, "sheet"), metalName);

        // double_sheet
        createMetalItemTexture(classLoader, metalItemsTextureDir, "double_sheet", metalName, baseItemTexture, color, 127);
        createMetalItemJSON(metalItemsModelDir, "double_sheet", metalName, "generated");

        // lamp
        createMetalItemTexture(classLoader, metalItemsTextureDir, "lamp", metalName, baseItemTexture, color, 127);
        createMetalItemJSON(metalItemsModelDir, "lamp", metalName, "generated");
        writeLampBlockstateJSONFile(createDir(blockstatesDir, "lamp"), metalName);

        // trapdoor
        // textures/blocks/trapdoor/metal.png
        File metalTrapdoor = new File(createDir(blocksDir, "trapdoor"), metalName + ".png");
        if (!metalTrapdoor.exists()) {
            copyImageAndManipulate(classLoader.getResourceAsStream("assets/tfcloader/textures/blocks/trapdoor/" + baseItemTexture + ".png"), metalTrapdoor, color, 127);
        }
        createMetalItemJSON(metalItemsModelDir, "trapdoor", metalName, "generated");
        writeTrapdoorBlockstateJSONFile(createDir(blockstatesDir, "trapdoor"), metalName);

        // fluid
        writeFluidBlockstateJSONFile(createDir(blockstatesDir, "fluid"), metalName);

        // ceramics/fired/mold/ingot/metal.png
        createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "ingot", metalName, color, 127);
        createMetalCeramicJSON(firedCeramicsMoldModelDir, "ingot", metalName);

        if (generateTools) {

            // Tool Parts

            // axe_head
            createMetalItemTexture(classLoader, metalItemsTextureDir, "axe_head", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "axe_head", metalName, "generated");

            // chisel_head
            createMetalItemTexture(classLoader, metalItemsTextureDir, "chisel_head", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "chisel_head", metalName, "generated");

            // hammer_head
            createMetalItemTexture(classLoader, metalItemsTextureDir, "hammer_head", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "hammer_head", metalName, "generated");

            // hoe_head
            createMetalItemTexture(classLoader, metalItemsTextureDir, "hoe_head", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "hoe_head", metalName, "generated");

            // javelin_head
            createMetalItemTexture(classLoader, metalItemsTextureDir, "javelin_head", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "javelin_head", metalName, "generated");

            // knife_blade
            createMetalItemTexture(classLoader, metalItemsTextureDir, "knife_blade", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "knife_blade", metalName, "generated");

            // mace_head
            createMetalItemTexture(classLoader, metalItemsTextureDir, "mace_head", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "mace_head", metalName, "generated");

            // pick_head
            createMetalItemTexture(classLoader, metalItemsTextureDir, "pick_head", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "pick_head", metalName, "generated");

            // propick_head
            createMetalItemTexture(classLoader, metalItemsTextureDir, "propick_head", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "propick_head", metalName, "generated");

            // saw_blade
            createMetalItemTexture(classLoader, metalItemsTextureDir, "saw_blade", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "saw_blade", metalName, "generated");

            // scythe_blade
            createMetalItemTexture(classLoader, metalItemsTextureDir, "scythe_blade", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "scythe_blade", metalName, "generated");

            // shovel_head
            createMetalItemTexture(classLoader, metalItemsTextureDir, "shovel_head", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "shovel_head", metalName, "generated");

            // sword_blade
            createMetalItemTexture(classLoader, metalItemsTextureDir, "sword_blade", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "sword_blade", metalName, "generated");

            // tuyere
            createMetalItemTexture(classLoader, metalItemsTextureDir, "tuyere", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "tuyere", metalName, "generated");

            // Tools

            // axe
            createMetalItemTexture(classLoader, metalItemsTextureDir, "axe", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "axe", metalName, "handheld");

            // chisel
            createMetalItemTexture(classLoader, metalItemsTextureDir, "chisel", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "chisel", metalName, "handheld");

            // hammer
            createMetalItemTexture(classLoader, metalItemsTextureDir, "hammer", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "hammer", metalName, "handheld");

            // hoe
            createMetalItemTexture(classLoader, metalItemsTextureDir, "hoe", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "hoe", metalName, "handheld");

            // javelin
            createMetalItemTexture(classLoader, metalItemsTextureDir, "javelin", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "javelin", metalName, "handheld");

            // knife
            createMetalItemTexture(classLoader, metalItemsTextureDir, "knife", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "knife", metalName, "handheld");

            // mace
            createMetalItemTexture(classLoader, metalItemsTextureDir, "mace", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "mace", metalName, "handheld");

            // pick
            createMetalItemTexture(classLoader, metalItemsTextureDir, "pick", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "pick", metalName, "handheld");

            // propick
            createMetalItemTexture(classLoader, metalItemsTextureDir, "propick", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "propick", metalName, "handheld");

            // saw
            createMetalItemTexture(classLoader, metalItemsTextureDir, "saw", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "saw", metalName, "handheld");

            // scythe
            createMetalItemTexture(classLoader, metalItemsTextureDir, "scythe", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "scythe", metalName, "handheld");

            // shears
            createMetalItemTexture(classLoader, metalItemsTextureDir, "shears", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "shears", metalName, "handheld");

            // shovel
            createMetalItemTexture(classLoader, metalItemsTextureDir, "shovel", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "shovel", metalName, "handheld");

            // sword
            createMetalItemTexture(classLoader, metalItemsTextureDir, "sword", metalName, toolTextures, color, 127);
            createMetalItemJSON(metalItemsModelDir, "sword", metalName, "handheld");

            // Fired Ceramic Molds
            // Only used for tier 2 or lower metals
            if (tier <= 2) {
                // axe_head
                createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "axe_head", metalName, color, 127);
                createMetalCeramicJSON(firedCeramicsMoldModelDir, "axe_head", metalName);

                // chisel_head
                createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "chisel_head", metalName, color, 127);
                createMetalCeramicJSON(firedCeramicsMoldModelDir, "chisel_head", metalName);

                // hammer_head
                createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "hammer_head", metalName, color, 127);
                createMetalCeramicJSON(firedCeramicsMoldModelDir, "hammer_head", metalName);

                // hoe_head
                createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "hoe_head", metalName, color, 127);
                createMetalCeramicJSON(firedCeramicsMoldModelDir, "hoe_head", metalName);

                // javelin_head
                createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "javelin_head", metalName, color, 127);
                createMetalCeramicJSON(firedCeramicsMoldModelDir, "javelin_head", metalName);

                // knife_blade
                createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "knife_blade", metalName, color, 127);
                createMetalCeramicJSON(firedCeramicsMoldModelDir, "knife_blade", metalName);

                // mace_head
                createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "mace_head", metalName, color, 127);
                createMetalCeramicJSON(firedCeramicsMoldModelDir, "mace_head", metalName);

                // pick_head
                createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "pick_head", metalName, color, 127);
                createMetalCeramicJSON(firedCeramicsMoldModelDir, "pick_head", metalName);

                // propick_head
                createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "propick_head", metalName, color, 127);
                createMetalCeramicJSON(firedCeramicsMoldModelDir, "propick_head", metalName);

                // saw_blade
                createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "saw_blade", metalName, color, 127);
                createMetalCeramicJSON(firedCeramicsMoldModelDir, "saw_blade", metalName);

                // scythe_blade
                createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "scythe_blade", metalName, color, 127);
                createMetalCeramicJSON(firedCeramicsMoldModelDir, "scythe_blade", metalName);

                // shovel_head
                createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "shovel_head", metalName, color, 127);
                createMetalCeramicJSON(firedCeramicsMoldModelDir, "shovel_head", metalName);

                // sword_blade
                createMetalCeramicTexture(classLoader, firedCeramicsMoldTextureDir, "sword_blade", metalName, color, 127);
                createMetalCeramicJSON(firedCeramicsMoldModelDir, "sword_blade", metalName);
            }

            // Misc

            // anvil
            // anvil texture is just blocks/metal, which has already been done. But there is another anvil item texture (unsure if it is used though)
            createMetalItemTexture(classLoader, metalItemsTextureDir, "anvil", metalName, baseItemTexture, color, 127);
            writeAnvilModelJSONFile(createDir(metalItemsModelDir, "anvil"), metalName);
            writeAnvilBlockstateJSONFile(createDir(blockstatesDir, "anvil"), metalName);

            // shield
            // This does both front and back
            // Default is based on bismuth_bronze
            createMetalShieldTexture(classLoader, metalItemsTextureDir, metalName, armorTextures.get(4), color, 127);
            writeShieldModelJSONFile(classLoader, createDir(metalItemsModelDir, "shield"), metalName, armorTextures.get(4));
        }

        if (generateArmor) {

            // Unfinished

            // unfinished_helmet
            createMetalItemTexture(classLoader, metalItemsTextureDir, "unfinished_helmet", metalName, armorTextures.get(0), color, 127);
            createMetalItemJSON(metalItemsModelDir, "unfinished_helmet", metalName, "generated");

            // unfinished_chestplate
            createMetalItemTexture(classLoader, metalItemsTextureDir, "unfinished_chestplate", metalName, armorTextures.get(1), color, 127);
            createMetalItemJSON(metalItemsModelDir, "unfinished_chestplate", metalName, "generated");

            // unfinished_greaves
            createMetalItemTexture(classLoader, metalItemsTextureDir, "unfinished_greaves", metalName, armorTextures.get(2), color, 127);
            createMetalItemJSON(metalItemsModelDir, "unfinished_greaves", metalName, "generated");

            // unfinished_boots
            createMetalItemTexture(classLoader, metalItemsTextureDir, "unfinished_boots", metalName, armorTextures.get(3), color, 127);
            createMetalItemJSON(metalItemsModelDir, "unfinished_boots", metalName, "generated");

            // Finished

            // helmet
            createMetalItemTexture(classLoader, metalItemsTextureDir, "helmet", metalName, armorTextures.get(0), color, 127);
            createMetalItemJSON(metalItemsModelDir, "helmet", metalName, "generated");
            createMetalArmorModelTexture(classLoader, armorModelTextureDir, metalName, armorTextures.get(0), color, 127);

            // chestplate
            createMetalItemTexture(classLoader, metalItemsTextureDir, "chestplate", metalName, armorTextures.get(1), color, 127);
            createMetalItemJSON(metalItemsModelDir, "chestplate", metalName, "generated");
            createMetalArmorModelTexture(classLoader, armorModelTextureDir, metalName, armorTextures.get(1), color, 127);

            // greaves
            createMetalItemTexture(classLoader, metalItemsTextureDir, "greaves", metalName, armorTextures.get(2), color, 127);
            createMetalItemJSON(metalItemsModelDir, "greaves", metalName, "generated");
            createMetalArmorModelTexture(classLoader, armorModelTextureDir, metalName, armorTextures.get(2), color, 127);

            // boots
            createMetalItemTexture(classLoader, metalItemsTextureDir, "boots", metalName, armorTextures.get(3), color, 127);
            createMetalItemJSON(metalItemsModelDir, "boots", metalName, "generated");
            createMetalArmorModelTexture(classLoader, armorModelTextureDir, metalName, armorTextures.get(3), color, 127);

        }

    }

    /**
     * Copies a texture given the base item dir, the metal item, and the metal name.
     * @param baseMetalItemTextureDir the directory to "textures/items/metal"
     * @param metalItem the name of the metal item, e.g. "ingot"
     * @param metalName the metal name
     * @param baseItemTexture the base texture to use. See {@link MetalTextureTypes} for valid strings
     * @param color the color to tint the manipulated pixels
     * @param alphaManipulation the alpha value of the default image that should be manipulated
     */
    @SuppressWarnings("SameParameterValue")
    private static void createMetalItemTexture(ClassLoader classLoader, File baseMetalItemTextureDir, String metalItem, String metalName, String baseItemTexture, int color, int alphaManipulation) {
        File itemDir = createDir(baseMetalItemTextureDir, metalItem);
        if (itemDir == null) {
            return;
        }
        File metalItemFile = new File(itemDir, metalName + ".png");
        if (!metalItemFile.exists()) {
            copyImageAndManipulate(classLoader.getResourceAsStream("assets/tfcloader/textures/items/metal/" + metalItem + "/" + baseItemTexture + ".png"), metalItemFile, color, alphaManipulation);
        }
    }

    /**
     * Copies a texture given the base item dir, the metal item, and the metal name.
     * @param baseMetalCeramicTextureDir the directory to "textures/items/ceramics/fired/mold"
     * @param metalItem the name of the metal item, e.g. "ingot"
     * @param metalName the metal name
     * @param color the color to tint the manipulated pixels
     * @param alphaManipulation the alpha value of the default image that should be manipulated
     */
    @SuppressWarnings("SameParameterValue")
    private static void createMetalCeramicTexture(ClassLoader classLoader, File baseMetalCeramicTextureDir, String metalItem, String metalName, int color, int alphaManipulation) {
        File itemDir = createDir(baseMetalCeramicTextureDir, metalItem);
        if (itemDir == null) {
            return;
        }
        File metalItemFile = new File(itemDir, metalName + ".png");
        if (!metalItemFile.exists()) {
            copyImageAndManipulate(classLoader.getResourceAsStream("assets/tfcloader/textures/items/ceramics/fired/mold/" + metalItem + "/default.png"), metalItemFile, color, alphaManipulation);
        } //Not going to bother changing the base item texture from wrought iron
    }

    /**
     * Copies a texture given the base item dir, the metal item, and the metal name.
     * @param baseMetalItemTextureDir the directory to "textures/items/metal"
     * @param metalName the metal name
     * @param baseItemTexture the base texture to use. See {@link MetalTextureTypes} for valid strings
     * @param color the color to tint the manipulated pixels
     * @param alphaManipulation the alpha value of the default image that should be manipulated
     */
    @SuppressWarnings("SameParameterValue")
    private static void createMetalShieldTexture(ClassLoader classLoader, File baseMetalItemTextureDir, String metalName, String baseItemTexture, int color, int alphaManipulation) {
        File itemDir = createDir(baseMetalItemTextureDir, "shield");
        if (itemDir == null) {
            return;
        }

        if (baseItemTexture.equals("blue_steel") || baseItemTexture.equals("red_steel") || baseItemTexture.equals("wrought_iron")) {
            //These have no _front or _back files, only metalName
            File shield = new File(itemDir, metalName + ".png");
            if (!shield.exists()) {
                copyImageAndManipulate(classLoader.getResourceAsStream("assets/tfcloader/textures/items/metal/shield/" + baseItemTexture + ".png"), shield, color, alphaManipulation);
            }
        } else {
            File shieldBack = new File(itemDir, metalName + "_back.png");
            if (!shieldBack.exists()) {
                copyImageAndManipulate(classLoader.getResourceAsStream("assets/tfcloader/textures/items/metal/shield/" + baseItemTexture + "_back.png"), shieldBack, color, alphaManipulation);
            }
            File shieldFront = new File(itemDir, metalName + "_front.png");
            if (!shieldFront.exists()) {
                copyImageAndManipulate(classLoader.getResourceAsStream("assets/tfcloader/textures/items/metal/shield/" + baseItemTexture + "_front.png"), shieldFront, color, alphaManipulation);
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void createMetalArmorModelTexture(ClassLoader classLoader, File baseDir, String metalName, String baseItemTexture, int color, int alphaManipulation) {
        File armorModelLayer1File = new File(baseDir, metalName + "_layer_1.png");
        File armorModelLayer2File = new File(baseDir, metalName + "_layer_2.png");
        if (!armorModelLayer1File.exists()) {
            copyImageAndManipulate(classLoader.getResourceAsStream("assets/tfcloader/textures/models/armor/" + baseItemTexture + "_layer_1.png"), armorModelLayer1File, color, alphaManipulation);
        }
        if (!armorModelLayer2File.exists()) {
            copyImageAndManipulate(classLoader.getResourceAsStream("assets/tfcloader/textures/models/armor/" + baseItemTexture + "_layer_2.png"), armorModelLayer2File, color, alphaManipulation);
        }
    }

    /**
     * Creates a json file for a metal item
     * @param baseMetalItemModelDir the directory to "models/item/metal"
     * @param metalItem the name of the metal item, e.g. "ingot"
     * @param metalName the metal name
     * @param parent for the JSON file. Usually "generated" or "handheld"
     */
    private static void createMetalItemJSON(File baseMetalItemModelDir, String metalItem, String metalName, String parent) {
        File itemDir = createDir(baseMetalItemModelDir, metalItem);
        if (itemDir == null) {
            return;
        }
        File metalItemFile = new File(itemDir, metalName + ".json");
        if (!metalItemFile.exists()) {
            if (createFile(metalItemFile)) {
                if (metalItem.equals("trapdoor")) {
                    writeTrapdoorJSONFile(metalItemFile, metalName);
                } else {
                    writeItemModelJSONFile(metalItemFile, parent, "items/metal/" + metalItem + "/" + metalName);
                }
            }
        }
    }

    /**
     * Creates a json file for a metal item
     * @param baseMetalCeramicModelDir the directory to "models/item/ceramics/fired/mold"
     * @param metalItem the name of the metal item, e.g. "ingot"
     * @param metalName the metal name
     */
    private static void createMetalCeramicJSON(File baseMetalCeramicModelDir, String metalItem, String metalName) {
        File itemDir = createDir(baseMetalCeramicModelDir, metalItem);
        if (itemDir == null) {
            return;
        }
        File metalItemFile = new File(itemDir, metalName + ".json");
        if (!metalItemFile.exists()) {
            if (createFile(metalItemFile)) {
                writeItemModelJSONFile(metalItemFile, "generated", "items/ceramics/fired/mold/" + metalItem + "/" + metalName);
            }
        }
    }

    private static void writeItemModelJSONFile(File file, String parent, String itemTexturePath) {
        try {
            Files.write(file.toPath(), createItemModelJSONText(parent, itemTexturePath));
        } catch (IOException e) {
            TFCLog.logger.error("Could not generate model JSON file for " + itemTexturePath, e);
        }
    }

    private static List<String> createItemModelJSONText(String parent, String itemTexturePath) {
        List<String> lines = new ArrayList<>(6);
        lines.add("{");
        lines.add("  \"parent\": \"item/" + parent + "\",");
        lines.add("  \"textures\": {");
        lines.add("    \"layer0\": \"tfc:" + itemTexturePath + "\"");
        lines.add("  }");
        lines.add("}");
        return lines;
    }


    //Trapdoor model

    private static void writeTrapdoorJSONFile(File file, String metalName) {
        //For some reason, there are two places where the trapdoor json is located (other is models/item/trapdoor/metal.json)
        //file is models/item/metal/trapdoor/metal.json
        File otherTrapdoorFile = createFile(createDir(file.getParentFile().getParentFile(), "trapdoor"), metalName + ".json");
        if (otherTrapdoorFile == null) {
            return;
        }
        try {
            Files.write(file.toPath(), createTrapdoorModelJSONText(metalName));
            Files.write(otherTrapdoorFile.toPath(), createTrapdoorModelJSONText(metalName));
        } catch (IOException e) {
            TFCLog.logger.error("Could not generate trapdoor model JSON file for " + metalName, e);
        }
    }

    private static List<String> createTrapdoorModelJSONText(String metalName) {
        List<String> lines = new ArrayList<>(6);
        lines.add("{");
        lines.add("  \"parent\": \"block/trapdoor_bottom\",");
        lines.add("  \"textures\": {");
        lines.add("    \"texture\": \"tfc:blocks/trapdoor/" + metalName + "\"");
        lines.add("  }");
        lines.add("}");
        return lines;
    }


    //Sheet blockstate

    private static void writeSheetBlockstateJSONFile(File file, String metalName) {
        File sheetJSON = createFile(file, metalName + ".json");
        if (sheetJSON == null) {
            return;
        }
        try {
            Files.write(sheetJSON.toPath(), createSheetBlockstateJSONText(metalName));
        } catch (IOException e) {
            TFCLog.logger.error("Could not generate sheet blockstate JSON file for " + metalName, e);
        }
    }

    //Ugly? Yes. Works? Also yes.
    private static List<String> createSheetBlockstateJSONText(String metalName) {
        List<String> lines = new ArrayList<>(4);
        lines.add("{\n" +
                "  \"forge_marker\": 1,\n" +
                "  \"defaults\": {\n" +
                "    \"model\": \"tfc:empty\",\n" +
                "    \"textures\": {");
        lines.add("      \"all\": \"tfc:blocks/metal/" + metalName + "\",");
        lines.add("      \"particle\": \"tfc:blocks/metal/" + metalName + "\"");
        lines.add("    }\n" +
                "  },\n" +
                "  \"variants\": {\n" +
                "    \"north\": {\n" +
                "      \"true\": {\n" +
                "        \"submodel\": {\n" +
                "          \"north\": {\n" +
                "            \"model\": \"tfc:sheet\",\n" +
                "            \"x\": 90\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"false\": {}\n" +
                "    },\n" +
                "    \"south\": {\n" +
                "      \"true\": {\n" +
                "        \"submodel\": {\n" +
                "          \"south\": {\n" +
                "            \"model\": \"tfc:sheet\",\n" +
                "            \"y\": 180,\n" +
                "            \"x\": 90\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"false\": {}\n" +
                "    },\n" +
                "    \"east\": {\n" +
                "      \"true\": {\n" +
                "        \"submodel\": {\n" +
                "          \"east\": {\n" +
                "            \"model\": \"tfc:sheet\",\n" +
                "            \"y\": 90,\n" +
                "            \"x\": 90\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"false\": {}\n" +
                "    },\n" +
                "    \"west\": {\n" +
                "      \"true\": {\n" +
                "        \"submodel\": {\n" +
                "          \"west\": {\n" +
                "            \"model\": \"tfc:sheet\",\n" +
                "            \"y\": 270,\n" +
                "            \"x\": 90\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"false\": {}\n" +
                "    },\n" +
                "    \"up\": {\n" +
                "      \"true\": {\n" +
                "        \"submodel\": {\n" +
                "          \"up\": {\n" +
                "            \"model\": \"tfc:sheet\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"false\": {}\n" +
                "    },\n" +
                "    \"down\": {\n" +
                "      \"true\": {\n" +
                "        \"submodel\": {\n" +
                "          \"down\": {\n" +
                "            \"model\": \"tfc:sheet\",\n" +
                "            \"x\": 180\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"false\": {}\n" +
                "    }\n" +
                "  }\n" +
                "}");
        return lines;
    }


    //Lamp blockstate

    private static void writeLampBlockstateJSONFile(File file, String metalName) {
        File lampJSON = createFile(file, metalName + ".json");
        if (lampJSON == null) {
            return;
        }
        try {
            Files.write(lampJSON.toPath(), createLampBlockstateJSONText(metalName));
        } catch (IOException e) {
            TFCLog.logger.error("Could not generate lamp blockstate JSON file for " + metalName, e);
        }
    }

    //Ugly? Yes. Works? Also yes.
    private static List<String> createLampBlockstateJSONText(String metalName) {
        List<String> lines = new ArrayList<>(4);
        lines.add("{\n" +
                "  \"forge_marker\": 1,\n" +
                "  \"defaults\": {\n" +
                "    \"model\": \"tfc:lamp/up\",\n" +
                "    \"textures\": {");
        lines.add("      \"sheet\": \"tfc:blocks/metal/" + metalName + "\",");
        lines.add("      \"particle\": \"tfc:blocks/metal/" + metalName + "\",");
        lines.add("      \"lamp\": \"tfc:blocks/lamp_unlit\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"variants\": {\n" +
                "    \"normal\": [\n" +
                "      {}\n" +
                "    ],\n" +
                "    \"lit\": {\n" +
                "      \"true\": {\n" +
                "        \"textures\": {\n" +
                "          \"lamp\": \"tfc:blocks/lamp_lit\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"false\": {}\n" +
                "    },\n" +
                "    \"facing\": {\n" +
                "      \"up\": {},\n" +
                "      \"down\": {\n" +
                "        \"model\": \"tfc:lamp/down\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
        return lines;
    }


    //Trapdoor blockstate

    private static void writeTrapdoorBlockstateJSONFile(File file, String metalName) {
        File trapdoorJSON = createFile(file, metalName + ".json");
        if (trapdoorJSON == null) {
            return;
        }
        try {
            Files.write(trapdoorJSON.toPath(), createTrapdoorBlockstateJSONText(metalName));
        } catch (IOException e) {
            TFCLog.logger.error("Could not generate trapdoor blockstate JSON file for " + metalName, e);
        }
    }

    //Ugly? Yes. Works? Also yes.
    private static List<String> createTrapdoorBlockstateJSONText(String metalName) {
        List<String> lines = new ArrayList<>(3);
        lines.add("{\n" +
                "  \"forge_marker\": 1,\n" +
                "  \"defaults\": {\n" +
                "    \"textures\": {");
        lines.add("      \"texture\": \"tfc:blocks/trapdoor/" + metalName + "\"");
        lines.add("    }\n" +
                "  },\n" +
                "  \"variants\": {\n" +
                "    \"facing=north,half=bottom,open=false\": {\"model\": \"minecraft:iron_trapdoor_bottom\"},\n" +
                "    \"facing=south,half=bottom,open=false\": {\"model\": \"minecraft:iron_trapdoor_bottom\"},\n" +
                "    \"facing=east,half=bottom,open=false\": {\"model\": \"minecraft:iron_trapdoor_bottom\"},\n" +
                "    \"facing=west,half=bottom,open=false\": {\"model\": \"minecraft:iron_trapdoor_bottom\"},\n" +
                "    \"facing=north,half=top,open=false\": {\"model\": \"minecraft:iron_trapdoor_top\"},\n" +
                "    \"facing=south,half=top,open=false\": {\"model\": \"minecraft:iron_trapdoor_top\"},\n" +
                "    \"facing=east,half=top,open=false\": {\"model\": \"minecraft:iron_trapdoor_top\"},\n" +
                "    \"facing=west,half=top,open=false\": {\"model\": \"minecraft:iron_trapdoor_top\"},\n" +
                "    \"facing=north,half=bottom,open=true\": {\"model\": \"minecraft:iron_trapdoor_open\"},\n" +
                "    \"facing=south,half=bottom,open=true\": {\"model\": \"minecraft:iron_trapdoor_open\", \"y\": 180},\n" +
                "    \"facing=east,half=bottom,open=true\": {\"model\": \"minecraft:iron_trapdoor_open\", \"y\": 90},\n" +
                "    \"facing=west,half=bottom,open=true\": {\"model\": \"minecraft:iron_trapdoor_open\", \"y\": 270},\n" +
                "    \"facing=north,half=top,open=true\": {\"model\": \"minecraft:iron_trapdoor_open\"},\n" +
                "    \"facing=south,half=top,open=true\": {\"model\": \"minecraft:iron_trapdoor_open\", \"y\": 180},\n" +
                "    \"facing=east,half=top,open=true\": {\"model\": \"minecraft:iron_trapdoor_open\", \"y\": 90},\n" +
                "    \"facing=west,half=top,open=true\": {\"model\": \"minecraft:iron_trapdoor_open\", \"y\": 270}\n" +
                "  }\n" +
                "}");
        return lines;
    }


    //Fluid blockstate

    private static void writeFluidBlockstateJSONFile(File file, String metalName) {
        File fluidJSON = createFile(file, metalName + ".json");
        if (fluidJSON == null) {
            return;
        }
        try {
            Files.write(fluidJSON.toPath(), createFluidBlockstateJSONText(metalName));
        } catch (IOException e) {
            TFCLog.logger.error("Could not generate fluid blockstate JSON file for " + metalName, e);
        }
    }

    private static List<String> createFluidBlockstateJSONText(String metalName) {
        List<String> lines = new ArrayList<>(3);
        lines.add("{\n" +
                "  \"forge_marker\": 1,\n" +
                "  \"defaults\": {\n" +
                "    \"model\": \"forge:fluid\",\n" +
                "    \"textures\": {}\n" +
                "  },\n" +
                "  \"variants\": {\n" +
                "    \"normal\": {\n" +
                "      \"transform\": \"forge:default-item\",\n" +
                "      \"custom\": {");
        lines.add("        \"fluid\": \"" + metalName + "\"");
        lines.add("      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
        return lines;
    }

    //Anvil model

    private static void writeAnvilModelJSONFile(File file, String metalName) {
        File anvilJSON = createFile(file, metalName + ".json");
        if (anvilJSON == null) {
            return;
        }
        try {
            Files.write(anvilJSON.toPath(), createAnvilModelJSONText(metalName));
        } catch (IOException e) {
            TFCLog.logger.error("Could not generate anvil model JSON file for " + metalName, e);
        }
    }

    private static List<String> createAnvilModelJSONText(String metalName) {
        List<String> lines = new ArrayList<>(3);
        lines.add("{\n" +
                "  \"parent\": \"tfc:item/metal/anvil/transformations\",\n" +
                "  \"textures\": {");
        lines.add("    \"all\": \"tfc:blocks/metal/" + metalName + "\"");
        lines.add("  }\n" +
                "}");
        return lines;
    }

    //Anvil blockstate

    private static void writeAnvilBlockstateJSONFile(File file, String metalName) {
        File anvilJSON = createFile(file, metalName + ".json");
        if (anvilJSON == null) {
            return;
        }
        try {
            Files.write(anvilJSON.toPath(), createAnvilBlockstateJSONText(metalName));
        } catch (IOException e) {
            TFCLog.logger.error("Could not generate anvil blockstate JSON file for " + metalName, e);
        }
    }

    private static List<String> createAnvilBlockstateJSONText(String metalName) {
        List<String> lines = new ArrayList<>(4);
        lines.add("{\n" +
                "  \"forge_marker\": 1,\n" +
                "  \"defaults\": {\n" +
                "    \"model\": \"tfc:anvil\",\n" +
                "    \"textures\": {");
        lines.add("      \"all\": \"tfc:blocks/metal/" + metalName + "\",");
        lines.add("      \"particle\": \"tfc:blocks/metal/" + metalName + "\"");
        lines.add("    }\n" +
                "  },\n" +
                "  \"variants\": {\n" +
                "    \"normal\": [\n" +
                "      {}\n" +
                "    ],\n" +
                "    \"axis\": {\n" +
                "      \"north\": {\n" +
                "        \"y\": 180\n" +
                "      },\n" +
                "      \"east\": {\n" +
                "        \"y\": 270\n" +
                "      },\n" +
                "      \"south\": {},\n" +
                "      \"west\": {\n" +
                "        \"y\": 90\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
        return lines;
    }


    //Shield model

    private static void writeShieldModelJSONFile(ClassLoader classLoader, File file, String metalName, String baseItemModel) {
        File shieldJSON = createFile(file, metalName + ".json");
        if (shieldJSON == null) {
            return;
        }
        try {
            FileHelper.copyInputToFileAndReplace(classLoader.getResource("assets/tfcloader/models/item/metal/shield/" + baseItemModel + ".json"), shieldJSON, "<$metalName>", metalName);
            //Files.write(shieldJSON.toPath(), createShieldModelJSONText(metalName));
        } catch (IOException e) {
            TFCLog.logger.error("Could not generate shield model JSON file for " + metalName, e);
        }

        File shieldBlockingJSON = createFile(file, metalName + "_blocking.json");
        if (shieldBlockingJSON == null) {
            return;
        }
        try {
            FileHelper.copyInputToFileAndReplace(classLoader.getResource("assets/tfcloader/models/item/metal/shield/" + baseItemModel + "_blocking.json"), shieldBlockingJSON, "<$metalName>", metalName);
            //Files.write(shieldBlockingJSON.toPath(), createShieldBlockingModelJSONText(metalName));
        } catch (IOException e) {
            TFCLog.logger.error("Could not generate shield blocking model JSON file for " + metalName, e);
        }
    }

}


