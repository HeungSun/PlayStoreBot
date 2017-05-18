package io.userhabit.kongsuny.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kongsuny on 2017. 5. 11..
 */

@Entity
@Table(name = "AppInfoList")
public class AppInfoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "imageUrl")
    private String imageUrl; //앱 아이콘 이미지 주소
    @Column(name = "title")
    private String title; //앱 이름
    @Column(name = "genre")
    private String genre; //앱 장르
    @Column(name = "updateDate")
    private String updateDate; //앱 업데이트 날짜
    @Column(name = "company")
    private String company; //앱 제작사
    @Column(name = "downLoads")
    private String downLoads; //앱 다운로드 수, ios는 없음
    @Column(name = "currentVersion")
    private String currentVersion; //앱 현재 버전
    @Column(name = "operationVersion")
    private String operationVersion; //앱 최소 버전
    @Column(name = "appInfoUpdateDate")
    private Date appInfoUpdateDate; //앱 최소 버전


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getAppInfoUpdateDate() {
        return appInfoUpdateDate;
    }

    public void setAppInfoUpdateDate(Date appInfoUpdateDate) {
        this.appInfoUpdateDate = appInfoUpdateDate;
    }
}

