package com.laganini.cloud.storage.repository;

import com.laganini.cloud.storage.r2dbc.entity.AbstractIdEntity;
import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@QueryEntity
@Table("test")
public class TestEntity
        extends AbstractIdEntity<Long>
{

    private String name;

}
