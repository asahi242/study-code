package com.demo.solr.utils;

import cn.hutool.core.bean.BeanUtil;
import com.demo.solr.entity.Person;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;

import java.io.IOException;
import java.util.*;

public class SolrUtil {
    //solr服务器所在的地址，test_core为自己创建的文档库目录
    private final static String SOLR_URL = "http://192.168.120.3:8983/solr/test_core";

    /**
     * 获取客户端的连接
     *
     * @return
     */
    public HttpSolrClient createSolrServer() {
        HttpSolrClient solr = new HttpSolrClient.Builder(SOLR_URL).withConnectionTimeout(10000)
                .withSocketTimeout(60000).build();
        return solr;
    }

    /**
     * 往索引库添加文档
     *
     * @throws SolrServerException
     * @throws IOException
     */
    public void addDoc(SolrInputDocument document) throws SolrServerException, IOException {
        HttpSolrClient solr = this.createSolrServer();
        solr.add(document);
        solr.commit();
        solr.close();
        System.out.println("添加成功: " + document.toString());
    }
    /**
     * 往索引库添加文档
     *
     * @throws SolrServerException
     * @throws IOException
     */
    public void addDoc(Object obj) throws SolrServerException, IOException {
        SolrInputDocument document = new SolrInputDocument();
        Map<String, Object> map;
        if (!(obj instanceof Map)){
            //hutool的BeanUtil
            map = BeanUtil.beanToMap(obj);

        }else {
            map = (Map<String, Object>) obj;
        }
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            document.addField(next.getKey(),next.getValue());
        }
        HttpSolrClient solr = this.createSolrServer();
        solr.add(document);
        solr.commit();
        solr.close();
        System.out.println("添加成功: " + document.toString());
    }
    /**
     * 往索引库添加文档
     *
     * @throws SolrServerException
     * @throws IOException
     */
    public void addDoc(List<Object> lists) throws SolrServerException, IOException {
        for (Object o:lists){
           this.addDoc(o);
        }
        System.out.println("添加成功: " + lists.toString());
    }

    /**
     * 根据ID从索引库删除文档
     *
     * @throws SolrServerException
     * @throws IOException
     */
    public void deleteDocById(String id) throws SolrServerException, IOException {
        HttpSolrClient server = this.createSolrServer();
        server.deleteById(id);
        server.commit();
        server.close();
        System.out.println("删除成功: " + id);
    }
    /**
     * 根据多个ID从索引库删除文档
     *
     * @throws SolrServerException
     * @throws IOException
     */
    public void deleteDocByIds(List<String> ids) throws SolrServerException, IOException {
        HttpSolrClient server = this.createSolrServer();
        server.deleteById(ids);
        server.commit();
        server.close();
        System.out.println("删除成功: " + ids.toString());
    }
    /**
     * 根据查询条件从索引库删除文档
     *
     * @throws SolrServerException
     * @throws IOException
     */
    public void deleteDocByQuery(String query) throws SolrServerException, IOException {
        HttpSolrClient server = this.createSolrServer();
        server.deleteByQuery(query);
        server.commit();
        server.close();
        System.out.println("删除成功: " + query);
    }

    /**
     * 根据设定的查询条件进行文档字段的高亮查询
     *
     * @throws Exception
     */
    public Map<String,Object> queryDoc(SolrInfo solrInfo) throws Exception {

        HttpSolrClient server = this.createSolrServer();
        SolrQuery query = new SolrQuery();

        //下面设置solr查询参数
        if(!StringUtils.isEmpty(solrInfo.getQ())){
            query.set("q", solrInfo.getQ());//相关查询，比如某条数据某个字段含有周、星、驰三个字  将会查询出来 ，这个作用适用于联想查询
        }
        //参数fq, 给query增加过滤查询条件
        if (solrInfo.getFq()!=null){
            query.addFilterQuery(solrInfo.getFq());
        }


        //参数df,给query设置默认搜索域，从哪个字段上查找
        if (!StringUtils.isEmpty(solrInfo.getDf())){
            query.set("df", solrInfo.getDf());
        }

        //参数sort,设置返回结果的排序规则
        if (solrInfo.getSort() != null) {
            query.setSort(solrInfo.getSort().getSortName(), solrInfo.getSort().getSortOrder());
        }

        //设置分页参数
        if (!StringUtils.isEmpty(String.valueOf(solrInfo.getStartRow()))){
            query.setStart(solrInfo.getStartRow());
        }
        if (!StringUtils.isEmpty(String.valueOf(solrInfo.getEndRow()))){
            query.setRows(solrInfo.getEndRow());
        }

        //设置高亮显示以及结果的样式
        if (solrInfo.isHightlight()) {
            query.addHighlightField(solrInfo.getHightlight().getHighlightField());
            query.setHighlightSimplePre(solrInfo.getHightlight().getHighlightSimplePre());
            query.setHighlightSimplePost(solrInfo.getHightlight().getHighlightSimplePost());
        }
        //执行查询
        QueryResponse response = server.query(query);
        //获取返回结果
        Map<String,Object> map = new HashMap<>();

        SolrDocumentList resultList = response.getResults();
        for (SolrDocument document : resultList) {
            Collection<String> fieldNames = document.getFieldNames();
            for (String fieldName : fieldNames) {
                //判断是否高亮
                if (solrInfo.isHightlight()){
                    Map<String, Map<String, List<String>>> hl = response.getHighlighting();
                    map.put("HLField",hl);
                    Map<String, List<String>> hlField = hl.get(document.getFieldValue("id"));
                    if(hlField.get(fieldName)!=null){
                        System.out.println(fieldName + ": " + hlField.get(fieldName).get(0));
                    }else{
                        System.out.println(fieldName + ": " + document.getFieldValue(fieldName));
                    }
                }else{
                    System.out.println(fieldName + ": " + document.getFieldValue(fieldName));
                }
            }
        }

        //获取实体对象形式
        List<Person> persons = response.getBeans(Person.class);
        map.put("objList",persons);
        return map;
    }

    public static void main(String[] args) throws Exception {
        SolrUtil solr = new SolrUtil();
        SolrInfo solrInfo = new SolrInfo();
        solrInfo.setQ("今天天气真好啊");
        solrInfo.setDf("name");
        solrInfo.setHightlight(true);
        Map<String, Object> map = solr.queryDoc(solrInfo);
        System.out.println(map.toString());

//        Person person = new Person();
//        person.setId("9");
//        person.setName("真好啊");
//        person.setDescription("bbb");
//        solr.addDoc(person);

//        List ids = new ArrayList();
//        ids.add("10");
//        solr.deleteDocByIds(ids);
    }
}