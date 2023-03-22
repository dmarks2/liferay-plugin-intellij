package de.dm.intellij.liferay.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.StringUtil;

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

    public JsonArray getUserSitesGroup() throws IOException {
        return serviceInvoker.invoke(Constants.CMD_GET_USER_SITES_GROUP, new JsonObject(), JsonArray.class);
    }

    public JsonObject getCompanyGroup(long companyId) throws IOException {
        JsonObject params = new JsonObject();
        params.addProperty("companyId", companyId);

        JsonObject JsonObject = serviceInvoker.invoke(Constants.CMD_GET_COMPANY_GROUP, params, JsonObject.class);

        if (log.isDebugEnabled()) {
            log.debug("getCompanyId(" + companyId + ") = " + JsonObject.toString());
        }

        return JsonObject;
    }

    public JsonObject getGroup(long companyId, String name) throws IOException {
        JsonObject params = new JsonObject();
        params.addProperty("companyId", companyId);
        params.addProperty("groupKey", name);

        JsonObject JsonObject = serviceInvoker.invoke(Constants.CMD_GET_GROUP, params, JsonObject.class);

        if (log.isDebugEnabled()) {
            log.debug("getGroup(" + companyId + ", " + name + ") = " + JsonObject.toString());
        }

        return JsonObject;
    }

    public JsonObject getGroup(long groupId) throws IOException {
        JsonObject params = new JsonObject();
        params.addProperty("groupId", groupId);

        JsonObject JsonObject = serviceInvoker.invoke(Constants.CMD_GET_GROUP, params, JsonObject.class);

        if (log.isDebugEnabled()) {
            log.debug("getGroup(" + groupId + ") = " + JsonObject.toString());
        }

        return JsonObject;
    }

    public long getDefaultCompanyId() throws IOException {
        if (defaultCompanyId == 0) {
            JsonArray userSites = getUserSitesGroup();
            if (userSites.size() > 0) {
                defaultCompanyId =  userSites.get(0).getAsJsonObject().get("companyId").getAsLong();
            }
        }

        return defaultCompanyId;
    }

    public long getCompanyGroupId() throws IOException {
        if (companyGroupId == 0) {
            long companyId = getDefaultCompanyId();

            JsonObject companyGroup = getCompanyGroup(companyId);
            if (companyGroup != null) {
                companyGroupId = companyGroup.get("groupId").getAsLong();
            }
        }

        return companyGroupId;
    }

    public long getGlobalGroupId() throws IOException {
        if (globalGroupId == 0) {
            long companyId = getDefaultCompanyId();

            JsonObject globalGroup = getGroup(companyId, String.valueOf(companyId));
            if (globalGroup != null) {
                globalGroupId = globalGroup.get("groupId").getAsLong();
            }
        }

        return globalGroupId;
    }

    public long getGroupId(String groupName) throws IOException {
        if (GroupConstants.GLOBAL.equals(groupName)) {
            return getGlobalGroupId();
        }

        long companyId = getDefaultCompanyId();

        JsonObject group = getGroup(companyId, groupName);
        if (group != null) {
            return group.get("groupId").getAsLong();
        }

        return 0;
    }

    public JsonObject fetchClassName(String value) throws IOException {
        JsonObject params = new JsonObject();
        params.addProperty("value", value);

        JsonObject JsonObject = serviceInvoker.invoke("/classname/fetch-class-name", params, JsonObject.class);

        if (log.isDebugEnabled()) {
            log.debug("fetchClassName(" + value + ") = " + JsonObject.toString());
        }

        return JsonObject;
    }

    public String getVersion() throws IOException {
        String result = serviceInvoker.invoke("/portal/get-version", new JsonObject(), String.class);

        result = StringUtil.unquoteString(result);

        if (log.isDebugEnabled()) {
            log.debug("getVersion() = " + result);
        }

        return result;
    }

    public long getClassNameId(String className) throws IOException {
        Long result = classNameIds.get(className);

        if (result == null) {
            JsonObject classNameJsonObject = fetchClassName(className);
            result = classNameJsonObject.get("classNameId").getAsLong();

            classNameIds.put(className, result);
        }

        return result;
    }

    public JsonArray getJournalStructures(long groupId) throws IOException {
        JsonObject params = new JsonObject();
        params.addProperty("groupId", groupId);

        JsonArray JsonArray = serviceInvoker.invoke("/journalstructure/get-structures", params, JsonArray.class);

        if (log.isDebugEnabled()) {
            log.debug("getJournalStructures(" + groupId + ") = " + JsonArray.toString());
        }

        return JsonArray;
    }

    public JsonObject getJournalStructure(long groupId, String name) throws IOException {
        JsonArray journalStructures = getJournalStructures(groupId);
        for (int i = 0; i < journalStructures.size(); i++) {
            JsonObject journalStructure = journalStructures.get(i).getAsJsonObject();
            if (name.equalsIgnoreCase(LiferayXMLUtil.getName(journalStructure.get("name").getAsString()))) {
                return journalStructure;
            }
        }
        return null;
    }

    public JsonArray getDDMStructures_70(long companyId, long groupId, long classNameId, int status) throws IOException {
        JsonArray groupIds = new JsonArray();
        groupIds.add(groupId);

        JsonObject params = new JsonObject();
        params.addProperty("companyId", companyId);
        params.add("groupIds", groupIds);
        params.addProperty("classNameId", classNameId);
        params.addProperty("status", status);

        JsonArray JsonArray = serviceInvoker.invoke("/ddm.ddmstructure/get-structures", params, JsonArray.class);

        if (log.isDebugEnabled()) {
            log.debug("getDDMStructures_70(" + companyId + ", " + groupId + ", " + classNameId + ", " + status + ") = " + JsonArray.toString());
        }

        return JsonArray;
    }

    public JsonArray getDDMStructures_73(long companyId, long groupId, long classNameId) throws IOException {
        JsonArray groupIds = new JsonArray();
        groupIds.add(groupId);

        JsonObject params = new JsonObject();
        params.addProperty("companyId", companyId);
        params.add("groupIds", groupIds);
        params.addProperty("classNameId", classNameId);
        params.addProperty("start", -1);
        params.addProperty("end", -1);
        params.addProperty("orderByComparator", (String) null);

        JsonArray JsonArray = serviceInvoker.invoke("/ddm.ddmstructure/get-structures", params, JsonArray.class);

        if (log.isDebugEnabled()) {
            log.debug("getDDMStructures_73(" + companyId + ", " + groupId + ", " + classNameId + ") = " + JsonArray.toString());
        }

        return JsonArray;
    }

    public JsonObject getDDMStructure(long companyId, long groupId, long classNameId, String structureKey) throws IOException {
        String version = getVersion();

        JsonArray ddmStructures;

        if (version.startsWith("7.3") || version.startsWith("7.4")) {
            ddmStructures = getDDMStructures_73(companyId, groupId, classNameId);
        } else {
            ddmStructures = getDDMStructures_70(companyId, groupId, classNameId, -1);
        }

        for (int i = 0; i < ddmStructures.size(); i++) {
            JsonObject ddmStructure = ddmStructures.get(i).getAsJsonObject();
            if (structureKey.equals(ddmStructure.get("structureKey").getAsString())) {
                return ddmStructure;
            }
        }
        return null;
    }

    public JsonArray getJournalStructureTemplates(long groupId, String structureId) throws IOException {
        JsonObject params = new JsonObject();
        params.addProperty("groupId", groupId);
        params.addProperty("structureId", structureId);

        JsonArray JsonArray = serviceInvoker.invoke("/journaltemplate/get-structure-templates", params, JsonArray.class);

        if (log.isDebugEnabled()) {
            log.debug("getJournalStructureTemplates(" + groupId + ", " + structureId + ") = " + JsonArray.toString());
        }

        return JsonArray;
    }

    public JsonObject getJournalStructureTemplate(long groupId, String structureId, String name) throws IOException {
        JsonArray journalTemplates = getJournalStructureTemplates(groupId, structureId);
        for (int i = 0; i < journalTemplates.size(); i++) {
            JsonObject journalTemplate = journalTemplates.get(i).getAsJsonObject();
            if (name.equalsIgnoreCase(LiferayXMLUtil.getName(journalTemplate.get("name").getAsString()))) {
                return journalTemplate;
            }
        }
        return null;
    }

    public JsonArray getDDMTemplatesByClassPK(long groupId, long classPK) throws IOException {
        JsonObject params = new JsonObject();
        params.addProperty("groupId", groupId);
        params.addProperty("classPK", classPK);

        JsonArray JsonArray = serviceInvoker.invoke("/ddmtemplate/get-templates-by-class-pk", params, JsonArray.class);

        if (log.isDebugEnabled()) {
            log.debug("getDDMTemplatesByClassPK(" + groupId + ", " + classPK + ") = " + JsonArray.toString());
        }

        return JsonArray;
    }

    public JsonArray getDDMTemplates_70(long companyId, long groupId, long classNameId, long resourceClassNameId, long classPK) throws IOException {
        JsonObject params = new JsonObject();
        params.addProperty("companyId", companyId);
        params.addProperty("groupId", groupId);
        params.addProperty("classNameId", classNameId);
        params.addProperty("classPK", classPK);
        params.addProperty("resourceClassNameId", resourceClassNameId);
        params.addProperty("status", -1);

        JsonArray JsonArray = serviceInvoker.invoke("/ddm.ddmtemplate/get-templates", params, JsonArray.class);

        if (log.isDebugEnabled()) {
            log.debug("getDDMTemplates_70(" + companyId + ", " + groupId + ", " + classNameId + ", "+ resourceClassNameId + ", " + classPK + ") = " + JsonArray.toString());
        }

        return JsonArray;
    }

    public JsonObject getDDMTemplateByClassPK(long groupId, long classPK, String name) throws IOException {
        JsonArray ddmTemplates = getDDMTemplatesByClassPK(groupId, classPK);
        for (int i = 0; i < ddmTemplates.size(); i++) {
            JsonObject ddmTemplate = ddmTemplates.get(i).getAsJsonObject();
            if (name.equalsIgnoreCase(LiferayXMLUtil.getName(ddmTemplate.get("name").getAsString()))) {
                return ddmTemplate;
            }
        }
        return null;
    }

    public JsonObject getDDMTemplate_70(long companyId, long groupId, long classNameId, long resourceClassNameId, long classPK, String templateKey) throws IOException {
        JsonArray ddmTemplates = getDDMTemplates_70(companyId, groupId, classNameId, resourceClassNameId, classPK);
        for (int i = 0; i < ddmTemplates.size(); i++) {
            JsonObject ddmTemplate = ddmTemplates.get(i).getAsJsonObject();
            if (templateKey.equalsIgnoreCase(ddmTemplate.get("templateKey").getAsString())) {
                return ddmTemplate;
            }
        }
        return null;
    }

    public JsonObject getDDMStructureByKey(String structureKey, String groupName) throws IOException {
        long classNameId = getClassNameId(Constants.CLASS_NAME_JOURNAL_ARTICLE_7_0);
        long companyId = getDefaultCompanyId();
        long groupId = getGroupId(groupName);

        JsonObject ddmStructure = getDDMStructure(companyId, groupId, classNameId, structureKey);

        return ddmStructure;
    }

    public JsonObject getDDMTemplateByKey(long structureId, String templateKey, String groupName) throws IOException {
        long companyId = getDefaultCompanyId();
        long groupId = getGroupId(groupName);

        long classNameIdDDMStructure = getClassNameId(Constants.CLASS_NAME_DDM_STRUCTURE_7_0);
        long journalArticleClassNameId = getClassNameId(Constants.CLASS_NAME_JOURNAL_ARTICLE_7_0);

        JsonObject ddmTemplate = getDDMTemplate_70(companyId, groupId, classNameIdDDMStructure, journalArticleClassNameId, structureId, templateKey);

        return ddmTemplate;
    }

    public String getFreemarkerTemplateName(String structureKey, String templateKey, String groupName) throws IOException {
        JsonObject ddmStructure = getDDMStructureByKey(structureKey, groupName);
        if (ddmStructure != null) {
            JsonObject ddmTemplate = getDDMTemplateByKey(ddmStructure.get("structureId").getAsLong(), templateKey, groupName);
            if (ddmTemplate != null) {
                //Identifier contains companyGroupId if rendered within a request
                long companyGroupId = getCompanyGroupId();

                String version = getVersion();

                if (version.startsWith("7.4")) {
                    return ddmTemplate.get("companyId").getAsLong() + "#" + companyGroupId + "#" + ddmTemplate.get("templateId").getAsString();
                } else {
                    return ddmTemplate.get("companyId").getAsLong() + "#" + companyGroupId + "#" + ddmTemplate.get("templateKey").getAsString();
                }
            }
        }

        return null;
    }
    public String getFreemarkerApplicationDisplayTemplateName(String type, String templateKey, String groupName) throws IOException {
        String[] classNames = Constants.APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.get(type);
        if (classNames == null) {
            classNames = Constants.APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.get(type);
        }
        if (classNames != null) {
            for (String className : classNames) {
                long classNameId = getClassNameId(className);

                if (classNameId > 0) {
                    long portletDisplayTemplateClassNameId = getClassNameId(Constants.CLASS_NAME_PORTLET_DISPLAY_TEMPLATE_7_0);

                    long companyId = getDefaultCompanyId();
                    long groupId = getGroupId(groupName);

                    JsonObject ddmTemplate = getDDMTemplate_70(companyId, groupId, classNameId, portletDisplayTemplateClassNameId, 0, templateKey);

                    if (ddmTemplate != null) {
                        //Identifier contains companyGroupId if rendered within a request
                        long companyGroupId = getCompanyGroupId();

                        return ddmTemplate.get("companyId").getAsLong() + "#" + companyGroupId + "#" + ddmTemplate.get("templateId").getAsLong();
                    }

                    break;
                }
            }
        }
        return null;
    }

}
