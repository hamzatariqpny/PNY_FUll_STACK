package com.pny.pny67_68.repository.model;

public class Chat {

    public String chatID ="";
    public String messageId ="";
    public String fromMessageId = "";
    public String fromMessageName = "";
    public String toMessageId = "";
    public String toMessageName = "";
    public String txtMessage = "";
    public String timeStamp = "";

    public Chat(String chatID, String messageId,String fromMessageId, String fromMessageName, String toMessageId, String toMessageName, String txtMessage, String timeStamp) {
        this.chatID = chatID;
        this.messageId = messageId;
        this.fromMessageId = fromMessageId;
        this.fromMessageName = fromMessageName;
        this.toMessageId = toMessageId;
        this.toMessageName = toMessageName;
        this.txtMessage = txtMessage;
        this.timeStamp = timeStamp;
    }

    public Chat(){

    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getFromMessageId() {
        return fromMessageId;
    }

    public void setFromMessageId(String fromMessageId) {
        this.fromMessageId = fromMessageId;
    }

    public String getFromMessageName() {
        return fromMessageName;
    }

    public void setFromMessageName(String fromMessageName) {
        this.fromMessageName = fromMessageName;
    }

    public String getToMessageId() {
        return toMessageId;
    }

    public void setToMessageId(String toMessageId) {
        this.toMessageId = toMessageId;
    }

    public String getToMessageName() {
        return toMessageName;
    }

    public void setToMessageName(String toMessageName) {
        this.toMessageName = toMessageName;
    }

    public String getTxtMessage() {
        return txtMessage;
    }

    public void setTxtMessage(String txtMessage) {
        this.txtMessage = txtMessage;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }




}
