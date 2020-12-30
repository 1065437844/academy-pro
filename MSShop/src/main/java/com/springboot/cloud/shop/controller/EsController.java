package com.springboot.cloud.shop.controller;


import com.springboot.cloud.shop.entity.UserEs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EsController {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @GetMapping("/TestEs")
    public void save() {
        UserEs user = new UserEs();
        user.setId(1001L);
        user.setName("李四");
        user.setAge(18);
        user.setHobby("足球、篮球、听音乐");

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withObject(user).build();

        String index = this.elasticsearchTemplate.index(indexQuery);

        System.out.println(index);
    }
}
