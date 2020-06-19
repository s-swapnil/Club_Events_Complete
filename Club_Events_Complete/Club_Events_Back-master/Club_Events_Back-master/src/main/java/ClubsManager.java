import java.util.ArrayList;

public class ClubsManager {
    private static final String cultural = "CULTURAL";
    private static final String technical = "TECHNICAL";
    private static final String sports = "SPORTS";
    private static final int concurrent=3;
    private static ArrayList<ClubData> clubList=new ArrayList<>();

    public ClubsManager() {
        AddCult();
        AddTech();
        AddSports();
        for (int i=0;i<concurrent;i++)
        {
            Thread thread=new Thread(new ScrapePage(clubList.get(i),i));
            thread.start();
        }


    }

    private void AddCult() {
        clubList.add(new ClubData(cultural, "Cadence", "cadence.iitg"));
        clubList.add(new ClubData(cultural, "Anchorenza & RadioG", "anchorenza.radiog"));
        clubList.add(new ClubData(cultural, "Fine Arts", "finearts.iitg"));
        clubList.add(new ClubData(cultural, "Montage", "montage.iitg"));
        clubList.add(new ClubData(cultural, "Lumiere", "iitgmovieclub"));
        clubList.add(new ClubData(cultural, "Octaves", "musicclubiitg"));
        clubList.add(new ClubData(cultural, "Xpressions", "xpressionsiitg"));
        clubList.add(new ClubData(cultural, "LitSoc", "litsociitg"));
        clubList.add(new ClubData(cultural, "DebSoc", "DebatingSocietyIITG"));
    }

    private void AddTech() {
        clubList.add(new ClubData(technical, "Aeromodelling", "Aeroiitg"));
        clubList.add(new ClubData(technical, "Equinox", "equinox.iitg"));
        clubList.add(new ClubData(technical, "Coding", "codingclubiitg"));
        clubList.add(new ClubData(technical, "Consulting & Analytics", "caciitg"));
        clubList.add(new ClubData(technical, "Electronics", "electronics.iitg"));
        clubList.add(new ClubData(technical, "Prakriti", "PrakritiClub"));
        clubList.add(new ClubData(technical, "Finance & Economics", "financeclubiitg"));
        clubList.add(new ClubData(technical, "Robotics", "robotics.iitg"));
        clubList.add(new ClubData(technical, "Quiz", "quizclubiitg"));
        clubList.add(new ClubData(technical, "E Cell", "ecell.iitg"));
    }

    private void AddSports(){
        clubList.add(new ClubData(sports,"Aquatics","aquatics.iitg"));
        clubList.add(new ClubData(sports,"Athletics","athletics.iitg"));
        clubList.add(new ClubData(sports,"Badminton","IITGBADDY"));
        clubList.add(new ClubData(sports,"Basketball","rhinosiitg"));
        clubList.add(new ClubData(sports,"Cricket","cricketiitg"));
        clubList.add(new ClubData(sports,"Football","footballiitg"));
        clubList.add(new ClubData(sports,"Hockey","HockeyIITG"));
        clubList.add(new ClubData(sports,"Squash","squashiitg"));
        clubList.add(new ClubData(sports,"Tennis","TennisClubIITG"));
        clubList.add(new ClubData(sports,"Table Tennis","TTIITG"));
        clubList.add(new ClubData(sports,"Volleyball","volley.iitg"));
        clubList.add(new ClubData(sports,"Weightlifting","WeightliftingClubIitGuwahati"));
    }
    public static int GetMax()
    {return clubList.size(); }

    public static ClubData GetClub(int i)
    {
        return clubList.get(i);
    }
    public static int getConcurrent()
    {
        return concurrent;
    }
}
