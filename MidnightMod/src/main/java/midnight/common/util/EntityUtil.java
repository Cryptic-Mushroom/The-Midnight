package midnight.common.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EntityUtil {
    private static final Map<Class<? extends LivingEntity>, Stance> STANCES = new HashMap<>();

    public static void register() {
        registerStance(SpiderEntity.class, Stance.QUADRUPEDAL);
        registerStance(CaveSpiderEntity.class, Stance.QUADRUPEDAL);
    }

    public static void registerStance(Class<? extends LivingEntity> entity, Stance stance) {
        STANCES.put(entity, stance);
    }

    public static Stance getStance(LivingEntity entity) {
        Stance registeredStance = STANCES.get(entity.getClass());
        if (registeredStance != null) {
            return registeredStance;
        }
        return guessStance(entity);
    }

    private static Stance guessStance(LivingEntity entity) {
        if (entity instanceof AnimalEntity) {
            return Stance.QUADRUPEDAL;
        } else if (entity instanceof MonsterEntity) {
            return Stance.BIPEDAL;
        }
        float height = Math.max(entity.getHeight(), entity.getEyeHeight());
        return height > entity.getWidth() ? Stance.BIPEDAL : Stance.QUADRUPEDAL;
    }

    public static boolean isCoveredBy(LivingEntity entity, IArmorMaterial material) {
        for (ItemStack armorStack : entity.getArmorInventoryList()) {
            if (armorStack.isEmpty()) {
                return false;
            }
            Item armorItem = armorStack.getItem();
            if (armorItem instanceof ArmorItem) {
                if (material != ((ArmorItem) armorItem).getArmorMaterial()) {
                    return false;
                }
            }
        }
        return true;
    }

    public enum Stance {
        BIPEDAL,
        QUADRUPEDAL,
        NONE
    }
}
