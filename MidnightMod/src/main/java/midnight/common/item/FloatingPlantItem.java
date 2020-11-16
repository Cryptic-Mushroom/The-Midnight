/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 21
 */

package midnight.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;

public class FloatingPlantItem extends BlockItem {
   public FloatingPlantItem(Block block, Item.Properties props) {
      super(block, props);
   }

   @Override
   public ActionResultType onItemUse(ItemUseContext ctx) {
      return ActionResultType.PASS;
   }

   @Override
   public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
      // Raytrace with fluid blocks
      BlockRayTraceResult rtr = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
      rtr = rtr.withBlockPos(rtr.getPos().up()); // withPos - it returns a new RayTraceResult with a different position

      ActionResultType result = super.onItemUse(new ItemUseContext(player, hand, rtr));
      return new ActionResult<>(result, player.getHeldItem(hand));
   }
}
