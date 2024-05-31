package galena.oreganized.content.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import galena.oreganized.Oreganized;
import galena.oreganized.client.model.ElectrumArmorModel;
import galena.oreganized.index.OArmorMaterials;
import galena.oreganized.utils.CustomModelArmor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Supplier;

public class ElectrumArmorItem extends ArmorItem implements CustomModelArmor {
    private static final String TEXTURE = Oreganized.MOD_ID + ":textures/entity/electrum_armor.png";

    public ElectrumArmorItem(Type slot) {
        super(OArmorMaterials.ELECTRUM, slot, new Properties());
    }


    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        if(slot == this.getEquipmentSlot()) {
            UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(this.getType());
            return ImmutableMultimap.of(
                    Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Electrum speed", 0.05, AttributeModifier.Operation.MULTIPLY_BASE),
                    Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", OArmorMaterials.ELECTRUM.getDefenseForType(this.type), AttributeModifier.Operation.ADDITION),
                    Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", OArmorMaterials.ELECTRUM.getToughness(), AttributeModifier.Operation.ADDITION),
                    Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", OArmorMaterials.ELECTRUM.getKnockbackResistance(), AttributeModifier.Operation.ADDITION)
            );
        }
        return super.getDefaultAttributeModifiers(slot);
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return TEXTURE;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Supplier<ArmorModelProvider> getModelProvider() {
        return () -> ElectrumArmorModel::getModel;
    }
}
