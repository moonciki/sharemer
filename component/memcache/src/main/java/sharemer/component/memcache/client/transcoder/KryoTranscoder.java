package sharemer.component.memcache.client.transcoder;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import net.rubyeye.xmemcached.transcoders.BaseSerializingTranscoder;
import net.rubyeye.xmemcached.transcoders.CachedData;
import net.rubyeye.xmemcached.transcoders.Transcoder;
import net.rubyeye.xmemcached.transcoders.TranscoderUtils;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * mc采用jdk原生序列化方式实现，性能较低，因此采用性能更高的Kryo
 */
public class KryoTranscoder<T> extends BaseSerializingTranscoder implements Transcoder<T> {

    private KryoPool kryoPool;

    public void setPackZeros(boolean packZeros) {
        this.transcoderUtils.setPackZeros(packZeros);
    }

    public void setPrimitiveAsString(boolean primitiveAsString) {
        this.primitiveAsString = primitiveAsString;
    }

    private final int maxSize;

    private boolean primitiveAsString;

    public final int getMaxSize() {
        return this.maxSize;
    }

    public static final int SERIALIZED = 1;
    public static final int COMPRESSED = 2;
    public static final int SPECIAL_MASK = 0xff00;
    public static final int SPECIAL_BOOLEAN = (1 << 8);
    public static final int SPECIAL_INT = (2 << 8);
    public static final int SPECIAL_LONG = (3 << 8);
    public static final int SPECIAL_DATE = (4 << 8);
    public static final int SPECIAL_BYTE = (5 << 8);
    public static final int SPECIAL_FLOAT = (6 << 8);
    public static final int SPECIAL_DOUBLE = (7 << 8);
    public static final int SPECIAL_BYTEARRAY = (8 << 8);

    private final TranscoderUtils transcoderUtils = new TranscoderUtils(true);

    public TranscoderUtils getTranscoderUtils() {
        return transcoderUtils;
    }

    public KryoTranscoder() {
        this(CachedData.MAX_SIZE);
    }

    public KryoTranscoder(int max) {
        this.maxSize = max;
        this.kryoPool = new KryoPool.Builder(() -> {
            Kryo kryo = new Kryo();
            kryo.setRegistrationRequired(false);
            kryo.setReferences(true);

            ((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
            return kryo;
        }).softReferences().build();
    }

    public boolean isPackZeros() {
        return this.transcoderUtils.isPackZeros();
    }

    public boolean isPrimitiveAsString() {
        return this.primitiveAsString;
    }

    public final T decode(CachedData d) {
        Object obj = d.decodedObject;
        if (obj != null) {
            return (T) obj;
        }
        byte[] data = d.getData();

        int flags = d.getFlag();
        if ((flags & COMPRESSED) != 0) {
            data = decompress(data);
        }
        flags = flags & SPECIAL_MASK;
        obj = decode0(d, data, flags);
        d.decodedObject = obj;
        return (T) obj;
    }

    protected final Object decode0(CachedData cachedData, byte[] data, int flags) {
        Object rv = null;
        if ((cachedData.getFlag() & SERIALIZED) != 0 && data != null) {
            rv = deserialize(data);
        } else {
            if (this.primitiveAsString) {
                if (flags == 0) {
                    return decodeString(data);
                }
            }
            if (flags != 0 && data != null) {
                switch (flags) {
                    case SPECIAL_BOOLEAN:
                        rv = Boolean.valueOf(this.transcoderUtils.decodeBoolean(data));
                        break;
                    case SPECIAL_INT:
                        rv = Integer.valueOf(this.transcoderUtils.decodeInt(data));
                        break;
                    case SPECIAL_LONG:
                        rv = Long.valueOf(this.transcoderUtils.decodeLong(data));
                        break;
                    case SPECIAL_BYTE:
                        rv = Byte.valueOf(this.transcoderUtils.decodeByte(data));
                        break;
                    case SPECIAL_FLOAT:
                        rv = new Float(Float.intBitsToFloat(this.transcoderUtils.decodeInt(data)));
                        break;
                    case SPECIAL_DOUBLE:
                        rv = new Double(Double.longBitsToDouble(this.transcoderUtils.decodeLong(data)));
                        break;
                    case SPECIAL_DATE:
                        rv = new Date(this.transcoderUtils.decodeLong(data));
                        break;
                    case SPECIAL_BYTEARRAY:
                        rv = data;
                        break;
                    default:
                        log.warn(String.format("Undecodeable with flags %x", flags));
                }
            } else {
                rv = decodeString(data);
            }
        }
        return rv;
    }

    public final CachedData encode(T o) {
        byte[] b = null;
        int flags = 0;
        if (o instanceof String) {
            b = encodeString((String) o);
        } else if (o instanceof Long) {
            if (this.primitiveAsString) {
                b = encodeString(o.toString());
            } else {
                b = this.transcoderUtils.encodeLong((Long) o);
            }
            flags |= SPECIAL_LONG;
        } else if (o instanceof Integer) {
            if (this.primitiveAsString) {
                b = encodeString(o.toString());
            } else {
                b = this.transcoderUtils.encodeInt((Integer) o);
            }
            flags |= SPECIAL_INT;
        } else if (o instanceof Boolean) {
            if (this.primitiveAsString) {
                b = encodeString(o.toString());
            } else {
                b = this.transcoderUtils.encodeBoolean((Boolean) o);
            }
            flags |= SPECIAL_BOOLEAN;
        } else if (o instanceof Date) {
            b = this.transcoderUtils.encodeLong(((Date) o).getTime());
            flags |= SPECIAL_DATE;
        } else if (o instanceof Byte) {
            if (this.primitiveAsString) {
                b = encodeString(o.toString());
            } else {
                b = this.transcoderUtils.encodeByte((Byte) o);
            }
            flags |= SPECIAL_BYTE;
        } else if (o instanceof Float) {
            if (this.primitiveAsString) {
                b = encodeString(o.toString());
            } else {
                b = this.transcoderUtils
                        .encodeInt(Float.floatToRawIntBits((Float) o));
            }
            flags |= SPECIAL_FLOAT;
        } else if (o instanceof Double) {
            if (this.primitiveAsString) {
                b = encodeString(o.toString());
            } else {
                b = this.transcoderUtils
                        .encodeLong(Double.doubleToRawLongBits((Double) o));
            }
            flags |= SPECIAL_DOUBLE;
        } else if (o instanceof byte[]) {
            b = (byte[]) o;
            flags |= SPECIAL_BYTEARRAY;
        } else {
            b = serialize(o);
            flags |= SERIALIZED;
        }
        assert b != null;
        if (this.primitiveAsString) {
            if ((flags & SERIALIZED) == 0) {
                flags = 0;
            }
        }
        if (b.length > this.compressionThreshold) {
            byte[] compressed = compress(b);
            if (compressed.length < b.length) {
                if (log.isDebugEnabled()) {
                    log.debug("Compressed " + o.getClass().getName() + " from "
                            + b.length + " to " + compressed.length);
                }
                b = compressed;
                flags |= COMPRESSED;
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Compression increased the size of "
                            + o.getClass().getName() + " from " + b.length
                            + " to " + compressed.length);
                }
            }
        }
        return new CachedData(flags, b, this.maxSize, -1);
    }


    protected byte[] serialize(Object o) {

        return kryoPool.run(k -> {
            try (Output output = new Output(new ByteArrayOutputStream())) {
                k.writeClassAndObject(output, o);
                output.flush();
                ByteArrayOutputStream bos = (ByteArrayOutputStream) output.getOutputStream();
                return bos.toByteArray();
            }
        });
    }

    protected T deserialize(byte[] in) {

        return kryoPool.run(k -> {
            try (Input input = new Input(new ByteArrayInputStream(in))) {
                return (T) k.readClassAndObject(input);
            }
        });
    }
}
