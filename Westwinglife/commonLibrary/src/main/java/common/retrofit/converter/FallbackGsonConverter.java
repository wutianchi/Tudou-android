package common.retrofit.converter;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Retro fit 1.9.0, add support return with String or bytes when JSON convert is failed.
 * Created by beansoft on 15/5/3.
 */
public class FallbackGsonConverter implements Converter {
    private final Gson gson;
    private String charset;

    /**
     * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public FallbackGsonConverter(Gson gson) {
        this(gson, "UTF-8");
    }

    /**
     * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use the specified charset.
     */
    public FallbackGsonConverter(Gson gson, String charset) {
        this.gson = gson;
        this.charset = charset;
    }

    @Override public Object fromBody(TypedInput body, Type type) throws ConversionException {
        String charset = this.charset;
        if (body.mimeType() != null) {
            charset = MimeUtil.parseCharset(body.mimeType(), charset);
        }
        InputStreamReader isr = null;
        String rawString = null;// row string
        byte[] bytes = null;// raw bytes
        try {
            bytes = TypedInputUtil.readReponse(body);
            rawString = new String(bytes, charset);
//            isr = new InputStreamReader(body.in(), charset);
            return gson.fromJson(rawString, type);
        } catch (IOException e) {
            throw new ConversionException(e);
        } catch (JsonParseException e) {
            // Try to back to string or bytes
            if(type.equals(  String.class) ) {
                try {
                    return rawString;
                } catch (Exception e1) {
                    throw new ConversionException(e1);
                }
            } else if(type.toString().equals("byte[]")) {
                return bytes;
            }
            throw new ConversionException(e);
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override public TypedOutput toBody(Object object) {
        try {
            return new JsonTypedOutput(gson.toJson(object).getBytes(charset), charset);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    private static class JsonTypedOutput implements TypedOutput {
        private final byte[] jsonBytes;
        private final String mimeType;

        JsonTypedOutput(byte[] jsonBytes, String encode) {
            this.jsonBytes = jsonBytes;
            this.mimeType = "application/json; charset=" + encode;
        }

        @Override public String fileName() {
            return null;
        }

        @Override public String mimeType() {
            return mimeType;
        }

        @Override public long length() {
            return jsonBytes.length;
        }

        @Override public void writeTo(OutputStream out) throws IOException {
            out.write(jsonBytes);
        }
    }
}
