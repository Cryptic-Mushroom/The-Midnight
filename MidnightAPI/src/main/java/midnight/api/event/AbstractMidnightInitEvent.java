/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.api.event;

import midnight.api.IMidnight;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.Event;

/**
 * The generic event to stay up-to-date with the loading lifecycle of the Midnight. Since plugins inject directly into
 * the Midnight, they should be triggered from the Midnight's loading thread, and not from that of your own mod.
 *
 * @author Shadew
 * @since 0.6.0
 */
public abstract class AbstractMidnightInitEvent extends Event {
    private final IMidnight midnight;
    private final Dist dist;

    protected AbstractMidnightInitEvent(IMidnight midnight, Dist dist) {
        this.midnight = midnight;
        this.dist = dist;
    }

    /**
     * Returns the {@link IMidnight} instance.
     */
    public IMidnight getMidnight() {
        return midnight;
    }

    /**
     * Returns the runtime distribution ({@link Dist}) of the Midnight.
     */
    public Dist getRuntimeDist() {
        return dist;
    }
}
