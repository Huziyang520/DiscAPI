# DiscAPI

A small library for adding custom music discs. It provides one generic disc item, a texture template whose inner and outer ring colors can be set per disc, and full datapack support for songs.

---

## English

### What it does

- **One disc item** (`discapi:disc`) that can hold any song. Songs are defined by datapacks, so you can add discs without writing code.
- **Datapack support**: a disc is a `jukebox_song` entry plus the item's components. Works with the vanilla jukebox, comparator output included.
- **Colorable texture template**: the disc art is split into an outer ring and an inner ring, both tinted per item, so every disc can look different without new textures.
- **Artist line**: an optional `discapi:artist` component shows "Artist: ..." in the tooltip.
- **Creative tab** with the generic disc, so it can be tested without commands.
- **Jade integration** (optional, no dependency bundled): looking at a playing jukebox shows a music-player style progress bar with the current position and total length.

### Adding a disc with a datapack

1. Put the song definition in your datapack at `data/<your_pack>/jukebox_song/<song_id>.json`:

```json
{
  "comparator_output": 9,
  "description": { "translate": "jukebox_song.your_pack.my_song" },
  "length_in_seconds": 120.0,
  "sound_event": "minecraft:music_disc.11"
}
```

`sound_event` must be an **existing** sound event: `minecraft:sound_event` is a built-in registry that only code extends, so a datapack or resource pack cannot invent `your_pack:music/my_song`. Point it at a vanilla (or modded) event and let the resource pack supply that event's audio - see "Adding the music file". A mod that registers its own event (as Sivon does) can use its own id instead.

2. Put the song text in `assets/<your_pack>/lang/en_us.json` (`jukebox_song.your_pack.my_song`), or use a plain `text` field instead of `translate`.

3. Hand out the disc. `jukebox_playable` takes the song id as a plain string (it is a reference to the `jukebox_song` entry, not an object). Colors are `[outer, inner, edge]` in ARGB - the three layers of the template - and the artist line is optional:

```
/give @p discapi:disc[jukebox_playable="your_pack:my_song",custom_model_data={colors:[-14725492,-5711617,-12566464]},discapi:artist="Your Name"]
```

4. A recipe is the usual datapack recipe with the same item and components. Since 26.x an ingredient is a **holder set**, so write the item id on its own (`"minecraft:diamond"`) or as a list (`["minecraft:diamond"]`) - the old `{"item": "minecraft:diamond"}` form is rejected:

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [" D ", "DGD", " D "],
  "key": { "D": ["minecraft:diamond"], "G": ["minecraft:gold_ingot"] },
  "result": {
    "id": "discapi:disc",
    "components": {
      "minecraft:jukebox_playable": "your_pack:my_song",
      "minecraft:custom_model_data": { "colors": [-14725492, -5711617, -12566464] },
      "discapi:artist": "Your Name"
    }
  }
}
```

5. A complete working pair - datapack, resource pack and a real song - lives in [`example/`](https://github.com/Huziyang520/DiscAPI/tree/26.X/example); see "The bundled example" below for where each folder goes.

### Adding the music file

Because the sound event has to already exist, a resource pack **replaces the audio of an existing event**. Override `music_disc.11` (what the example uses):

1. `assets/<your_pack>/sounds/music/my_song.ogg` (Ogg Vorbis, mono is recommended so the disc is positional).
2. `assets/minecraft/sounds.json` - note the `minecraft` namespace, that is where the event lives:

```json
{
  "music_disc.11": {
    "replace": true,
    "sounds": [
      { "name": "your_pack:music/my_song", "stream": true }
    ]
  }
}
```

The key must be the event id you put in `sound_event` (`minecraft:music_disc.11`), and `replace: true` drops the vanilla file list instead of appending to it. While this pack is enabled every disc using that event plays the new track - that is why mods often register their own event instead.

### The bundled example

`example/` ships a ready-to-run pair that uses everything above:

- `example/example_datapack/` - `jukebox_song`, a shaped recipe and `/function example_disc:give_disc`; copy it to `<world>/datapacks/`.
- `example/example_resourcepack/` - the audio, the `sounds.json` entry and the song title; copy it to `<instance>/resourcepacks/` and enable it.

Enable the resource pack first, then open the world (or `/reload`). You get a red disc - tooltip "Artist: Rick Astley 莎士牛比亚（B站）", jukebox entry "Never Gonna Give You Up", 3:45 long - crafted from 1 gold ingot and 4 diamonds, comparator output 9.

The example overrides the vanilla `minecraft:music_disc.11` event with its own audio, so while the resource pack is enabled that vanilla disc plays the same track. Its `never_gonna_give_you_up.ogg` is a fan cover (original by Rick Astley, cover by 莎士牛比亚 on Bilibili) bundled only so the example runs out of the box: it is third-party material, **not** covered by this project's CC0 licence, and may be replaced or deleted at any time.

The `pack.mcmeta` files inside the example target 26.3 (resource 97.1, data 121.0); change the format numbers to run it on another version.

### Making your own disc item (mods)

```java
KeenRegistry registry = KeenRegistry.of("mymod");
registry.item("my_disc", DiscApi.discFactory(DiscApi.songKey("mymod", "my_song")));
```

The item model then points at `discapi:item/disc_template`.

### Requirements

- **KeenLib** is required. Install it next to this mod.
- Optional: **Cloth Config** for the config screen, **Mod Menu** for a config button on Fabric.

### License

Released under [CC0 1.0 Universal](https://github.com/Huziyang520/DiscAPI/blob/26.X/LICENSE).

### Links

- Issues: <https://github.com/Huziyang520/DiscAPI/issues>
- Backup feedback: <https://issue.mengcai.online/>

---

## 中文

### 这个模组做什么

- **一个通用唱片物品**（`discapi:disc`），可以承载任意歌曲。歌曲由数据包定义，因此不加代码也能新增唱片。
- **数据包支持**：一张唱片 = 一条 `jukebox_song` + 物品组件，原版唱片机与红石比较器输出都能正常工作。
- **可染色纹理模板**：唱片贴图拆成外圈与内圈两层，各自按物品染色，因此不需要新贴图就能让每张唱片外观不同。
- **作者行**：可选的 `discapi:artist` 组件会在提示里显示「作者：……」。
- **创造模式标签页**内置通用唱片，不用命令也能直接取来试。
- **Jade 联动**（可选，不内置依赖）：看向正在播放的唱片机时，显示音乐播放器样式的进度条，含当前时间与总时长。

### 用数据包新增一张唱片

1. 在数据包里放歌曲定义 `data/<你的包>/jukebox_song/<歌曲 id>.json`：

```json
{
  "comparator_output": 9,
  "description": { "translate": "jukebox_song.your_pack.my_song" },
  "length_in_seconds": 120.0,
  "sound_event": "minecraft:music_disc.11"
}
```

`sound_event` 必须指向**已存在**的声音事件：`minecraft:sound_event` 是内置注册表，只有代码（原版或模组）能往里加，数据包/资源包造不出 `your_pack:music/my_song`。做法是引用一个原版（或模组）事件，由资源包替换它的音频——见「放入音乐文件」。模组自己注册事件（如司凤）才可以用自己的 id。

2. 在 `assets/<你的包>/lang/zh_cn.json` 里补上 `jukebox_song.your_pack.my_song` 的文本；也可以把 `translate` 换成纯 `text`。

3. 发放唱片。`jukebox_playable` 直接写歌曲 id 字符串（它是对 `jukebox_song` 条目的引用，不是对象）。颜色是 `[外圈, 内圈, 厚度]` 的 ARGB（正好对应模板三层），作者行可选：

```
/give @p discapi:disc[jukebox_playable="your_pack:my_song",custom_model_data={colors:[-14725492,-5711617,-12566464]},discapi:artist="作者名"]
```

4. 配方就是普通的数据包配方，物品与组件照抄上面这一行。26.x 起**配料是 holder set**，直接写物品 id（`"minecraft:diamond"`）或用列表（`["minecraft:diamond"]`），旧的 `{"item": "minecraft:diamond"}` 写法会被拒绝：

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [" D ", "DGD", " D "],
  "key": { "D": ["minecraft:diamond"], "G": ["minecraft:gold_ingot"] },
  "result": {
    "id": "discapi:disc",
    "components": {
      "minecraft:jukebox_playable": "your_pack:my_song",
      "minecraft:custom_model_data": { "colors": [-14725492, -5711617, -12566464] },
      "discapi:artist": "作者名"
    }
  }
}
```

5. 一套完整可用的示例（数据包 + 资源包 + 真实音频）放在 [`example/`](https://github.com/Huziyang520/DiscAPI/tree/26.X/example)，各放哪里见下面「随附示例」。

### 放入音乐文件

因为声音事件必须已经存在，资源包的做法是**替换某个已有事件的音频**。以示例用的 `music_disc.11` 为例：

1. `assets/<你的包>/sounds/music/my_song.ogg`（Ogg Vorbis；建议单声道，这样唱片声音有距离衰减）。
2. `assets/minecraft/sounds.json`——注意命名空间是 `minecraft`，事件住在那里：

```json
{
  "music_disc.11": {
    "replace": true,
    "sounds": [
      { "name": "your_pack:music/my_song", "stream": true }
    ]
  }
}
```

键必须就是你在 `sound_event` 里写的那个事件（`minecraft:music_disc.11`）；`replace: true` 表示丢弃原版的文件列表而不是追加。资源包启用期间，凡是用这个事件的唱片都会放新歌——这也是模组倾向自己注册事件的原因。

### 随附示例

`example/` 里是一套可直接运行的示例，把上面三步都串起来了：

- `example/example_datapack/` —— `jukebox_song`、合成配方、`/function example_disc:give_disc`；复制到 `<存档>/datapacks/`。
- `example/example_resourcepack/` —— 音频、`sounds.json` 条目、曲名；复制到 `<实例>/resourcepacks/` 并启用。

先启用资源包，再进存档（或 `/reload`）。得到一张红色唱片：提示「作者：Rick Astley 莎士牛比亚（B站）」、唱片机曲名 Never Gonna Give You Up、时长 3:45，合成是中间 1 金锭 + 四角 4 钻石，比较器输出 9。

示例占用原版 `minecraft:music_disc.11` 事件并替换其音频，所以资源包启用期间那张原版唱片会放同一首歌。随附的 `never_gonna_give_you_up.ogg` 是二创翻唱（原曲 Rick Astley，翻唱 莎士牛比亚／B站），只为示例开箱可用才附带：属第三方素材，**不受**本项目 CC0 许可覆盖，随时可替换或删除。

示例内 `pack.mcmeta` 的格式号对应 26.3（资源 97.1、数据 121.0），换版本要改。

### 自定义唱片物品（模组）

```java
KeenRegistry registry = KeenRegistry.of("mymod");
registry.item("my_disc", DiscApi.discFactory(DiscApi.songKey("mymod", "my_song")));
```

物品模型指向 `discapi:item/disc_template`。

### 前置与可选

- 必须安装 **KeenLib**。
- 可选：装 **Cloth Config** 才有配置界面；Fabric 端再装 **Mod Menu** 才有配置入口。

### 许可证

本项目基于 [CC0 1.0 Universal](https://github.com/Huziyang520/DiscAPI/blob/26.X/LICENSE) 发布。

### 链接

- 问题反馈：<https://github.com/Huziyang520/DiscAPI/issues>
- 备用反馈站：<https://issue.mengcai.online/>
