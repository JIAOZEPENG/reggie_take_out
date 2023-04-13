package com.buba.pojo;

import lombok.Data;

@Data
public class User {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

    private String avatar;

    private int status;

    private String email;
}
