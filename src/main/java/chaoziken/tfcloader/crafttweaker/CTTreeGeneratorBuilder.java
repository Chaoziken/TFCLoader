package chaoziken.tfcloader.crafttweaker;

import chaoziken.tfcloader.crafttweaker.util.TreeGeneratorTypes;
import crafttweaker.annotations.ZenRegister;
import net.dries007.tfc.api.util.ITreeGenerator;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenConstructor;

import java.util.Locale;

@ZenClass("mods.tfcloader.TreeGeneratorBuilder")
@ZenRegister
@SuppressWarnings("unused")
public class CTTreeGeneratorBuilder {

    private final ITreeGenerator inner;

    /**
     * Gets a tree generator defined by TFC. See {@link net.dries007.tfc.types.DefaultTrees} for names.
     * @param generatorName the name of the generator, without GEN_. In either snake_case or SCREAMING_SNAKE_CASE
     */
    @ZenConstructor
    public CTTreeGeneratorBuilder(String generatorName) {
        try {
            TreeGeneratorTypes generatorTypes = TreeGeneratorTypes.valueOf("GEN_" + generatorName.toUpperCase(Locale.ROOT));
            this.inner = generatorTypes.getInner();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("There is no tree generator with name " + generatorName);
        }
    }

    /**
     * TODO Custom generation constructor
     */
    @ZenConstructor
    public CTTreeGeneratorBuilder() {
        throw new IllegalStateException("Custom tree generation is not yet implemented!");
    }

    public ITreeGenerator getInner() {
        return inner;
    }

}
