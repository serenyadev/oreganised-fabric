package galena.oreganized.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.armortrim.ArmorTrim;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HumanoidArmorLayer.class)
public interface HumanoidArmorLayerAccessor {

    @Invoker("getArmorModel")
    HumanoidModel<?> invokeGetArmorModel(EquipmentSlot slot);

    @Invoker("renderTrim")
    void invokeRenderTrim(ArmorMaterial armorMaterial, PoseStack poseStack, MultiBufferSource buffer, int packedLight, ArmorTrim trim, HumanoidModel<?> model, boolean innerTexture);


    @Invoker
    void invokeSetPartVisibility(HumanoidModel<? extends LivingEntity> p_117126_, EquipmentSlot p_117127_);

    @Invoker
    boolean invokeUsesInnerModel(EquipmentSlot p_117129_);


    @Invoker("getArmorLocation")
    ResourceLocation invokeGetArmorResource(ArmorItem armorItem, boolean layer2, @Nullable String suffi);

    @Invoker("renderModel")
    void invokeRenderModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ArmorItem armorItem, HumanoidModel<?> model, boolean withGlint, float red, float green, float blue, @Nullable String armorSuffix);

    @Invoker("renderGlint")
    void invokeRenderGlint(PoseStack p_289673_, MultiBufferSource p_289654_, int p_289649_, HumanoidModel<?> p_289659_);

}
