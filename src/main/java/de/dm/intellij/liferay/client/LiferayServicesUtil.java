package de.dm.intellij.liferay.client;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class LiferayServicesUtil {

    private final static Logger log = Logger.getInstance(LiferayServicesUtil.class);

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

        JSONObject jsonObject = serviceInvoker.invoke(Constants.CMD_GET_COMPANY_GROUP, params, JSONObject.class);

        if (log.isDebugEnabled()) {
            log.debug("getCompanyId(" + companyId + ") = " + jsonObject.toString());
        }

        return jsonObject;
    }

    public JSONObject getGroup(long companyId, String name) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("companyId", companyId);
        params.put("groupKey", name);

        JSONObject jsonObject = serviceInvoker.invoke(Constants.CMD_GET_GROUP, params, JSONObject.class);

        if (log.isDebugEnabled()) {
            log.debug("getGroup(" + companyId + ", " + name + ") = " + jsonObject.toString());
        }

        return jsonObject;
    }

    public JSONObject getGroup(long groupId) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("groupId", groupId);

        JSONObject jsonObject = serviceInvoker.invoke(Constants.CMD_GET_GROUP, params, JSONObject.class);

        if (log.isDebugEnabled()) {
            log.debug("getGroup(" + groupId + ") = " + jsonObject.toString());
        }

        return jsonObject;
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

        JSONObject jsonObject = serviceInvoker.invoke("/classname/fetch-class-name", params, JSONObject.class);

        if (log.isDebugEnabled()) {
            log.debug("fetchClassName(" + value + ") = " + jsonObject.toString());
        }

        return jsonObject;
    }

    public String getVersion() throws IOException {
        String result = serviceInvoker.invoke("/portal/get-version", new JSONObject(), String.class);

        result = StringUtil.unquoteString(result);

        if (log.isDebugEnabled()) {
            log.debug("getVersion() = " + result);
        }

        return result;
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

        JSONArray jsonArray = serviceInvoker.invoke("/journalstructure/get-structures", params, JSONArray.class);

        if (log.isDebugEnabled()) {
            log.debug("getJournalStructures(" + groupId + ") = " + jsonArray.toString());
        }

        return jsonArray;
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

        JSONArray jsonArray = serviceInvoker.invoke("/ddm.ddmstructure/get-structures", params, JSONArray.class);

        if (log.isDebugEnabled()) {
            log.debug("getDDMStructures_70(" + companyId + ", " + groupId + ", " + classNameId + ", " + status + ") = " + jsonArray.toString());
        }

        return jsonArray;
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

        JSONArray jsonArray = serviceInvoker.invoke("/ddm.ddmstructure/get-structures", params, JSONArray.class);

        if (log.isDebugEnabled()) {
            log.debug("getDDMStructures_73(" + companyId + ", " + groupId + ", " + classNameId + ") = " + jsonArray.toString());
        }

        return jsonArray;
    }

    public JSONObject getDDMStructure(long companyId, long groupId, long classNameId, String structureKey) throws JSONException, IOException {
        String version = getVersion();

        JSONArray ddmStructures;

        if (version.startsWith("7.3") || version.startsWith("7.4")) {
            ddmStructures = getDDMStructures_73(companyId, groupId, classNameId);
        } else {
            ddmStructures = getDDMStructures_70(companyId, groupId, classNameId, -1);
        }

        for (int i = 0; i < ddmStructures.length(); i++) {
            JSONObject ddmStructure = ddmStructures.getJSONObject(i);
            if (structureKey.equals(ddmStructure.getString("structureKey"))) {
                return ddmStructure;
            }
        }
        return null;
    }

    public JSONArray getJournalStructureTemplates(long groupId, String structureId) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("groupId", groupId);
        params.put("structureId", structureId);

        JSONArray jsonArray = serviceInvoker.invoke("/journaltemplate/get-structure-templates", params, JSONArray.class);

        if (log.isDebugEnabled()) {
            log.debug("getJournalStructureTemplates(" + groupId + ", " + structureId + ") = " + jsonArray.toString());
        }

        return jsonArray;
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

        JSONArray jsonArray = serviceInvoker.invoke("/ddmtemplate/get-templates-by-class-pk", params, JSONArray.class);

        if (log.isDebugEnabled()) {
            log.debug("getDDMTemplatesByClassPK(" + groupId + ", " + classPK + ") = " + jsonArray.toString());
        }

        return jsonArray;
    }

    public JSONArray getDDMTemplates_70(long companyId, long groupId, long classNameId, long resourceClassNameId, long classPK) throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("companyId", companyId);
        params.put("groupId", groupId);
        params.put("classNameId", classNameId);
        params.put("classPK", classPK);
        params.put("resourceClassNameId", resourceClassNameId);
        params.put("status", -1);

        JSONArray jsonArray = serviceInvoker.invoke("/ddm.ddmtemplate/get-templates", params, JSONArray.class);

        if (log.isDebugEnabled()) {
            log.debug("getDDMTemplates_70(" + companyId + ", " + groupId + ", " + classNameId + ", "+ resourceClassNameId + ", " + classPK + ") = " + jsonArray.toString());
        }

        return jsonArray;
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

    public JSONObject getDDMTemplate_70(long companyId, long groupId, long classNameId, long resourceClassNameId, long classPK, String templateKey) throws JSONException, IOException {
        JSONArray ddmTemplates = getDDMTemplates_70(companyId, groupId, classNameId, resourceClassNameId, classPK);
        for (int i = 0; i < ddmTemplates.length(); i++) {
            JSONObject ddmTemplate = ddmTemplates.getJSONObject(i);
            if (templateKey.equalsIgnoreCase(ddmTemplate.getString("templateKey"))) {
                return ddmTemplate;
            }
        }
        return null;
    }

    public JSONObject getDDMStructureByKey(String structureKey, String groupName) throws IOException {
        long classNameId = getClassNameId(Constants.CLASS_NAME_JOURNAL_ARTICLE_7_0);
        long companyId = getDefaultCompanyId();
        long groupId = getGroupId(groupName);

        JSONObject ddmStructure = getDDMStructure(companyId, groupId, classNameId, structureKey);

        return ddmStructure;
    }

    public JSONObject getDDMTemplateByKey(long structureId, String templateKey, String groupName) throws IOException {
        long companyId = getDefaultCompanyId();
        long groupId = getGroupId(groupName);

        long classNameIdDDMStructure = getClassNameId(Constants.CLASS_NAME_DDM_STRUCTURE_7_0);
        long journalArticleClassNameId = getClassNameId(Constants.CLASS_NAME_JOURNAL_ARTICLE_7_0);

        JSONObject ddmTemplate = getDDMTemplate_70(companyId, groupId, classNameIdDDMStructure, journalArticleClassNameId, structureId, templateKey);

        return ddmTemplate;
    }

    public String getFreemarkerTemplateName(String structureKey, String templateKey, String groupName) throws IOException {
        JSONObject ddmStructure = getDDMStructureByKey(structureKey, groupName);
        if (ddmStructure != null) {
            JSONObject ddmTemplate = getDDMTemplateByKey(ddmStructure.getLong("structureId"), templateKey, groupName);
            if (ddmTemplate != null) {
                //Identifier contains companyGroupId if rendered within a request
                long companyGroupId = getCompanyGroupId();

                String version = getVersion();

                if (version.startsWith("7.4")) {
                    return ddmTemplate.getLong("companyId") + "#" + companyGroupId + "#" + ddmTemplate.getString("templateId");
                } else {
                    return ddmTemplate.getLong("companyId") + "#" + companyGroupId + "#" + ddmTemplate.getString("templateKey");
                }
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
