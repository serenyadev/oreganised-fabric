package galena.oreganized.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import galena.oreganized.utils.CustomModelArmor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin {

    @Shadow protected abstract void setPartVisibility(HumanoidModel<?> model, EquipmentSlot slot);

    @Shadow protected abstract boolean usesInnerModel(EquipmentSlot slot);

    @Shadow protected abstract void renderTrim(ArmorMaterial armorMaterial, PoseStack poseStack, MultiBufferSource buffer, int packedLight, ArmorTrim trim, HumanoidModel<?> model, boolean innerTexture);

    @Shadow protected abstract void renderGlint(PoseStack poseStack, MultiBufferSource buffer, int packedLight, HumanoidModel<?> model);

    @Inject(
            method = "renderArmorPiece",
            at = @At("HEAD"),
            cancellable = true
    )
    public void renderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, LivingEntity entity, EquipmentSlot slot, int packedLight, HumanoidModel<?> model, CallbackInfo ci) {
        var self = (HumanoidArmorLayer) (Object) this;

        ItemStack itemStack = entity.getItemBySlot(slot);
        Item item = itemStack.getItem();
        if (item instanceof ArmorItem armorItem && item instanceof CustomModelArmor custom) {
            if (armorItem.getEquipmentSlot() == slot) {
                this.setPartVisibility(model, slot);
                boolean bl = this.usesInnerModel(slot);

                HumanoidModel<?> customModel = custom.getModelProvider().get().accept(entity, itemStack, slot, model);
                ((HumanoidModel) self.getParentModel()).copyPropertiesTo(customModel);

                oreganized$renderCustomModel(poseStack, buffer, packedLight, ResourceLocation.tryParse(custom.getArmorTexture(itemStack, entity, slot, null)), customModel, 1.0F, 1.0F, 1.0F);

                ArmorTrim.getTrim(entity.level().registryAccess(), itemStack).ifPresent((permutation) -> {
                    this.renderTrim(armorItem.getMaterial(), poseStack, buffer, packedLight, permutation, customModel, bl);
                });
                if (itemStack.hasFoil()) {
                    this.renderGlint(poseStack, buffer, packedLight, customModel);
                }

                ci.cancel();
            }
        }
    }

    @Unique
    public void oreganized$renderCustomModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ResourceLocation armorTexture, HumanoidModel<?> model, float red, float green, float blue) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(armorTexture));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }


}
