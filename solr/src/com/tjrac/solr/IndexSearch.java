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
	 * �򵥲�ѯ
	 * @throws SolrServerException 
	 */
	@Test
	public void simpleSearch() throws SolrServerException{
		//����HttpSolrServer
		HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
		
		//����solrQuery����
		SolrQuery solrQuery = new SolrQuery();
		//�����ѯ����
		solrQuery.setQuery("product_name:С����");
		//��ʼ��ѯ�� ���ز�ѯ���
		QueryResponse queryResponse = server.query(solrQuery);
		//��ȡƥ�䵽�����н��
		SolrDocumentList results = queryResponse.getResults();
		
		//ƥ�䵽�Ľ������
		long count = results.getNumFound();
		System.out.println("ƥ�䵽�Ľ��������"+count);
		
		//�������
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
	 * ���Ӳ�ѯ
	 */
	@Test
	public void complexQuery() throws Exception{
		//��������
		HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
		//������ѯ����
		SolrQuery solrQuery = new SolrQuery();
		
		//�����ѯ����
		solrQuery.setQuery("product_name:С����");
		//���ù��������������Ҫ���ö�����������Ļ��� ��Ҫʹ��add
		solrQuery.setFilterQueries("product_price:[1 TO 10]");
//		solrQuery.addFilterQuery("");
		//��������
		solrQuery.setSort("product_price", ORDER.asc);
		//���÷�ҳ��Ϣ
		solrQuery.setStart(0);
		solrQuery.setRows(10);
		//������ʾ��field����ļ���
		solrQuery.setFields("id, product_price, product_name, product_catalog, product_picture");
		//����Ĭ�ϵ���
		solrQuery.set("df", "product_keywords");
		//���ø�����Ϣ
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("product_name");
		solrQuery.setHighlightSimplePre("<em>");
		solrQuery.setHighlightSimplePost("</em>");
		
		//ִ�в�ѯ �� ���ؽ��
		QueryResponse response = server.query(solrQuery);
		SolrDocumentList list = response.getResults();
		//�������
		long count = list.getNumFound();
		System.out.println("���������"+count);
		
		//��ȡ������ʾ����Ϣ --��������Ϣ �Ƿ���һ��list�������� ������map���� 
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		for (SolrDocument doc : list) {
			System.out.println(doc.get("id"));
			
			List<String> list2 = highlighting.get(doc.get("id")).get("product_name");
			if(list2 != null){
				System.out.println("������ʾ����Ʒ�����ǣ�"+list2.get(0));
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
