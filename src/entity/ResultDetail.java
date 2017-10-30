package entity;

/**
 * 游戏结果 封装实体类
 * @author Jack Chen
 */
public class ResultDetail {
    private String userid;      //用户ID
    private String nickName;    //昵称
    private int faceValue;   //牌面点数和
    private int bet;         //初始赌注
    private int property;         //初始赌注
    private String status;      //胜负

    public ResultDetail() {
    }

    public ResultDetail(String userid, String nickName, int faceValue, int bet) {
        this.userid = userid;
        this.nickName = nickName;
        this.faceValue = faceValue;
        this.bet = bet;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(int faceValue) {
        this.faceValue = faceValue;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
