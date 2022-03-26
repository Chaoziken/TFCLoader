package chaoziken.tfcloader.crafttweaker.util;

import net.dries007.tfc.api.util.ITreeGenerator;
import net.dries007.tfc.types.DefaultTrees;

@SuppressWarnings("unused")
public enum TreeGeneratorTypes {

    GEN_NORMAL(DefaultTrees.GEN_NORMAL),
    GEN_MEDIUM(DefaultTrees.GEN_MEDIUM),
    GEN_TALL(DefaultTrees.GEN_TALL),
    GEN_CONIFER(DefaultTrees.GEN_CONIFER),
    GEN_TROPICAL(DefaultTrees.GEN_TROPICAL),
    GEN_WILLOW(DefaultTrees.GEN_WILLOW),
    GEN_ACACIA(DefaultTrees.GEN_ACACIA),
    GEN_KAPOK(DefaultTrees.GEN_KAPOK),
    GEN_SEQUOIA(DefaultTrees.GEN_SEQUOIA),
    GEN_KAPOK_COMPOSITE(DefaultTrees.GEN_KAPOK_COMPOSITE),
    GEN_BUSHES(DefaultTrees.GEN_BUSHES);

    TreeGeneratorTypes(ITreeGenerator inner) {
        this.inner = inner;
    }

    private final ITreeGenerator inner;

    public ITreeGenerator getInner() {
        return inner;
    }
}
