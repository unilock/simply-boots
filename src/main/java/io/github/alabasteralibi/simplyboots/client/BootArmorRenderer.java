package io.github.alabasteralibi.simplyboots.client;

import io.github.alabasteralibi.simplyboots.items.BootItem;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BootArmorRenderer extends GeoArmorRenderer<BootItem> {
    public BootArmorRenderer() {
        super(new BootModel());
    }

    @Override
    public GeoBone getRightLegBone(GeoModel<BootItem> model) {
        return model.getBone("rightLeg").orElse(null);
    }

    @Override
    public GeoBone getLeftLegBone(GeoModel<BootItem> model) {
        return model.getBone("leftLeg").orElse(null);
    }

    @Override
    public GeoBone getRightBootBone(GeoModel<BootItem> model) {
        return model.getBone("rightBoot").orElse(null);
    }

    @Override
    public GeoBone getLeftBootBone(GeoModel<BootItem> model) {
        return model.getBone("leftBoot").orElse(null);
    }
}
