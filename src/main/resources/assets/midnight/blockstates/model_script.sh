#!/bin/sh

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
