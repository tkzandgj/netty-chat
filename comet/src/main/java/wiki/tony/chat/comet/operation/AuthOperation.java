package wiki.tony.chat.comet.operation;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wiki.tony.chat.base.bean.Constants;
import wiki.tony.chat.base.service.AuthService;
import wiki.tony.chat.base.bean.Proto;

/**
 * 认证操作
 */
@Component
public class AuthOperation extends AbstractOperation {

    private final Logger logger = LoggerFactory.getLogger(AuthOperation.class);

    @Value("${server.id}")
    private int serverId;
    @Autowired
    private AuthService authService;

    @Override
    public Integer op() {
        return Constants.OP_AUTH;
    }

    @Override
    public void action(Channel ch, Proto proto) throws Exception {
        // connection auth
        /**
         * 把用户的信息存储到Netty中的AttributeMap中，因为是存储到Channel上的AttributeMap中的
         * 这样的话在多个ChannelHandler之间是可以共享的
         */
        setKey(ch, authService.auth(serverId, proto));

        // write reply
        proto.setOperation(Constants.OP_AUTH_REPLY);
        proto.setBody(null);
        ch.writeAndFlush(proto);

        logger.debug("auth ok");
    }

}
