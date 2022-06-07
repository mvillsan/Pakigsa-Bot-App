package com.example.pakigsabot.FeedbackBO;

public class FeedbackModel {
    String feedbackID;
    String estID;
    String custID;
    String feedbackDate;
    String feedbackReview;
    String feedbackRating;
    String feedbackDesc;

    public FeedbackModel() {
        //empty constructor needed
    }

    public FeedbackModel(String feedbackID, String estID, String custID, String feedbackDate,
                         String feedbackReview, String feedbackRating, String feedbackDesc) {
        this.feedbackID = feedbackID;
        this.estID = estID;
        this.custID = custID;
        this.feedbackDate = feedbackDate;
        this.feedbackReview = feedbackReview;
        this.feedbackRating = feedbackRating;
        this.feedbackDesc = feedbackDesc;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getEstID() {
        return estID;
    }

    public void setEstID(String estID) {
        this.estID = estID;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getFeedbackReview() {
        return feedbackReview;
    }

    public void setFeedbackReview(String feedbackReview) {
        this.feedbackReview = feedbackReview;
    }

    public String getFeedbackRating() {
        return feedbackRating;
    }

    public void setFeedbackRating(String feedbackRating) {
        this.feedbackRating = feedbackRating;
    }

    public String getFeedbackDesc() {
        return feedbackDesc;
    }

    public void setFeedbackDesc(String feedbackDesc) {
        this.feedbackDesc = feedbackDesc;
    }
}
