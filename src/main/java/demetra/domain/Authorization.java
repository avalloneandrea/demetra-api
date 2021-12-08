package demetra.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Authorization {

    @JsonProperty("access_token")
    public String accessToken;

}
