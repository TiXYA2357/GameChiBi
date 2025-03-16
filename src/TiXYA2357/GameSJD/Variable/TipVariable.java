package TiXYA2357.GameSJD.Variable;

import cn.nukkit.Player;
import tip.utils.Api;
import tip.utils.variables.BaseVariable;

import static TiXYA2357.GameSJD.Lib.TipsCoin;
import static TiXYA2357.GameSJD.Lib.getPlayerInfo;
import static TiXYA2357.GameSJD.Main.nks;

public class TipVariable extends BaseVariable {

    public TipVariable(Player player, String str) {
        super(player);
        this.string = str;
    }

    public static void init() {
        Api.registerVariables("GameChiBi",TipVariable.class);
    }

    @Deprecated
    public boolean isResetMessafe(){
        return true;
    }
    public String getString(){
        return this.string;
    }

    public void strReplace() {
     addStrReplaceString(TipsCoin,getPlayerInfo(player, "斗币").toString());
    }}