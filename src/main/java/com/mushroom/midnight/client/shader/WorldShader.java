package com.mushroom.midnight.client.shader;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.shader.ShaderInstance;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class WorldShader implements ISelectiveResourceReloadListener {
    private static final Minecraft MC = Minecraft.getInstance();

    private final ResourceLocation location;

    private final Map<String, ResourceLocation> textures = new HashMap<>();

    private ShaderInstance instance;
    private boolean initialized;

    public WorldShader(ResourceLocation location) {
        this.location = location;
        ((IReloadableResourceManager) MC.getResourceManager()).addReloadListener(this);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        if (resourcePredicate.test(VanillaResourceType.SHADERS)) {
            if (this.instance != null) {
                this.instance.close();
            }
            for (ResourceLocation texture : this.textures.values()) {
                this.prepareTexture(texture);
            }
            this.initialized = false;
        }
    }

    public WorldShader withTextureSampler(String key, ResourceLocation texture) {
        this.prepareTexture(texture);
        this.textures.put(key, texture);
        return this;
    }

    private void prepareTexture(ResourceLocation texture) {
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
    }

    public ShaderHandle use(Consumer<ShaderHandle> setup) {
        ShaderHandle handle = this.makeHandle();
        setup.accept(handle);
        handle.getUniform("FogMode").set(GL11.glGetInteger(GL11.GL_FOG_MODE));

        handle.use();

        return handle;
    }

    private ShaderHandle makeHandle() {
        ShaderInstance manager = this.get();
        if (manager != null) {
            return new PresentShaderHandle(manager);
        }
        return new VoidShaderHandle();
    }

    private ShaderInstance get() {
        if (!this.initialized) {
            this.initialize();
        }
        return this.instance;
    }

    private void initialize() {
        if (this.initialized) {
            return;
        }

        try {
            Minecraft mc = Minecraft.getInstance();
            this.instance = new ShaderInstance(mc.getResourceManager(), this.location.toString());

            for (Map.Entry<String, ResourceLocation> entry : this.textures.entrySet()) {
                ITextureObject texture = mc.getTextureManager().getTexture(entry.getValue());
                this.instance.func_216537_a(entry.getKey(), texture);
            }
        } catch (IOException e) {
            Midnight.LOGGER.error("Failed to load shader {}", this.location, e);
        } finally {
            this.initialized = true;
        }
    }

    public boolean isAvailable() {
        if (!GLX.usePostProcess) {
            return false;
        }
        return !this.initialized || this.instance != null;
    }
}
