/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 26
 */

package midnight.api;

public interface MidnightClientAPI extends MidnightAPI {
    static MidnightClientAPI get() {
        return (MidnightClientAPI) MidnightAPI.get();
    }
}
