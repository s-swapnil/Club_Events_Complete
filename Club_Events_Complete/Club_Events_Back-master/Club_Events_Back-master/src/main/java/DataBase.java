import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DataBase {

    public static void AddEntries(Elements elements, ClubData club) {
        Long posts = 0L;
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference clubs = db.collection("Clubs");
        DocumentReference doc = db.collection("Clubs").document(club.getName());
        Query query = clubs.whereEqualTo("NAME", club.getName());
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        try {
            for (DocumentSnapshot snapshot : querySnapshot.get().getDocuments()) {
                posts = (Long) snapshot.get("POSTS");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


            for (int i=elements.size()-1;i>=0;i--) {
                posts++;
               Element element=elements.get(i);
                Entry entry = new DataBase.Entry(club.getName(),club.getBoard(),ScrapePage.GetText(element), ScrapePage.GetTimeStamp(element),ScrapePage.GetURL(element)
                ,ScrapePage.GetVid(element),ScrapePage.GetImgs(element),ScrapePage.GetMoreImgs(element),posts);
                ApiFuture<WriteResult> update = doc.collection("Posts").document(String.valueOf(posts)).set(entry);

            }

        ApiFuture<WriteResult> future = doc.update("LAST_POST", ScrapePage.GetTimeStamp(elements.get(0)));
        ApiFuture<WriteResult> future2 = doc.update("POSTS",posts);
    }

    public static void AddPage(ClubData club)
    {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference clubs = db.collection("Clubs");
        PageEntry pageEntry=new DataBase.PageEntry(new Timestamp(2),club.getName(),0L,club.getBoard());
        ApiFuture<WriteResult> update = clubs.document(club.getName()).set(pageEntry);
        Query update2 = clubs.document(club.getName()).collection("Posts").orderBy("timestamp",Query.Direction.DESCENDING);
    }

    static class Entry {
        public String clubName;
        public String board;
        public String text;
        public Timestamp timestamp;
        public String URL;
        public String VID_IMG_URL;
        public ArrayList<String> IMG_URLS;
        public String MoreImgs;
        public Long PostNum;

        public Entry() {
        }

        public Entry(String clubName,String board,String text, Timestamp timestamp,String URL,String VID_IMG_URL,ArrayList<String> IMG_URLS, String MoreImgs,Long PostNum) {
            this.clubName = clubName;
            this.board=board;
            this.text = text;
            this.timestamp = timestamp;
            this.URL=URL;
            this.VID_IMG_URL=VID_IMG_URL;
            this.IMG_URLS=IMG_URLS;
            this.MoreImgs=MoreImgs;
            this.PostNum=PostNum;

        }
    }

    static class PageEntry{
        public Timestamp LAST_POST;
        public String NAME;
        public long POSTS;
        public String BOARD;


        public PageEntry(){}

        public PageEntry(Timestamp LAST_POST,String NAME,long POSTS,String BOARD)
        {
            this.LAST_POST=LAST_POST;
            this.NAME=NAME;
            this.POSTS=POSTS;
            this.BOARD=BOARD;

        }
    }
}
