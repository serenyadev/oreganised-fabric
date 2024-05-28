package galena.oreganized.utils;

import io.github.fabricators_of_create.porting_lib.item.ArmorTextureItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public interface CustomModelArmor extends ArmorTextureItem {

    @Environment(EnvType.CLIENT)
    Supplier<ArmorModelProvider> getModelProvider();

    interface ArmorModelProvider {
        HumanoidModel<?> accept(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> original);
    }

}
