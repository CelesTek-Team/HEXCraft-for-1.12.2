package celestek.hexcraft.utility;

/**
 * @author Thorinair   <celestek@openmailbox.org>
 */
public enum EHexColors {
    RED (0xff0000, "red", "glow"),
    ORANGE (0xff8000, "orange", "glow"),
    YELLOW (0xffff00, "yellow", "glow"),
    LIME (0x80ff00, "lime", "glow"),
    GREEN (0x00ff00, "green", "glow"),
    TURQUOISE (0x00ff80, "turquoise", "glow"),
    CYAN (0x00ffff, "cyan", "glow"),
    SKY_BLUE (0x0080ff, "skyblue", "glow"),
    BLUE (0x0000ff, "blue", "glow"),
    PURPLE (0x8000ff, "purple", "glow"),
    MAGENTA (0xff00ff, "magenta", "glow"),
    PINK (0xff0080, "pink", "glow"),
    WHITE (0xffffff, "white", "glow"),
    LIGHT_GRAY (0xc0c0c0, "lightgray", "glow"),
    GRAY (0x808080, "gray", "glow"),
    DARK_GRAY (0x404040, "darkgray", "glow"),
    BLACK (0x181818, "black", "glow"),
    RAINBOW (-1, "rainbow", "glow_rainbow"),
    DIMMED (0x808080, null, null),
    NONE (-1, null, null);

    public final int color;
    public final String name;
    public final String texture;

    EHexColors(int color, String name, String texture) {
        this.color = color;
        this.name = name;
        this.texture = texture;
    }
}
