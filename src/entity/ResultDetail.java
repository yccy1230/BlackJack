package entity;

/**
 * 游戏结果 封装实体类
 * @author Jack Chen
 */
public class ResultDetail {
    private String userid;      //用户ID
    private String nickName;    //昵称
    private String faceValue;   //牌面点数和
    private String bet;         //初始赌注
    private String odds;        //赔率
    private String status;      //胜负

    public ResultDetail() {
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

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public String getBet() {
        return bet;
    }

    public void setBet(String bet) {
        this.bet = bet;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
