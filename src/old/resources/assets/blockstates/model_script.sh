#!/bin/sh

#
# Copyright (c) 2020 Cryptic Mushroom and contributors
# This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
# https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
#
# Last updated: 2020 - 10 - 18
#

cd "$(dirname $0)"

test -f "$1.json" && exit 1
printf '{
  "forge_marker": 1,
  "defaults": {
    "model": "cube_all",
    "textures": {
      "all": "midnight:blocks/%s"
    }
  },
  "variants": {
    "normal": [{}],
    "inventory": [{}]
  }
}' "$1" > "$1.json"
