package io.github.alabasteralibi.simplyboots;

import io.github.alabasteralibi.simplyboots.items.BootItems;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BootModel extends AnimatedGeoModel<BootItems.BaseBootItem>
{
    @Override
    public Identifier getModelLocation(BootItems.BaseBootItem object)
    {
        return new Identifier("simplyboots", "geo/fancy_boots.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BootItems.BaseBootItem object)
    {
        return new Identifier("simplyboots", "textures/armor/" + object.toString() + ".png");
    }

    @Override
    public Identifier getAnimationFileLocation(BootItems.BaseBootItem animatable) {
        return null;
    }
}
