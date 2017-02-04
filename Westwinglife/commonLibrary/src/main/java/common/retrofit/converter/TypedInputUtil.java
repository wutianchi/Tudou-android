package common.retrofit.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit.client.Response;
import retrofit.mime.TypedInput;

/**
 * Created by beansoft on 15/5/3.
 */
public class TypedInputUtil {
    public static String readReponse(TypedInput input, String charset) throws IOException {
        return new String(readReponse(input), charset);
    }

    public static byte[] readReponse(TypedInput input) throws IOException {
        int data = 0;

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        InputStream in = input.in();

        while ((data = in.read()) != -1) {
            bout.write(data);
        }

        in.close();
        return bout.toByteArray();
    }

    public static byte[] readReponse(Response response) throws IOException{
        return readReponse(response.getBody());
    }
}
