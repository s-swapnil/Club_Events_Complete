import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class ScrapePage implements Runnable {

    private ClubData club;
    private int number;

    public ScrapePage(ClubData club, int number) {
        this.club = club;
        this.number = number;

    }

    public void run() {


        AddNewData();

        // driver.close();

    }

    private void AddNewData() {
        try {
            WebDriver driver = new ChromeDriver();
            driver.get(("https://www.facebook.com/pg/" + club.getFB_URL() + "/posts/"));
            Document document = Jsoup.parse(driver.getPageSource());

            java.sql.Timestamp lastPost = new java.sql.Timestamp(0);
            Firestore db = FirestoreClient.getFirestore();
            CollectionReference clubs = db.collection("Clubs");
            Query query = clubs.whereEqualTo("NAME", club.getName());
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            if (querySnapshot.get().isEmpty()) {
                DataBase.AddPage(club);
            }
            for (DocumentSnapshot snapshot : querySnapshot.get().getDocuments()) {
                lastPost = ((Timestamp) snapshot.get("LAST_POST")).toSqlTimestamp();
                System.out.println(lastPost + "\n\n");

            }

            int i = 0;
            int limit=10;
            Elements elements = new Elements();
            do {
                if (document.select("div._1xnd > div._4-u2._4-u8").size() <= i) {
                    break;
                }
                Element element = document.select("div._1xnd > div._4-u2._4-u8").get(i);


                System.out.println(GetTimeStamp(element));
                System.out.println(GetText(element) + "\n\n");

                if (GetTimeStamp(element).equals(lastPost)) {
                    break;
                }
                if (!element.select("i._5m7w").isEmpty())
                {   i++;
                limit++;
                    continue;
                }
                else {
                    elements.add(element);
                }


                i++;

            }
            while (i < limit);
            driver.close();
            if (!elements.isEmpty()) {
                DataBase.AddEntries(elements, club);
            }
            RunNext();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private static int getMonthNumber(String monthName) {
        return Month.valueOf(monthName.toUpperCase()).getValue();
    }

    public static java.sql.Timestamp GetTimeStamp(Element element) {
        Element timeStampData = element.select("abbr._5ptz").get(0);
        String data = timeStampData.attr("title");

        String[] parts = data.split(" ");
        String[] minuParts = parts[5].split(":");

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("IST"));
        c.set(Calendar.YEAR, Integer.parseInt(parts[3]));
        c.set(Calendar.MONTH, getMonthNumber(parts[2]) - 1);
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[1]));
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(minuParts[0]));
        c.set(Calendar.MINUTE, Integer.parseInt(minuParts[1]));
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long milli = c.getTimeInMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(milli);
        return timestamp;
    }

    public static String GetText(Element element) {
        if (element.select("div._5pbx").isEmpty()) {
            return " ";
        } else {
            Element message = element.select("div._5pbx").get(0);
            return message.text().replaceAll("See more", " ");
        }
    }

    public static String GetURL(Element element)
    {
        Element aData=element.select("a._5pcq").get(0);
        return aData.attr("href");
    }

    public static String GetVid(Element element)
    {
        if (element.select("div.clearfix._2r3x > div.lfloat._ohe > span._3m6- > div._150c").isEmpty())
        {
            return null;
        }
        else
        {
            Element vidElement=element.select("div.clearfix._2r3x > div.lfloat._ohe > span._3m6- > div._150c > img").get(0);
            return vidElement.attr("src");

        }
    }
    public static ArrayList<String> GetImgs(Element element)
    {ArrayList<String> imagesURLS=new ArrayList<>();
    if (element.select("div.uiScaledImageContainer").isEmpty())
    {
        return null;
    }
    else {
        Elements images = element.select("div.uiScaledImageContainer");
        for (Element image : images) {
            Element imgTag = image.select("img").get(0);
            imagesURLS.add(imgTag.attr("src"));

        }
        return imagesURLS;
    }
    }
    public static String GetMoreImgs (Element element)
    {
        if (element.select("div._52db").isEmpty())
        {
            return null;
        }
        else
        {
            return element.select("div._52db").get(0).text();
        }
    }

    public void RunNext() {
        number += ClubsManager.getConcurrent();
        if (number < ClubsManager.GetMax()) {
            club = ClubsManager.GetClub(number);
            run();
        }
    }
}
