/**
 * 
 */
package me.firecloud.ideawork.tradewatcher;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.firecloud.ideawork.tradewatcher.model.Trade;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author kkppccdd
 * 
 */
public class RestService {

	private String serviceEndPoint;

	private String modelName;

	private String userName;

	private String password;

	private CloseableHttpClient httpClient;

	private ObjectMapper objectMapper;

	public RestService(String serviceEndPoint, String modelName,
			String userName, String password) {
		this.serviceEndPoint = serviceEndPoint;
		this.modelName = modelName;
		this.userName = userName;
		this.password = password;

		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				this.userName, this.password);
		provider.setCredentials(AuthScope.ANY, credentials);

		httpClient = HttpClients.custom()
				.setDefaultCredentialsProvider(provider).build();

		objectMapper = new ObjectMapper();
	}

	public List<Trade> get(Map<String, Object> criteria) throws Exception {
		String criteriaJson = objectMapper.writeValueAsString(criteria);

		HttpGet request = new HttpGet(serviceEndPoint + "/" + modelName
				+ "?criteria=" + URLEncoder.encode(criteriaJson, "UTF-8"));

		request.addHeader("Accept", "application/json");

		CloseableHttpResponse response = httpClient.execute(request);
		try {
			if (response.getStatusLine().getStatusCode() == 200) {
				String json = EntityUtils.toString(response.getEntity());
				//
				Trade[] entities = objectMapper.readValue(json, Trade[].class);

				List<Trade> trades = new ArrayList<Trade>(entities.length);
				for (Trade trade : entities) {
					trades.add(trade);
				}

				return trades;
			} else {
				throw new Exception(response.getStatusLine().toString());
			}
		} finally {
			response.close();
		}

	}

	public Trade get(String identifier) throws Exception {
		HttpGet request = new HttpGet(serviceEndPoint + "/" + modelName + "/"
				+ identifier);
		request.addHeader("Accept", "application/json");

		CloseableHttpResponse response = httpClient.execute(request);
		try {
			if (response.getStatusLine().getStatusCode() == 200) {
				String json = EntityUtils.toString(response.getEntity());
				//
				Trade entity = objectMapper.readValue(json, Trade.class);

				return entity;
			} else {
				throw new Exception(response.getStatusLine().toString());
			}
		} finally {
			response.close();
		}
	}

	public Trade save(Trade entity) throws Exception {
		HttpPost request = new HttpPost(serviceEndPoint + "/" + modelName);

		String json = objectMapper.writeValueAsString(entity);

		request.addHeader("Content-Type", "application/json");
		request.addHeader("Accept", "application/json");
		request.setEntity(new StringEntity(json,"UTF-8"));

		CloseableHttpResponse response = httpClient.execute(request);

		try {
			if (response.getStatusLine().getStatusCode() == 200) {
				String responseJson = EntityUtils
						.toString(response.getEntity());
				//
				Trade responseEntity = objectMapper.readValue(responseJson,
						Trade.class);

				return responseEntity;
			} else {
				throw new Exception(response.getStatusLine().toString());
			}
		} finally {
			response.close();
		}
	}

	public Trade update(String identifier, Trade entity) throws Exception {
		HttpPut request = new HttpPut(serviceEndPoint + "/" + modelName + "/"
				+ entity.getId());

		String json = objectMapper.writeValueAsString(entity);

		request.addHeader("Accept", "application/json");
		
		StringEntity body =new StringEntity(json,"UTF-8");
		body.setContentType("application/json");
		request.setEntity(body);

		CloseableHttpResponse response = httpClient.execute(request);

		try {
			if (response.getStatusLine().getStatusCode() == 200) {
				String responseJson = EntityUtils
						.toString(response.getEntity());
				//
				Trade responseEntity = objectMapper.readValue(responseJson,
						Trade.class);

				return responseEntity;
			} else {
				throw new Exception(response.getStatusLine().toString());
			}
		} finally {
			response.close();
		}
	}

}
