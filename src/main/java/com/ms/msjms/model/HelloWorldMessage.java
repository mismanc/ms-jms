package com.ms.msjms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HelloWorldMessage implements Serializable {

    private static final long serialVersionUID = -6774736287282L;

    private UUID id;
    private String message;

}
