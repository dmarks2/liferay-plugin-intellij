package de.dm.intellij.liferay.language.fragment;

public class FragmentAttributeInformationHolder {

    public static final FragmentAttributeInformationHolder EMPTY = new Builder().build();

    private String[] attributeValues;
    private String typeName;
    private boolean required;
    private boolean idType;
    private boolean idRefType;

    private FragmentAttributeInformationHolder() {
    }

    public String[] getAttributeValues() {
        return attributeValues;
    }

    private void setAttributeValues(String[] attributeValues) {
        this.attributeValues = attributeValues;
    }

    public boolean isIdType() {
        return idType;
    }

    private void setIdType(boolean idType) {
        this.idType = idType;
    }

    public boolean isIdRefType() {
        return idRefType;
    }

    private void setIdRefType(boolean idRefType) {
        this.idRefType = idRefType;
    }

    public boolean isRequired() {
        return required;
    }

    private void setRequired(boolean required) {
        this.required = required;
    }

    public String getTypeName() {
        return typeName;
    }

    private void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public static class Builder {
        private String[] attributeValues = new String[0];
        private String typeName;
        private boolean required;
        private boolean idType;
        private boolean idRefType;

        public FragmentAttributeInformationHolder build() {
            FragmentAttributeInformationHolder result = new FragmentAttributeInformationHolder();

            result.setAttributeValues(attributeValues);
            result.setTypeName(typeName);
            result.setRequired(required);
            result.setIdType(idType);
            result.setIdRefType(idRefType);

            return result;
        }

        public Builder attributeValues(String[] attributeValues) {
            this.attributeValues = attributeValues;

            return this;
        }

        public Builder typeName(String typeName) {
            this.typeName = typeName;

            return this;
        }

        public Builder required(boolean required) {
            this.required = required;

            return this;
        }

        public Builder idType(boolean idType) {
            this.idType = idType;

            return this;
        }

        public Builder idRefType(boolean idRefType) {
            this.idRefType = idRefType;

            return this;
        }

    }
}
