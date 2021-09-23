package pres.ljc.project.pojo;

public class Answer {
    private Long answerId;              // 答案编号
    private String answerText;          // 答案内容
    private String answerTime;          // 答案提交时间
    private Long paperId;               // 问卷编号
    private Long fieldId;               // 域编号
    private String groupId;             // 回答者编号

    public Answer() {
    }

    public Answer(Long answerId, String answerText, String answerTime, Long paperId, Long fieldId, String groupId) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.answerTime = answerTime;
        this.paperId = paperId;
        this.fieldId = fieldId;
        this.groupId = groupId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", answerText='" + answerText + '\'' +
                ", answerTime='" + answerTime + '\'' +
                ", paperId=" + paperId +
                ", fieldId=" + fieldId +
                ", groupId='" + groupId + '\'' +
                '}';
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
