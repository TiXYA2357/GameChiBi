# 游戏赤壁 #

## <span style="color:purple;">源自中国古代赤壁战争而创作的玩法</span> ##

---
### <span style="color:cyan;">游戏玩法:</span> ###
* <span style="color:yellow;">每局战事10分钟,可以选择支援自己喜欢的某个势力进行支援,</span>
* <span style="color:yellow;">如果支援的势力在本局中取得最终胜利，支援获胜方可获得2.7倍支援物资</span>
* <span style="color:yellow;">每个势力的获胜几率均相同</span>

**<span style="color:red;">严禁利用游戏进行外围</span>**

_(什么是外围?外围指利用随机结果进行现实中的金钱赌博)_

---

### <span style="color:cyan;">插件功能:</span>
1. [x] <span style="color:yellow;">自定义指令</span>
2. [x] <span style="color:yellow;">自定义标题</span>
3. [x] <span style="color:yellow;">自定义倍率</span>
4. [x] <span style="color:yellow;">自定义战况</span>
5. [x] <span style="color:yellow;">自定义势力</span>
6. [x] <span style="color:lightgreen;">管理员控盘</span>
7. [x] <span style="color:red;">关服保存战局数据</span>
8. [x] <span style="color:yellow;">自定义默认经济</span>
9. [x] <span style="color:yellow;">自定义经济名称</span>
10. [x] <span style="color:yellow;">自定义支援范围限制</span>
11. [x] <span style="color:yellow;">自定义每次战局时间</span>
12. [x] <span style="color:yellow;">战事历史查询</span>
13. [x] <span style="color:yellow;">玩家支援记录查询</span>
14. [x] <span style="color:yellow;">上局战局结算查询</span>
15. [x] <span style="color:yellow;">自定义保存玩家或战事日志数量</span>
16. [x] <span style="color:yellow;">修改战局进程</span>
17. [x] <span style="color:yellow;">以经济为条件执行自定义指令</span>

---
### <span style="color:cyan;">配置文件:</span>
```yaml
三界斗命令: gcb
物资名称: §a斗币§r§f
消息前缀: §b三界斗 §a>>> §7
UI标题: §e三界斗
上次获胜势力: §b神界§r
最终开盘: 随机
斗币变量: '{dcoin}'
战利品返还倍率: 2.7
三界斗次数: 1
最小增援: 1000
最大增援: 10000
默认斗币数量: 100
保留战事日志数量: 300
保留玩家增援记录: 50
战局时长: 600
战局倒计时记录: 600
规则介绍: |-
  每次战事为十分钟,
  可以选择支援自己喜欢的某个势力进行支援,
  如果支援的势力在本局中取得最终胜利，
  支援获胜方可获得2.7倍支援物资
  每个势力的获胜几率相同
  严禁利用游戏进行外围
  (什么是外围?外围指利用随机结果进行现实中的金钱赌博)
势力列表:
  - §b神界§r
  - §e佛界§r
  - §c魔界§r
战局:
  - '{win} §r§f趁夜劫粮, {lose} §r§f士气大减'
  - '{win} §r§f大受鼓舞,强攻 {lose} §r§f大营'
  - '{win} §r§f使用火计, {lose} §r§f中计损兵'
  - '{win} §r§f武将单挑,直取 {lose} §r§f武将首级'
  - '{win} §r§f山路埋伏, {lose} §r§f中伏惨败'
  - '{win} §r§f全军冲锋, {lose} §r§f战线后退'
  - '{win} §r§f增兵前线, {lose} §r§f士气低落'
  - '{win} §r§f假传军令, {lose} §r§f中计内斗'
  - '{win} §r§f援军到达, {lose} §r§f受夹击大败'
  - '{win} §r§f迂回攻击, {lose} §r§f侧翼受损'
  - '{win} §r§f新得名将, {lose} §r§f士气低落'
  - '{win} §r§f得奇才军事, {lose} §r§f人心浮动'
  - '{win} §r§f奉旨讨逆, {lose} §r§f人心不稳'
  - '{win} §r§f偷袭后方, {lose} §r§f士气大落'
  - '{win} §r§f深夜偷营, {lose} §r§f损失惨重'
  - '{win} §r§f长驱直入, {lose} §r§f主将受伤'
  - '{win} §r§f使用诈降计, {lose} §r§f中计受损'
  - '{win} §r§f调动船队突袭, {lose} §r§f水军大败'
  - '{win} §r§f猛将直取 {lose} §r§f主将,士气大振'
  - '{win} §r§f阵法精奇, {lose} §r§f无以应对'
  - '{win} §r§f无懈可击, {lose} §r§f无可奈何'
  - '{win} §r§f指挥若定, {lose} §r§f无功而返'
  - '{win} §r§f三军齐动, {lose} §r§f望风而逃'
  - '{win} §r§f诱敌深入, {lose} §r§f中伏大败'
  - '{win} §r§f火箭齐射, 火烧 {lose} §r§f大营'

#系统运算数据,请勿修改
上次获胜玩家: []
#系统运算数据,请勿修改
势力增援: []
#系统运算数据,请勿修改
增援玩家列表:
  - §b神界§r: []
  - §e佛界§r: []
  - §c魔界§r: []

战事日志: []

```
---
### <span style="color:red;">行为许可:</span>
_<span style="color:purple;">如果您有以下违规行为请立刻停止对此插件的使用</span>_

**<span style="color:brown;">[原则性条件]</span>**
1. <span style="color:lightgreen;">任何参与此插件进行了数据运算的游戏经济在<span style="color:cyan;">[获得方式]
<span style="color:lightgreen;">上不得以任何形式涉及现实货币经济,</span>
2. <span style="color:lightgreen;">即使该游戏经济可以通过游戏行为免费获得的,</span>
3. <span style="color:lightgreen;">付费型游戏经济免费赠送的,付费性游戏经济赠品进行兑换的,</span>
4. <span style="color:lightgreen;">玩家以任何形式发起赞助从而赠送或交易的,线下交易的</span>

**<span style="color:brown;">[转载及整合]</span>**
* <span style="color:lightgreen;">用户可以无需授权许可转载此插件至其它平台,但需要注明出处</span>
* <span style="color:lightgreen;">用户不得以任何方式直接或间接的方式倒卖此插件或声称插件是自己原创的</span>
* <span style="color:lightgreen;">用户在[免费]型整合包上附带此插件无需授权许可,但需要注明出处</span>
* <span style="color:lightgreen;">用户在[付费]型整合包上附带此插件需要找作者进行授权许可,并且需要注明出处</span>
* <span style="color:lightgreen;">开发者在使用了此插件或调用了第三方调用了此插件API时,因该项目而参与了相关数据运算的游戏经济在[获得方式]上不允许任何形式的涉及现实货币经济</span>
---

**<span style="color:brown;">[免责声明]</span>**
* <span style="color:lightgreen;">用户使用此插件造成的任何后果均为用户个人观点/意愿,与作者无关,需用户自行承担</span>

**<span style="color:brown;">[小提示]</span>**

**<span style="color:lightgreen;">《中华人民共和国刑法》第三百零三条规定法规定</span>**

* <span style="color:lightgreen;">以营利为目的，聚众赌博或者以赌博为业的，处三年以下有期徒刑、拘役或者管制，并处罚金。</span>
* <span style="color:lightgreen;">开设赌场的，处五年以下有期徒刑、拘役或者管制，并处罚金；情节严重的，处五年以上十年以下有期徒刑，并处罚金。</span>
---
* <span style="color:lightgreen;">此插件仅供娱乐和学习交流使用,请勿用于商业用途</span>
* <span style="color:lightgreen;">版权所有: <span style="color:cyan;">@TiXYA2357</span>



