package com.app.gearapp.Model;

public class TodoModel {

    String id,vehicle_id,user_id,delivery_date,address,contact_name,last_name,
            contact_phone,email,sender_phone,delivery_time,eta,billing_address,
            owner_name,payment_mode,payment_amt,job_status,created_at,updated_at;

    public TodoModel(String id, String vehicle_id, String user_id, String delivery_date, String address, String contact_name, String last_name, String contact_phone, String email, String sender_phone, String delivery_time, String eta, String billing_address, String owner_name, String payment_mode, String payment_amt, String job_status, String created_at, String updated_at) {
        this.id = id;
        this.vehicle_id = vehicle_id;
        this.user_id = user_id;
        this.delivery_date = delivery_date;
        this.address = address;
        this.contact_name = contact_name;
        this.last_name = last_name;
        this.contact_phone = contact_phone;
        this.email = email;
        this.sender_phone = sender_phone;
        this.delivery_time = delivery_time;
        this.eta = eta;
        this.billing_address = billing_address;
        this.owner_name = owner_name;
        this.payment_mode = payment_mode;
        this.payment_amt = payment_amt;
        this.job_status = job_status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSender_phone() {
        return sender_phone;
    }

    public void setSender_phone(String sender_phone) {
        this.sender_phone = sender_phone;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(String billing_address) {
        this.billing_address = billing_address;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getPayment_amt() {
        return payment_amt;
    }

    public void setPayment_amt(String payment_amt) {
        this.payment_amt = payment_amt;
    }

    public String getJob_status() {
        return job_status;
    }

    public void setJob_status(String job_status) {
        this.job_status = job_status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
