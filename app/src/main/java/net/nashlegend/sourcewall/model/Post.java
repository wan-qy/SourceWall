package net.nashlegend.sourcewall.model;

import android.text.TextUtils;

import net.nashlegend.sourcewall.request.api.APIBase;
import net.nashlegend.sourcewall.swrequest.JsonHandler;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Post extends AceModel {

    private String id = "";
    private String title = "";
    private String url = "";
    private String titleImageUrl = "";
    private boolean authorExists = true;
    private String authorAvatarUrl = "";
    private String author = "";
    private String authorID = "";
    private String groupName = "";
    private String groupID = "";
    private String tag = "";
    private int likeNum = 0;
    private int replyNum = 0;
    private String content = "";
    private String date = "";
    private boolean featured = true;//是否展现在集合列表还是单个小组。true表示展示在单个小组列表中
    private boolean desc = false;

    private ArrayList<UComment> hotComments = new ArrayList<>();
    private ArrayList<UComment> comments = new ArrayList<>();

    public static Post fromJson(JSONObject postResult) throws Exception {
        Post detail = new Post();
        String postID = postResult.getString("id");
        String title = postResult.getString("title");
        String date = postResult.optString("date_created");
        String content = "<div id=\"postContent\" class=\"html-text-mixin gbbcode-content\">" + postResult.optString("html") + "</div>";
        //int likeNum = getJsonInt(postResult, "");//取不到like数量
        int recommendNum = postResult.optInt("recommends_count");
        int reply_num = postResult.optInt("replies_count");
        JSONObject authorObject = JsonHandler.getJsonObject(postResult, "author");
        String authorAvatarUrl = JsonHandler.getJsonObject(authorObject, "avatar").getString("large").replaceAll("\\?.*$", "");
        String author = authorObject.optString("nickname");
        String authorID = authorObject.optString("url").replaceAll("\\D+", "");
        JSONObject groupObject = JsonHandler.getJsonObject(postResult, "group");
        String groupName = groupObject.optString("name");
        String groupID = postResult.optString("group_id");
        detail.setGroupID(groupID);
        detail.setGroupName(groupName);
        detail.setAuthor(author);
        detail.setAuthorAvatarUrl(authorAvatarUrl);
        detail.setAuthorID(authorID);
        detail.setId(postID);
        detail.setTitle(title);
        detail.setDate(APIBase.parseDate(date));
        detail.setContent(content);
        detail.setReplyNum(reply_num);
        return detail;
    }

    public static ArrayList<Post> fromHtmlList(String html) throws Exception {
        ArrayList<Post> list = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByClass("post-list");
        if (elements.size() == 1) {
            Elements postlist = elements.get(0).getElementsByTag("li");
            for (Element aPostlist : postlist) {
                Post item = new Post();
                Element link = aPostlist.getElementsByClass("post").get(0);
                String postTitle = link.getElementsByTag("h4").get(0).text();
                String postUrl = link.attr("href");
                String postImageUrl = "";
                String postAuthor = "";//没有Author名……
                String postGroup = aPostlist.getElementsByClass("post-author").get(0).text();//没错，post-author是小组名……
                Elements children = aPostlist.getElementsByClass("post-info-right").get(0).children();
                int postLike = Integer.valueOf(children.get(0).text().replaceAll("\\D*", ""));
                int postComm = Integer.valueOf(children.get(1).text().replaceAll("\\D*", ""));
                item.setTitle(postTitle);
                item.setUrl(postUrl);
                item.setId(postUrl.replaceAll("\\?.*$", "").replaceAll("\\D+", ""));
                item.setTitleImageUrl(postImageUrl);
                item.setAuthor(postAuthor);
                item.setGroupName(postGroup);
                item.setLikeNum(postLike);
                item.setReplyNum(postComm);
                item.setFeatured(false);
                list.add(item);
            }
        }
        return list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        if (TextUtils.isEmpty(title)) {
            url = "果壳小组";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        if (!TextUtils.isEmpty(id)) {
            url = "http://www.guokr.com/post/" + id + "/";
        }
        return url;
    }

    public void setUrl(String url) {
        if (url != null && url.startsWith("http://m.guokr.com")) {
            url.replace("http://m.guokr.com", "http://www.guokr.com");
        }
        this.url = url;
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public void setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
    }

    public String getAuthorAvatarUrl() {
        return authorAvatarUrl;
    }

    public void setAuthorAvatarUrl(String authorAvatarUrl) {
        this.authorAvatarUrl = authorAvatarUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<UComment> getHotComments() {
        return hotComments;
    }

    public void setHotComments(ArrayList<UComment> hotComments) {
        this.hotComments = hotComments;
    }

    public ArrayList<UComment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<UComment> comments) {
        this.comments = comments;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean isAuthorExists() {
        return authorExists;
    }

    public void setAuthorExists(boolean authorExists) {
        this.authorExists = authorExists;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }
}
