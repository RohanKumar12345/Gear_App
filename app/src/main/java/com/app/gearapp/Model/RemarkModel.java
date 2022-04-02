package com.app.gearapp.Model;

public class RemarkModel {
    String id,remarks;

    public RemarkModel(String id, String remarks) {
        this.id = id;
        this.remarks = remarks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
