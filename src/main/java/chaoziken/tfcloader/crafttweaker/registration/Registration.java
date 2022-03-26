package chaoziken.tfcloader.crafttweaker.registration;

import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.*;
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

    @SubscribeEvent
    public static void onPreRegisterTree(TFCRegistryEvent.RegisterPreBlock<Tree> event) {
        for (Tree tree : RegistryLists.treeRegistry) {
            event.getRegistry().register(tree);
        }
    }

    @SubscribeEvent
    public static void onPreRegisterPlant(TFCRegistryEvent.RegisterPreBlock<Plant> event) {
        for (Plant plant : RegistryLists.plantRegistry) {
            event.getRegistry().register(plant);
        }
    }

}
