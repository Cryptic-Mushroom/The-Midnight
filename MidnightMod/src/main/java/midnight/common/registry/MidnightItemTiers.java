package midnight.common.registry;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import midnight.common.block.MnBlocks;
import midnight.common.item.MnItems;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class MidnightItemTiers {
    public static final IItemTier SHADOWROOT = new Builder()
            .withHarvestLevel(0)
            .withMaxUses(59)
            .withEfficiency(2.0F)
            .withAttackDamage(0.0F)
            .withEnchantability(14)
            .withRepairMaterial(() -> Ingredient.fromItems(MnBlocks.SHADOWROOT_PLANKS))
            .build();

    public static final IItemTier NIGHTSTONE = new Builder()
            .withHarvestLevel(1)
            .withMaxUses(150)
            .withEfficiency(4.0F)
            .withAttackDamage(1.0F)
            .withEnchantability(5)
            .withRepairMaterial(() -> Ingredient.fromItems(MnBlocks.NIGHTSTONE))
            .build();

    public static final IItemTier EBONITE = new Builder()
            .withHarvestLevel(2)
            .withMaxUses(350)
            .withEfficiency(6.0F)
            .withAttackDamage(2.0F)
            .withEnchantability(8)
            .withRepairMaterial(() -> Ingredient.fromItems(MnItems.EBONITE))
            .build();

    public static final IItemTier NAGRILITE = new Builder()
            .withHarvestLevel(3)
            .withMaxUses(850)
            .withEfficiency(3.0F)
            .withAttackDamage(3.0F)
            .withEnchantability(10)
            .withRepairMaterial(() -> Ingredient.fromItems(MnItems.NAGRILITE_INGOT))
            .build();

    public static final IItemTier TENEBRUM = new Builder()
            .withHarvestLevel(3)
            .withMaxUses(1561)
            .withEfficiency(10.0F)
            .withAttackDamage(4.0F)
            .withEnchantability(10)
            .withRepairMaterial(() -> Ingredient.fromItems(MnItems.TENEBRUM_INGOT))
            .build();

    private static class Builder {
        private int maxUses;
        private float efficiency;
        private float attackDamage;
        private int harvestLevel;
        private int enchantability;
        private Supplier<Ingredient> repairMaterial;

        public Builder withMaxUses(int maxUses) {
            this.maxUses = maxUses;
            return this;
        }

        public Builder withEfficiency(float efficiency) {
            this.efficiency = efficiency;
            return this;
        }

        public Builder withAttackDamage(float attackDamage) {
            this.attackDamage = attackDamage;
            return this;
        }

        public Builder withHarvestLevel(int harvestLevel) {
            this.harvestLevel = harvestLevel;
            return this;
        }

        public Builder withEnchantability(int enchantability) {
            this.enchantability = enchantability;
            return this;
        }

        public Builder withRepairMaterial(Supplier<Ingredient> repairMaterial) {
            this.repairMaterial = repairMaterial;
            return this;
        }

        public IItemTier build() {
            return new IItemTier() {
                private Ingredient repairMaterial;

                @Override
                public int getMaxUses() {
                    return Builder.this.maxUses;
                }

                @Override
                public float getEfficiency() {
                    return Builder.this.efficiency;
                }

                @Override
                public float getAttackDamage() {
                    return Builder.this.attackDamage;
                }

                @Override
                public int getHarvestLevel() {
                    return Builder.this.harvestLevel;
                }

                @Override
                public int getEnchantability() {
                    return Builder.this.enchantability;
                }

                @Override
                @Nonnull
                public Ingredient getRepairMaterial() {
                    if (this.repairMaterial == null) {
                        this.repairMaterial = Builder.this.repairMaterial.get();
                    }
                    return this.repairMaterial;
                }
            };
        }
    }
}
