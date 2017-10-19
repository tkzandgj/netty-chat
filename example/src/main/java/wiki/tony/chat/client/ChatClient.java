package wiki.tony.chat.client;

import com.google.protobuf.ByteString;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import wiki.tony.chat.base.bean.Proto;
import wiki.tony.chat.base.pb.Auth;
import wiki.tony.chat.base.pb.Message;
import wiki.tony.chat.comet.codec.TcpProtoCodec;

public class ChatClient {

    private static final String host = "127.0.0.1";
    private static final Integer port = 9090;


    public static void start() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 编码解码
                            ch.pipeline().addLast(new TcpProtoCodec());
                        }
                    });

            ChannelFuture f = b.connect().sync();

            Auth.AuthReq authReq = Auth.AuthReq.newBuilder()
                    .setUid(1)
                    .setToken("test")
                    .build();
            Proto proto = new Proto();
            proto.setVersion((short) 1);
            proto.setOperation(1);
            proto.setBody(authReq.toByteArray());
            f.channel().writeAndFlush(proto);

            Message.MsgData msgData= Message.MsgData.newBuilder()
                    .setTo(1)
                    .setType(Message.MsgType.SINGLE_TEXT)
                    .setData(ByteString.copyFromUtf8("TEST"))
                    .build();
            proto = new Proto();
            proto.setVersion((short) 1);
            proto.setOperation(5);
            proto.setBody(msgData.toByteArray());
            f.channel().writeAndFlush(proto);

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args){
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
