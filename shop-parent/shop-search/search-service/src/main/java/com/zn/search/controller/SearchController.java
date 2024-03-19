package com.zn.search.controller;

import com.zn.search.po.SearchRequest;
import com.zn.search.po.SearchResponse;
import com.zn.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张男
 * @date: 2023/12/17---13:57
 */
@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    @PostMapping("/query")
    public ResponseEntity<SearchResponse> queryGoodsByPage(@RequestBody SearchRequest searchRequest) {
        SearchResponse result = searchService.search(searchRequest);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(result);
    }
}
