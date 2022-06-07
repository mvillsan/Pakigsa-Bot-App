package com.example.pakigsabot.EstCancellationPolicyBO;

public class RestoCancelPolModel {

    String restoCancelPolId;
    String restoCancelPolDesc;

    public RestoCancelPolModel() {
        //empty constructor needed
    }

    public RestoCancelPolModel(String restoCancelPolId, String restoCancelPolDesc) {
        this.restoCancelPolId = restoCancelPolId;
        this.restoCancelPolDesc = restoCancelPolDesc;
    }

    public String getRestoCancelPolId() {
        return restoCancelPolId;
    }

    public void setRestoCancelPolId(String restoCancelPolId) {
        this.restoCancelPolId = restoCancelPolId;
    }

    public String getRestoCancelPolDesc() {
        return restoCancelPolDesc;
    }

    public void setRestoCancelPolDesc(String restoCancelPolDesc) {
        this.restoCancelPolDesc = restoCancelPolDesc;
    }

}

