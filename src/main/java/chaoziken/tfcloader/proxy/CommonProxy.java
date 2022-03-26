package chaoziken.tfcloader.proxy;

import chaoziken.tfcloader.crafttweaker.registration.Registration;
import chaoziken.tfcloader.util.FileHelper;
import crafttweaker.CraftTweakerAPI;
import net.dries007.tfc.TerraFirmaCraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {

    private static File tfcResourceDir;

    public void preInit(FMLPreInitializationEvent e) {
        File tfcConfigDir = FileHelper.createDir(e.getModConfigurationDirectory(), TerraFirmaCraft.MOD_ID);
        if (tfcConfigDir != null) {
            FileHelper.createBlankJSONFile(tfcConfigDir, "tfcloader_ore_spawn_data.json");
        }

        tfcResourceDir = FileHelper.createDir(FileHelper.createDir(e.getModConfigurationDirectory().getParentFile(), "resources"), "tfc");

        MinecraftForge.EVENT_BUS.register(Registration.class);

        CraftTweakerAPI.tweaker.loadScript(false, "tfcloader");
    }

    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {

    }

    public static File getTfcResourceDir() {
        return tfcResourceDir;
    }
}
