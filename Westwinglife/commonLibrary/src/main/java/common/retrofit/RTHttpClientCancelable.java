package common.retrofit;

import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.retrofit.converter.FallbackGsonConverter;
import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;

/**
 * A RetrofitHttpClient with cancel support, 通常情况下无需使用。
 * http://stackoverflow.com/questions/18131382/using-squares-retrofit-client-is-it-possible-to-cancel-an-in-progress-request
 * Created by beansoft on 15/5/4.
 */
public class RTHttpClientCancelable {
    protected static final MainThreadExecutor mainThreadExecutor = new MainThreadExecutor();
    protected ExecutorService mExecutorService;
    protected RestAdapter restAdapter;
    protected String baseURL;

    public RTHttpClientCancelable(String baseURL) {
        this.baseURL = baseURL;

    }

    public <T> T create(Class<T> service) {
        return restAdapter.create(service);
    }

    public void init() {
        mExecutorService = Executors.newCachedThreadPool();

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseURL)//"http://192.168.1.107:9090"
//                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new FallbackGsonConverter(new GsonBuilder().create(), "UTF-8"))
                .setExecutors(mExecutorService, mainThreadExecutor)
                .build();
    }

    /**
     * Only call this method after a call of stopAll(), normally you don't need to do so.
     */
    public void restart() {
        if(mExecutorService.isShutdown() || mExecutorService.isTerminated()) {
            init();
        }

    }

    public void stopAll(){
        List<Runnable> pendingAndOngoing = mExecutorService.shutdownNow();
        // probably await for termination.
    }
}