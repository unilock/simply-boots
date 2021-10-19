package io.github.alabasteralibi.simplyboots;

import io.github.alabasteralibi.simplyboots.items.BootItems;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BootModel extends AnimatedGeoModel<BootItems.BaseBootItem>
{
    @Override
    public Identifier getModelLocation(BootItems.BaseBootItem object)
    {
        return new Identifier("simplyboots", "geo/player_armor.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BootItems.BaseBootItem object)
    {
        return new Identifier("simplyboots", "textures/armor/frostspark_boots.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BootItems.BaseBootItem animatable) {
        return null;
    }
}
