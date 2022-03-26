package chaoziken.tfcloader.crafttweaker;

import chaoziken.tfcloader.TFCLoader;
import chaoziken.tfcloader.crafttweaker.util.defaults.DefaultTrees;
import chaoziken.tfcloader.crafttweaker.util.defaults.IDefaultType;
import crafttweaker.annotations.ZenRegister;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.api.util.ITreeGenerator;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenConstructor;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tfcloader.TreeBuilder")
@ZenRegister
@SuppressWarnings("unused")
public class CTTreeBuilder {

    private final ResourceLocation treeName;
    private Tree inner;
    private final ITreeGenerator generator;
    private int maxGrowthRadius;
    private float dominance;
    private int maxHeight;
    private int maxDecayDistance;
    private boolean isConifer;
    private ITreeGenerator bushGenerator;
    private boolean canMakeTannin;
    private float minGrowthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;
    private float minDensity;
    private float maxDensity;
    private float burnTemp;
    private int burnTicks;

    /**
     * Builder for trees
     * @param treeName  the name of the tree
     * @param minRain   min rainfall
     * @param maxRain   max rainfall
     * @param minTemp   min temperature
     * @param maxTemp   max temperature
     * @param treeGeneratorBuilder a {@link CTTreeGeneratorBuilder} that describes how this tree generates
     */
    @ZenConstructor
    public CTTreeBuilder(String treeName, float minRain, float maxRain, float minTemp, float maxTemp, CTTreeGeneratorBuilder treeGeneratorBuilder) {
        CTRegistry.validateName(treeName, "TreeBuilder");

        IDefaultType defaultTrees = new DefaultTrees();
        defaultTrees.checkIfDefault(treeName);

        this.treeName = new ResourceLocation(TFCLoader.MODID, treeName);
        this.minRain = minRain;
        this.maxRain = maxRain;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.generator = treeGeneratorBuilder.getInner();

        //Defaults from TFC's Tree class
        this.maxGrowthRadius = 1;
        this.dominance = 0.001f * (maxTemp - minTemp) * (maxRain - minRain);
        this.maxHeight = 6;
        this.maxDecayDistance = 4;
        this.isConifer = false;
        this.bushGenerator = null;
        this.canMakeTannin = false;
        this.minGrowthTime = 7;
        this.minDensity = 0.1f;
        this.maxDensity = 2f;
        this.burnTemp = 675;
        this.burnTicks = 1500;
    }

    /**
     * Used to check growth conditions
     */
    @ZenMethod
    public CTTreeBuilder setRadius(int maxGrowthRadius) {
        this.maxGrowthRadius = maxGrowthRadius;
        return this;
    }

    /**
     * Maximum decay distance for leaves
     */
    @ZenMethod
    public CTTreeBuilder setDecayDist(int maxDecayDistance) {
        this.maxDecayDistance = maxDecayDistance;
        return this;
    }

    /**
     * NOTE: This is unused in base TFC
     */
    @ZenMethod
    public CTTreeBuilder setConifer() {
        isConifer = true;
        return this;
    }

    /**
     * Sets the bush generator to {@link net.dries007.tfc.types.DefaultTrees#GEN_BUSHES}
     */
    @ZenMethod
    public CTTreeBuilder setBushes() {
        bushGenerator = net.dries007.tfc.types.DefaultTrees.GEN_BUSHES;
        return this;
    }

    /**
     * Sets the bush generator
     * @param bushGenerator a {@link CTTreeGeneratorBuilder} describing how this tree will generate bushes
     */
    @ZenMethod
    public CTTreeBuilder setBushes(CTTreeGeneratorBuilder bushGenerator) {
        Validate.notNull(bushGenerator, "TreeGenerator for setBushes in TreeBuilder " + treeName.getPath() + " is null!");
        this.bushGenerator = bushGenerator.getInner();
        return this;
    }

    /**
     * Whether this tree's wood can be used to make tannin
     */
    @ZenMethod
    public CTTreeBuilder setTannin() {
        canMakeTannin = true;
        return this;
    }

    /**
     * Used to check growth conditions
     */
    @ZenMethod
    public CTTreeBuilder setHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    /**
     * The amount of time (in in-game days) that this tree requires to grow
     */
    @ZenMethod
    public CTTreeBuilder setGrowthTime(float growthTime) {
        this.minGrowthTime = growthTime;
        return this;
    }

    /**
     * Determines how dense this tree's generation is
     * @param min min density. "Use -1 to get all density values. 0.1 is the default, to create really low density regions of no trees"
     * @param max max density. "Use 2 to get all density values"
     */
    @ZenMethod
    public CTTreeBuilder setDensity(float min, float max) {
        this.minDensity = min;
        this.maxDensity = max;
        return this;
    }

    /**
     * "How much this tree is chosen over other trees. Range 0 <> 10 with 10 being the most common"
     */
    @ZenMethod
    public CTTreeBuilder setDominance(float dom) {
        this.dominance = dom;
        return this;
    }

    /**
     * Sets how this tree's wood behaves in a fire pit or similar
     * @param burnTemp the temperature at which this tree will burn
     * @param burnTicks the number of ticks this tree will burn
     */
    @ZenMethod
    public CTTreeBuilder setBurnInfo(float burnTemp, int burnTicks) {
        this.burnTemp = burnTemp;
        this.burnTicks = burnTicks;
        return this;
    }

    /**
     * Builds the tree. Use this LAST
     */
    @ZenMethod
    public CTTreeBuilder build() {
        this.inner = new Tree(treeName, generator, minTemp, maxTemp, minRain, maxRain, minDensity, maxDensity, dominance, maxGrowthRadius, maxHeight, maxDecayDistance, isConifer, bushGenerator, canMakeTannin, minGrowthTime, burnTemp, burnTicks);
        return this;
    }

    public Tree getInner() {
        return inner;
    }

}
