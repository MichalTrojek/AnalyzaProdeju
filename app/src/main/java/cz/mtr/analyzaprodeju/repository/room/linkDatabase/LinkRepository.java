package cz.mtr.analyzaprodeju.repository.room.linkDatabase;

import android.app.Application;

public class LinkRepository {

    private LinkDao mLinkDao;
    private LinksDatabase db;

    public LinkRepository(Application app) {
        db = LinksDatabase.getInstance(app);
        mLinkDao = db.linkDao();
    }


    public String getLink(String ean) {
        return mLinkDao.getLink(ean);
    }

    public void insertLink(String ean, String imageLink) {
        LinkEntity link = new LinkEntity(ean, imageLink);
        mLinkDao.insertLink(link);
    }


}
