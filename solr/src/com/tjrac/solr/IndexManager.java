package com.tjrac.solr;

import java.io.IOException;
import java.util.Iterator;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * 索引的增删改查
 * @author Administrator
 *
 */
public class IndexManager {
	
	
	
	/**
	 * 增加 / 修改 索引
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Test
	public void insertAndUpdateIndex() throws SolrServerException, IOException{
		//创建 httpSolrServer -- 找到服务器的url
		HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
		
		//创建document对象  --若id不存在 则添加 若id已经存在 则修改
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "c1001");
		doc.addField("name", "solr test");
		server.add(doc);
		
		server.commit();
	}
	
	
	
	/**
	 * 删除索引
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Test
	public void deleteIndex() throws SolrServerException, IOException{
		HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
		
		//通过id删除索引
		server.deleteById("c1001");
		
		//根据条件来删除
		server.deleteByQuery("id:c1001");
		
		//删除全部
		server.deleteByQuery("*:*");
		
		server.commit();
	}
	
	

	
	
}
