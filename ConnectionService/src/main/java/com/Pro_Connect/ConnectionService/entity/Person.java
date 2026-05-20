package com.Pro_Connect.ConnectionService.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class Person {

    @Id
    @GeneratedValue
//    This is the id created by neo4j automatically
    private Long id;

//  This is the actual userId
    private Long userId;
    private String name;
}
