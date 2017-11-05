# CoolqJavaSDK

## 简介

一个基于[CoolqSocketAPI](https://github.com/yukixz/cqsocketapi)二次开发的CoolqJavaSDK。

感谢@[jqqqqqqqqqq](https://github.com/jqqqqqqqqqq)给与了非常多的关键性指导（以及他的[repo](https://github.com/jqqqqqqqqqq/coolq-telegram-bot)）。

目前还是半成品。

## 最近

- 项目整体结构变化，讲模块层次继续下移，并且整体打包为一个package。

## TODO

- **为所有的`ex.printStackTrace()`加上应有的异常处理。**
- 把协议中剩余的Msg全部实现，并添加相应的deal接口。
- 测试框架的稳定性。
- 用正则过滤图片、at信息等等。


## 一个简单的复读机

```java
import cqjsdk.msg.*;
import cqjsdk.*;

public class FuDuJi {
    public class FuDuJi_Module extends CQJModule {
        public FuDuJi_Module(){
            String[] strings ={"GroupMessage"};
            register(strings);
        }
        protected void dealGroupMsg(RecvGroupMsg msg){
            String text = msg.getText();
            text = "（".concat(text).concat("）");
            SendGroupMsg smsg = new SendGroupMsg(msg.getGroup(), text);
            sendMsg(smsg);
        }
    }
    public void go(){
        FuDuJi_Module f = new FuDuJi_Module();
        Client c = Client.getClient(11235,23333);
        c.start();
    }
    public static void main(String[] args){
        FuDuJi fdj = new FuDuJi();
        fdj.go();
    }
}
```

