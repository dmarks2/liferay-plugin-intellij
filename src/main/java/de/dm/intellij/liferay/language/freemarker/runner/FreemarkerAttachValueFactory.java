package de.dm.intellij.liferay.language.freemarker.runner;

import freemarker.debug.DebugModel;
import freemarker.template.TemplateModelException;

import java.rmi.RemoteException;

public class FreemarkerAttachValueFactory {

    private static final int VALID_VARIBLE_TYPES = DebugModel.TYPE_BOOLEAN | DebugModel.TYPE_COLLECTION |
        DebugModel.TYPE_CONFIGURATION | DebugModel.TYPE_DATE | DebugModel.TYPE_HASH | DebugModel.TYPE_HASH_EX |
        DebugModel.TYPE_NUMBER | DebugModel.TYPE_SCALAR | DebugModel.TYPE_SEQUENCE | DebugModel.TYPE_TEMPLATE;

    public static <T extends FreemarkerAttachValue> FreemarkerAttachValue createFreemarkerAttachValue(DebugModel debugModel, String name, Class<T> type) throws RemoteException, TemplateModelException {
        DebugModel value = debugModel.get(name);

        return createFreemarkerAttachValue(name, value, type);
    }

    public static <T extends FreemarkerAttachValue> FreemarkerAttachValue createFreemarkerAttachValue(String name, DebugModel value, Class<T> type) {
        if (isValidVariable(value)) {
            if (FreemarkerAttachTemplateValue.class.equals(type)) {
                return new FreemarkerAttachTemplateValue(value);
            } else if (FreemarkerAttachConfigurationValue.class.equals(type)) {
                return new FreemarkerAttachConfigurationValue(value);
            } else if (FreemarkerAttachBaseValue.class.equals(type)) {
                return new FreemarkerAttachBaseValue(value);
            } else {
                return new FreemarkerAttachValue(value);
            }
        }

        return null;
    }

    public static boolean isValidSequence( DebugModel model )
    {
        try
        {
            return model != null && model.size() > 0;
        }
        catch( Exception e )
        {
            return false;
        }
    }

    public static boolean isValidVariable( DebugModel model )
    {
        boolean retval = false;

        if( model != null )
        {
            try
            {
                int types = model.getModelTypes();

                retval = ( VALID_VARIBLE_TYPES & types ) > 0;

                if( retval && isSequenceType( types ) && ! isValidSequence( model ) )
                {
                    retval = false;
                }
            }
            catch( RemoteException e )
            {
                e.printStackTrace();
            }
        }

        return retval;
    }

    private static boolean isSequenceType( int types ) {
        return ( DebugModel.TYPE_SEQUENCE & types ) > 0;
    }

}
