package de.dm.intellij.liferay.index;

import com.intellij.lang.LighterAST;
import com.intellij.lang.LighterASTNode;
import com.intellij.psi.impl.source.JavaLightTreeUtil;
import com.intellij.psi.impl.source.tree.JavaElementType;
import com.intellij.psi.impl.source.tree.JavaSourceUtil;
import com.intellij.psi.impl.source.tree.LightTreeUtil;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.indexing.FileContentImpl;

public class LighterASTJavaFileHelper {

    private FileContent fileContent;
    private LighterAST lighterAST;
    private LighterASTNode rootNode;

    public LighterASTJavaFileHelper(FileContent fileContent) {
        this.fileContent = fileContent;

        if (fileContent instanceof FileContentImpl) {
            lighterAST = ((FileContentImpl)fileContent).getLighterASTForPsiDependentIndex();

            rootNode = lighterAST.getRoot();
        }
    }

    public String getClassQualifiedName() {
        LighterASTNode packageStatementNode = LightTreeUtil.firstChildOfType(lighterAST, rootNode, JavaElementType.PACKAGE_STATEMENT);
        LighterASTNode classNode = LightTreeUtil.firstChildOfType(lighterAST, rootNode, JavaElementType.CLASS);

        String packageName = null;

        if (packageStatementNode != null) {
            LighterASTNode javaCodeReferenceNode = LightTreeUtil.firstChildOfType(lighterAST, packageStatementNode, JavaElementType.JAVA_CODE_REFERENCE);
            if (javaCodeReferenceNode != null) {
                packageName = JavaSourceUtil.getReferenceText(lighterAST, javaCodeReferenceNode);
            }
        }

        String className = "";
        if (classNode != null) {
            className = JavaLightTreeUtil.getNameIdentifierText(lighterAST, classNode);
        }
        if (packageName != null) {
            return packageName + "." + className;
        } else {
            return className;
        }
    }

}
