package com.mushroom.midnight.common.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

import static com.mushroom.midnight.Midnight.MODID;

public class InBiomeLootCondition implements LootCondition {
    private final Biome requiredBiome;

    public InBiomeLootCondition(Biome requiredBiome) {
        this.requiredBiome = requiredBiome;
    }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        if (context.getLootedEntity() == null) {
            return false;
        }
        Biome biome = context.getWorld().getBiome(context.getLootedEntity().getPosition());
        return biome == requiredBiome;
    }

    public static class Serialiser extends LootCondition.Serializer<InBiomeLootCondition> {
        public Serialiser() {
            super(new ResourceLocation(MODID, "in_biome"), InBiomeLootCondition.class);
        }

        @Override
        public void serialize(JsonObject json, InBiomeLootCondition value, JsonSerializationContext context) {
            json.addProperty("biome", value.requiredBiome.getRegistryName().toString());
        }

        @Override
        public InBiomeLootCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            if (!json.has("biome")) {
                throw new JsonSyntaxException("Missing biome tag, expected to find a biome registry name");
            }
            ResourceLocation biomeResLoc = new ResourceLocation(json.get("biome").getAsString());
            Biome biome = ForgeRegistries.BIOMES.getValue(biomeResLoc);
            if (biome == null) {
                throw new JsonSyntaxException("Invalid biome tag. " + biomeResLoc + " does not exist in the biome registry.");
            }
            return new InBiomeLootCondition(biome);
        }
    }
}
