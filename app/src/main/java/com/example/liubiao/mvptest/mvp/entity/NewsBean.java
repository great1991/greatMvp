package com.example.liubiao.mvptest.mvp.entity;

import java.util.List;

/**
 * Created by liubiao on 2016/12/7.
 */

public class NewsBean {


    /**
     * imgextra : [{"imgsrc":"http://cms-bucket.nosdn.127.net/0ed87d22e8704535b3289af1ee88d53c20161208074833.jpeg"},{"imgsrc":"http://cms-bucket.nosdn.127.net/ab690263b63c401187371a545e97ab5e20161208074833.jpeg"}]
     * template : recommend
     * skipID : 00AO0001|2218394
     * lmodify : 2016-12-08 08:39:34
     * postid : PHOT23MCQ000100A
     * source : 网易原创
     * title : 巴基斯坦载48人客机失事 黑匣子被找到
     * hasImg : 0
     * digest :
     * alias : Region
     * boardid : photoview_bbs
     * photosetID : 00AO0001|2218394
     * hasAD : 0
     * imgsrc : http://cms-bucket.nosdn.127.net/b354c9031d9d4edfb753b55d5ef8161120161208074833.jpeg
     * ptime : 2016-12-08 07:48:44
     * hasHead : 1
     * order : 1
     * votecount : 590
     * hasCover : false
     * docid : 9IG74V5H00963VRO_C7OFDA9AluowenwenupdateDoc
     * tname : 社会
     * priority : 200
     * ename : shehui
     * replyCount : 683
     * imgsum : 12
     * hasIcon : true
     * skipType : photoset
     * cid : C1348647991705
     */
    private List<AdsBean> ads;
    private List<ImgextraEntity> imgextra;
    private String template;
    private String skipID;
    private String lmodify;
    private String postid;
    private String source;
    private String title;
    private int hasImg;
    private String digest;
    private String alias;
    private String boardid;
    private String photosetID;
    private int hasAD;
    private String imgsrc;
    private String ptime;
    private int hasHead;
    private int order;
    private int votecount;
    private boolean hasCover;
    private String docid;
    private String tname;
    private int priority;
    private String ename;
    private int replyCount;
    private int imgsum;
    private boolean hasIcon;
    private String skipType;
    private String cid;

    public void setImgextra(List<ImgextraEntity> imgextra) {
        this.imgextra = imgextra;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setSkipID(String skipID) {
        this.skipID = skipID;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHasImg(int hasImg) {
        this.hasImg = hasImg;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    public void setPhotosetID(String photosetID) {
        this.photosetID = photosetID;
    }

    public void setHasAD(int hasAD) {
        this.hasAD = hasAD;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public void setHasHead(int hasHead) {
        this.hasHead = hasHead;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setVotecount(int votecount) {
        this.votecount = votecount;
    }

    public void setHasCover(boolean hasCover) {
        this.hasCover = hasCover;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public void setImgsum(int imgsum) {
        this.imgsum = imgsum;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<ImgextraEntity> getImgextra() {
        return imgextra;
    }

    public String getTemplate() {
        return template;
    }

    public String getSkipID() {
        return skipID;
    }

    public String getLmodify() {
        return lmodify;
    }

    public String getPostid() {
        return postid;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public int getHasImg() {
        return hasImg;
    }

    public String getDigest() {
        return digest;
    }

    public String getAlias() {
        return alias;
    }

    public String getBoardid() {
        return boardid;
    }

    public String getPhotosetID() {
        return photosetID;
    }

    public int getHasAD() {
        return hasAD;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public String getPtime() {
        return ptime;
    }

    public int getHasHead() {
        return hasHead;
    }

    public int getOrder() {
        return order;
    }

    public int getVotecount() {
        return votecount;
    }

    public boolean isHasCover() {
        return hasCover;
    }

    public String getDocid() {
        return docid;
    }

    public String getTname() {
        return tname;
    }

    public int getPriority() {
        return priority;
    }

    public String getEname() {
        return ename;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public int getImgsum() {
        return imgsum;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public String getSkipType() {
        return skipType;
    }

    public String getCid() {
        return cid;
    }

    public class ImgextraEntity {
        /**
         * imgsrc : http://cms-bucket.nosdn.127.net/0ed87d22e8704535b3289af1ee88d53c20161208074833.jpeg
         */
        private String imgsrc;

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getImgsrc() {
            return imgsrc;
        }
    }
    public static class AdsBean {
        private String title;
        private String tag;
        private String imgsrc;
        private String subtitle;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }



}
