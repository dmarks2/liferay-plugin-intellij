package de.dm.intellij.liferay.language.service;

import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import com.liferay.service.builder.Column;
import com.liferay.service.builder.Entity;
import com.liferay.service.builder.ServiceBuilder;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LiferayServiceBuilderFactoryUtilTest extends LightCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/service/LiferayServiceBuilderFactoryUtilTest";
    }

    public void testReadServiceXml() throws IOException, JAXBException {
        myFixture.configureByFile(
            "service.xml"
        );

        PsiFile file = myFixture.getFile();

        InputStream inputStream = file.getVirtualFile().getInputStream();

        ServiceBuilder serviceBuilder = LiferayServiceBuilderFactoryUtil.readServiceXml(inputStream);

        String packagePath = serviceBuilder.getPackagePath();
        assertEquals("de.dm.liferay", packagePath);

        String namespace = serviceBuilder.getNamespace();
        assertEquals("my-namespace", namespace);

        List<Entity> entities = serviceBuilder.getEntity();
        assertFalse(entities.isEmpty());

        Entity entity = entities.iterator().next();
        assertEquals("MyModel", entity.getName());

        List<Column> columns = entity.getColumn();
        assertFalse(columns.isEmpty());

        Column column = columns.iterator().next();
        assertEquals("myModelId", column.getName());
        assertEquals("long", column.getType());
        assertEquals("true", column.getPrimary());
    }

}
