package de.dm.intellij.liferay.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import com.intellij.openapi.util.text.StringUtil;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;

public class ServiceInvoker {

    private final WebTarget invokeTarget;
    private final WebTarget jsonwsTarget;
    private final ObjectMapper mapper;

    public ServiceInvoker(URI endpoint) {
        this(endpoint, null, null);
    }

    public ServiceInvoker(URI endpoint, String username, String password) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(MultiPartFeature.class);

        Client client = ClientBuilder.newClient(clientConfig);

        if ( (StringUtil.isNotEmpty(username)) && (StringUtil.isNotEmpty(password)) ) {
            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
            client.register(feature);
        }

        client.property(ClientProperties.CONNECT_TIMEOUT, 3000);
        client.property(ClientProperties.READ_TIMEOUT, 3000);

        mapper = new ObjectMapper();
        mapper.registerModule(new JsonOrgModule());
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);
        client.register(provider);

        jsonwsTarget =  client.target(endpoint);
        invokeTarget = jsonwsTarget.path(Constants.PATH_INVOKE);
    }

    public <T> T invoke(String command, JSONObject params, Class<T> clazz) throws JSONException, IOException {
        return invoke(command, params, clazz, null);
    }

    public <T> T invoke(String command, JSONObject params, Class<T> clazz, Map<String, File> files) throws JSONException, IOException {
        JSONObject commandObject = new JSONObject();
        commandObject.put(command, params);

        Invocation.Builder builder;
        Response clientResponse;

        if (files != null) {
            MultiPart multiPart = new MultiPart().type(MediaType.MULTIPART_FORM_DATA_TYPE);

            for (Map.Entry<String, File> entry : files.entrySet()) {
                if (entry.getValue().exists()) {
                    FileDataBodyPart fileDataBodyPart = new FileDataBodyPart(entry.getKey(), entry.getValue(), MediaType.APPLICATION_OCTET_STREAM_TYPE);
                    multiPart.bodyPart(fileDataBodyPart);
                }
            }
            Iterator<String> it = params.keys();
            while (it.hasNext()) {
                String key = it.next();
                Object value = params.opt(key);

                if ("serviceContext".equals(key)) {
                    FormDataBodyPart formDataBodyPart = new FormDataBodyPart("+serviceContext", "com.liferay.portal.kernel.service.ServiceContext");
                    multiPart.bodyPart(formDataBodyPart);

                    JSONObject serviceContext = (JSONObject)value;
                    JSONObject attributes = serviceContext.getJSONObject("attributes");
                    if (attributes != null) {
                        Iterator<String> attributesIterator = attributes.keys();
                        while (attributesIterator.hasNext()) {
                            String attributeName = attributesIterator.next();
                            Object attributeValue = attributes.opt(attributeName);

                            String attributeValueString;
                            if (attributeValue instanceof String[]) {
                                attributeValueString = "[" + StringUtil.join((String[])attributeValue) + "]";
                            } else {
                                attributeValueString = String.valueOf(attributeValue);
                            }

                            formDataBodyPart = new FormDataBodyPart("serviceContext.attributes." + attributeName, attributeValueString);
                            multiPart.bodyPart(formDataBodyPart);
                        }
                    }

                } else {
                    FormDataBodyPart formDataBodyPart = new FormDataBodyPart(key, String.valueOf(value));
                    multiPart.bodyPart(formDataBodyPart);
                }
            }
            builder = jsonwsTarget.path(command).request(MediaType.MULTIPART_FORM_DATA);

            clientResponse = builder.post(Entity.entity(multiPart, multiPart.getMediaType()));
        } else {
            builder = invokeTarget.request(MediaType.APPLICATION_JSON);

            Entity<String> entity = Entity.entity(mapper.writeValueAsString(commandObject), MediaType.APPLICATION_JSON);

            clientResponse = builder.post(entity);
        }

        String response = clientResponse.readEntity(String.class);
        if ( (response != null) && (response.length() > 0) ) {
            if (clazz == String.class) {
                return (T) response;
            } else if (clazz == Long.class) {
                return (T) Long.valueOf(response);
            } else if (clazz == JSONObject.class) {
                return (T)mapper.readValue(response, JSONObject.class);
            } else if (clazz == JSONArray.class) {
                return (T)mapper.readValue(response, JSONArray.class);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
