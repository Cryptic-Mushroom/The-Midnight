/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.common.item;

import midnight.common.block.MnBlocks;
import midnight.common.block.SuavisBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Item resembling a raw suavis slice. Can be eaten, cooked or be placed on the ground to grow more suavis. This is an
 * alternative item for placing suavis, unlike the block item it places only one slice of suavis.
 *
 * @author Shadew
 * @since 0.6.0
 */
public class RawSuavisItem extends BlockItem {
    public RawSuavisItem(Properties props) {
        super(MnBlocks.SUAVIS, props);
    }

    // Enchanted glint on suavis - just for the effect, it's not really enchanted
    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    // Place stage 0 suavis - not stage 3 as the suavis block item does
    @Nullable
    @Override
    protected BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = MnBlocks.SUAVIS.getDefaultState().with(SuavisBlock.STAGE, 0);
        return canPlace(context, state) ? state : null;
    }

    // BlockItem adds the item mapped to the block to the item group, but since we are not mapped to suavis we have
    // to add ourselves
    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> list) {
        if (isInGroup(group)) {
            list.add(new ItemStack(this));
        }
    }

    // Make sure we do not add this item to the block-to-item-map, since suavis as a block is already in here,
    // mapped to its own block item
    @Override
    public void addToBlockToItemMap(Map<Block, Item> map, Item item) {
    }

    @Override
    public void removeFromBlockToItemMap(Map<Block, Item> map, Item item) {
    }

    // Use item translation key, BlockItem use block's translation key by default
    @Override
    public String getTranslationKey() {
        return getDefaultTranslationKey();
    }
}
