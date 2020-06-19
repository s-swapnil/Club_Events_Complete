public class ClubData {

private final String board,name,FB_URL;

    public ClubData(String board,String name,String FB_URL) {
        this.board=board;
        this.name=name;
        this.FB_URL=FB_URL;
    }

    public String getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }

    public String getFB_URL() {
        return FB_URL;
    }
}
