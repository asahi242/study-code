package com.asahi.demo.solr;

import org.apache.solr.client.solrj.SolrQuery;

public class SolrInfo {
    private String q;//solr查询参数
    private String[] fq;//给query增加过滤查询条件
    private String df;//设置默认搜索域，从哪个字段上查找
    private Integer startRow;//设置分页参数 开始页
    private Integer endRow;//设置分页参数 结束页
    private Sort sort;
    private boolean isHightlight;
    private Hightlight hightlight = new Hightlight();

    public SolrInfo(String q, String[] fq, String df, Integer startRow, Integer endRow,
                    Sort sort, boolean isHightlight, Hightlight hightlight) {
        this.q = q;
        this.fq = fq;
        this.df = df;
        this.startRow = startRow;
        this.endRow = endRow;
        this.sort = sort;
        this.isHightlight = isHightlight;
        this.hightlight = hightlight;
    }

    public SolrInfo() {
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String[] getFq() {
        return fq;
    }

    public void setFq(String[] fq) {
        this.fq = fq;
    }

    public String getDf() {
        return df;
    }

    public void setDf(String df) {
        this.df = df;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getEndRow() {
        return endRow;
    }

    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public boolean isHightlight() {
        return isHightlight;
    }

    public Hightlight getHightlight() {
        return hightlight;
    }

    public void setHightlight(Hightlight hightlight) {
        this.hightlight = hightlight;
    }

    //设置高亮显示以及结果的样式
    static class Hightlight{
        private String HighlightField = "name";
        private String HighlightSimplePre = "<font color='red'>";
        private String HighlightSimplePost = "</font>";

        public Hightlight() {
        }

        public Hightlight(String highlightField,
                          String highlightSimplePre, String highlightSimplePost) {
            HighlightField = highlightField;
            HighlightSimplePre = highlightSimplePre;
            HighlightSimplePost = highlightSimplePost;
        }

        public String getHighlightField() {
            return HighlightField;
        }

        public void setHighlightField(String highlightField) {
            HighlightField = highlightField;
        }

        public String getHighlightSimplePre() {
            return HighlightSimplePre;
        }

        public void setHighlightSimplePre(String highlightSimplePre) {
            HighlightSimplePre = highlightSimplePre;
        }

        public String getHighlightSimplePost() {
            return HighlightSimplePost;
        }

        public void setHighlightSimplePost(String highlightSimplePost) {
            HighlightSimplePost = highlightSimplePost;
        }
    }
    //设置返回结果的排序规则
    static class Sort{
        private String sortName;
        private SolrQuery.ORDER sortOrder;

        public Sort(String sortName, SolrQuery.ORDER sortOrder) {
            this.sortName = sortName;
            this.sortOrder = sortOrder;
        }
        public Sort(){
        }

        public String getSortName() {
            return sortName;
        }

        public void setSortName(String sortName) {
            this.sortName = sortName;
        }

        public SolrQuery.ORDER getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(SolrQuery.ORDER sortOrder) {
            this.sortOrder = sortOrder;
        }
    }

}
