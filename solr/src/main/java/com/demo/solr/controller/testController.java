package com.demo.solr.controller;

import com.demo.solr.utils.SolrInfo;
import com.demo.solr.utils.SolrUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class testController {
    @PostMapping("/getField")
    public Map<String,Object> getField(String name) throws Exception {
        SolrUtil solr = new SolrUtil();
        SolrInfo solrInfo = new SolrInfo();
        solrInfo.setQ(name);
        solrInfo.setDf("name");
        Map<String, Object> map = solr.queryDoc(solrInfo);
        return map;
    }
}
