package com.sonarUser;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class NewUserTest {
	public static void main(String[] args) {
		NewUserTest newUserTest = new NewUserTest();
		newUserTest.CreateNewUser();
	}
	
	public void CreateNewUser() {
		
		ClientConfig clientConfig = new DefaultClientConfig();
		Client client = Client.create(clientConfig);
		client.addFilter(new LoggingFilter());
		WebResource webResource = client.resource("http://localhost:9000/api/authentication/login");
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("login", "admin");
		formData.add("password", "admin");
		ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(ClientResponse.class, formData);
		
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		} else {
			System.out.println(response.getHeaders().get("X-XSRF-TOKEN"));
			System.out.println("Response : " + response.getStatus());
			webResource = client.resource("http://localhost:9000/api/users/create");
			formData = new MultivaluedMapImpl();
			formData.add("login", "kussm12");
			formData.add("name", "kumar");
			formData.add("email", "kumar@admin.com");
			formData.add("password", "shanu");
			Builder weBuilder = webResource.header("X-XSRF-TOKEN", response.getCookies().get(1).getValue());
			for (int i = 0; i < response.getCookies().size(); i++) {
				weBuilder = weBuilder.cookie(response.getCookies().get(i));
			}
			response = weBuilder
					.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, formData);
			System.out.println("Response : " + response.getStatus());
		}
	}
}

//Request URL:http://localhost:9000/api/authentication/login
//login=admin&password=admin
//X-XSRF-TOKEN:lot5gfnvtavatgq55r5crrich7
//Cookie:XSRF-TOKEN=lot5gfnvtavatgq55r5crrich7;
//JWT-SESSION=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJBV0ZMaG5DVjhNOFliVlFSWGhUQyIsInN1YiI6ImFkbWluIiwiaWF0IjoxNTE3MzkwNTU3LCJleHAiOjE1MTc2NTUwMzIsImxhc3RSZWZyZXNoVGltZSI6MTUxNzM5MDU1NzMzMywieHNyZlRva2VuIjoibG90NWdmbnZ0YXZhdGdxNTVyNWNycmljaDcifQ.oYiD9zIfphV7Ua-2_IDk4roYeJia8C3OF08KNtcAL5g
//Request URL:http://localhost:9000/api/users/create
//login=abcd&name=abcd&email=abcd%40abcd.com&password=abcd&scmAccount=
