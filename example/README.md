# Example content / 示例内容

English
-------
A complete, working pair of packs that adds a new disc through DiscAPI **without
writing any Java code**:

- `example_datapack/` - `jukebox_song` entry, a shaped recipe and a
  `/function example_disc:give_disc` helper. Goes to `<world>/datapacks/`.
- `example_resourcepack/` - the audio (Ogg Vorbis), the `sounds.json` that feeds
  it to an existing sound event, and the song title. Goes to
  `<instance>/resourcepacks/`.

Read the `README.txt` inside each folder for the details, and the "Adding a disc
with a datapack" section of the main README for the format rules.

Two things worth knowing before you copy these into a world:

- `minecraft:sound_event` is a **built-in registry**. Datapacks and resource
  packs cannot invent a new event id, so the example overrides the existing
  `minecraft:music_disc.11` and marks the entry `"replace": true`. While the
  resource pack is enabled, the vanilla "11" disc therefore plays the same
  track.
- The bundled `never_gonna_give_you_up.ogg` is **third-party audio**
  ("Never Gonna Give You Up" by Rick Astley). It is included only so the example
  runs out of the box, is **not** covered by this project's CC0 licence, and can
  be deleted at any time - replace it with your own file and keep the same path.
  Rights holders may request its removal through the issue tracker.

中文
----
一对**完整可用**的示例包，不写任何 Java 代码就通过 DiscAPI 新增一张唱片：

- `example_datapack/` —— `jukebox_song` 条目、合成配方、`/function example_disc:give_disc`；放进 `<存档>/datapacks/`。
- `example_resourcepack/` —— 音频（Ogg Vorbis）、把它喂给已有声音事件的 `sounds.json`、曲名；放进 `<实例>/resourcepacks/`。

细节看两个文件夹里的 `README.txt`，格式规则看主 README 的「用数据包新增一张唱片」。

复制到存档前需要知道两件事：

- `minecraft:sound_event` 是**内置注册表**，数据包和资源包都造不出新事件 id，所以示例占用已有的
  `minecraft:music_disc.11` 并加 `"replace": true`。资源包启用期间，原版「11」唱片会放同一首歌。
- 随附的 `never_gonna_give_you_up.ogg` 是**第三方音频**（Rick Astley《Never Gonna Give You Up》）。
  只是为了示例开箱可用才附带，**不受**本项目 CC0 许可覆盖，随时可删——换成自己的文件、保持路径不变即可。
  权利人可通过 issue 要求移除。
