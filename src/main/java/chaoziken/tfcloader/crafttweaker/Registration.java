package chaoziken.tfcloader.crafttweaker;

import chaoziken.tfcloader.util.TFCLog;
import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class Registration {

    @SubscribeEvent
    public static void onPreRegisterRockCategory(TFCRegistryEvent.RegisterPreBlock<RockCategory> event) {
        for (RockCategory rockCategory : RegistryLists.rockCategoryRegistry) {
            event.getRegistry().register(rockCategory);
        }
    }

    @SubscribeEvent
    public static void onPreRegisterRock(TFCRegistryEvent.RegisterPreBlock<Rock> event) {
        for (Rock rock : RegistryLists.rockRegistry) {
            event.getRegistry().register(rock);
        }
    }

    @SubscribeEvent
    public static void onPreRegisterMetal(TFCRegistryEvent.RegisterPreBlock<Metal> event) {
        TFCLog.logger.info("Number of metal registry entries: " + RegistryLists.metalRegistry.size());
        for (Metal metal : RegistryLists.metalRegistry) {
            event.getRegistry().register(metal);
        }
    }

    @SubscribeEvent
    public static void onPreRegisterOre(TFCRegistryEvent.RegisterPreBlock<Ore> event) {
        for (Ore ore : RegistryLists.oreRegistry) {
            event.getRegistry().register(ore);
        }
    }

}
