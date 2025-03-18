package TiXYA2357.GameSJD.Command;

import TiXYA2357.GameSJD.Form_Custom;
import TiXYA2357.GameSJD.Form_Simple;
import TiXYA2357.GameSJD.Lib;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.handler.FormResponseHandler;
import cn.nukkit.form.window.FormWindowCustom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static TiXYA2357.GameSJD.Lib.*;
import static TiXYA2357.GameSJD.Main.nks;
import static TiXYA2357.GameSJD.RepeatTask.RandomOneStatus;
import static TiXYA2357.GameSJD.RepeatTask.timeDown;

public class ChiBiCommand extends Command {

    public ChiBiCommand(String name) {
        super(name,"§r§a三界斗");
    }
    /**
     *   UI界面
     * <p>
     *   标题
     * <p>
     *   按钮1： 刷新界面
     *   按钮2：支援势力
     *   按钮3：上次战利品表
     *   按钮4：游戏规则介绍
     *   按钮5：（admin） 设置开盘
     * <p>
     *
     * */
    @Override
    public boolean execute(CommandSender sender, String str, String[] args) {
        if (sender instanceof Player p){
            if (args.length == 0) {
                String status = "§f第 " + SJDCount + " §f届 " + UIT + " §r§f正在进行中,将在 " + timeDown
                + " 秒后分出胜负,请求支援!\n\n§r§d上次战事 " + (lastWinner.isEmpty() ? "暂无势力" : lastWinner)
                + " §r§d获胜\n\n" + RandomOneStatus + "\n\n"  + getAllOnesMoney() ;
                Form_Simple form = new Form_Simple(UIT,status);
                form.add("§f刷新当前列表" , () -> Delayed(() -> nks.dispatchCommand(p,ChiBiCMD),5));//刷新战况
                if (Integer.parseInt(getPlayerInfo(p,"战利品").toString()) < 1)
                 form.add("§f支援" , () -> nks.dispatchCommand(p,ChiBiCMD + " support"));//todo 加个领取奖励判断
                else form.add("§d领取战利品", () -> nks.dispatchCommand(p,ChiBiCMD + " gold"));
                form.add("§f上次获胜支援方" , () -> nks.dispatchCommand(p,ChiBiCMD + " last_win"));
                form.add("§f我的增援记录" , () -> nks.dispatchCommand(p,ChiBiCMD + " mylog"));
                if (p.isOp()) form.add("§f管理菜单" , () -> nks.dispatchCommand(p,ChiBiCMD + " admin"));
                form.add("§f战事记录历史" , () -> nks.dispatchCommand(p,ChiBiCMD + " slog"));
                form.add("§f游戏规则介绍" , () -> nks.dispatchCommand(p,ChiBiCMD + " rule"));
                form.show(p); }else {
                switch (args[0].toLowerCase()) {
                    case "last_win" -> {
                        String lastWinner = "上次战事 " + (Lib.lastWinner.isEmpty() ? "暂无势力" : Lib.lastWinner) + " §r§f获胜\n\n 上次获胜的支援方:\n\n "
                        + (lastPlayers.isEmpty() ? "暂无获胜支援方" : lastPlayers.toString().replace("[", "")
                        .replace("]", "").replace(",", "\n§r§f"));
                        Form_Simple form = new Form_Simple(UIT + " §r§a上次获胜支援方",lastWinner);
                        form.add("§f返回" , () -> nks.dispatchCommand(p,ChiBiCMD));
                        form.setClose(() -> nks.dispatchCommand(p,ChiBiCMD));
                        form.show(p);
                    }
                    case "mylog" -> {
                        String mylog = getPlayerInfo(p,"支援记录").toString()
                        == null ? "暂无支援记录" : getPlayerInfo(p,"支援记录").toString()
                        .replace("[", "").replace("]", "")
                        .replace(",", "\n§e").replace("，", ",");
                        Form_Simple form = new Form_Simple(UIT + " §r§a我的支援记录","我的增援记录:\n\n "+mylog);
                        form.add("§f返回" , () -> nks.dispatchCommand(p,ChiBiCMD));
                        form.setClose(() -> nks.dispatchCommand(p,ChiBiCMD));
                        form.show(p);
                    }
                    case "slog" -> {
                        String slog = Lib.slog.isEmpty() ? "暂无战事记录" : Lib.slog.toString()
                        .replace("[", "").replace("]", "")
                        .replace(",", "\n§7").replace("，", ",");
                        Form_Simple form = new Form_Simple(UIT + " §r§a战事历史","战事记录历史:\n\n "+slog);
                        form.add("§f返回" , () -> nks.dispatchCommand(p,ChiBiCMD));
                        form.setClose(() -> nks.dispatchCommand(p,ChiBiCMD));
                        form.show(p);
                    }
                    case "rule" -> {
                        Form_Simple form = new Form_Simple(UIT,"§f游戏规则介绍:\n\n"+ChiBiRule);
                        form.add("§f返回" , () -> nks.dispatchCommand(p,ChiBiCMD));
                        form.setClose(() -> nks.dispatchCommand(p,ChiBiCMD));
                        form.show(p);
                    }
                    case "support" -> {
                        if (Integer.parseInt(getPlayerInfo(p,"战利品").toString()) > 0){
                        nks.dispatchCommand(p,ChiBiCMD + " gold"); return true;}
                        if (!getPlayerInfo(p,"支援场次编号").equals(SJDCount.toString())){
                            setPlayerInfo(p,"支援场次编号",SJDCount.toString());}
                        Form_Custom form = new Form_Custom(UIT+" §r§a支援势力");
                        AtomicInteger old_m = new AtomicInteger();
                        PlayerHT.forEach(num -> {//hash表(玩家的UUID:支援数额)
                            if (num.split(":")[0].equals(p.getUniqueId().toString()))
                                old_m.set(Integer.parseInt(num.split(":")[1]));});
                        if (!PlayerHelpOnes.getOrDefault(p.getUniqueId(),"暂无").equals("暂无")) {
                            form.add("支援记录",new ElementLabel("§d你已支援 " + PlayerHelpOnes.get(p.getUniqueId())
                            + "§r§d " + old_m.get() + " §r§d" + CoinName));
                        }
                        form.add("势力",new ElementStepSlider("§f选择需要支援的势力: §", ones));
                        form.add("zy", new ElementInput("请输入支援的"+CoinName + "§r§f(支援数量在 "
                        + MinMoney + " - " + MaxMoney + " 之间)", "" ,MinMoney+""));
                        form.add("MyCoin", new ElementLabel("§f我拥有 §r§f" + getPlayerInfo(p,"斗币") + " " + CoinName));
                        form.setClose(() -> nks.dispatchCommand(p,ChiBiCMD));
                        form.setSubmit(() -> {try {
                           Delayed(() -> nks.dispatchCommand(p, ChiBiCMD + " support"),10);
                            String ones = form.getStepSliderRes("势力");
                            if (!PlayerHelpOnes.getOrDefault(p.getUniqueId(), ones).replace("暂无", ones).equals(ones)) {
                                p.sendMessage(PT + "你已支援过了 " + PlayerHelpOnes.get(p.getUniqueId()) + " §r§7势力,不可支援多个势力"); return;
                            }
                            int m = getPlayerInfo(p, "斗币");
                            if (MinMoney < 1) MinMoney = 1;
                            if (m == 0 || m < MinMoney) {
                                p.sendMessage(PT + "输入数量过低或你没有足够的 " + CoinName); return;
                            }
                            if (old_m.get() + Integer.parseInt(form.getInputRes("zy")) > MaxMoney) {
                                p.sendMessage(PT + "你最多可以支援 " + MaxMoney + " " + CoinName); return;
                            }
                            m -= Integer.parseInt(form.getInputRes("zy"));
                            setPlayerInfo(p, "斗币", m);
                            PlayerHT.add(p.getUniqueId() + ":" + (old_m.get() + Integer.parseInt(form.getInputRes("zy"))));
                            setPlayerInfo(p, "支援势力经济", (old_m.get() + Integer.parseInt(form.getInputRes("zy"))));
                            PlayerHelpOnes.put(p.getUniqueId(), ones);
                            setPlayerInfo(p, "支援势力", ones);
                            onesPlayer.forEach(onesPlayer -> {
                                if (onesPlayer.containsKey(ones))
                                    onesPlayer.put(ones, PlayerHT);
                            });
                            setConfig("增援玩家列表", onesPlayer);
                            HashMap<String, Integer> map = new HashMap<>();
                            map.put(ones, map.getOrDefault(ones, 0) + Integer.parseInt(form.getInputRes("zy")));
                            p.sendMessage(PT + "你已成功支援 " + ones + " §r§7势力 " + form.getInputRes("zy") + " §r§7" + CoinName);
                            nks.dispatchCommand(p, ChiBiCMD);
                            AtomicBoolean hasMap = new AtomicBoolean(false);
                            onesMoney.forEach(om -> {//添加到各个势力总支援的列表中
                                if (om.containsKey(ones)) om.put(ones, om.getOrDefault(ones, 0)
                                        + Integer.parseInt(form.getInputRes("zy")));
                                hasMap.set(true);
                            }); if (!hasMap.get()) onesMoney.add(map);
                        }catch (NumberFormatException e){p.sendMessage(PT + "输入数量错误");}
                        });//储存支援记录到对应的势力
                        form.show(p);
                    }
                    case "gold" -> {
                        int gold = Integer.parseInt(getPlayerInfo(p,"战利品").toString());
                        if (gold < 1) { nks.dispatchCommand(p, ChiBiCMD + " support"); return true;}
                        p.sendMessage(PT + "你已成功领取战利品: " + gold + " " + CoinName);
                        setPlayerInfo(p,"斗币", Integer.parseInt(getPlayerInfo(p,"斗币").toString()) + gold);
                        setPlayerInfo(p,"战利品",0); setPlayerInfo(p,"支援势力","暂无");
                        PlayerHelpOnes.put(p.getUniqueId(),"暂无");
                        Delayed(() -> nks.dispatchCommand(p, ChiBiCMD + " support"),10);
                    }
                    case "run" -> {
                        if (!p.isOp()) return false;
                        if (args.length == 1) {
                            Form_Custom form = new Form_Custom("§d以玩家运行条件指令§e");
                            form.add("c0",new ElementLabel("§dTip: " + ChiBiCMD + "§r§d run <"
                              + CoinName + "§r§d数量> <指令> 可根据所需数量条件是否执行此指令"));
                            form.add("c1",new ElementDropdown("§d选择玩家",
                                    nks.getOnlinePlayers().values().stream().map(Player::getName).toList()));
                            form.add("c2",new ElementInput("§d输入所需"+CoinName+"§r§d数量","","0"));
                            form.add("c3",new ElementInput("§d输入指令","可以用@p代替玩家",""));
                            form.setSubmit(() -> {try {
                            form.setClose(() -> nks.dispatchCommand(p,ChiBiCMD));
                            String name = form.getRes("c1");
                            int money = Integer.parseInt(form.getRes("c2"));
                            Player target = nks.getPlayer(name);
                            if ((int) getPlayerInfo(target, "斗币") < money) {
                               p.sendMessage(PT + CoinName + " §r§7不足(" + getPlayerInfo(target, "斗币") + "/" + money + ")");return;}
                            setPlayerInfo(target,"斗币",(int) getPlayerInfo(target, "斗币") - money);
                            String cmd = form.getRes("c3"); asOPCmd(target, cmd.replace("@p", target.getName()));
                            } catch (NumberFormatException ex) {p.sendMessage(PT + "请输入正确的数字(整数)");}
                            });form.show(p);
                        } else {
                            int money = Integer.parseInt(args[1]);
                            if ((int) getPlayerInfo(p, "斗币") < money) {
                                p.sendMessage(PT + CoinName + " §r§7不足(" + getPlayerInfo(p, "斗币") + "/" + money + ")");
                                return false;}
                            String cmd = args[2].replace("@p", p.getName());
                            setPlayerInfo(p, "斗币", (int) getPlayerInfo(p, "斗币") - money);
                            asOPCmd(p, cmd);}
                    }
                    case "money" -> {
                        if (!p.isOp()) return false;
                        if (args.length < 3) p.sendMessage(PT + ChiBiCMD
                           + " §r§d帮助:§7\nadd <玩家名> <数量> 增加经济\nset <玩家名> <数量> 设置经济\nremove <玩家名> <数量> 移除经济");
                        else{try{
                            int money = Integer.parseInt(args[3]);
                            Player target = nks.getPlayer(args[2].replace("@p", p.getName()));
                            if (target != null){ String type = "";
                                switch (args[1]) {
                            case "add" -> {
                                setPlayerInfo(target, "斗币", (int) getPlayerInfo(target, "斗币") + money);
                                type = "§r§a增加";}
                            case "set" -> {
                                setPlayerInfo(target, "斗币", money);
                                type = "§r§c设置";}
                            case "remove" -> {
                                setPlayerInfo(target, "斗币", (int) getPlayerInfo(target, "斗币") - money);
                                type = "§r§b移除";}
                            default -> nks.dispatchCommand(p,ChiBiCMD + " money");
                                }if (target.getUniqueId() != p.getUniqueId())
                                   p.sendMessage(PT + "已为 " + target.getName() + type + "§7 " + money + " " + CoinName );
                               target.sendMessage(PT + "你的" + CoinName + "§r§7已" + type + "§7: " + money);}
                        }catch (NumberFormatException ex){p.sendMessage(PT + "请输入正确的数字(整数)");}
                        }}
                    case "admin" -> {
                        if (!p.isOp()) return false;
                        Form_Simple form = new Form_Simple(UIT + " §d管理员界面","");
                        form.add("§f设置开盘" , () -> {
                            Form_Custom form_c = new Form_Custom(UIT+" §f设置开盘");
                            List<String> one = new ArrayList<>(ones); one.add("随机");
                            form_c.add("tip",new ElementLabel("当前最终开盘结果: "+AdminEndWinner));
                            form_c.add("kp",new ElementStepSlider("§f选择一个势力: §", one));
                            form_c.setSubmit(() -> {
                                form_c.setClose(() -> nks.dispatchCommand(p,ChiBiCMD));
                                AdminEndWinner = form_c.getRes("kp");
                                setConfig("最终开盘",AdminEndWinner);
                                p.sendMessage(PT+"§f设置成功: " + AdminEndWinner);
                            });form_c.show(p);});
                        form.add("§f战局进程" , () -> {
                            Form_Custom form_c = new Form_Custom(UIT+" §f战局进程设置");
                            form_c.add("type",new ElementStepSlider("§f选择操作类型: §", Arrays.asList("§f设置","§a增加","§c减少")));
                            form_c.add("time",new ElementInput("§f输入时间(秒)", "",timeDown+""));
                            form_c.setSubmit(() -> {
                                form_c.setClose(() -> nks.dispatchCommand(p,ChiBiCMD));
                                try {int ts = Integer.parseInt(form_c.getRes("time"));
                                    String type = "";
                                    switch (form_c.getRes("type").toString()){
                                        case "§f设置" -> {
                                            if (ts < 1) ts = 0;
                                            timeDown = ts;
                                            type = "§e设置";}
                                        case "§a增加" -> {
                                            if (ts < 1) ts = 1;
                                            timeDown += ts;
                                            type = "§a增加";}
                                        case "§c减少" -> {
                                            if (ts < 1) ts = 1;
                                            timeDown -= ts;
                                            type = "§c减少";}}
                                    p.sendMessage(PT+"成功"+type+"§r§7战局时间进程: "+ts+"秒");
                                } catch (NumberFormatException e) {p.sendMessage(PT+"§f请输入正确的数字(整数)");}});
                            form_c.show(p);});
                        form.add("§f修改玩家经济",() -> {
                            Form_Custom form_c = new Form_Custom(UIT+" §f修改玩家经济");
                            form_c.add("type",new ElementStepSlider("§f选择操作类型: §", Arrays.asList("§f设置","§a增加","§c减少")));
                            form_c.add("pls",new ElementDropdown("§f输入玩家名",
                            nks.getOnlinePlayers().values().stream().map(Player::getName).toList()));
                            form_c.add("coin",new ElementInput("§f输入经济", ""));
                            form_c.add("tips", new ElementLabel("§d输入 " + ChiBiCMD + " §r§dmoney 可获取相关帮助"));
                            form_c.setSubmit(() -> {
                                form_c.setClose(() -> nks.dispatchCommand(p,ChiBiCMD));
                                Player pt = nks.getPlayer((String) form_c.getRes("pls"));
                                try{ int coin = Integer.parseInt(form_c.getRes("coin"));
                                String tp = "";
                                switch (form_c.getRes("type").toString()) {
                                    case "§f设置" -> {setPlayerInfo(pt, "斗币", coin);
                                        tp = "§e设置";}
                                    case "§a增加" -> {
                                        coin += Integer.parseInt(getPlayerInfo(pt,"斗币").toString());
                                        setPlayerInfo(pt,"斗币",coin); tp = "§a增加";}
                                    case "§c减少" -> {
                                        coin = Integer.parseInt(getPlayerInfo(pt,"斗币").toString()) - coin;
                                        if (coin < 0) coin = 0; setPlayerInfo(pt,"斗币",coin); tp = "§c减少";}}
                                p.sendMessage(PT+"§f成功为 "+pt.getName()+" §r"+tp+"§f "+coin+" §f"+CoinName);}
                                catch (NumberFormatException e){p.sendMessage(PT+"§f输入数量错误");}});
                            form_c.show(p);});
                        form.add("§f代替执行指令", () -> {nks.dispatchCommand(p,ChiBiCMD + " run");});
                        form.add("§f重载配置" , () -> {if (loadConfig()) p.sendMessage(PT+"重载成功");
                            else p.sendMessage(PT+"重载失败");});
                        form.add("§f返回" , () -> nks.dispatchCommand(p,ChiBiCMD));
                        form.setClose(() -> nks.dispatchCommand(p,ChiBiCMD));
                        form.show(p);
                    }
                }
            }

        }
        return false;
    }}
