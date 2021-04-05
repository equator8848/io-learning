// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: protobuf/SubscribeReq.proto

package com.equator.echo.protobuf;

public final class SubscribeReqProto {
  private SubscribeReqProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface SubscribeReqOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required int32 subReqId = 1;
    /**
     * <code>required int32 subReqId = 1;</code>
     */
    boolean hasSubReqId();
    /**
     * <code>required int32 subReqId = 1;</code>
     */
    int getSubReqId();

    // required string userName = 2;
    /**
     * <code>required string userName = 2;</code>
     */
    boolean hasUserName();
    /**
     * <code>required string userName = 2;</code>
     */
    java.lang.String getUserName();
    /**
     * <code>required string userName = 2;</code>
     */
    com.google.protobuf.ByteString
        getUserNameBytes();

    // required string productName = 3;
    /**
     * <code>required string productName = 3;</code>
     */
    boolean hasProductName();
    /**
     * <code>required string productName = 3;</code>
     */
    java.lang.String getProductName();
    /**
     * <code>required string productName = 3;</code>
     */
    com.google.protobuf.ByteString
        getProductNameBytes();

    // repeated string address = 4;
    /**
     * <code>repeated string address = 4;</code>
     *
     * <pre>
     * repeated 表示list
     * </pre>
     */
    java.util.List<java.lang.String>
    getAddressList();
    /**
     * <code>repeated string address = 4;</code>
     *
     * <pre>
     * repeated 表示list
     * </pre>
     */
    int getAddressCount();
    /**
     * <code>repeated string address = 4;</code>
     *
     * <pre>
     * repeated 表示list
     * </pre>
     */
    java.lang.String getAddress(int index);
    /**
     * <code>repeated string address = 4;</code>
     *
     * <pre>
     * repeated 表示list
     * </pre>
     */
    com.google.protobuf.ByteString
        getAddressBytes(int index);
  }
  /**
   * Protobuf type {@code netty.SubscribeReq}
   */
  public static final class SubscribeReq extends
      com.google.protobuf.GeneratedMessage
      implements SubscribeReqOrBuilder {
    // Use SubscribeReq.newBuilder() to construct.
    private SubscribeReq(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private SubscribeReq(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final SubscribeReq defaultInstance;
    public static SubscribeReq getDefaultInstance() {
      return defaultInstance;
    }

    public SubscribeReq getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private SubscribeReq(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              subReqId_ = input.readInt32();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              userName_ = input.readBytes();
              break;
            }
            case 26: {
              bitField0_ |= 0x00000004;
              productName_ = input.readBytes();
              break;
            }
            case 34: {
              if (!((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
                address_ = new com.google.protobuf.LazyStringArrayList();
                mutable_bitField0_ |= 0x00000008;
              }
              address_.add(input.readBytes());
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
          address_ = new com.google.protobuf.UnmodifiableLazyStringList(address_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.equator.echo.protobuf.SubscribeReqProto.internal_static_netty_SubscribeReq_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.equator.echo.protobuf.SubscribeReqProto.internal_static_netty_SubscribeReq_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq.class, com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq.Builder.class);
    }

    public static com.google.protobuf.Parser<SubscribeReq> PARSER =
        new com.google.protobuf.AbstractParser<SubscribeReq>() {
      public SubscribeReq parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new SubscribeReq(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<SubscribeReq> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required int32 subReqId = 1;
    public static final int SUBREQID_FIELD_NUMBER = 1;
    private int subReqId_;
    /**
     * <code>required int32 subReqId = 1;</code>
     */
    public boolean hasSubReqId() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required int32 subReqId = 1;</code>
     */
    public int getSubReqId() {
      return subReqId_;
    }

    // required string userName = 2;
    public static final int USERNAME_FIELD_NUMBER = 2;
    private java.lang.Object userName_;
    /**
     * <code>required string userName = 2;</code>
     */
    public boolean hasUserName() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required string userName = 2;</code>
     */
    public java.lang.String getUserName() {
      java.lang.Object ref = userName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          userName_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string userName = 2;</code>
     */
    public com.google.protobuf.ByteString
        getUserNameBytes() {
      java.lang.Object ref = userName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        userName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    // required string productName = 3;
    public static final int PRODUCTNAME_FIELD_NUMBER = 3;
    private java.lang.Object productName_;
    /**
     * <code>required string productName = 3;</code>
     */
    public boolean hasProductName() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required string productName = 3;</code>
     */
    public java.lang.String getProductName() {
      java.lang.Object ref = productName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          productName_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string productName = 3;</code>
     */
    public com.google.protobuf.ByteString
        getProductNameBytes() {
      java.lang.Object ref = productName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        productName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    // repeated string address = 4;
    public static final int ADDRESS_FIELD_NUMBER = 4;
    private com.google.protobuf.LazyStringList address_;
    /**
     * <code>repeated string address = 4;</code>
     *
     * <pre>
     * repeated 表示list
     * </pre>
     */
    public java.util.List<java.lang.String>
        getAddressList() {
      return address_;
    }
    /**
     * <code>repeated string address = 4;</code>
     *
     * <pre>
     * repeated 表示list
     * </pre>
     */
    public int getAddressCount() {
      return address_.size();
    }
    /**
     * <code>repeated string address = 4;</code>
     *
     * <pre>
     * repeated 表示list
     * </pre>
     */
    public java.lang.String getAddress(int index) {
      return address_.get(index);
    }
    /**
     * <code>repeated string address = 4;</code>
     *
     * <pre>
     * repeated 表示list
     * </pre>
     */
    public com.google.protobuf.ByteString
        getAddressBytes(int index) {
      return address_.getByteString(index);
    }

    private void initFields() {
      subReqId_ = 0;
      userName_ = "";
      productName_ = "";
      address_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasSubReqId()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasUserName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasProductName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt32(1, subReqId_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getUserNameBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeBytes(3, getProductNameBytes());
      }
      for (int i = 0; i < address_.size(); i++) {
        output.writeBytes(4, address_.getByteString(i));
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, subReqId_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getUserNameBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(3, getProductNameBytes());
      }
      {
        int dataSize = 0;
        for (int i = 0; i < address_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeBytesSizeNoTag(address_.getByteString(i));
        }
        size += dataSize;
        size += 1 * getAddressList().size();
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code netty.SubscribeReq}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.equator.echo.protobuf.SubscribeReqProto.SubscribeReqOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.equator.echo.protobuf.SubscribeReqProto.internal_static_netty_SubscribeReq_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.equator.echo.protobuf.SubscribeReqProto.internal_static_netty_SubscribeReq_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq.class, com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq.Builder.class);
      }

      // Construct using com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        subReqId_ = 0;
        bitField0_ = (bitField0_ & ~0x00000001);
        userName_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        productName_ = "";
        bitField0_ = (bitField0_ & ~0x00000004);
        address_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000008);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.equator.echo.protobuf.SubscribeReqProto.internal_static_netty_SubscribeReq_descriptor;
      }

      public com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq getDefaultInstanceForType() {
        return com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq.getDefaultInstance();
      }

      public com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq build() {
        com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq buildPartial() {
        com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq result = new com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.subReqId_ = subReqId_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.userName_ = userName_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.productName_ = productName_;
        if (((bitField0_ & 0x00000008) == 0x00000008)) {
          address_ = new com.google.protobuf.UnmodifiableLazyStringList(
              address_);
          bitField0_ = (bitField0_ & ~0x00000008);
        }
        result.address_ = address_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq) {
          return mergeFrom((com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq other) {
        if (other == com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq.getDefaultInstance()) return this;
        if (other.hasSubReqId()) {
          setSubReqId(other.getSubReqId());
        }
        if (other.hasUserName()) {
          bitField0_ |= 0x00000002;
          userName_ = other.userName_;
          onChanged();
        }
        if (other.hasProductName()) {
          bitField0_ |= 0x00000004;
          productName_ = other.productName_;
          onChanged();
        }
        if (!other.address_.isEmpty()) {
          if (address_.isEmpty()) {
            address_ = other.address_;
            bitField0_ = (bitField0_ & ~0x00000008);
          } else {
            ensureAddressIsMutable();
            address_.addAll(other.address_);
          }
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasSubReqId()) {
          
          return false;
        }
        if (!hasUserName()) {
          
          return false;
        }
        if (!hasProductName()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.equator.echo.protobuf.SubscribeReqProto.SubscribeReq) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required int32 subReqId = 1;
      private int subReqId_ ;
      /**
       * <code>required int32 subReqId = 1;</code>
       */
      public boolean hasSubReqId() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required int32 subReqId = 1;</code>
       */
      public int getSubReqId() {
        return subReqId_;
      }
      /**
       * <code>required int32 subReqId = 1;</code>
       */
      public Builder setSubReqId(int value) {
        bitField0_ |= 0x00000001;
        subReqId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 subReqId = 1;</code>
       */
      public Builder clearSubReqId() {
        bitField0_ = (bitField0_ & ~0x00000001);
        subReqId_ = 0;
        onChanged();
        return this;
      }

      // required string userName = 2;
      private java.lang.Object userName_ = "";
      /**
       * <code>required string userName = 2;</code>
       */
      public boolean hasUserName() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required string userName = 2;</code>
       */
      public java.lang.String getUserName() {
        java.lang.Object ref = userName_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          userName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string userName = 2;</code>
       */
      public com.google.protobuf.ByteString
          getUserNameBytes() {
        java.lang.Object ref = userName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          userName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string userName = 2;</code>
       */
      public Builder setUserName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        userName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string userName = 2;</code>
       */
      public Builder clearUserName() {
        bitField0_ = (bitField0_ & ~0x00000002);
        userName_ = getDefaultInstance().getUserName();
        onChanged();
        return this;
      }
      /**
       * <code>required string userName = 2;</code>
       */
      public Builder setUserNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        userName_ = value;
        onChanged();
        return this;
      }

      // required string productName = 3;
      private java.lang.Object productName_ = "";
      /**
       * <code>required string productName = 3;</code>
       */
      public boolean hasProductName() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>required string productName = 3;</code>
       */
      public java.lang.String getProductName() {
        java.lang.Object ref = productName_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          productName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string productName = 3;</code>
       */
      public com.google.protobuf.ByteString
          getProductNameBytes() {
        java.lang.Object ref = productName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          productName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string productName = 3;</code>
       */
      public Builder setProductName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        productName_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string productName = 3;</code>
       */
      public Builder clearProductName() {
        bitField0_ = (bitField0_ & ~0x00000004);
        productName_ = getDefaultInstance().getProductName();
        onChanged();
        return this;
      }
      /**
       * <code>required string productName = 3;</code>
       */
      public Builder setProductNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        productName_ = value;
        onChanged();
        return this;
      }

      // repeated string address = 4;
      private com.google.protobuf.LazyStringList address_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      private void ensureAddressIsMutable() {
        if (!((bitField0_ & 0x00000008) == 0x00000008)) {
          address_ = new com.google.protobuf.LazyStringArrayList(address_);
          bitField0_ |= 0x00000008;
         }
      }
      /**
       * <code>repeated string address = 4;</code>
       *
       * <pre>
       * repeated 表示list
       * </pre>
       */
      public java.util.List<java.lang.String>
          getAddressList() {
        return java.util.Collections.unmodifiableList(address_);
      }
      /**
       * <code>repeated string address = 4;</code>
       *
       * <pre>
       * repeated 表示list
       * </pre>
       */
      public int getAddressCount() {
        return address_.size();
      }
      /**
       * <code>repeated string address = 4;</code>
       *
       * <pre>
       * repeated 表示list
       * </pre>
       */
      public java.lang.String getAddress(int index) {
        return address_.get(index);
      }
      /**
       * <code>repeated string address = 4;</code>
       *
       * <pre>
       * repeated 表示list
       * </pre>
       */
      public com.google.protobuf.ByteString
          getAddressBytes(int index) {
        return address_.getByteString(index);
      }
      /**
       * <code>repeated string address = 4;</code>
       *
       * <pre>
       * repeated 表示list
       * </pre>
       */
      public Builder setAddress(
          int index, java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureAddressIsMutable();
        address_.set(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string address = 4;</code>
       *
       * <pre>
       * repeated 表示list
       * </pre>
       */
      public Builder addAddress(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureAddressIsMutable();
        address_.add(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string address = 4;</code>
       *
       * <pre>
       * repeated 表示list
       * </pre>
       */
      public Builder addAllAddress(
          java.lang.Iterable<java.lang.String> values) {
        ensureAddressIsMutable();
        super.addAll(values, address_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string address = 4;</code>
       *
       * <pre>
       * repeated 表示list
       * </pre>
       */
      public Builder clearAddress() {
        address_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000008);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string address = 4;</code>
       *
       * <pre>
       * repeated 表示list
       * </pre>
       */
      public Builder addAddressBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureAddressIsMutable();
        address_.add(value);
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:netty.SubscribeReq)
    }

    static {
      defaultInstance = new SubscribeReq(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:netty.SubscribeReq)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_netty_SubscribeReq_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_netty_SubscribeReq_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\033protobuf/SubscribeReq.proto\022\005netty\"X\n\014" +
      "SubscribeReq\022\020\n\010subReqId\030\001 \002(\005\022\020\n\010userNa" +
      "me\030\002 \002(\t\022\023\n\013productName\030\003 \002(\t\022\017\n\007address" +
      "\030\004 \003(\tB.\n\031com.equator.echo.protobufB\021Sub" +
      "scribeReqProto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_netty_SubscribeReq_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_netty_SubscribeReq_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_netty_SubscribeReq_descriptor,
              new java.lang.String[] { "SubReqId", "UserName", "ProductName", "Address", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
