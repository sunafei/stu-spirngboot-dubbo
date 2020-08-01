package com.sun.api.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class UserInfo implements Serializable {
    private String name;
    private String tel;
}
