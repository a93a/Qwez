package com.example.qwez.repository.opentdb.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseBody implements Serializable
{

    @SerializedName("response_code")
    @Expose
    private int responseCode;
    @SerializedName("results")
    @Expose
    private List<Question> questions = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseBody() {
    }

    /**
     *
     * @param responseCode
     * @param questions
     */
    public ResponseBody(int responseCode, List<Question> questions) {
        super();
        this.responseCode = responseCode;
        this.questions = questions;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setResults(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "ResponseBody{" +
                "responseCode=" + responseCode +
                ", questions=" + questions +
                '}';
    }
}
