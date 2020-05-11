package com.library.base.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChenKang on 2017/11/14.
 */

public class TestBean implements Serializable {
    /**
     * current_page : 1
     * data : [{"id":718,"title":"汽车挡风玻璃碎了？简单几步恢复如初","pic":null,"created_at":"2017-12-28 12:50:21","weburl":"http://sp.uu139.com/webview/news?id=718"},{"id":717,"title":"【荐读】这10类员工请立马辞职","pic":null,"created_at":"2017-12-28 09:09:10","weburl":"http://sp.uu139.com/webview/news?id=717"},{"id":716,"title":"【荐读】领导会重点培养的6种人","pic":null,"created_at":"2017-12-28 09:06:50","weburl":"http://sp.uu139.com/webview/news?id=716"},{"id":715,"title":"大数据、业务创新和安全：智慧保险的\u201c一体两翼\u201d","pic":null,"created_at":"2017-12-28 09:02:20","weburl":"http://sp.uu139.com/webview/news?id=715"},{"id":714,"title":"云计算、物联网等科技应用重塑保险业竞争格局","pic":null,"created_at":"2017-12-28 09:00:45","weburl":"http://sp.uu139.com/webview/news?id=714"},{"id":713,"title":"保险入\u201c云\u201d端","pic":null,"created_at":"2017-12-28 08:58:48","weburl":"http://sp.uu139.com/webview/news?id=713"},{"id":712,"title":"保险人的权利与义务","pic":null,"created_at":"2017-12-28 08:55:01","weburl":"http://sp.uu139.com/webview/news?id=712"},{"id":711,"title":"【求真相】交通事故\u201c私了\u201d后，还能不能反悔呢？","pic":null,"created_at":"2017-12-28 08:32:50","weburl":"http://sp.uu139.com/webview/news?id=711"},{"id":710,"title":"【司法判例】保险公司\u201c拖一拖\u201d的代价\u2014\u2014保险人未及时作出核定的赔偿责任解析","pic":null,"created_at":"2017-12-28 08:26:54","weburl":"http://sp.uu139.com/webview/news?id=710"},{"id":709,"title":"【案例分享】超标电动车发生交通事故造成损害，生产者是否担责？","pic":null,"created_at":"2017-12-28 08:23:36","weburl":"http://sp.uu139.com/webview/news?id=709"}]
     * first_page_url : http://47.92.68.208:89/spi/news/index?sort%5Bcreated_at%5D=desc&filters%5Bstatus%5D=1&page=1
     * from : 1
     * last_page : 72
     * last_page_url : http://47.92.68.208:89/spi/news/index?sort%5Bcreated_at%5D=desc&filters%5Bstatus%5D=1&page=72
     * next_page_url : http://47.92.68.208:89/spi/news/index?sort%5Bcreated_at%5D=desc&filters%5Bstatus%5D=1&page=2
     * path : http://47.92.68.208:89/spi/news/index
     * per_page : 10
     * prev_page_url : null
     * to : 10
     * total : 718
     */

    private String name ;
    @SerializedName("current_page")
    private int currentPage;
    @SerializedName("first_page_url")
    private String firstPageUrl;
    @SerializedName("from")
    private int from;
    @SerializedName("last_page")
    private int lastPage;
    @SerializedName("last_page_url")
    private String lastPageUrl;
    @SerializedName("next_page_url")
    private String nextPageUrl;
    @SerializedName("path")
    private String path;
    @SerializedName("per_page")
    private int perPage;
    @SerializedName("prev_page_url")
    private Object prevPageUrl;
    @SerializedName("to")
    private int to;
    @SerializedName("total")
    private int total;
    @SerializedName("data")
    private List<Object> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getFirstPageUrl() {
        return firstPageUrl;
    }

    public void setFirstPageUrl(String firstPageUrl) {
        this.firstPageUrl = firstPageUrl;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public String getLastPageUrl() {
        return lastPageUrl;
    }

    public void setLastPageUrl(String lastPageUrl) {
        this.lastPageUrl = lastPageUrl;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public Object getPrevPageUrl() {
        return prevPageUrl;
    }

    public void setPrevPageUrl(Object prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }




}
