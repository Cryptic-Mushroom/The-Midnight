package com.mushroom.midnight.client.shader;

import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.client.shader.ShaderInstance;

public class PresentShaderHandle implements ShaderHandle {
    private final ShaderInstance handle;

    PresentShaderHandle(ShaderInstance handle) {
        this.handle = handle;
    }

    @Override
    public ShaderDefault getUniform(String key) {
        return this.handle.func_216539_a(key);
    }

    @Override
    public void use() {
        this.handle.func_216535_f();
    }

    @Override
    public void close() {
        this.handle.func_216544_e();
    }
}
