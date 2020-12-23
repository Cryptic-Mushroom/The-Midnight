/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 23
 */

package midnight.tests;

import midnight.api.event.MidnightInitEvent;
import midnight.api.event.MidnightPostInitEvent;
import midnight.api.event.MidnightPreInitEvent;
import midnight.api.plugin.MidnightEventSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@MidnightEventSubscriber
public final class MidnightEventSubscriberTest {
    private MidnightEventSubscriberTest() {
    }

    @SubscribeEvent
    public static void onPreInit(MidnightPreInitEvent event) {
        System.out.println("Midnight pre-init event received on " + event.getRuntimeDist());
    }

    @SubscribeEvent
    public static void onInit(MidnightInitEvent event) {
        System.out.println("Midnight init event received on " + event.getRuntimeDist());
    }

    @SubscribeEvent
    public static void onPostInit(MidnightPostInitEvent event) {
        System.out.println("Midnight post-init event received on " + event.getRuntimeDist());
    }
}
