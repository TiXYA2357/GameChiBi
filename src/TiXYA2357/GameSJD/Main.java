package TiXYA2357.GameSJD;

import TiXYA2357.GameSJD.Command.ChiBiCommand;
import TiXYA2357.GameSJD.Variable.TipVariable;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;

import java.util.ArrayList;
import java.util.List;

import static TiXYA2357.GameSJD.Lib.*;


public class Main extends PluginBase implements Listener{
    public static Main Main;
    public static Plugin plugin;
    public static String ConfigPath;
    public static Server nks = Server.getInstance();
    @Override
    public void onLoad() {
        Main = this;
        plugin = this;
        ConfigPath = this.getDataFolder().getPath();
        loadConfig();
    }
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getScheduler().scheduleRepeatingTask(this, new RepeatTask(this), 20);
        this.getServer().getCommandMap().register("GameSJD", new ChiBiCommand(ChiBiCMD));
        nks.getLogger().info("§a三界斗插件加载成功\n              作者:§b TiXYA2357");
        nks.getLogger().info("§e插件是免费的,如果遇到收费那么你被 §4§lNever Gonna Give You Up §r§e了!");
        try {Class.forName("tip.utils.variables.BaseVariable");
            TipVariable.init(); } catch (Exception ignored) {}
    }
    @Override
    public void onDisable() {
        TimeDownLog = RepeatTask.timeDown;
        setConfig("战局倒计时记录",TimeDownLog);
        setConfig("势力增援",onesMoney);
    }
    @SuppressWarnings("unchecked")
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (getPlayerInfo(p, "玩家名称") == "" ||
           !((List<String>) getPlayerInfo(p, "玩家名称")).contains(p.getName())) {
        List<String> PlayerNames = ((getPlayerInfo(p, "玩家名称")) == ""
        ? new ArrayList<>() : new ArrayList<> ((getPlayerInfo(p, "玩家名称"))));
        PlayerNames.add(0,p.getName());PlayerNames.remove(""); setPlayerInfo(p,"玩家名称",PlayerNames);}
        if (getPlayerInfo(p,"斗币").equals("")) setPlayerInfo(p,"斗币",DefaultMoney);
        if (getPlayerInfo(p,"战利品").equals("")) setPlayerInfo(p,"战利品",0);
        if (getPlayerInfo(p,"支援场次编号").equals("")) setPlayerInfo(p,"支援场次编号","1");
        if (getPlayerInfo(p,"支援势力").equals("") || !getPlayerInfo(p,"支援场次编号").toString()
           .equals(SJDCount.toString())){ setPlayerInfo(p,"支援势力","暂无"); setPlayerInfo(p,"支援势力经济",0);}
        if (getPlayerInfo(p, "支援记录") == "") setPlayerInfo(p,"支援记录",new ArrayList<>());
        PlayerHelpOnes.put(p.getUniqueId(),getPlayerInfo(p,"支援势力").toString());
        String hc = getPlayerInfo(p,"支援势力经济").toString();
        if (!hc.isEmpty() && !hc.equals("0")) PlayerHT.add(p.getUniqueId() + ":" + hc);
    }
}