package TiXYA2357.GameSJD;

import cn.nukkit.Player;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.Config;
import lombok.SneakyThrows;

import java.util.*;

import static TiXYA2357.GameSJD.Main.*;

public class Lib {
    private Lib(){}
    private static Lib libs = null;
    @SuppressWarnings("all")
    public static Lib lib = libs();
    /**
     * 插件总指令
     */
    public static String ChiBiCMD;
    /**
     * 物资名称
     */
    public static String CoinName;
    /**
     * 插件提示前缀
     */
    public static String PT;
    /**
     * 插件UI标题
     */
    public static String UIT;
    public static String ChiBiRule;
    /**
     * 上次战事获胜的势力
     */
    public static String lastWinner;
    /**
     * 设置开盘的势力,为空则随机
     */
    public static String AdminEndWinner;
    /**
     * 斗币的Tips变量
     */
    public static String TipsCoin;

    /**
     * 上次战事获胜的支援方,格式为 "玩家的UUID:返还物资"
     */
    public static List<String> lastPlayers;
    /**
     * 势力列表,默认有 神 佛 魔 三个势力
     */
    public static List<String> ones;
    /**
     * 战局,随机List成员字符串
     */
    public static List<String> oneStatus;
    /**
     * 战事记录历史
     */
    public static List<String> slog;

    public static List<String> PlayerHT = new ArrayList<>();//

    public static List<HashMap<String,Integer>> onesMoney = new ArrayList<>();//
    public static List<HashMap<String,List<String>>> onesPlayer = new ArrayList<>();//
    /**
     * 三界斗总次数
     */
    public static Integer SJDCount;
    /**
     * 战利品返还倍率(默认2.7)
     */
    public static Double DoubleGive;
    /**
     * 玩家的默认经济
     */
    public static Integer DefaultMoney;
    public static Integer MinMoney;
    public static Integer MaxMoney;
    /**
     * 保留战事记录历史的数量
     */
    public static Integer KeepLogCount;
    /**
     * 保留玩家增援记录的历史数量
     */
    public static Integer KeepPlayerLogCount;
    /**
     * 每次战事的时长
     */
    public static Integer TimeDown;
    public static Integer TimeDownLog;

    /**
     * 记录玩家帮助的势力
     */
    public static Map<UUID,String> PlayerHelpOnes = new HashMap<>();
    /**
     * @return 所有势力物资的总经济
     */
    @SuppressWarnings("all")
    public static Integer getAllMoney(){
        return onesMoney.isEmpty() ? 0 : onesMoney.stream().flatMap(map
        -> map.values().stream()).mapToInt(Integer::intValue).sum();
    }

    /**
     * @return 某个势力的总物资
     */
    public static Integer getOnesMoneyByOnes(String ones){
        return onesMoney.isEmpty() ? 0 : onesMoney.stream().filter(map -> map.containsKey(ones))
                .map(map -> map.get(ones)).findFirst().orElse(0);
    }

    /**
     * @return 每个势力物资的单独经济
     */
    public static String getAllOnesMoney(){
        return onesMoney.isEmpty() ? "" : onesMoney.stream().map(map -> map.entrySet().stream()
        .map(entry -> entry.getKey() + " 的总支援为 " + entry.getValue() + " " + CoinName).toList())
        .flatMap(List::stream).toList().toString().replace("[", "").replace("]", "")
        .replace(",", "\n§f");
    }

    /**
     * @return 上次支援方获胜的玩家
     */
    public static String getLastWinnerPlayers(){
        if (onesPlayer.isEmpty()) return "暂无获胜支援方";
        StringBuffer sb = new StringBuffer("暂无获胜支援方");
        onesPlayer.forEach(map -> map.forEach((ones, str) -> { if (ones.equals(lastWinner)){ sb.replace(0, sb.length(), "");
         str.forEach(str2 -> sb.append(getPlayerNameByUUID(UUID.fromString(str2.split(":")[0]))).append(" 获得战利品 ")
         .append(Integer.parseInt(str2.split(":")[1]) * DoubleGive).append(" ").append(CoinName).append("\n"));}
        })); return sb.toString();
    }
    @SuppressWarnings("all")
    private static Lib libs(){return libs == null ? libs = new Lib() : libs;}
    public static boolean loadConfig(){
        try {
            ChiBiCMD = InLoad("三界斗命令", "gcb");
            CoinName = InLoad("物资名称", "§a斗币§r§f");
            PT = InLoad("消息前缀", "§b三界斗 §a>>> §7");
            UIT = InLoad("UI标题", "§e三界斗");
            SJDCount = InLoad("三界斗次数", 1);
            MinMoney = InLoad("最小增援", 0);
            MaxMoney = InLoad("最大增援", 1000);
            DefaultMoney = InLoad("默认斗币数量", 10000);
            TimeDown = InLoad("战局时长", 600);
            TimeDownLog = InLoad("战局倒计时记录", TimeDown);
            KeepLogCount = InLoad("保留战事日志数量", 300);
            KeepPlayerLogCount = InLoad("保留玩家增援记录", 50);
            DoubleGive = InLoad("战利品返还倍率", 2.7);
            ChiBiRule = InLoad("规则介绍", "emmm,,,还没有写完呢");
            ones = InLoad("势力列表", new ArrayList<>());
            if (ones.isEmpty()){
                ones.add("§c魔界§r");
                ones.add("§e佛界§r");
                ones.add("§b神界§r");
                InLoad("势力列表", ones);
            }
            oneStatus = InLoad("战局", new ArrayList<>());
            lastWinner = InLoad("上次获胜势力", "暂无势力");
            lastPlayers = InLoad("上次获胜玩家", new ArrayList<>());
            AdminEndWinner = InLoad("最终开盘", "随机");
            TipsCoin = InLoad("斗币变量", "{dcoin}");
            onesMoney = InLoad("势力增援", new ArrayList<>());
            HashMap<String, List<String> > map = new HashMap<>();
            ones.forEach(ones -> map.put(ones, new ArrayList<>()));
            List<HashMap<String,List<String>>> map2 = new ArrayList<>();
            map2.add(map);
            onesPlayer = InLoad("增援玩家列表", map2);

            slog = InLoad("战事日志", new ArrayList<>());
            return true;
        } catch (Exception e) {nks.getLogger().info(UIT+" §c配置文件加载失败: §e\n" + e + "\n§c插件已禁用");
            nks.getPluginManager().disablePlugin(plugin); return false;
        }
    }

    public static void setConfig(String Key ,Object Value){
        Config config = new Config(ConfigPath+"/config.yml", Config.YAML);
        config.set(Key, Value);
        config.save();config.reload();}

    public static <T> T InLoad(String Name, T Default){
        Config config = new Config(ConfigPath+"/config.yml", Config.YAML);
        if (!config.exists(Name)) config.set(Name, Default);
        T Obj = config.get(Name, Default);
        config.save(); config.reload(); return Obj;}

    @SuppressWarnings("unchecked")
    public static String getPlayerNameByUUID(UUID uuid){
        return ((List<String>) getPlayerInfoByUUID(uuid, "玩家名称")).get(0);
    }
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T> T getPlayerInfo(Player p, String key){
        Config config = new Config(ConfigPath + "/Players/" + p.getUniqueId() + ".yml",Config.YAML);
        if (!config.exists(key)) config.set(key,"");
        return (T) config.get(key);
    }
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T> T getPlayerInfoByUUID(UUID uuid, String key){
        Config config = new Config(ConfigPath + "/Players/" + uuid + ".yml",Config.YAML);
        if (!config.exists(key)) config.set(key,"");
        return (T) config.get(key);}
    @SneakyThrows
    public static void setPlayerInfoByUUID(String uuid, String key, Object value){
        Config config = new Config(ConfigPath + "/Players/" + uuid + ".yml",Config.YAML);
        config.set(key, value);
        config.save();
        config.reload();
    }
    @SneakyThrows
    public static void setPlayerInfo(Player p, String key, Object value){
        Config config = new Config(ConfigPath + "/Players/" + p.getUniqueId() + ".yml",Config.YAML);
        config.set(key, value);
        config.save();
        config.reload();
    }
    //方法名(() -> {方法体})
    @SuppressWarnings("all")
    public static void Repeat (Runnable logic,int time,boolean async){
        nks.getScheduler().scheduleRepeatingTask(plugin, logic,time);}
    @SuppressWarnings("all")
    public static void Delayed (Runnable logic,int time){
        Delayed(logic,time,false);}
    @SuppressWarnings("all")
    public static void Delayed (Runnable logic,int time,boolean async){
        nks.getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int i) {logic.run();}},time,async);}
    public static void Async (Runnable logic){
        nks.getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
            @Override
            public void onRun() {logic.run();}});}
    @SuppressWarnings("all")
    public static void asOPCmd(Player p,String cmd){
        boolean flag = p.isOp();if (!flag) p.setOp(true);
        nks.dispatchCommand(p, cmd.replace("@s",p.getName())
        .replaceFirst("/", ""));if (!flag) p.setOp(false);
    }
}
