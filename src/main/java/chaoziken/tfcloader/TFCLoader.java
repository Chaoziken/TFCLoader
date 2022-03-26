package chaoziken.tfcloader;

import chaoziken.tfcloader.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TFCLoader.MODID, name = TFCLoader.MODNAME, version = TFCLoader.MODVERSION, dependencies = TFCLoader.MODDEPS, useMetadata = true)
public class TFCLoader {

    public static final String MODID = "tfcloader";
    public static final String MODNAME = "TerraFirmaCraft Loader";
    public static final String MODVERSION = "0.0.1";
    public static final String MODDEPS = "required-after:forge@[14.23.5.2847,);required-after:tfc;required-after:crafttweaker@[4.1.11,)";

    @SidedProxy(clientSide = "chaoziken.tfcloader.proxy.ClientProxy", serverSide = "chaoziken.tfcloader.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static TFCLoader instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

}
