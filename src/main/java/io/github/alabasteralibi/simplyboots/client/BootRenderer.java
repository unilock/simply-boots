package io.github.alabasteralibi.simplyboots.client;

import io.github.alabasteralibi.simplyboots.items.BootItems;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class BootRenderer extends GeoArmorRenderer<BootItems.BaseBootItem> {
    public BootRenderer() {
        super(new BootModel());

        this.headBone = null;
        this.bodyBone = null;
        this.rightArmBone = null;
        this.leftArmBone = null;
        this.rightLegBone = "rightLeg";
        this.leftLegBone = "leftLeg";
        this.rightBootBone = "rightBoot";
        this.leftBootBone = "leftBoot";
    }
}
