package musicTranspose.databasereader;
public class ScoreMetadata {
    private int id;
    private int music_id;
    private String description;
    private long dateAdded; // Unix timestamp
    private int register_id;
    private String part;

    // Constructors, getters, and setters
    public ScoreMetadata() {
    }

    public ScoreMetadata(int id, int music_id, String description, long dateAdded, int register_id, String part) {
        this.id = id;
        this.music_id = music_id;
        this.description = description;
        this.dateAdded = dateAdded;
        this.register_id = register_id;
        this.part = part;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getRegister_id() {
        return register_id;
    }

    public void setRegister_id(int register_id) {
        this.register_id = register_id;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }
}
