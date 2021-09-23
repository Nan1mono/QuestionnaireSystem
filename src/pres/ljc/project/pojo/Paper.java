package pres.ljc.project.pojo;

public class Paper {
    private Long paperId;                       // 问卷编号
    private String paperTitle;                  // 问卷标题
    private String paperDesc;                   // 问卷说明
    private String paperCreated;                // 问卷创建时间
    private int paperStatus;                    // 问卷状态 （显示 or 删除(隐藏)）
    private int paperCount;                     // 已经提交的人数

    public Paper() {
    }

    public Paper(Long paperId, String paperTitle, String paperDesc, String paperCreated, int paperStatus, int paperCount) {
        this.paperId = paperId;
        this.paperTitle = paperTitle;
        this.paperDesc = paperDesc;
        this.paperCreated = paperCreated;
        this.paperStatus = paperStatus;
        this.paperCount = paperCount;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getPaperDesc() {
        return paperDesc;
    }

    public void setPaperDesc(String paperDesc) {
        this.paperDesc = paperDesc;
    }

    public String getPaperCreated() {
        return paperCreated;
    }

    public void setPaperCreated(String paperCreated) {
        this.paperCreated = paperCreated;
    }

    public int getPaperStatus() {
        return paperStatus;
    }

    public void setPaperStatus(int paperStatus) {
        this.paperStatus = paperStatus;
    }

    public int getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(int paperCount) {
        this.paperCount = paperCount;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "paperId=" + paperId +
                ", paperTitle='" + paperTitle + '\'' +
                ", paperDesc='" + paperDesc + '\'' +
                ", paperCreated='" + paperCreated + '\'' +
                ", paperStatus=" + paperStatus +
                ", paperCount=" + paperCount +
                '}';
    }
}
