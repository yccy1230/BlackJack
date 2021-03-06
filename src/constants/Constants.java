package constants;

public class Constants {


    /**系统参数*/
    public static final String APP_NAME = "Black Jack";
    public static final String DEFAULT_IP = "115.159.35.11";
    public static final int MAX_PLAYER_NUM = 5;
    public static final int DEFAULT_PORT = 6666;
    
    /**参数变量名*/
    public static final String PARAM_NICK_NAME ="nickname";
    public static final String PARAM_PLAYERS ="players";
    public static final String PARAM_PLAYER ="player";
    public static final String PARAM_DEALER ="dealer";
    public static final String PARAM_USER_ID = "userid";
    public static final String PARAM_BET ="bet";
    public static final String PARAM_ERROR_MSG ="errormsg";
    public static final String PARAM_RESULT_CODE = "resultcode";
    public static final String PARAM_ROOM_ID = "roomid";
    public static final String PARAM_SURRENDER_MSG ="errormsg";
    public static final String PARAM_START_GAME = "startgame";
    public static final String PARAM_GAME_RESULT = "gameresult";

    /**用户状态*/
    public static final int USER_IDEL = 0;
    public static final int USER_READY = 1;
    public static final int USER_SURRENDER = 2;
    public static final int USER_STAND = 3;
    public static final int USER_OVER = 4;
    public static final int USER_BLACKJACK = 5;
    public static final int EMPTY_USER = -1;//UI使用

    /**返回结果*/
    public static final int SUCCESS_CODE= 200;
    public static final int ERROR_CODE= 400;

    /**Player Panel Size*/
    public static final int PLAYER_PANEL_WIDTH = 360;
    public static final int PLAYER_PANEL_HEIGHT = 180;

    /**卡牌View Size*/
    public static final int CARD_WIDTH = 90;
    public static final int CARD_HEIGHT = 140;
    public static final int CARD_WIDTH_DISTANCE = 30;
    public static final int CARD_HEIGHT_DISTANCE = 40;
    public static final int DEALER_CARD_WIDTH_DISTANCE = 40;
    public static final int DEALER_CARD_WIDTH = 90;
    public static final int DEALER_CARD_HEIGHT = 140;
    

    /**MainFrame Size*/
    public static final int MAIN_FRAME_WIDTH = 1200;
    public static final int MAIN_FRAME_HEIGHT = 750;


    /**卡牌定义类*/
    public static final String CLUBS_1 = "Clubs_1";
    public static final String CLUBS_2 = "Clubs_2";
    public static final String CLUBS_3 = "Clubs_3";
    public static final String CLUBS_4 = "Clubs_4";
    public static final String CLUBS_5 = "Clubs_5";
    public static final String CLUBS_6 = "Clubs_6";
    public static final String CLUBS_7 = "Clubs_7";
    public static final String CLUBS_8 = "Clubs_8";
    public static final String CLUBS_9 = "Clubs_9";
    public static final String CLUBS_10 = "Clubs_10";
    public static final String CLUBS_J = "Clubs_J";
    public static final String CLUBS_Q = "Clubs_Q";
    public static final String CLUBS_K = "Clubs_K";
    public static final String DIAMONDS_1 = "Diamonds_1";
    public static final String DIAMONDS_2 = "Diamonds_2";
    public static final String DIAMONDS_3 = "Diamonds_3";
    public static final String DIAMONDS_4 = "Diamonds_4";
    public static final String DIAMONDS_5 = "Diamonds_5";
    public static final String DIAMONDS_6 = "Diamonds_6";
    public static final String DIAMONDS_7 = "Diamonds_7";
    public static final String DIAMONDS_8 = "Diamonds_8";
    public static final String DIAMONDS_9 = "Diamonds_9";
    public static final String DIAMONDS_10 = "Diamonds_10";
    public static final String DIAMONDS_J = "Diamonds_J";
    public static final String DIAMONDS_Q = "Diamonds_Q";
    public static final String DIAMONDS_K = "Diamonds_K";
    public static final String HEARTS_1 = "Hearts_1";
    public static final String HEARTS_2 = "Hearts_2";
    public static final String HEARTS_3 = "Hearts_3";
    public static final String HEARTS_4 = "Hearts_4";
    public static final String HEARTS_5 = "Hearts_5";
    public static final String HEARTS_6 = "Hearts_6";
    public static final String HEARTS_7 = "Hearts_7";
    public static final String HEARTS_8 = "Hearts_8";
    public static final String HEARTS_9 = "Hearts_9";
    public static final String HEARTS_10 = "Hearts_10";
    public static final String HEARTS_J = "Hearts_J";
    public static final String HEARTS_Q = "Hearts_Q";
    public static final String HEARTS_K = "Hearts_K";
    public static final String SPADES_1 = "Spades_1";
    public static final String SPADES_2 = "Spades_2";
    public static final String SPADES_3 = "Spades_3";
    public static final String SPADES_4 = "Spades_4";
    public static final String SPADES_5 = "Spades_5";
    public static final String SPADES_6 = "Spades_6";
    public static final String SPADES_7 = "Spades_7";
    public static final String SPADES_8 = "Spades_8";
    public static final String SPADES_9 = "Spades_9";
    public static final String SPADES_10 = "Spades_10";
    public static final String SPADES_J = "Spades_J";
    public static final String SPADES_Q = "Spades_Q";
    public static final String SPADES_K = "Spades_K";
    public static final String BACKGROUND = "BackGround";
}
