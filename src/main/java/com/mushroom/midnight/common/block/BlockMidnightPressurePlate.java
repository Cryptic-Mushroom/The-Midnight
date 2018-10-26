package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMidnightPressurePlate extends BlockPressurePlate implements IModelProvider {

    private final SoundEvent clickOnSound;
    private final SoundEvent clickOffSound;
    private final boolean highPitch;

    /**
     * Assumes nothing. You must provide a material, pressure sensitivity, sound
     * type, click on sound, click off sound, and high pitch specification for this
     * one to work.
     */
    public BlockMidnightPressurePlate(Material materialIn, Sensitivity sensitivityIn, SoundType soundTypeIn, SoundEvent clickOnSoundIn, SoundEvent clickOffSoundIn, boolean highPitch) {

        super(materialIn, sensitivityIn);
        this.setCreativeTab(Midnight.MIDNIGHT_TAB);
        this.setSoundType(soundTypeIn);
        this.clickOnSound = clickOnSoundIn;
        this.clickOffSound = clickOffSoundIn;
        this.highPitch = highPitch;

    }

    /**
     * If you want to be simple, use this. Assumes you want either a wooden or a
     * stone pressure plate.
     */
    public BlockMidnightPressurePlate(boolean wooden) {

        this(wooden ? Material.WOOD : Material.ROCK, wooden ? Sensitivity.EVERYTHING : Sensitivity.MOBS, wooden ? SoundType.WOOD : SoundType.STONE, wooden ? SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_ON : SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_ON, wooden ? SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_OFF : SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_OFF, wooden);

    }

    @Override
    protected void playClickOnSound(World worldIn, BlockPos pos) {

        worldIn.playSound((EntityPlayer) null, pos, this.clickOnSound, SoundCategory.BLOCKS, 0.3F, this.highPitch ? 0.8F : 0.6F);

    }

    @Override
    protected void playClickOffSound(World worldIn, BlockPos pos) {

        worldIn.playSound((EntityPlayer) null, pos, this.clickOffSound, SoundCategory.BLOCKS, 0.3F, this.highPitch ? 0.7F : 0.5F);

    }

}
