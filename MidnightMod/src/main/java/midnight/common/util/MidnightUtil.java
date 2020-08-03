package midnight.common.util;

import com.google.common.base.Preconditions;
import net.minecraftforge.common.util.FakePlayer;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Function;

public final class MidnightUtil {
    public static ResourceLocation transformPath(ResourceLocation identifier, Function<String, String> function) {
        Preconditions.checkNotNull(identifier);
        return new ResourceLocation(identifier.getNamespace(), function.apply(identifier.getPath()));
    }

    /*public static boolean isMidnightDimension(@Nullable World world) {
        if (world == null) return false;
        return world.func_230315_m_() == MidnightDimensions.MIDNIGHT;
    }*/

    public static boolean isNotFakePlayer(@Nullable Entity entity) {
        return !(entity instanceof FakePlayer);
    }

    public static float[] getRGBColorF(int color) {
        float[] rgb = new float[3];
        rgb[0] = ((color >> 16) & 255) / 255f;
        rgb[1] = ((color >> 8) & 255) / 255f;
        rgb[2] = (color & 255) / 255f;
        return rgb;
    }

    public static ItemEntity spawnItemStack(World world, Vector3d vector3d, Item block) {
        return spawnItemStack(world, vector3d.x + 0.5d, vector3d.y, vector3d.z + 0.5d, block.asItem());
    }

    public static ItemEntity spawnItemStack(World world, BlockPos pos, Block block) {
        return spawnItemStack(world, pos, block.asItem());
    }

    public static ItemEntity spawnItemStack(World world, BlockPos pos, Item item) {
        return spawnItemStack(world, pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d, item);
    }

    public static ItemEntity spawnItemStack(World world, double x, double y, double z, Item item) {
        return spawnItemStack(world, x, y, z, new ItemStack(item, 1));
    }

    public static ItemEntity spawnItemStack(World world, BlockPos pos, ItemStack stack) {
        return spawnItemStack(world, pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d, stack);
    }

    public static ItemEntity spawnItemStack(World world, double x, double y, double z, ItemStack stack) {
        ItemEntity itemEntity = new ItemEntity(world, x, y, z, stack);
        world.addEntity(itemEntity);
        return itemEntity;
    }

    public static Vector3d lerp(Vector3d a, Vector3d b, double alpha) {
        if (alpha <= 0.0) return a;
        if (alpha >= 1.0) return b;

        return new Vector3d(
                a.x + (b.x - a.x) * alpha,
                a.y + (b.y - a.y) * alpha,
                a.z + (b.z - a.z) * alpha
        );
    }
}
