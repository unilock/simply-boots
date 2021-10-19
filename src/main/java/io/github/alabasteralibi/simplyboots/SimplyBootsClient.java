package io.github.alabasteralibi.simplyboots;

import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Environment(EnvType.CLIENT)
public class SimplyBootsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GeckoLib.initialize();
        register(SimplyBootsItems.HERMES_BOOTS, "hermes_boots");
        register(SimplyBootsItems.ROCKET_BOOTS, "rocket_boots");
        register(SimplyBootsItems.SPECTRE_BOOTS, "spectre_boots");
        register(SimplyBootsItems.LIGHTNING_BOOTS, "lightning_boots");
        register(SimplyBootsItems.FROSTSPARK_BOOTS, "frostspark_boots");
        register(SimplyBootsItems.WATER_WALKING_BOOTS, "water_walking_boots");
        register(SimplyBootsItems.OBSIDIAN_WATER_WALKING_BOOTS, "obsidian_water_walking_boots");
        register(SimplyBootsItems.LAVA_WADERS, "lava_waders");
        register(SimplyBootsItems.TERRASPARK_BOOTS, "terraspark_boots");
    }

    private static void register(Item item, String name) {
        GeoArmorRenderer.registerArmorRenderer(new BootRenderer(), item);
    }

    /*
    private static void register(Item item, String name) {
        ArmorRenderer.register((entity, stack, slot, defaultModel) -> new BootArmorModel(), item);
        ArmorRenderingRegistry.registerTexture((entity, stack, slot, secondLayer, suffix, defaultTexture) ->
                new Identifier("simplyboots", "textures/armor/" + name + ".png"), item);
    }
     */

    /* Might get implemented eventually, just gonna keep it here for now
    private static class ClassicBootArmorModel extends BipedEntityModel<LivingEntity> {
        ClassicBootArmorModel(float scale) {
            super(scale, 0, 16, 16);
            this.rightLeg = new ModelPart(this, 0, 0);
            this.rightLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1.0F);
            this.leftLeg = new ModelPart(this, 0, 0);
            this.leftLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 1.0F);
            this.leftLeg.mirror = true;
        }

        @Override
        protected Iterable<ModelPart> getBodyParts() {
            return ImmutableList.of(rightLeg, leftLeg);
        }

        @Override
        protected Iterable<ModelPart> getHeadParts() {
            return Collections::emptyIterator;
        }
    }


    private static class BootArmorModel extends BipedEntityModel<LivingEntity> {
        BootArmorModel() {
            super(1.0F);
            textureWidth = 16;
            textureHeight = 32;
            rightLeg = ModelPartBuilder.create().uv(0, 0).cuboid(-2.1F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.1F, 0.0F, 0.0F);
            rightLeg.setPivot(-1.9F, 12.0F, 0.0F);
            rightLeg.setTextureOffset(0, 10).addCuboid(-2.1F, 9.0F, -4.0F, 4.0F, 3.0F, 2.0F, 0.1F, 0.0F, 0.0F);

            ModelPart rightWing = new ModelPart(this);
            rightWing.setPivot(-3.4272F, 8.3136F, 1.7522F);
            rightLeg.addChild(rightWing);
            setRotationAngle(rightWing, 0.6109F, -0.3927F, -0.2618F);
            rightWing.setTextureOffset(0, 15).addCuboid(0.15F, -3.0F, -2.72F, 0.0F, 5.0F, 5.0F, 0.0F, false);

            leftLeg = new ModelPart(this);
            leftLeg.setPivot(1.9F, 12.0F, 0.0F);
            leftLeg.mirror = true;
            leftLeg.setTextureOffset(0, 0).addCuboid(-1.9F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.1F, 0.0F, 0.0F);
            leftLeg.setTextureOffset(0, 10).addCuboid(-1.9F, 9.0F, -4.0F, 4.0F, 3.0F, 2.0F, 0.1F, 0.0F, 0.0F);

            ModelPart leftWing = new ModelPart(this);
            leftWing.setPivot(3.4272F, 8.3136F, 1.7522F);
            leftLeg.addChild(leftWing);
            setRotationAngle(leftWing, 0.6109F, 0.3927F, 0.2618F);
            leftWing.setTextureOffset(0, 15).addCuboid(-0.15F, -3.0F, -2.72F, 0.0F, 5.0F, 5.0F, 0.0F, true);
        }



        @Override
        protected Iterable<ModelPart> getBodyParts() {
            return ImmutableList.of(rightLeg, leftLeg);
        }

        @Override
        protected Iterable<ModelPart> getHeadParts() {
            return Collections::emptyIterator;
        }

        @Override
        public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            rightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
            leftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        public void setRotationAngle(ModelPart bone, float x, float y, float z) {
            bone.pitch = x;
            bone.yaw = y;
            bone.roll = z;
        }
    }
        */
}
