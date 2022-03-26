package chaoziken.tfcloader.crafttweaker;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;

import java.util.List;

public class RegistryLists {

    public static final List<RockCategory> rockCategoryRegistry = new ObjectArrayList<>();
    public static final List<Rock> rockRegistry = new ObjectArrayList<>();
    public static final List<Metal> metalRegistry = new ObjectArrayList<>();
    public static final List<Ore> oreRegistry = new ObjectArrayList<>();

}
