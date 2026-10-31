DiscAPI example disc - DATAPACK half
======================================

English
-------
  data/example_disc/jukebox_song/never_gonna_give_you_up.json
      The song record. `sound_event` points at an existing vanilla event
      (`minecraft:music_disc.11`) whose audio the resource pack replaces,
      because the sound_event registry is built-in and cannot be extended by
      data or resource packs.
  data/example_disc/recipe/never_gonna_give_you_up.json
      Shaped recipe that crafts the disc with its components already set.
  data/example_disc/function/give_disc.mcfunction
      `/function example_disc:give_disc` hands the same disc to yourself.

Install: copy this folder into `<world>/datapacks/`, then `/reload`
(or `/datapack enable "file/example_datapack"`).

26.x syntax this pack follows:
  * `minecraft:jukebox_playable` is the song id as a **plain string**.
  * Recipe ingredients are **holder sets**: `["minecraft:diamond"]`.

pack.mcmeta targets data pack format 121.0 (Minecraft 26.3).

中文
----
  `jukebox_song` 歌曲定义：因为 sound_event 是内置注册表（数据和资源包都加不了），
  这里引用原版已有事件 `minecraft:music_disc.11`，音频由资源包替换。
  另有已写好组件的合成配方与 `/function example_disc:give_disc`。

安装：放进存档 `datapacks/` 后 `/reload`（或
`/datapack enable "file/example_datapack"`）。

本包遵守 26.x 的写法：`minecraft:jukebox_playable` 值是歌曲 id 字符串；
配方配料是 holder set（`["minecraft:diamond"]`）。

`pack.mcmeta` 的格式号是 121.0（对应 Minecraft 26.3）。
