package com.mushroom.midnight.common.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

import static com.mushroom.midnight.Midnight.MODID;

public class InBlockLootCondition implements LootCondition {
    private final Block block;

    public InBlockLootCondition(Block block) {
        this.block = block;
    }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        return context.getLootedEntity() != null && context.getWorld().getBlockState(context.getLootedEntity().getPosition()).getBlock() == block;
    }

    public static class Serialiser extends LootCondition.Serializer<InBlockLootCondition> {
        public Serialiser() {
            super(new ResourceLocation(MODID, "in_block"), InBlockLootCondition.class);
        }

        @Override
        public void serialize(JsonObject json, InBlockLootCondition value, JsonSerializationContext context) {
            json.addProperty("block", value.block.getRegistryName().toString());
        }

        @Override
        public InBlockLootCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            if (!json.has("block")) {
                throw new JsonSyntaxException("Missing block tag, expected to find a block registry name");
            }
            ResourceLocation rl = new ResourceLocation(json.get("block").getAsString());
            Block block = ForgeRegistries.BLOCKS.getValue(rl);
            if (block == null) {
                throw new JsonSyntaxException("Invalid block tag. " + rl + " does not exist in the block registry.");
            }
            return new InBlockLootCondition(block);
        }
    }
}
