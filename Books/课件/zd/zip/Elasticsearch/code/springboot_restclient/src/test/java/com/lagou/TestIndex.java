package com.lagou;

import org.apache.http.util.EntityUtils;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;

import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestIndex {
    @Autowired
    RestHighLevelClient  client;
    // ???????????????
    /*
    *
    PUT /elasticsearch_test
    {
      "settings": {},
      "mappings": {
        "properties": {
          "description": {
            "type": "text",
            "analyzer": "ik_max_word"
          },
          "name": {
            "type": "keyword"
          },
          "pic": {
            "type": "text",
            "index": false
          },
          "studymodel": {
            "type": "keyword"
          }
        }
      }
    }
    * */
    @Test
   public   void   testCreateIndex() throws IOException {
       // ????????????????????????????????????
        CreateIndexRequest  createIndexRequest  = new CreateIndexRequest("elasticsearch_test");
        //????????????
       /* XContentBuilder   builder  = XContentFactory.jsonBuilder()
                .startObject()
                .field("properties")
                .startObject()
                .field("description").startObject().field("type","text").field("analyzer","ik_max_word").endObject()
                .field("name").startObject().field("type","keyword").endObject()
                .field("pic").startObject().field("type","text").field("index","false").endObject()
                .field("studymodel").startObject().field("type","keyword").endObject()
                .endObject()
                .endObject();
        createIndexRequest.mapping("doc",builder);

        */

        createIndexRequest.mapping("doc","{\n" +
                "        \"properties\": {\n" +
                "          \"description\": {\n" +
                "            \"type\": \"text\",\n" +
                "            \"analyzer\": \"ik_max_word\"\n" +
                "          },\n" +
                "          \"name\": {\n" +
                "            \"type\": \"keyword\"\n" +
                "          },\n" +
                "          \"pic\": {\n" +
                "            \"type\": \"text\",\n" +
                "            \"index\": false\n" +
                "          },\n" +
                "          \"studymodel\": {\n" +
                "            \"type\": \"keyword\"\n" +
                "          }\n" +
                "        }\n" +
                "      }", XContentType.JSON);
        // ????????????????????????
        IndicesClient  indicesClient  = client.indices();

        CreateIndexResponse  createIndexResponse = indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
        // ????????????
        boolean  acknowledged = createIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
   }

   @Test
    public   void   testDeleteIndex() throws  IOException{
        // ?????? ??????????????????????????????
        DeleteIndexRequest   deleteIndexRequest  = new DeleteIndexRequest("elasticsearch_test");
        IndicesClient   indicesClient  = client.indices();

        AcknowledgedResponse  deleteResponse = indicesClient.delete(deleteIndexRequest,RequestOptions.DEFAULT);
        // ????????????
        boolean   acknowledge = deleteResponse.isAcknowledged();
       System.out.println(acknowledge);
    }


   //????????????
    /*
    POST /elasticsearch_test/_doc/1
    {
      "name": "spring cloud??????",
      "description": "????????????????????????????????????????????? 1.????????????????????? 2.spring cloud ???????????? 3.??????Spring Boot 4.????????????eureka???",
      "studymodel":"201001",
      "timestamp": "2020-08-22 20:09:18",
      "price": 5.6
    }
     */
    @Test
    public   void   testAddDoc()throws  IOException{
        // ????????????????????????
        //IndexRequest indexRequest  = new IndexRequest("elasticsearch_test","doc");
        IndexRequest indexRequest  = new IndexRequest("elasticsearch_test");

        //indexRequest.id("2");
        // ????????????  ??????json??????
        Map<String,Object>  jsonMap = new HashMap<>();
        jsonMap.put("name","spring cloud??????3");
        jsonMap.put("description","??????????????????????????????????????????3??? 1.????????????????????? 2.spring cloud ???????????? 3.??????Spring Boot 4.????????????eureka???");
        jsonMap.put("studymodel","3101001");
        jsonMap.put("timestamp","2020-07-22 20:09:18");
        jsonMap.put("price",35.6);
        indexRequest.source(jsonMap);
        // ????????????
        IndexResponse  indexResponse = client.index(indexRequest,RequestOptions.DEFAULT);
        DocWriteResponse.Result  result = indexResponse.getResult();
        System.out.println(result);
    }

    // ????????????
    @Test
    public  void  testGetDoc()throws  IOException{
        // ??????????????????
        GetRequest  getRequest  = new GetRequest("elasticsearch_test","12");
        GetResponse  getResponse  = client.get(getRequest,RequestOptions.DEFAULT);

        // ??????????????????
        Map<String,Object> sourceMap = getResponse.getSourceAsMap();
        System.out.println(sourceMap);
    }

    //??????????????????
    /*
       GET   /elasticsearch_test/_search
        {
          "query":{
             "match_all":{}
          }
        }
    */
    @Test
    public  void   testSearchAll()throws  IOException{
        // ??????????????????
        SearchRequest  searchRequest = new SearchRequest("elasticsearch_test");
        searchRequest.searchType(SearchType.QUERY_THEN_FETCH);
        // ?????????????????????
        SearchSourceBuilder  searchSourceBuilder = new SearchSourceBuilder();
        // ??????????????????
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        // ?????????????????? ???????????????
        searchRequest.source(searchSourceBuilder);
        // ??????client  ????????????
        SearchResponse searchResponse  = client.search(searchRequest,RequestOptions.DEFAULT);
        // ????????????
        SearchHits  hits = searchResponse.getHits();
        // ????????????????????????
        TotalHits  totalHits  = hits.getTotalHits();
        System.out.println("????????????????????????:"+totalHits.value);
        // ??????????????????????????????
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit  hit : searchHits){
            String  id = hit.getId();
            // ??????????????????
            Map<String,Object>  sourceMap = hit.getSourceAsMap();
            String  name  = (String)sourceMap.get("name");
            String  timestamp  = (String)sourceMap.get("timestamp");
            String  description  = (String)sourceMap.get("description");
            Double   price  = (Double)sourceMap.get("price");
            System.out.println(name);
            System.out.println(timestamp);
            System.out.println(description);
            System.out.println(price);
        }
    }

    @Test
    public  void   testTermQuery()throws  IOException{
        // ??????????????????
        SearchRequest  searchRequest = new SearchRequest("elasticsearch_test");
        // ?????????????????????
        SearchSourceBuilder  searchSourceBuilder = new SearchSourceBuilder();
        // ??????????????????
        //searchSourceBuilder.query(QueryBuilders.termQuery("name","spring cloud??????"));
        searchSourceBuilder.query(QueryBuilders.termQuery("description","spring"));
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        // ?????????????????? ???????????????
        searchRequest.source(searchSourceBuilder);
        // ??????client  ????????????
        SearchResponse searchResponse  = client.search(searchRequest,RequestOptions.DEFAULT);

        // ????????????
        SearchHits  hits = searchResponse.getHits();
        // ????????????????????????
        TotalHits  totalHits  = hits.getTotalHits();
        System.out.println("????????????????????????:"+totalHits.value);
        // ??????????????????????????????
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit  hit : searchHits){
            String  id = hit.getId();
            // ??????????????????
            Map<String,Object>  sourceMap = hit.getSourceAsMap();
            String  name  = (String)sourceMap.get("name");
            String  timestamp  = (String)sourceMap.get("timestamp");
            String  description  = (String)sourceMap.get("description");
            Double   price  = (Double)sourceMap.get("price");
            System.out.println(name);
            System.out.println(timestamp);
            System.out.println(description);
            System.out.println(price);
        }
    }
    @Test
    public  void   testSearchAllPage()throws  IOException{
        // ??????????????????
        SearchRequest  searchRequest = new SearchRequest("elasticsearch_test");
        // ?????????????????????
        SearchSourceBuilder  searchSourceBuilder = new SearchSourceBuilder();
        // ??????????????????
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        // ??????????????????
        int  page = 2;
        int  size = 2;
        // ????????? from
        int  form = (page-1)*size;
        searchSourceBuilder.from(form);
        searchSourceBuilder.size(size);
        // ??????price ??????
        searchSourceBuilder.sort("price",SortOrder.DESC);
        // ?????????????????? ???????????????
        searchRequest.source(searchSourceBuilder);
        // ??????client  ????????????
        SearchResponse searchResponse  = client.search(searchRequest,RequestOptions.DEFAULT);
        // ????????????
        SearchHits  hits = searchResponse.getHits();
        // ????????????????????????
        TotalHits  totalHits  = hits.getTotalHits();
        System.out.println("????????????????????????:"+totalHits.value);
        // ??????????????????????????????
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit  hit : searchHits){
            String  id = hit.getId();
            // ??????????????????
            Map<String,Object>  sourceMap = hit.getSourceAsMap();
            String  name  = (String)sourceMap.get("name");
            String  timestamp  = (String)sourceMap.get("timestamp");
            String  description  = (String)sourceMap.get("description");
            Double   price  = (Double)sourceMap.get("price");
            System.out.println(name);
            System.out.println(timestamp);
            System.out.println(description);
            System.out.println(price);
        }
    }
    @Test
    public  void   testTermQueryPage()throws  IOException{
        // ??????????????????
        SearchRequest  searchRequest = new SearchRequest("elasticsearch_test");
        // ?????????????????????
        SearchSourceBuilder  searchSourceBuilder = new SearchSourceBuilder();
        // ??????????????????
        searchSourceBuilder.query(QueryBuilders.termQuery("name","spring cloud??????"));
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        // ??????????????????
        int  page = 1;
        int  size = 2;
        // ????????? from
        int  form = (page-1)*size;
        searchSourceBuilder.from(form);
        searchSourceBuilder.size(size);
        // ??????price ??????
        searchSourceBuilder.sort("price",SortOrder.DESC);
        // ?????????????????? ???????????????
        searchRequest.source(searchSourceBuilder);
        // ??????client  ????????????
        SearchResponse searchResponse  = client.search(searchRequest,RequestOptions.DEFAULT);
        // ????????????
        SearchHits  hits = searchResponse.getHits();
        // ????????????????????????
        TotalHits  totalHits  = hits.getTotalHits();
        System.out.println("????????????????????????:"+totalHits.value);
        // ??????????????????????????????
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit  hit : searchHits){
            String  id = hit.getId();
            // ??????????????????
            Map<String,Object>  sourceMap = hit.getSourceAsMap();
            String  name  = (String)sourceMap.get("name");
            String  timestamp  = (String)sourceMap.get("timestamp");
            String  description  = (String)sourceMap.get("description");
            Double   price  = (Double)sourceMap.get("price");
            System.out.println(name);
            System.out.println(timestamp);
            System.out.println(description);
            System.out.println(price);
        }
    }

    public List<Map<String,Object> > searchQuestionBO(String scrollId){
        // ??????????????????
        SearchRequest  searchRequest = new SearchRequest("elasticsearch_test");
        // ?????????????????????
        SearchSourceBuilder  searchSourceBuilder = new SearchSourceBuilder();
        // ??????????????????
        searchSourceBuilder.query(QueryBuilders.termQuery("name","spring cloud??????"));
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        // ??????price ??????
        searchSourceBuilder.sort("price",SortOrder.DESC);
        // ?????????????????? ???????????????
        searchRequest.source(searchSourceBuilder);
        List<Map<String,Object> > questionBOList = new ArrayList<>();
        SearchResponse searchResponse = null;
        boolean deepSearch = false;
        try {

            deepSearch = true;
            searchRequest.source().size(2);
            final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(30L));
            searchRequest.scroll(scroll);
            searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);

            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = client.scroll(scrollRequest,RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            SearchHit[] searchHit = searchHits.getHits();
            long total = searchHits.getTotalHits().value;
            for (int i = 0;i < searchHit.length;i++) {
                SearchHit item = searchHit[i];
                questionBOList.add(item.getSourceAsMap());
            }
            return questionBOList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionBOList;
    }
}





