package wiki.tony.chat.comet.bean;

import io.netty.util.AttributeKey;
import wiki.tony.chat.base.bean.AuthToken;

/**
 * 常量
 */
public class Constants {


    /**
     * AttributeMap是绑定在Channel或者ChannelHandlerContext上的一个附件，
     * 主要是用来存储一些业务逻辑数据，相当于依附在这两个对象上的寄生虫一样，相当于附件一样
     * 对于ChannelHandlerContext来说，要是ChannelHandlerContext上如果有AttributeMap
     * 都是绑定上下文的，也就是说如果A的ChannelHandlerContext中的AttributeMap，B的ChannelHandlerContext
     * 是无法读取到的
     *
     * 对于Channel来说，Channel上的AttributeMap里面存的数据是共享的
     */
    public static final AttributeKey<String> KEY_USER_ID = AttributeKey.valueOf("key");

}
