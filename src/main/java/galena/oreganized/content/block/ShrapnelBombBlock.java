package galena.oreganized.content.block;

import galena.oreganized.content.entity.ShrapnelBomb;
import galena.oreganized.index.OSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ShrapnelBombBlock extends TntBlock {

    public ShrapnelBombBlock(Properties properties) {
        super(properties);
    }

    public void explode(Level world, BlockPos pos, @Nullable LivingEntity entity) {
        if (!world.isClientSide) {
            ShrapnelBomb shrapnelBomb = new ShrapnelBomb(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, entity);
            world.addFreshEntity(shrapnelBomb);
            world.playSound(null, shrapnelBomb.getX(), shrapnelBomb.getY(), shrapnelBomb.getZ(), OSoundEvents.SHRAPNEL_BOMB_PRIMED.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            world.gameEvent(entity, GameEvent.PRIME_FUSE, pos);
        }
    }
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!itemStack.is(Items.FLINT_AND_STEEL) && !itemStack.is(Items.FIRE_CHARGE)) {
            return super.use(state, level, pos, player, hand, hit);
        } else {
            explode(level, pos, player);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            Item item = itemStack.getItem();
            if (!player.isCreative()) {
                if (itemStack.is(Items.FLINT_AND_STEEL)) {
                    itemStack.hurtAndBreak(1, player, (playerx) -> {
                        playerx.broadcastBreakEvent(hand);
                    });
                } else {
                    itemStack.shrink(1);
                }
            }

            player.awardStat(Stats.ITEM_USED.get(item));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    public void wasExploded(Level world, BlockPos pos, Explosion explosion) {
        if (!world.isClientSide) {
            ShrapnelBomb shrapnelBomb = new ShrapnelBomb(world, (double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, explosion.getIndirectSourceEntity());
            int fuse = shrapnelBomb.getFuse();
            shrapnelBomb.setFuse((short)(world.random.nextInt(fuse / 4) + fuse / 8));
            world.addFreshEntity(shrapnelBomb);
        }
    }
}
