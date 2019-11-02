package com.mushroom.midnight.common.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public class ParticleDataOneInt implements IParticleData {
    public static final IDeserializer<ParticleDataOneInt> DESERIALIZER = new IDeserializer<ParticleDataOneInt>() {
        @Override
        public ParticleDataOneInt deserialize(ParticleType<ParticleDataOneInt> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            int oneInt = reader.readInt();
            return new ParticleDataOneInt(particleType, oneInt);
        }

        @Override
        public ParticleDataOneInt read(ParticleType<ParticleDataOneInt> particleType, PacketBuffer buf) {
            return new ParticleDataOneInt(particleType, buf.readInt());
        }
    };
    private final ParticleType<ParticleDataOneInt> particleType;
    public final int oneInt;

    public ParticleDataOneInt(ParticleType<ParticleDataOneInt> particleType, int oneInt) {
        this.particleType = particleType;
        this.oneInt = oneInt;
    }

    @Override
    public ParticleType<?> getType() {
        return this.particleType;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeInt(this.oneInt);
    }

    @Override
    public String getParameters() {
        return String.format(Locale.ROOT, "%s %2d", ForgeRegistries.PARTICLE_TYPES.getKey(getType()), this.oneInt);
    }
}
