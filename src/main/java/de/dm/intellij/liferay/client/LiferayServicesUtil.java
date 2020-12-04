package de.dm.intellij.liferay.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class LiferayServicesUtil {

    private final ServiceInvoker serviceInvoker;

    private long defaultCompanyId;
    private long globalGroupId;
    private long companyGroupId;
    private Map<String, Long> classNameIds = new HashMap<>();

    public LiferayServicesUtil(URI endpoint) {
        this.serviceInvoker = new ServiceInvoker(endpoint);
    }

    public LiferayServicesUtil(URI endpoint, String username, String password) {
        this.serviceInvoker = new ServiceInvoker(endpoint, username, password);
    }

    public JSONArray getUserSitesGroup() throws IOException, JSONException {
        return serviceInvoker.invoke(Constants.CMD_GET_USER_SITES_GROUP, new JSONObject(), JSONArray.class);
    }

    public JSONObject getCompanyGroup(long companyId) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("companyId", companyId);

        return serviceInvoker.invoke(Constants.CMD_GET_COMPANY_GROUP, params, JSONObject.class);
    }

    public JSONObject getGroup(long companyId, String name) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("companyId", companyId);
        params.put("groupKey", name);

        return serviceInvoker.invoke(Constants.CMD_GET_GROUP, params, JSONObject.class);
    }

    public JSONObject getGroup(long groupId) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("groupId", groupId);

        return serviceInvoker.invoke(Constants.CMD_GET_GROUP, params, JSONObject.class);
    }

    public long getDefaultCompanyId() throws IOException, JSONException {
        if (defaultCompanyId == 0) {
            JSONArray userSites = getUserSitesGroup();
            if (userSites.length() > 0) {
                defaultCompanyId =  userSites.getJSONObject(0).getLong("companyId");
            }
        }

        return defaultCompanyId;
    }

    public long getCompanyGroupId() throws IOException, JSONException {
        if (companyGroupId == 0) {
            long companyId = getDefaultCompanyId();

            JSONObject companyGroup = getCompanyGroup(companyId);
            if (companyGroup != null) {
                companyGroupId = companyGroup.getLong("groupId");
            }
        }

        return companyGroupId;
    }

    public long getGlobalGroupId() throws IOException, JSONException {
        if (globalGroupId == 0) {
            long companyId = getDefaultCompanyId();

            JSONObject globalGroup = getGroup(companyId, String.valueOf(companyId));
            if (globalGroup != null) {
                globalGroupId = globalGroup.getLong("groupId");
            }
        }

        return globalGroupId;
    }

    public long getGroupId(String groupName) throws IOException, JSONException {
        if (GroupConstants.GLOBAL.equals(groupName)) {
            return getGlobalGroupId();
        }

        long companyId = getDefaultCompanyId();

        JSONObject group = getGroup(companyId, groupName);
        if (group != null) {
            return group.getLong("groupId");
        }

        return 0;
    }

    public JSONObject fetchClassName(String value) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("value", value);

        return serviceInvoker.invoke("/classname/fetch-class-name", params, JSONObject.class);
    }

    public String getVersion() throws IOException {
        return serviceInvoker.invoke("/portal/get-version", new JSONObject(), String.class);
    }

    public long getClassNameId(String className) throws IOException {
        Long result = classNameIds.get(className);

        if (result == null) {
            JSONObject classNameJSONObject = fetchClassName(className);
            result = classNameJSONObject.getLong("classNameId");

            classNameIds.put(className, result);
        }

        return result;
    }

    public JSONArray getJournalStructures(long groupId) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("groupId", groupId);

        return serviceInvoker.invoke("/journalstructure/get-structures", params, JSONArray.class);
    }

    public JSONObject getJournalStructure(long groupId, String name) throws JSONException, IOException {
        JSONArray journalStructures = getJournalStructures(groupId);
        for (int i = 0; i < journalStructures.length(); i++) {
            JSONObject journalStructure = journalStructures.getJSONObject(i);
            if (name.equalsIgnoreCase(LiferayXMLUtil.getName(journalStructure.getString("name")))) {
                return journalStructure;
            }
        }
        return null;
    }

    public JSONArray getDDMStructures_70(long companyId, long groupId, long classNameId, int status) throws JSONException, IOException {
        JSONArray groupIds = new JSONArray();
        groupIds.put(groupId);

        JSONObject params = new JSONObject();
        params.put("companyId", companyId);
        params.put("groupIds", groupIds);
        params.put("classNameId", classNameId);
        params.put("status", status);

        return serviceInvoker.invoke("/ddm.ddmstructure/get-structures", params, JSONArray.class);
    }

    public JSONArray getDDMStructures_73(long companyId, long groupId, long classNameId) throws JSONException, IOException {
        JSONArray groupIds = new JSONArray();
        groupIds.put(groupId);

        JSONObject params = new JSONObject();
        params.put("companyId", companyId);
        params.put("groupIds", groupIds);
        params.put("classNameId", classNameId);
        params.put("start", -1);
        params.put("end", -1);
        params.put("orderByComparator", JSONObject.NULL);

        return serviceInvoker.invoke("/ddm.ddmstructure/get-structures", params, JSONArray.class);
    }

    public JSONObject getDDMStructure(long companyId, long groupId, long classNameId, String name) throws JSONException, IOException {
        String version = getVersion();

        JSONArray ddmStructures;

        if (version.startsWith("7.3")) {
            ddmStructures = getDDMStructures_73(companyId, groupId, classNameId);
        } else {
            ddmStructures = getDDMStructures_70(companyId, groupId, classNameId, -1);
        }

        for (int i = 0; i < ddmStructures.length(); i++) {
            JSONObject ddmStructure = ddmStructures.getJSONObject(i);
            if (name.equalsIgnoreCase(LiferayXMLUtil.getName(ddmStructure.getString("name")))) {
                return ddmStructure;
            }
        }
        return null;
    }

    public JSONArray getJournalStructureTemplates(long groupId, String structureId) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("groupId", groupId);
        params.put("structureId", structureId);

        return serviceInvoker.invoke("/journaltemplate/get-structure-templates", params, JSONArray.class);
    }

    public JSONObject getJournalStructureTemplate(long groupId, String structureId, String name) throws JSONException, IOException {
        JSONArray journalTemplates = getJournalStructureTemplates(groupId, structureId);
        for (int i = 0; i < journalTemplates.length(); i++) {
            JSONObject journalTemplate = journalTemplates.getJSONObject(i);
            if (name.equalsIgnoreCase(LiferayXMLUtil.getName(journalTemplate.getString("name")))) {
                return journalTemplate;
            }
        }
        return null;
    }

    public JSONArray getDDMTemplatesByClassPK(long groupId, long classPK) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("groupId", groupId);
        params.put("classPK", classPK);

        return serviceInvoker.invoke("/ddmtemplate/get-templates-by-class-pk", params, JSONArray.class);
    }

    public JSONArray getDDMTemplates_70(long companyId, long groupId, long classNameId, long resourceClassNameId, long classPK) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("companyId", companyId);
        params.put("groupId", groupId);
        params.put("classNameId", classNameId);
        params.put("classPK", classPK);
        params.put("resourceClassNameId", resourceClassNameId);
        params.put("status", -1);

        return serviceInvoker.invoke("/ddm.ddmtemplate/get-templates", params, JSONArray.class);
    }

    public JSONObject getDDMTemplateByClassPK(long groupId, long classPK, String name) throws JSONException, IOException {
        JSONArray ddmTemplates = getDDMTemplatesByClassPK(groupId, classPK);
        for (int i = 0; i < ddmTemplates.length(); i++) {
            JSONObject ddmTemplate = ddmTemplates.getJSONObject(i);
            if (name.equalsIgnoreCase(LiferayXMLUtil.getName(ddmTemplate.getString("name")))) {
                return ddmTemplate;
            }
        }
        return null;
    }

    public JSONObject getDDMTemplate_70(long companyId, long groupId, long classNameId, long resourceClassNameId, long classPK, String name) throws JSONException, IOException {
        JSONArray ddmTemplates = getDDMTemplates_70(companyId, groupId, classNameId, resourceClassNameId, classPK);
        for (int i = 0; i < ddmTemplates.length(); i++) {
            JSONObject ddmTemplate = ddmTemplates.getJSONObject(i);
            if (name.equalsIgnoreCase(LiferayXMLUtil.getName(ddmTemplate.getString("name")))) {
                return ddmTemplate;
            }
        }
        return null;
    }

    public JSONObject getDDMStructureByName(String name, String groupName) throws IOException {
        long classNameId = getClassNameId(Constants.CLASS_NAME_JOURNAL_ARTICLE_7_0);
        long companyId = getDefaultCompanyId();
        long groupId = getGroupId(groupName);

        JSONObject ddmStructure = getDDMStructure(companyId, groupId, classNameId, name);

        return ddmStructure;
    }

    public JSONObject getDDMTemplateByName(long structureId, String name, String groupName) throws IOException {
        long companyId = getDefaultCompanyId();
        long groupId = getGroupId(groupName);

        long classNameIdDDMStructure = getClassNameId(Constants.CLASS_NAME_DDM_STRUCTURE_7_0);
        long journalArticleClassNameId = getClassNameId(Constants.CLASS_NAME_JOURNAL_ARTICLE_7_0);

        JSONObject ddmTemplate = getDDMTemplate_70(companyId, groupId, classNameIdDDMStructure, journalArticleClassNameId, structureId, name);

        return ddmTemplate;
    }

    public String getFreemarkerTemplateName(String structureName, String templateName, String groupName) throws IOException {
        JSONObject ddmStructure = getDDMStructureByName(structureName, groupName);
        if (ddmStructure != null) {
            JSONObject ddmTemplate = getDDMTemplateByName(ddmStructure.getLong("structureId"), templateName, groupName);
            if (ddmTemplate != null) {
                //Identifier contains companyGroupId if rendered within a request
                long companyGroupId = getCompanyGroupId();

                return ddmTemplate.getLong("companyId") + "#" + companyGroupId + "#" + ddmTemplate.getString("templateKey");
            }
        }

        return null;
    }
    public String getFreemarkerApplicationDisplayTemplateName(String type, String templateName, String groupName) throws IOException {
        String className = Constants.APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.get(type);
        if (className == null) {
            className = Constants.APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.get(type);
        }
        if (className != null) {
            long classNameId = getClassNameId(className);

            if (classNameId > 0) {
                long portletDisplayTemplateClassNameId = getClassNameId(Constants.CLASS_NAME_PORTLET_DISPLAY_TEMPLATE_7_0);

                long companyId = getDefaultCompanyId();
                long groupId = getGroupId(groupName);

                JSONObject ddmTemplate = getDDMTemplate_70(companyId, groupId, classNameId, portletDisplayTemplateClassNameId, 0, templateName);

                if (ddmTemplate != null) {
                    //Identifier contains companyGroupId if rendered within a request
                    long companyGroupId = getCompanyGroupId();

                    return ddmTemplate.getLong("companyId") + "#" + companyGroupId + "#" + ddmTemplate.getLong("templateId");
                }
            }
        }
        return null;
    }

}
