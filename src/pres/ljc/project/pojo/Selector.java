package pres.ljc.project.pojo;

public class Selector {
    private Long selectorId;                // 选项编号
    private String selectorText;            // 选项文本
    private Long fieldId;                   // 域编号

    public Selector() {
    }

    public Selector(Long selectorId, String selectorText, Long fieldId) {
        this.selectorId = selectorId;
        this.selectorText = selectorText;
        this.fieldId = fieldId;
    }

    public Long getSelectorId() {
        return selectorId;
    }

    public void setSelectorId(Long selectorId) {
        this.selectorId = selectorId;
    }

    public String getSelectorText() {
        return selectorText;
    }

    public void setSelectorText(String selectorText) {
        this.selectorText = selectorText;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public String toString() {
        return "Selector{" +
                "selectorId=" + selectorId +
                ", selectorText='" + selectorText + '\'' +
                ", fieldId=" + fieldId +
                '}';
    }
}
