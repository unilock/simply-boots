package io.github.alabasteralibi.simplyboots.client;

import io.github.alabasteralibi.simplyboots.SimplyBootsHelpers;
import io.github.alabasteralibi.simplyboots.items.BootItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BootModel extends GeoModel<BootItem> {
    @Override
    public Identifier getModelResource(BootItem animatable) {
        return SimplyBootsHelpers.id("geo/fancy_boots.geo.json");
    }

    @Override
    public Identifier getTextureResource(BootItem animatable) {
        return SimplyBootsHelpers.id("textures/armor/" + animatable.toString() + ".png");
    }

    @Override
    public Identifier getAnimationResource(BootItem animatable) {
        return null;
    }
}
