package io.userhabit.kongsuny.model;

/**
 * Created by kongsuny on 2017. 5. 11..
 */
public class AppInfoModel {
    
    private String imageUrl; //앱 아이콘 이미지 주소
    private String title; //앱 이름
    private String genre; //앱 장르
    private String updateDate; //앱 업데이트 날짜
    private String company; //앱 파일사이즈
    private String downLoads; //앱 다운로드 수 
    private String currentVersion; //앱 현재 버전
    private String operationVersion; //앱 최소 버전


    public AppInfoModel(String imageUrl, String title, String genre, String updateDate, String company, String downLoads, String currentVersion, String operationVersion) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.genre = genre;
        this.updateDate = updateDate;
        this.company = company;
        this.downLoads = downLoads;
        this.currentVersion = currentVersion;
        this.operationVersion = operationVersion;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getcompany() {
        return company;
    }

    public void setcompany(String company) {
        this.company = company;
    }

    public String getDownLoads() {
        return downLoads;
    }

    public void setDownLoads(String downLoads) {
        this.downLoads = downLoads;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getOperationVersion() {
        return operationVersion;
    }

    public void setOperationVersion(String operationVersion) {
        this.operationVersion = operationVersion;
    }
}

