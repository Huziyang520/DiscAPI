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

### Adding a disc with a datapack

1. Put the song definition in your datapack at `data/<your_pack>/jukebox_song/<song_id>.json`:

```json
{
  "comparator_output": 9,
  "description": { "translate": "jukebox_song.your_pack.my_song" },
  "length_in_seconds": 120.0,
  "sound_event": "your_pack:music/my_song"
}
```

2. Put the song text in `assets/<your_pack>/lang/en_us.json` (`jukebox_song.your_pack.my_song`), or use a plain `text` field instead of `translate`.

3. Hand out the disc. Colors are `[outer ring, inner ring]` in ARGB, the artist line is optional:

```
/give @p discapi:disc[jukebox_playable={song:"your_pack:my_song"},custom_model_data={colors:[-14725492,-5711617]},discapi:artist="Your Name"]
```

4. A recipe is the usual datapack recipe with the same item and components.

### Adding the music file

The sound itself is a normal resource pack file:

1. `assets/<your_pack>/sounds/music/my_song.ogg` (Ogg Vorbis, mono is recommended so the disc is positional).
2. `assets/<your_pack>/sounds.json`:

```json
{
  "music.my_song": {
    "sounds": [
      { "name": "your_pack:music/my_song", "stream": true }
    ]
  }
}
```

The `sound_event` in step 1 must match the `sounds.json` key, prefixed with your namespace (`your_pack:music.my_song` here).

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

Released under [CC0 1.0 Universal](LICENSE).

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

### 用数据包新增一张唱片

1. 在数据包里放歌曲定义 `data/<你的包>/jukebox_song/<歌曲 id>.json`：

```json
{
  "comparator_output": 9,
  "description": { "translate": "jukebox_song.your_pack.my_song" },
  "length_in_seconds": 120.0,
  "sound_event": "your_pack:music/my_song"
}
```

2. 在 `assets/<你的包>/lang/zh_cn.json` 里补上 `jukebox_song.your_pack.my_song` 的文本；也可以把 `translate` 换成纯 `text`。

3. 发放唱片。颜色是 `[外圈, 内圈]` 的 ARGB，作者行可选：

```
/give @p discapi:disc[jukebox_playable={song:"your_pack:my_song"},custom_model_data={colors:[-14725492,-5711617]},discapi:artist="作者名"]
```

4. 配方就是普通的数据包配方，物品与组件照抄上面这一行。

### 放入音乐文件

音乐本体是普通的资源包内容：

1. `assets/<你的包>/sounds/music/my_song.ogg`（Ogg Vorbis；建议单声道，这样唱片声音有距离衰减）。
2. `assets/<你的包>/sounds.json`：

```json
{
  "music.my_song": {
    "sounds": [
      { "name": "your_pack:music/my_song", "stream": true }
    ]
  }
}
```

第 1 步里的 `sound_event` 要等于这里 `sounds.json` 的键再加命名空间前缀（本例为 `your_pack:music.my_song`）。

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

本项目基于 [CC0 1.0 Universal](LICENSE) 发布。

### 链接

- 问题反馈：<https://github.com/Huziyang520/DiscAPI/issues>
- 备用反馈站：<https://issue.mengcai.online/>
