package org.webmaple.common.model;

/**
 * @author lyifee
 * on 2020/12/27
 */
public class SpiderAdvance {
    /**
     * 爬取站点
     */
    private String site;

    /**
     * 总页数/url数
     */
    private int pageNum;

    /**
     * 当前页数/url数
     */
    private int temp;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
