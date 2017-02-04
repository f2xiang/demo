package com.tjrac.solr;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class IndexSearch {
	
	
	
	/**
	 * 简单查询
	 * @throws SolrServerException 
	 */
	@Test
	public void simpleSearch() throws SolrServerException{
		//创建HttpSolrServer
		HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
		
		//创建solrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		//输入查询条件
		solrQuery.setQuery("product_name:小黄人");
		//开始查询， 返回查询结果
		QueryResponse queryResponse = server.query(solrQuery);
		//获取匹配到的所有结果
		SolrDocumentList results = queryResponse.getResults();
		
		//匹配到的结果总数
		long count = results.getNumFound();
		System.out.println("匹配到的结果总数："+count);
		
		//遍历结果
		for (SolrDocument doc : results) {
			System.out.println(doc.get("id"));
			System.out.println(doc.get("product_name"));
			System.out.println(doc.get("product_catalog"));
			System.out.println(doc.get("product_picture"));
			System.out.println(doc.get("product_price"));
			System.out.println("=============================");
		}
		
	}
	
	
	
	/**
	 * 复杂查询
	 */
	@Test
	public void complexQuery() throws Exception{
		//创建服务
		HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
		//创建查询对象
		SolrQuery solrQuery = new SolrQuery();
		
		//输入查询条件
		solrQuery.setQuery("product_name:小黄人");
		//设置过滤条件，如果需要设置多个过滤条件的话， 需要使用add
		solrQuery.setFilterQueries("product_price:[1 TO 10]");
//		solrQuery.addFilterQuery("");
		//设置排序
		solrQuery.setSort("product_price", ORDER.asc);
		//设置分页信息
		solrQuery.setStart(0);
		solrQuery.setRows(10);
		//设置显示的field的域的集合
		solrQuery.setFields("id, product_price, product_name, product_catalog, product_picture");
		//设置默认的域
		solrQuery.set("df", "product_keywords");
		//设置高亮信息
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("product_name");
		solrQuery.setHighlightSimplePre("<em>");
		solrQuery.setHighlightSimplePost("</em>");
		
		//执行查询 并 返回结果
		QueryResponse response = server.query(solrQuery);
		SolrDocumentList list = response.getResults();
		//结果总数
		long count = list.getNumFound();
		System.out.println("结果总数："+count);
		
		//获取高亮显示的信息 --高亮的信息 是放在一个list集合里面 外面是map套着 
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		for (SolrDocument doc : list) {
			System.out.println(doc.get("id"));
			
			List<String> list2 = highlighting.get(doc.get("id")).get("product_name");
			if(list2 != null){
				System.out.println("高亮显示的商品名称是："+list2.get(0));
			}else{
				System.out.println(doc.get("product_name"));
			}
			
			System.out.println(doc.get("product_catalog"));
			System.out.println(doc.get("product_picture"));
			System.out.println(doc.get("product_price"));
			System.out.println("=============================");
		}
		
	}
	
	
}
