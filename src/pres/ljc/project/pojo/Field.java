package pres.ljc.project.pojo;

public class Field {
    private Long fieldId;                   // 域编号
    private String fieldName;               // 域名称
    private int fieldType;                  // 域类型(1 文本说明  2 单行文本   3  多行文本   4  单选按钮   5  多选按钮)
    private String fieldDesc;               // 域说明
    private Long paperId;                   // 问卷编号

    public Field() {
    }

    public Field(Long fieldId, String fieldName, int fieldType, String fieldDesc, Long paperId) {
        this.fieldId = fieldId;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.fieldDesc = fieldDesc;
        this.paperId = paperId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getFieldType() {
        return fieldType;
    }

    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    @Override
    public String toString() {
        return "Field{" +
                "fieldId=" + fieldId +
                ", fieldName='" + fieldName + '\'' +
                ", fieldType=" + fieldType +
                ", fieldDesc='" + fieldDesc + '\'' +
                ", paperId=" + paperId +
                '}';
    }
}
