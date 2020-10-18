/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.api.event;

import midnight.api.IMidnight;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Midnight event triggered at initialization time, once the Midnight has received the {@link FMLCommonSetupEvent}.
 *
 * @author Shadew
 * @since 0.6.0
 */
public class MidnightInitEvent extends AbstractMidnightInitEvent {
    /**
     * @param midnight The {@link IMidnight} instance.
     * @param dist The {@link Dist} to run the initialization event.
     */
    public MidnightInitEvent(IMidnight midnight, Dist dist) {
        super(midnight, dist);
    }
}
