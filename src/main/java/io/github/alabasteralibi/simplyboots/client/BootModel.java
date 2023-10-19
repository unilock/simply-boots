package io.github.alabasteralibi.simplyboots.client;

import io.github.alabasteralibi.simplyboots.SimplyBootsHelpers;
import io.github.alabasteralibi.simplyboots.items.BootItems;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BootModel extends GeoModel<BootItems.BaseBootItem> {
    @Override
    public Identifier getModelResource(BootItems.BaseBootItem animatable) {
        return SimplyBootsHelpers.id("geo/fancy_boots.geo.json");
    }

    @Override
    public Identifier getTextureResource(BootItems.BaseBootItem animatable) {
        return SimplyBootsHelpers.id("textures/armor/" + animatable.toString() + ".png");
    }

    @Override
    public Identifier getAnimationResource(BootItems.BaseBootItem animatable) {
        return null;
    }
}
