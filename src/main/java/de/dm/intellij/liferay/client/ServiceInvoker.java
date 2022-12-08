package de.dm.intellij.liferay.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intellij.openapi.util.text.StringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class ServiceInvoker {

    //TODO rewrite to Apache Http and Google gson

    private final URI invokeTarget;
    private final URI jsonwsTarget;

    private final HttpClient httpClient;

    public ServiceInvoker(URI endpoint) {
        this(endpoint, null, null);
    }

    public ServiceInvoker(URI endpoint, String username, String password) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder
                .create()
                .setRoutePlanner(new SystemDefaultRoutePlanner(null));

        if ( (StringUtil.isNotEmpty(username)) && (StringUtil.isNotEmpty(password)) ) {
            CredentialsProvider provider = new BasicCredentialsProvider();

            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);

            provider.setCredentials(AuthScope.ANY, credentials);

            httpClientBuilder.setDefaultCredentialsProvider(provider);
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000)
                .setSocketTimeout(3000)
                .setConnectionRequestTimeout(3000)
                .build();

        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        httpClient = httpClientBuilder.build();

        //clientConfig.register(MultiPartFeature.class);

        /*
        mapper = new ObjectMapper();
        mapper.registerModule(new JsonOrgModule());
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);
        client.register(provider);

         */

        jsonwsTarget = endpoint;
        invokeTarget = jsonwsTarget.resolve(Constants.PATH_INVOKE);
    }

    public <T> T invoke(String command, JsonObject params, Class<T> clazz) throws IOException {
        return invoke(command, params, clazz, null);
    }

    public <T> T invoke(String command, JsonObject params, Class<T> clazz, Map<String, File> files) throws IOException {
        JsonObject commandObject = new JsonObject();
        commandObject.add(command, params);

        //Invocation.Builder builder;

        HttpResponse response;

        if (files != null) {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            for (Map.Entry<String, File> entry : files.entrySet()) {
                if (entry.getValue().exists()) {
                    FileBody fileBody = new FileBody(entry.getValue(), ContentType.APPLICATION_OCTET_STREAM, entry.getKey());

                    builder.addPart(entry.getKey(), fileBody);
                }
            }

            for (String key : params.keySet()) {
                Object value = params.get(key);

                if ("serviceContext".equals(key)) {
                    StringBody formDataBodyPart = new StringBody("com.liferay.portal.kernel.service.ServiceContext", ContentType.MULTIPART_FORM_DATA);
                    builder.addPart("+serviceContext", formDataBodyPart);

                    JsonObject serviceContext = (JsonObject) value;

                    JsonObject attributes = serviceContext.getAsJsonObject("attributes");

                    if (attributes != null) {
                        for (String attributeName : attributes.keySet()) {
                            JsonElement attributeValue = attributes.get(attributeName);

                            String attributeValueString = attributeValue.getAsString();

                            /*
                            if (attributeValue instanceof String[]) {
                                attributeValueString = "[" + StringUtil.join((String[]) attributeValue) + "]";
                            } else {
                                attributeValueString = String.valueOf(attributeValue);
                            }
                            */

                            formDataBodyPart = new StringBody(attributeValueString, ContentType.MULTIPART_FORM_DATA);
                            builder.addPart("serviceContext.attributes." + attributeName, formDataBodyPart);
                        }
                    }

                } else {
                    StringBody formDataBodyPart = new StringBody( String.valueOf(value), ContentType.MULTIPART_FORM_DATA);
                    builder.addPart(key, formDataBodyPart);
                }
            }

            HttpPost httpPost = new HttpPost(jsonwsTarget.resolve(command));
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);

            response = httpClient.execute(httpPost);
        } else {
            HttpPost httpPost = new HttpPost(invokeTarget);

            httpPost.setEntity(new StringEntity(commandObject.toString(), ContentType.APPLICATION_JSON));

            response = httpClient.execute(httpPost);
        }

        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);

        if (StringUtil.isNotEmpty(result)) {
            if (clazz == String.class) {
                return (T) response;
            } else if (clazz == Long.class) {
                return (T) Long.valueOf(result);
            } else if (clazz == JsonObject.class) {
                return (T)new Gson().fromJson(result, JsonObject.class);
            } else if (clazz == JsonArray.class) {
                return (T)new Gson().fromJson(result, JsonArray.class);
            } else {
                return null;
            }
        }

        return null;

    }

}
