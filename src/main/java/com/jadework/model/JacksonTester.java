package com.jadework.model;

/**
 * @Copyright: Copyright (c) 2016,${year},
 * @Description: ${todo}
 */

import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class JacksonTester {
    public static void main(String args[]){

        try {
            User user = new User("jade","pioneer");

            System.out.println(new ObjectMapper().writeValueAsString(user));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}