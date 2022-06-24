package de.dm.toolbox.liferay.resources.importer.service;

import com.liferay.portal.kernel.model.Company;

import javax.servlet.ServletContext;

public interface AdvancedResourcesImporterService {

    void importResources(Company company, ServletContext servletContext, String groupKey, boolean indexAfterImport, boolean updateExistingLayouts);
    void importResources(Company company, ServletContext servletContext, String groupKey, boolean indexAfterImport, boolean updateExistingLayouts, boolean clearCache);

}
