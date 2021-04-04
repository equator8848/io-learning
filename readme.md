# Java网络编程系统学习
> 参考资料：《Netty权威指南》
# IO入门

## BIO

- 为每个客户端连接创建一个新的线程
- 客户端个数:IO线程数 = 1:1
- 同步阻塞
## 伪异步IO

- 使用线程池
- 客户端个数:IO线程数 = M:N（M>>N）
- 同步阻塞
## NIO

- Selector 多路复用器（选择器）
- 客户端个数:IO线程数 = M:1
- 同步非阻塞
## AIO

- NIO2.0新增异步套接字通道
- 异步非阻塞
- 客户端个数:IO线程数 = M:0 （不需要额外的IO线程，被动回调）
## Netty

- Netty是NIO框架
# 粘包和拆包

- LineBasedFrameDecoder是以换行符为结束标志的解码器
- DelimiterBasedFrameDecoder用于使用指定分隔符分割的消息解码
- FixedLengthFrameDecoder用于固定长度的消息解码

# 编码与解码

## 编码解码技术选型要点

- 是否跨语言
- 编码后的大小
- 编解码的性能
- API是否方便，开发者工作量

## 常用编解码框架技术

### Protobuf

- 结构化数据存储格式
- 高效的解码编码性能
- 跨语言，官方支持Java、C++、Python
- 使用数据描述文件对数据结构进行说明，代码生成机制

### Thrift

> 适用于静态的数据交换，需要先确定好它的数据结构，数据结构发生变化时，必须重新编辑IDL文件，编译生成代码

- 主要组成部分：语言系统与IDL编辑器、TProtocol、TTransport、TProcessor、TServer

### JBoss Marshalling

- 更多是在JBoss内部使用

## MessagePack编码解码器

