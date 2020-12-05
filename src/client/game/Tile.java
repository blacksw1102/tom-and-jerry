package client.game;
public class Tile {
    private String tileId;
    private int tilePermission;

    /* 타일 권한 */
    public static int NONE = 0;
    public static int ON_PLACE_ITEM     = 1;
    public static int ON_PLACE_TOM      = 1 << 1;
    public static int ON_PLACE_JERRY    = 1 << 2;
    public static int ON_PLACE_PLAYER   = ON_PLACE_TOM | ON_PLACE_JERRY;
    public static int ON_PLACE_ANY      = ON_PLACE_PLAYER | ON_PLACE_ITEM;

    /* 타일 목록 */
    public static Tile VOID_TILE = new Tile("VOID", Tile.NONE);
    public static Tile WALL_TILE = new Tile("WALL", Tile.NONE);
    public static Tile GROUND_TILE = new Tile("GROUND", Tile.ON_PLACE_ANY);
    public static Tile FLOOR1_TILE = new Tile("Floor1", Tile.ON_PLACE_ANY);
    public static Tile FLOOR2_TILE = new Tile("Floor2", Tile.ON_PLACE_ANY);
    public static Tile FLOOR3_TILE = new Tile("Floor3", Tile.ON_PLACE_ANY);
    public static Tile FLOOR4_TILE = new Tile("Floor4", Tile.ON_PLACE_ANY);

    public Tile() {
    }

    public Tile(String id, int permission) {
        setTileId(id);
        setTilePermission(permission);
    }

    public String getTileId() {
        return tileId;
    }

    public void setTileId(String tileId) {
        this.tileId = tileId;
    }

    public int getTilePermission() {
        return tilePermission;
    }

    public void setTilePermission(int tilePermission) {
        this.tilePermission = tilePermission;
    }
}