DiscAPI example disc - RESOURCE PACK half
==========================================

English
-------
Minecraft's `sound_event` registry is a **built-in** registry: entries exist only
because code (vanilla or a mod) registered them. A resource pack can not create
a new event id - `sounds.json` only points an *existing* event at sound files
(and may replace its file list with "replace": true).

So this pack overrides one existing vanilla event and feeds it our audio:

  assets/minecraft/sounds.json
        "music_disc.11": { "replace": true, "sounds": [ example_disc:music/never_gonna_give_you_up ] }
  assets/example_disc/sounds/music/never_gonna_give_you_up.ogg
        the song: Ogg Vorbis, mono, ~76 kbps, 3:45
        a fan cover (二创) by Rick Astley + 莎士牛比亚 (Bilibili) - third-party
        audio, not covered by this project's CC0 licence
  assets/example_disc/lang/en_us.json / zh_cn.json
        the song title used by the jukebox entry

Side effect (by design for a test pack): while this pack is enabled, the
vanilla "11" music disc also plays this track, because both discs reference the
same event. Disable the pack to get vanilla audio back.

The datapack's `jukebox_song` uses `"sound_event": "minecraft:music_disc.11"`.

pack.mcmeta targets resource pack format 97.1 (Minecraft 26.3).

中文
----
Minecraft 的 `sound_event` 是**内置注册表**：条目只来自代码（原版或模组）。
资源包无法新建事件 id——`sounds.json` 只能把**已存在**的事件指向音频文件，
或用 `"replace": true` 替换它的文件列表。

因此本包覆盖了一个原版已有事件来喂我们的音频：

  assets/minecraft/sounds.json
        "music_disc.11": { "replace": true, "sounds": [ example_disc:music/never_gonna_give_you_up ] }
  assets/example_disc/sounds/music/never_gonna_give_you_up.ogg
        音乐本体：Ogg Vorbis、单声道、约 76 kbps、3:45
        二创翻唱，原曲 Rick Astley、翻唱 莎士牛比亚（B站）——第三方音频，
        不在本项目 CC0 范围
  assets/example_disc/lang/en_us.json / zh_cn.json
        唱片机条目用的曲名

副作用（测试包可以接受）：资源包启用期间，原版「11」唱片也会放这首歌——
两张唱片引用的是同一个事件。关掉资源包即可恢复原版音频。

数据包里 `jukebox_song` 用的是 `"sound_event": "minecraft:music_disc.11"`。

`pack.mcmeta` 的格式号是 97.1（对应 Minecraft 26.3）。
