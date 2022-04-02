package com.app.gearapp.Model;

public class RecipientModel {
    String id,recipient,recipient_type;

    public RecipientModel(String id, String recipient, String recipient_type) {
        this.id = id;
        this.recipient = recipient;
        this.recipient_type = recipient_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient_type() {
        return recipient_type;
    }

    public void setRecipient_type(String recipient_type) {
        this.recipient_type = recipient_type;
    }
}
