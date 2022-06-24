import de.dm.toolbox.liferay.resources.importer.service.AdvancedResourcesImporterService;
import com.liferay.portal.kernel.model.Company;
import javax.servlet.ServletContext;

public class MyImporter {

    public void run() {
        AdvancedResourcesImporterService advancedResourcesImporterService = new AdvancedResourcesImporterService();

        advancedResourcesImporterService.importResources(new Company(), new ServletContext(), "MY_GROUP", false, false);
    }

}