package com.example.clubevents;

public class Links {
    public static final int FB_DEV=1;
    public static final int INSTA_DEV=2;
    public static final int GIT_DEV=3;
    public static final int CODE_FB=4;
    public static final int CODE_WEB=5;
    public static final int CODE_GIT=6;
    public static final int SENDMAIL_DEV=7;
    public static final int SENDMAIL_CODE=8;

    public static String getURL(int i)
    {
        switch (i)
        {
            case FB_DEV:
            {  return "https://www.facebook.com/swapnil.srivastava.520900";

            }
            case INSTA_DEV:
            {  return "https://www.instagram.com/swap1204nil/";

            }
            case GIT_DEV:
            {  return "https://github.com/s-swapnil";

            }
            case CODE_FB:
            {  return "https://www.facebook.com/codingclubiitg/";

            }
            case CODE_WEB:
            {  return "https://www.iitg.ac.in/stud/gymkhana/technical/home/CodingHome.html";

            }
            case CODE_GIT:
            {  return "https://github.com/iit-guwahati";

            }
            case SENDMAIL_DEV:
            {  return "mailto:s.swapnil1309@gmail.com";

            }
            case SENDMAIL_CODE:
            {  return "mailto:codingclubiitg@gmail.com";
            }

        }

        return null;
    }
}
