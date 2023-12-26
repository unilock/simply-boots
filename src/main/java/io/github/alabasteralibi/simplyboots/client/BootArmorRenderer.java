package io.github.alabasteralibi.simplyboots.client;

import io.github.alabasteralibi.simplyboots.items.BootItem;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BootArmorRenderer extends GeoArmorRenderer<BootItem> {
    public BootArmorRenderer() {
        super(new BootModel());
    }

    @Override
    public GeoBone getRightLegBone() {
        return this.model.getBone("rightLeg").orElse(null);
    }

    @Override
    public GeoBone getLeftLegBone() {
        return this.model.getBone("leftLeg").orElse(null);
    }

    @Override
    public GeoBone getRightBootBone() {
        return this.model.getBone("rightBoot").orElse(null);
    }

    @Override
    public GeoBone getLeftBootBone() {
        return this.model.getBone("leftBoot").orElse(null);
    }
}
