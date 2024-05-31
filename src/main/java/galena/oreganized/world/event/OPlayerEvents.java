package galena.oreganized.world.event;

import galena.oreganized.OreganizedConfig;
import galena.oreganized.content.block.MoltenLeadCauldronBlock;
import galena.oreganized.index.OBlocks;
import galena.oreganized.index.OEffects;
import galena.oreganized.index.OItems;
import galena.oreganized.index.OTags;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class OPlayerEvents {

    public static void register() {
        UseBlockCallback.EVENT.register(OPlayerEvents::blockItemInteractions);
    }

    public static InteractionResult blockItemInteractions(Player player, Level world, InteractionHand hand, BlockHitResult hit) {
        BlockPos pos = hit.getBlockPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = player.getItemInHand(hand);

        if(stack.getItem() instanceof AxeItem) {
            Block unwaxed = OBlocks.WAXED_BLOCKS.get(state.getBlock());
            if(unwaxed != null) {
                world.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                world.levelEvent(player, 3004, pos, 0);

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, pos, stack);
                }

                world.setBlock(pos, unwaxed.defaultBlockState(), 11);
                world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, unwaxed.defaultBlockState()));
                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));

                return InteractionResult.sidedSuccess(world.isClientSide);
            }
        }

        // Waxing (Using Honeycomb on a waxable block).
        if (stack.is(Items.HONEYCOMB) && OBlocks.WAXED_BLOCKS.inverse().get(state.getBlock()) != null) {

            if (player instanceof ServerPlayer) CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);

            player.swing(hand);
            if (!player.isCreative()) stack.shrink(1);
            Block waxedBlock = OBlocks.WAXED_BLOCKS.inverse().get(state.getBlock());
            if (!world.isClientSide() && waxedBlock != null) world.setBlock(pos, waxedBlock.defaultBlockState(), 11);
            world.levelEvent(player, 3003, pos, 0);
        }

        if (stack.is(Items.MUSIC_DISC_11) && state.is(OBlocks.MOLTEN_LEAD_CAULDRON.get())) {
            if (!state.getValue(MoltenLeadCauldronBlock.AGE).equals(3)) return InteractionResult.PASS;
            ItemStack newDisc = new ItemStack(OItems.MUSIC_DISC_STRUCTURE.get());

            player.swing(hand);
            if (!player.isCreative()) stack.shrink(1);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!world.isClientSide()) player.awardStat(Stats.ITEM_USED.get(stack.getItem()));

            if (stack.isEmpty()) {
                player.setItemInHand(hand, newDisc);
                return InteractionResult.PASS;
            }
            if (!player.getInventory().add(newDisc)) {
                player.drop(newDisc, false);
                //return;
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }


    public static void onFinishEating(LivingEntity entity, ItemStack stack) {
        if (stack.isEdible()) {
            boolean leadPoisoning = false; // TODO entity.isInFluidType(OFluids.MOLTEN_LEAD_TYPE.get());
            if (entity instanceof Player player) {
                for (int i = 0; i < 9; i++) {
                    if (player.getInventory().items.get(i).is(OTags.Items.LEAD_SOURCE))
                        leadPoisoning = true;
                }
            }
            if ((entity.getOffhandItem().is(OTags.Items.LEAD_SOURCE) || leadPoisoning) && OreganizedConfig.COMMON.leadPoisining.get()) {
                if (OreganizedConfig.stunningFromConfig()) entity.addEffect(new MobEffectInstance(OEffects.STUNNING.get(), 40 * 20));
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 200));
            }
        }
    }
}
