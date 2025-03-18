package TiXYA2357.GameSJD;


import cn.nukkit.scheduler.PluginTask;
import lombok.SneakyThrows;

import java.util.*;

import static TiXYA2357.GameSJD.Lib.*;
import static TiXYA2357.GameSJD.Main.nks;


public class RepeatTask extends PluginTask<Main> {

    public RepeatTask(Main main){ super(main); }
    public static String RandomOneStatus = "";
    public static String RandomOne = "";

    public static int timeDown = TimeDownLog;
    @SuppressWarnings("all")
    private static void endTo(){
        timeDown = TimeDown;
        SJDCount ++;
        AdminEndWinner = "随机";
        lastWinner = RandomOne;
        setConfig("三界斗次数",SJDCount);
        setConfig("最终开盘",AdminEndWinner);
        setConfig("上次获胜势力",lastWinner);

        slog.add(0,"§7第 " + (SJDCount - 1) + " 届 " + UIT + "§r§7 " + lastWinner + " §r§7获胜，支持者获得战利品共 "
        + (int) (getOnesMoneyByOnes(lastWinner) * DoubleGive) + " " + CoinName + " §r§7!");
        if (slog.size() >= KeepLogCount) slog.remove(slog.get(KeepLogCount -1)); setConfig("战事日志",slog);// 保存战事日志

        //输出提示并保存获胜玩家
        if (getOnesMoneyByOnes(lastWinner) > 0){ nks.broadcastMessage(PT + "第 "
        + (SJDCount - 1) + " 届 " + UIT + " §r§7胜负已分,上次战事 " + lastWinner + " §r§7获胜,为支持者发放战利品共 "
        + (int)(getOnesMoneyByOnes(lastWinner) * DoubleGive) + " " + CoinName + " §r§7!" );}

        String lp = getLastWinnerPlayers(); lastPlayers = lp.equals("暂无获胜支援方") || lp.equals("") ? new ArrayList<>()
        : new ArrayList<>(List.of(getLastWinnerPlayers().split("\n"))); lastPlayers.remove(""); setConfig("上次获胜玩家",lastPlayers);

        onesPlayer.forEach(map -> {//对每一个增援的势力处理
            map.forEach((map_ones,map_str) -> {
                if (map_ones.equals(lastWinner))//获胜方处理
                    map_str.forEach(string -> { setPlayerInfoByUUID(string.split(":")[0],
                            "战利品", (int)(Integer.parseInt(string.split(":")[1]) * DoubleGive));

                        setPlayerInfoByUUID(string.split(":")[0], "支援势力", "暂无");
                        setPlayerInfoByUUID(string.split(":")[0], "支援势力经济", 0);

                        List<String> htlog = (getPlayerInfoByUUID(UUID.fromString(string.split(":")[0]),"支援记录") == "" ? new ArrayList<>() :
                                new ArrayList<>((List<String>) getPlayerInfoByUUID(UUID.fromString(string.split(":")[0]),"支援记录")));
                        htlog.add(0,"§c第 " + (SJDCount - 1) + " §r§c届 " + UIT + " §r§c中支援 " + lastWinner + " §r§c获胜，获得 "
                                + (int)(Integer.parseInt(string.split(":")[1]) * DoubleGive) + " " + CoinName + " §r§7");
                        if (htlog.size() >= KeepPlayerLogCount) htlog.remove(htlog.get(KeepPlayerLogCount -1));
                        setPlayerInfoByUUID(string.split(":")[0],"支援记录",htlog);//写入记录

                        try{ nks.getPlayer(UUID.fromString(string.split(":")[0])).get().sendMessage(PT + "恭喜你在第 "
                                + (SJDCount - 1) + " 届 " + UIT + " §r§7中支援的 " + lastWinner + " §r§7获胜,获得战利品 "
                                + (int)(getOnesMoneyByOnes(lastWinner) * DoubleGive) + " " + CoinName + " §r§7!快打开UI领取吧!");}catch (Exception ignored){}});

                else map_str.forEach(string -> {//战败方处理
                    List<String> htlog = (getPlayerInfoByUUID(UUID.fromString(string.split(":")[0]),"支援记录").toString().equals("暂无支援记录") ? new ArrayList<>() :
                            new ArrayList<>((List<String>) getPlayerInfoByUUID(UUID.fromString(string.split(":")[0]),"支援记录")));
                    htlog.add(0,"§f第 " + (SJDCount - 1) + " §r§f届 " + UIT + " §r§f中支援 " + PlayerHelpOnes
                            .get(UUID.fromString(string.split(":")[0])) + " §r§f战败，失去 " + Integer.parseInt(string.split(":")[1])
                            + " " + CoinName + " §r§7");
                    if (htlog.size() >= KeepPlayerLogCount) htlog.remove(htlog.get(KeepPlayerLogCount -1));
                    setPlayerInfoByUUID(string.split(":")[0],"支援记录",htlog);//写入记录

                    setPlayerInfoByUUID(string.split(":")[0], "支援势力", "暂无");
                    setPlayerInfoByUUID(string.split(":")[0], "支援势力经济", 0);

                    try{nks.getPlayer(UUID.fromString(string.split(":")[0])).get().sendMessage(PT + "很遗憾你在第 " + (SJDCount - 1)
                            + " 届 " + UIT + " §r§7中支援的 " + PlayerHelpOnes.get(UUID.fromString(string.split(":")[0])) + " §r§7战败,请再接再厉吧 ");
                    }catch (Exception ignored){}});
            });
        });

        HashMap<String, List<String> > map = new HashMap<>();
        ones.forEach(ones -> map.put(ones, new ArrayList<>()));
        List<HashMap<String,List<String>>> map2 = new ArrayList<>();
        map2.add(map); onesPlayer.clear();
        onesPlayer = map2; setConfig("增援玩家列表",onesPlayer);// 重置增援玩家列表

        onesMoney.clear(); setConfig("势力增援",onesMoney);
        PlayerHT.clear(); PlayerHelpOnes.clear();
        nks.getOnlinePlayers().values().forEach(ps -> PlayerHelpOnes.put(ps.getUniqueId(),"暂无"));}
    @SneakyThrows
    @Override
    public void onRun(int i) {//循环任务默认是异步线程
        RandomOne = (timeDown > 0 || AdminEndWinner.isEmpty() || AdminEndWinner.equals("随机") ? (ones.size()
        <= 1 ? (ones.isEmpty() ? "无势力" : ones.get(0)) : ones.get(new Random().nextInt(0,ones.size())))
        : AdminEndWinner);  List<String> rd = new ArrayList<>(ones); String win = RandomOne; rd.remove(win);

        RandomOneStatus =  oneStatus.isEmpty() ? "前线战斗太激烈,无法获取战况!" : oneStatus.get(new Random()
        .nextInt(0,oneStatus.size())).replace("{win}", win).replace("{lose}",
        rd.get(new Random().nextInt(0,rd.size())));

        if (timeDown > 0) timeDown --;
        else endTo();
        }}